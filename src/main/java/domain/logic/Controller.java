package domain.logic;

import data.abstraction.Model;
import data.annotations.Bind;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

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
                    .forEach(field -> fillFieldWithData(field, data.get("YEARS").length, data));

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

                if (field.getName().equals("YEARS")) {
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
        return this;
    }

    public Controller runScript(String script) {
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
                .append(formatArrayValues(values))
                .append("\n")
        );

        return builder.toString();
    }

    private String getFormattedFieldData(Field field) {
        StringBuilder formatted = new StringBuilder(field.getName()).append("\t");

        try {
            if (field.getType() == double[].class) {
                formatted.append(formatArrayValues((double[]) field.get(model)));
            } else if (field.getType() == int[].class) {
                formatted.append(formatArrayValues((int[]) field.get(model)));
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        formatted.append("\n");
        return formatted.toString();
    }

    private String formatArrayValues(double[] array) {
        return Arrays.stream(array)
                .mapToObj(value -> String.format("%.2f", value).replace(",", "."))
                .reduce((v1, v2) -> v1 + "\t" + v2)
                .orElse("");
    }

    private String formatArrayValues(int[] array) {
        return Arrays.stream(array)
                .mapToObj(Integer::toString)
                .reduce((v1, v2) -> v1 + "\t" + v2)
                .orElse("");
    }
}
