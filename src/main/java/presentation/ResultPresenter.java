package presentation;

import data.contracts.ResultContract;

import java.util.Arrays;

public class ResultPresenter implements ResultContract.Presenter {
    private final ResultContract.View view;
    private final ResultContract.Model model;

    public ResultPresenter(ResultContract.View view, ResultContract.Model model) {
        this.view = view;
        this.model = model;
        initListeners();
    }
    @Override
    public void initListeners() {
        view.addRunScriptButtonListener(e -> {});
        view.addCreateScriptButtonListener(e -> {});
    }

    @Override
    public void updateResultTable(String tsvData) {
        String[][] parsedData = model.parseTSV(tsvData);

        if (parsedData.length > 0) {
            String[] columnHeaders = parsedData[0];
            String[][] tableRows = Arrays.copyOfRange(parsedData, 1, parsedData.length);

            view.displayTableData(columnHeaders, tableRows);
        }
    }
}
