package presentation;

import data.abstraction.DataUpdatedListener;
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
        view.setModelList(model.loadModelList("./src/main/java/domain/models"));
        view.setDataList(model.loadDataList("./src/main/resources/data"));
    }

    @Override
    public void initListeners() {
        view.addRunButtonListener(_ -> {
            try {
                if (view.getSelectedModel() != null && view.getSelectedData() != null) {
                    controller = new Controller(
                            "domain.models." + view.getSelectedModel().replace(".java", "")
                    );
                    controller.readDataFrom(
                            "./src/main/resources/data/" + view.getSelectedData()
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
