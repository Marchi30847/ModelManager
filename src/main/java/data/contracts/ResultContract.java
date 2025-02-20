package data.contracts;

import domain.logic.Controller;

import java.awt.event.ActionListener;
import java.io.File;
import java.util.function.Consumer;

public interface ResultContract {
    interface View {
        void addRunScriptButtonListener(ActionListener actionListener);
        void addCreateScriptButtonListener(ActionListener actionListener);
        void displayTableData(String[] columns, String[][] data);
        void createFileChooser(Consumer<File> onResult);
        void createDialogWindow(Consumer<String> onResult);
        void createErrorWindow(String message);
    }

    interface Presenter {
        void initListeners();
        void updateResultTable(String tsv);
        Controller getController();
        void setController(Controller controller);
    }

    interface Model {
        String[][] parseTSV(String tsvData);
    }
}
