package data.contracts;

import data.abstraction.DataUpdatedListener;
import domain.logic.Controller;

import java.awt.event.ActionListener;
import java.util.List;

public interface SelectContract {
    interface View {
        void addRunButtonListener(ActionListener actionListener);
        void setModelList(List<String> modelList);
        void setDataList(List<String> dataList);
        String getSelectedModel();
        String getSelectedData();

    }

    interface Presenter {
        void initListeners();
        void setDataUpdatedListener(DataUpdatedListener dataUpdatedListener);
        Controller getController();
        void setController(Controller controller);
    }

    interface Model {
        List<String> loadModelList(String directoryPath);
        List<String> loadDataList(String directoryPath);
    }
}
