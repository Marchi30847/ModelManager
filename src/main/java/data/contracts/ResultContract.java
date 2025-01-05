package data.contracts;

import domain.logic.Controller;

import java.awt.event.ActionListener;

public interface ResultContract {
    interface View {
        void addRunScriptButtonListener(ActionListener actionListener);
        void addCreateScriptButtonListener(ActionListener actionListener);
        void displayTableData(String[] columns, String[][] data);
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
