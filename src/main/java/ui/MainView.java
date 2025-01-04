package ui;

import data.contracts.ResultContract;
import data.contracts.SelectContract;
import domain.logic.ResultModel;
import domain.logic.SelectModel;
import presentation.Coordinator;
import presentation.ResultPresenter;
import presentation.SelectPresenter;

import javax.swing.*;
import java.awt.*;

public class MainView {
    private final JFrame frame = new JFrame();
    private final JSplitPane splitPane = new JSplitPane();
    private final SelectView selectView = new SelectView();
    private final ResultView resultView = new ResultView();
    private Coordinator coordinator;

    public MainView() {
        configureFrame();
        configureSplitPane();
        createCoordinator();
        addAll();
    }

    private void configureFrame() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(new BorderLayout());
        frame.setTitle("Modeling Environment");
        frame.setVisible(true);
    }

    private void configureSplitPane() {
        splitPane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setLeftComponent(selectView);
        splitPane.setRightComponent(resultView);
        splitPane.setDividerLocation(250);
        splitPane.setResizeWeight(0.25);
    }

    private void createCoordinator() {
        SelectContract.Presenter selectPresenter = new SelectPresenter(
                selectView,
                new SelectModel()
        );
        ResultContract.Presenter resultPresenter = new ResultPresenter(
                resultView,
                new ResultModel()
        );
        coordinator = new Coordinator(selectPresenter, resultPresenter);
    }


    private void addAll() {
        frame.add(splitPane, BorderLayout.CENTER);
    }
}
