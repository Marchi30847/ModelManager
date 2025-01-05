package domain.logic;

import data.abstraction.Model;
import data.annotations.Bind;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;

public class Controller {
    private final Model model;
    private final Map<String, double[]> scriptVariables = new LinkedHashMap<>();

    public Controller(String modelName) {
        try {
            this.model = (Model) Class.forName(modelName).getConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    public Controller readDataFrom(String fName) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fName));
            Map<String, double[]> data = new HashMap<>();

            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();

                String[] parts = line.split("\\s+");
                String fieldName = parts[0];

                double[] values = Arrays.stream(parts)
                        .skip(1)
                        .mapToDouble(Double::parseDouble)
                        .toArray();

                data.put(fieldName, values);
            }

            Arrays.stream(model.getClass().getDeclaredFields())
                    .peek(field -> field.setAccessible(true))
                    .filter(field -> field.isAnnotationPresent(Bind.class))
                    .forEach(field -> fillFieldWithData(field, data.get("LATA").length, data));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return this;
    }

    private void fillFieldWithData(Field field, int period, Map<String, double[]> data) {
        try {
            if (field.getName().equals("LL")) {
                field.set(model, period);
                return;
            }

            double[] values = new double[period];
            if (!data.containsKey(field.getName()) || data.get(field.getName()).length == 0) {
                field.set(model, values);
            } else {
                System.arraycopy(
                        data.get(field.getName()), 0,
                        values, 0,
                        data.get(field.getName()).length
                );
                Arrays.fill(
                        values,
                        data.get(field.getName()).length, period,
                        data.get(field.getName())[data.get(field.getName()).length - 1]
                );

                if (field.getName().equals("LATA")) {
                    int[] intValues = Arrays.stream(values)
                            .mapToInt(value -> (int) value)
                            .toArray();
                    field.set(model, intValues);
                } else {
                    field.set(model, values);
                }
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public Controller runModel() {
        model.run();
        return this;
    }

    public Controller runScriptFromFile(String fName) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fName));
            StringBuilder script = new StringBuilder();

            String line;
            while ((line = reader.readLine()) != null) {
                script.append(line).append("\n");
            }
            runScript(script.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return this;
    }

    public Controller runScript(String script) {
        Binding binding = new Binding();

        List<String> boundFieldNames = Arrays.stream(model.getClass().getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(Bind.class))
                .peek(field -> field.setAccessible(true))
                .peek(field -> {
                    try {
                        binding.setVariable(field.getName(), field.get(model));
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                })
                .map(Field::getName)
                .toList();

        scriptVariables.forEach(binding::setVariable);

        GroovyShell shell = new GroovyShell(binding);
        shell.evaluate(script);

        Arrays.stream(binding.getVariables().entrySet().toArray())
                .map(entry -> (Map.Entry<String, Object>) entry)
                .filter(entry -> !(entry.getKey().length() < 2) && !(entry.getKey().matches("[a-z]")))
                .filter(entry -> !boundFieldNames.contains(entry.getKey()))
                .forEach(entry -> scriptVariables.put(entry.getKey(), (double[]) entry.getValue()));

        return this;
    }

    public String getResultsAsTsv() {
        StringBuilder builder = new StringBuilder();
        Arrays.stream(model.getClass().getDeclaredFields())
                .filter(field -> field.getType().isArray())
                .peek(field -> field.setAccessible(true))
                .map(this::getFormattedFieldData)
                .forEach(builder::append);

        scriptVariables.forEach((key, values) -> builder
                .append(key)
                .append("\t")
                .append(formatArrayValues(Arrays.stream(values)
                        .boxed()
                        .toArray(Double[]::new)
                ))
                .append("\n")
        );

        return builder.toString();
    }

    private String getFormattedFieldData(Field field) {
        StringBuilder formatted = new StringBuilder(field.getName()).append("\t");

        try {
            if (field.getType() == double[].class) {
                double[] values = (double[]) field.get(model);
                formatted.append(formatArrayValues(
                        Arrays.stream(values)
                                .boxed()
                                .toArray(Double[]::new)
                ));
            } else if (field.getType() == int[].class) {
                int[] values = (int[]) field.get(model);
                formatted.append(formatArrayValues(
                        Arrays.stream(values)
                                .boxed()
                                .toArray(Integer[]::new)
                ));
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        formatted.append("\n");
        return formatted.toString();
    }

    private <T> String formatArrayValues(T[] array) {
        return Arrays.stream(array)
                .map(T::toString)       //Object::toString
                .reduce((v1, v2) -> v1 + "\t" + v2)
                .orElse("");
    }
}
