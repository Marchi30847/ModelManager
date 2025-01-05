package presentation;

import data.constants.Paths;
import data.contracts.ResultContract;
import domain.logic.Controller;

import javax.swing.*;
import java.awt.*;
import java.io.File;
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
        view.addRunScriptButtonListener   (_ -> runScriptButtonAction());
        view.addCreateScriptButtonListener(_ -> createScriptButtonAction());
    }

    private void runScriptButtonAction() {
        if (controller != null) {
            JFileChooser chooser = new JFileChooser();
            chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            chooser.setDialogTitle("Choose a script to run");
            chooser.setCurrentDirectory(new File(Paths.PATH_TO_SCRIPTS));
            int result = chooser.showOpenDialog((Component) view);

            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = chooser.getSelectedFile();
                controller.runScriptFromFile(selectedFile.getAbsolutePath());
                updateResultTable(controller.getResultsAsTsv());
            }
        }
    }

    private void createScriptButtonAction() {
        if (controller != null) {
            JDialog dialog = new JDialog();
            dialog.setTitle("Create a new script");
            dialog.setModal(true);
            dialog.setSize(new Dimension(400, 200));
            dialog.setLayout(new BorderLayout());

            JTextArea textArea = new JTextArea();
            JScrollPane scrollPane = new JScrollPane(textArea);

            JButton runButton = new JButton("Run");
            runButton.addActionListener(_ -> {
                if (!textArea.getText().trim().isEmpty()) {
                    controller.runScript(textArea.getText());
                    updateResultTable(controller.getResultsAsTsv());
                    dialog.dispose();
                }
            });

            dialog.add(scrollPane, BorderLayout.CENTER);
            dialog.add(runButton, BorderLayout.SOUTH);

            dialog.setVisible(true);
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
