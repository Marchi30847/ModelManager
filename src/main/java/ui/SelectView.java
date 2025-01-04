package ui;

import data.contracts.SelectContract;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Vector;

public class SelectView extends JPanel implements SelectContract.View {
    private final JLabel        titleLabel  = new JLabel();
    private final JScrollPane   modelScroll = new JScrollPane();
    private final JScrollPane   dataScroll  = new JScrollPane();
    private final JList<String> modelList   = new JList<>();
    private final JList<String> dataList    = new JList<>();
    private final JButton       runButton   = new JButton();

    public SelectView() {
        configure();
        configureTitleLabel();
        configureModelScrollPane();
        configureDataScrollPane();
        configureModelList();
        configureDataList();
        configureRunButton();
        addAll();
    }

    private void configure() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    }

    private void configureTitleLabel() {
        titleLabel.setText("Select model and data");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
    }

    private void configureModelScrollPane() {
        modelScroll.setViewportView(modelList);
    }

    private void configureDataScrollPane() {
        dataScroll.setViewportView(dataList);
    }

    private void configureModelList() {
        modelList.setModel(new DefaultListModel<>());
        modelList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    private void configureDataList() {
        dataList.setModel(new DefaultListModel<>());
        dataList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    private void configureRunButton() {
        runButton.setText("Run model");
    }

    private void addAll() {
        add(titleLabel, BorderLayout.NORTH);
        add(modelScroll, BorderLayout.WEST);
        add(dataScroll, BorderLayout.EAST);
        add(runButton, BorderLayout.SOUTH);
    }

    @Override
    public void addRunButtonListener(ActionListener actionListener) {
        runButton.addActionListener(actionListener);
    }

    @Override
    public void setModelList(List<String> modelList) {
        this.modelList.setListData(new Vector<>(modelList));
    }

    @Override
    public void setDataList(List<String> dataList) {
        this.dataList.setListData(new Vector<>(dataList));
    }

    @Override
    public String getSelectedModel() {
        return modelList.getSelectedValue();
    }

    @Override
    public String getSelectedData() {
        return dataList.getSelectedValue();
    }
}
