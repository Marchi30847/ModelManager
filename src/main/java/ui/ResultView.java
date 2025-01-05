package ui;

import data.constants.Paths;
import data.contracts.ResultContract;
import data.enums.Fonts;
import data.enums.Palette;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.function.Consumer;

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

        resultTable.getTableHeader().setBackground(Palette.HEADER_TABLE_ROW.getColor());
        Fonts.applyToComponent(
                resultTable.getTableHeader(),
                Fonts.BODY_FONT
        );

        resultTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                if (row % 2 == 0) {
                    component.setBackground(Palette.EVEN_TABLE_ROW.getColor());
                } else {
                    component.setBackground(Palette.ODD_TABLE_ROW.getColor());
                }

                Fonts.applyToComponent(
                        component,
                        Fonts.BODY_FONT
                );

                if (column > 0) {
                    ((JLabel) component).setHorizontalAlignment(SwingConstants.RIGHT);
                }


                if (isSelected) {
                    component.setBackground(Palette.SELECTED.getColor());
                    Fonts.applyToComponent(
                            component,
                            Fonts.SELECTED_BODY_FONT
                    );
                }

                return component;
            }
        });
    }

    private void configureButtonPanel() {
        buttonPanel.setLayout(new GridLayout(1, 2, 25, 0));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(25, 100, 25, 100));
        buttonPanel.setPreferredSize(new Dimension(
                buttonPanel.getPreferredSize().width,
                100
        ));

        buttonPanel.add(runScriptButton);
        buttonPanel.add(createScriptButton);
    }

    private void configureRunScriptButton() {
        runScriptButton.setText("Run script from file");
        Fonts.applyToComponent(
                runScriptButton,
                Fonts.BUTTON_FONT
        );
    }

    private void configureCreateScriptButton() {
        createScriptButton.setText("Create and run ad hoc script");
        Fonts.applyToComponent(
                createScriptButton,
                Fonts.BUTTON_FONT
        );
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

    @Override
    public void createFileChooser(Consumer<File> consumer) {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        chooser.setDialogTitle("Choose a script to run");
        chooser.setCurrentDirectory(new File(Paths.PATH_TO_SCRIPTS));
        int result = chooser.showOpenDialog(null);

        if (result == JFileChooser.APPROVE_OPTION) {
            consumer.accept(chooser.getSelectedFile());
        }
    }

    @Override
    public void createDialogWindow(Consumer<String> onResult) {
        JDialog dialog = new JDialog();
        dialog.setTitle("Create a new script");
        dialog.setModal(true);
        dialog.setSize(new Dimension(400, 200));
        dialog.setLayout(new BorderLayout());

        JTextArea textArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(textArea);

        JButton runButton = new JButton("Run script");
        runButton.addActionListener(_ -> {
            if (!textArea.getText().trim().isEmpty()) {
                String script = textArea.getText();
                dialog.dispose();
                onResult.accept(script);
            }
        });

        dialog.add(scrollPane, BorderLayout.CENTER);
        dialog.add(runButton, BorderLayout.SOUTH);

        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }

    @Override
    public void createErrorWindow(String message) {
        JOptionPane optionPane = new JOptionPane(message, JOptionPane.ERROR_MESSAGE);
        JDialog dialog = optionPane.createDialog(this, "Incorrect script syntax");
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }
}