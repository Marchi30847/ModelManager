package ui;

import data.contracts.ResultContract;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;

public class ResultView extends JPanel implements ResultContract.View {
    private final JScrollPane tableScroll = new JScrollPane();
    private final JTable resultTable = new JTable();
    private final JPanel buttonPanel = new JPanel();
    private final JButton runScriptButton = new JButton();
    private final JButton createScriptButton = new JButton();

    public ResultView() {
        configure();
        configureTableScrollPane();
        configureResultTable();
        configureButtonPanel();
        configureRunScriptButton();
        configureCreateScriptButton();
        addAll();
    }

    private void configure() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    }

    private void configureTableScrollPane() {
        tableScroll.setViewportView(resultTable);
    }

    private void configureResultTable() {
        resultTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        resultTable.setDefaultEditor(Object.class, null);
        resultTable.getTableHeader().setReorderingAllowed(false);

        resultTable.setRowHeight(35);

        resultTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                if (row % 2 == 0) {
                    component.setBackground(new Color(240, 240, 240));
                } else {
                    component.setBackground(new Color(220, 220, 220));
                }

                if (isSelected) {
                    component.setBackground(table.getSelectionBackground());
                }

                return component;
            }
        });
    }

    private void configureButtonPanel() {
        buttonPanel.setLayout(new GridLayout(1, 2, 10, 0));
        buttonPanel.add(runScriptButton);
        buttonPanel.add(createScriptButton);
    }

    private void configureRunScriptButton() {
        runScriptButton.setText("Run script from file");
    }

    private void configureCreateScriptButton() {
        createScriptButton.setText("Create and run ad hoc script");
    }

    private void addAll() {
        add(tableScroll, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    @Override
    public void addRunScriptButtonListener(ActionListener actionListener) {
        runScriptButton.addActionListener(actionListener);
    }

    @Override
    public void addCreateScriptButtonListener(ActionListener actionListener) {
        createScriptButton.addActionListener(actionListener);
    }

    @Override
    public void displayTableData(String[] columns, String[][] data) {
        DefaultTableModel tableModel = new DefaultTableModel(data, columns);
        resultTable.setModel(tableModel);
    }
}