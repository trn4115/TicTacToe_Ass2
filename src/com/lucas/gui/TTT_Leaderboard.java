/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lucas.gui;

/**
 * Student Name:    Lucas Hashemi 
 * Student ID:      14885318 
 * Assignment:      PDC/SC_Assignment_2
 * Group:           40
 *
 * @author lucas
 */
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;

/**
 * TTT_Leaderboard class represents a graphical user interface for displaying a
 * leaderboard.
 */
public class TTT_Leaderboard extends JFrame {

    // Constants for screen dimensions
    private static final int SCREEN_WIDTH = 400;
    private static final int SCREEN_HEIGHT = 500;

    // Constants for fonts
    private static final Font CELL_FONT = new Font("Dialog", Font.BOLD, 10);
    private static final Font HEADER_FONT = new Font("Dialog", Font.BOLD, 12);
    private static final Font DELETE_BUTTON_FONT = new Font("Dialog", Font.BOLD, 10);

    private static final Color BACKGROUND_COLOR = Color.decode("#011627");

    // Instance variables
    private TTT_Database dbManager = TTT_Database.getInstance();
    private JTable leaderboardTable;
    private JScrollPane scrollPane;

    // Constructor
    public TTT_Leaderboard() {
        initializeUI();
    }

    /**
     * Initializes the user interface for the leaderboard.
     */
    private void initializeUI() {
        setTitle("Leaderboard"); // Set the window title to "Leaderboard"
        setSize(SCREEN_WIDTH, SCREEN_HEIGHT); // Set the window size
        setLocationRelativeTo(null); // Center the window on the screen

        String[] columnNames = {"Rank", "Player Name", "Score", "Delete"}; // Define table column names
        Object[][] data = getLeaderboardData(); // Get the data for the leaderboard

        // Create a table model with specified data and column names
        DefaultTableModel tableModel = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 3; // Make cells in column 3 (Delete) editable
            }
        };

        leaderboardTable = new JTable(tableModel); // Create a JTable with the table model
        customizeTableAppearance(); // Customize the appearance of the table
        addTableButtonColumn(); // Add button column for Delete
        customizeTableHeader(); // Customize the appearance of the table header
        centerAlignTextInCells(); // Center align text in table cells
        configureScrollPane(); // Configure the scroll pane

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);  // Set the default close operation
    }

    /**
     * Customizes the appearance of the JTable, including row height and font.
     */
    private void customizeTableAppearance() {
        leaderboardTable.setRowHeight(20); // Set the row height of the table
        leaderboardTable.setFont(CELL_FONT); // Set the font for table cells
    }

    /**
     * Adds a button column to the JTable for deleting entries.
     */
    private void addTableButtonColumn() {
        leaderboardTable.getColumnModel().getColumn(3).setCellRenderer(new ButtonRenderer()); // Set button renderer for column 3
        leaderboardTable.getColumnModel().getColumn(3).setCellEditor(new ButtonEditor(new JCheckBox())); // Set button editor for column 3
    }

    /**
     * Customizes the appearance of the table header, including font and
     * behavior.
     */
    private void customizeTableHeader() {
        JTableHeader header = leaderboardTable.getTableHeader(); // Get the table header
        header.setFont(HEADER_FONT); // Set the font for the table header
        header.setReorderingAllowed(false); // Disable column reordering
        header.setResizingAllowed(false);  // Disable column resizing
    }

    /**
     * Configures a JScrollPane to contain the JTable and sets its background
     * color.
     */
    private void configureScrollPane() {
        scrollPane = new JScrollPane(leaderboardTable); // Create a scroll pane containing the leaderboard table
        scrollPane.getViewport().setBackground(BACKGROUND_COLOR);  // Set the background color of the viewport
        add(scrollPane);  // Add the scroll pane to the UI
    }

    /**
     * Retrieves leaderboard data from the database.
     *
     * @return A two-dimensional array containing the leaderboard data.
     */
    private Object[][] getLeaderboardData() {
        try {
            Connection conn = dbManager.getConnection(); // Get a database connection using a Singleton instance

            String selectSQL = "SELECT PLAYER_NAME, SCORE FROM LEADERBOARD ORDER BY SCORE DESC";

            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(selectSQL);

            ArrayList<Object[]> dataList = new ArrayList<>();

            int rank = 1;
            while (resultSet.next()) {
                String playerName = resultSet.getString("PLAYER_NAME");
                int score = resultSet.getInt("SCORE");
                Object[] row = {rank, playerName, score, "Delete"}; // Create a row with rank, playerName, score, and "Delete"
                dataList.add(row);
                rank++;
            }

            Object[][] data = new Object[dataList.size()][];
            for (int i = 0; i < dataList.size(); i++) {
                data[i] = dataList.get(i);
            }

            return data;
        } catch (SQLException ex) {
            System.err.println("Error: " + ex.getMessage()); // Print an error message if there's an SQL exception
            return new Object[0][0];  // Return an empty 2D array in case of an error
        }
    }

    public void showLeaderboard() {
        SwingUtilities.invokeLater(() -> setVisible(true)); // Show the leaderboard UI on the Swing event dispatch thread
    }

    /**
     * Centers the text in the table cells, except for the "Delete" column.
     */
    private void centerAlignTextInCells() {
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER); // Set horizontal alignment to center

        // Loop through the columns and set the renderer for each column
        for (int i = 0; i < leaderboardTable.getColumnCount(); i++) {
            if (i != 3) { // Skip the "Delete" column (column 3)
                leaderboardTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
            }
        }
    }

    // Inner class for rendering the "Delete" button in the table
    class ButtonRenderer extends JButton implements TableCellRenderer {

        /**
         * Constructor for the ButtonRenderer class. Sets the button to be
         * opaque.
         */
        public ButtonRenderer() {
            setOpaque(true);
            setFont(DELETE_BUTTON_FONT);
        }

        /**
         * Renders the "Delete" button component in a table cell.
         *
         * @param table The JTable in which the cell is rendered.
         * @param value The value of the cell (typically, the button label).
         * @param isSelected Indicates if the cell is currently selected.
         * @param hasFocus Indicates if the cell has focus.
         * @param row The row index of the cell.
         * @param column The column index of the cell.
         * @return The rendered button component.
         */
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            setText((value == null) ? "Delete" : value.toString());
            setForeground(Color.BLACK);
            return this;
        }
    }

// Inner class for handling button clicks in the table
    class ButtonEditor extends DefaultCellEditor {

        private JButton button;
        private String label;
        private boolean isPushed;
        private int selectedRow;

        /**
         * Constructor for the ButtonEditor class.
         *
         * @param checkBox A JCheckBox component to be used as the editor.
         */
        public ButtonEditor(JCheckBox checkBox) {
            super(checkBox);
            button = new JButton();
            button.setOpaque(true);
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    fireEditingStopped();
                    deleteRow(selectedRow);
                }
            });
        }

        /**
         * Gets the table cell editor component.
         *
         * @param table The JTable in which the cell is edited.
         * @param value The value of the cell (typically, the button label).
         * @param isSelected Indicates if the cell is currently selected.
         * @param row The row index of the cell.
         * @param column The column index of the cell.
         * @return The editor component, which is a button.
         */
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            selectedRow = row;
            label = (value == null) ? "Delete" : value.toString();
            isPushed = true;
            return button;
        }

        /**
         * Gets the value of the cell editor.
         *
         * @return The value of the cell editor, typically the button label.
         */
        @Override
        public Object getCellEditorValue() {
            return label;
        }

        /**
         * Deletes a row in the table based on the selected row index.
         *
         * @param row The index of the row to be deleted.
         */
        private void deleteRow(int row) {
            // Retrieve the player name from the table and delete the player's data.
            String playerName = (String) leaderboardTable.getValueAt(row, 1); // Assuming "Player Name" is in the second column (index 1)
            dbManager.deletePlayer(playerName); // Use the Singleton instance

            // Remove the deleted row from the table's model.
            DefaultTableModel model = (DefaultTableModel) leaderboardTable.getModel();
            model.removeRow(row);
        }
    }
}