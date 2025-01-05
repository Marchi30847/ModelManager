package ui;

import data.contracts.SelectContract;
import data.enums.Fonts;
import data.enums.Palette;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Vector;

public class SelectView extends JPanel implements SelectContract.View {
    private final JLabel        titleLabel   = new JLabel();
    private final JPanel        contentPanel = new JPanel();
    private final JScrollPane   modelScroll  = new JScrollPane();
    private final JScrollPane   dataScroll   = new JScrollPane();
    private final JList<String> modelList    = new JList<>();
    private final JList<String> dataList     = new JList<>();
    private final JPanel        buttonPanel  = new JPanel();
    private final JButton       runButton    = new JButton();

    public SelectView() {
        configure();
        configureTitleLabel();
        configureContentPanel();
        configureModelScrollPane();
        configureDataScrollPane();
        configureModelList();
        configureDataList();
        configureButtonPanel();
        configureRunButton();
        addAll();
    }

    private void configure() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    }

    private void configureTitleLabel() {
        titleLabel.setText("Select model and data");
        titleLabel.setHorizontalAlignment(SwingConstants.LEFT);
        titleLabel.setVerticalAlignment(SwingConstants.CENTER);
        Fonts.applyToComponent(
                titleLabel,
                Fonts.HEADER_FONT
        );
    }

    private void configureContentPanel() {
        contentPanel.setLayout(new GridLayout(1, 2, 10, 0));
        contentPanel.add(modelScroll);
        contentPanel.add(dataScroll);
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
        modelList.setSelectionBackground(Palette.SELECTED.getColor());
        Fonts.applyToComponent(
                modelList,
                Fonts.BODY_FONT
        );
    }

    private void configureDataList() {
        dataList.setModel(new DefaultListModel<>());
        dataList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        dataList.setSelectionBackground(Palette.SELECTED.getColor());
        Fonts.applyToComponent(
                dataList,
                Fonts.BODY_FONT
        );
    }

    private void configureButtonPanel() {
        buttonPanel.setLayout(new GridLayout(1, 1, 0,0));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));
        buttonPanel.setPreferredSize(new Dimension(
                buttonPanel.getPreferredSize().width,
                100
        ));

        buttonPanel.add(runButton);
    }

    private void configureRunButton() {
        runButton.setText("Run model");
        Fonts.applyToComponent(
                runButton,
                Fonts.BUTTON_FONT
        );
    }

    private void addAll() {
        add(titleLabel, BorderLayout.NORTH);
        add(contentPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
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