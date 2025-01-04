package domain.logic;

import data.contracts.ResultContract;

import java.util.Arrays;

public class ResultModel implements ResultContract.Model {
    @Override
    public String[][] parseTSV(String tsvData) {
        return Arrays.stream(tsvData.split("\n"))
                .map(row -> row.split("\t"))
                .toArray(String[][]::new);
    }
}
