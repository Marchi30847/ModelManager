package presentation;

import data.abstraction.DataUpdatedListener;
import data.constants.Paths;
import data.contracts.SelectContract;
import domain.logic.Controller;

public class SelectPresenter implements SelectContract.Presenter {
    private final SelectContract.View view;
    private final SelectContract.Model model;
    private Controller controller;
    private DataUpdatedListener dataUpdatedListener;

    public SelectPresenter(SelectContract.View view, SelectContract.Model model) {
        this.view = view;
        this.model = model;
        initListeners();
        view.setModelList(model.loadModelList(Paths.PATH_TO_MODELS));
        view.setDataList(model.loadDataList(Paths.PATH_TO_RESOURCES_DATA));
    }

    @Override
    public void initListeners() {
        view.addRunButtonListener(_ -> {
            try {
                if (view.getSelectedModel() != null && view.getSelectedData() != null) {
                    controller = new Controller(
                            Paths.MODELS_DIRECTORY + "." + view.getSelectedModel().replace(".java", "")
                    );
                    controller.readDataFrom(
                            Paths.PATH_TO_RESOURCES_DATA + "/" + view.getSelectedData()
                    );
                    controller.runModel();
                    dataUpdatedListener.updateData();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void setDataUpdatedListener(DataUpdatedListener dataUpdatedListener) {
        this.dataUpdatedListener = dataUpdatedListener;
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
