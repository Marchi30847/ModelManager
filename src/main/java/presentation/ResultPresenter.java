package presentation;

import data.contracts.ResultContract;
import domain.logic.Controller;

import java.util.Arrays;

public class ResultPresenter implements ResultContract.Presenter {
    private final ResultContract.View view;
    private final ResultContract.Model model;
    private Controller controller;

    public ResultPresenter(ResultContract.View view, ResultContract.Model model) {
        this.view = view;
        this.model = model;
        initListeners();
    }

    @Override
    public void initListeners() {
        view.addRunScriptButtonListener(_ -> runScriptButtonAction());
        view.addCreateScriptButtonListener(_ -> createScriptButtonAction());
    }

    private void runScriptButtonAction() {
        if (controller != null) {
            view.createFileChooser(selectedFile -> {
                controller.runScriptFromFile(selectedFile.getAbsolutePath());
                updateResultTable(controller.getResultsAsTsv());
            });
        }
    }

    private void createScriptButtonAction() {
        if (controller != null) {
            view.createDialogWindow(script -> {
                controller.runScript(script);
                updateResultTable(controller.getResultsAsTsv());
            });
        }
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

    @Override
    public Controller getController() {
        return controller;
    }

    @Override
    public void setController(Controller controller) {
        this.controller = controller;
    }
}
