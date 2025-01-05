package domain.logic;

import data.contracts.ResultContract;

import java.text.DecimalFormat;
import java.util.Arrays;

public class ResultModel implements ResultContract.Model {
    @Override
    public String[][] parseTSV(String tsvData) {
        String[] rows = tsvData.split("\n");

        String[] header = rows[0].split("\t");

        String[][] data = Arrays.stream(rows)
                .skip(1)
                .map(row -> row.split("\t"))
                .map(row -> Arrays.stream(row)
                        .map(this::formatValue)
                        .toArray(String[]::new)
                )
                .toArray(String[][]::new);

        String[][] result = new String[data.length + 1][];
        result[0] = header;
        System.arraycopy(data, 0, result, 1, data.length);

        return result;
    }


    private String formatValue(String value) {
        try {
            double number = Double.parseDouble(value);

            DecimalFormat formatter = new DecimalFormat("#,###.##");
            return formatter.format(number).replace(",", ".");
        } catch (NumberFormatException e) {
            return value;
        }
    }
}
