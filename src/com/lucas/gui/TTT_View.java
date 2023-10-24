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
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

/**
 * The View class is responsible for managing the graphical user interface (GUI)
 * and displaying the gameFrame state based on information from the model.
 */
public class TTT_View {

    // Constants for screen dimensions
    private static final int SCREEN_WIDTH = 400;
    private static final int SCREEN_HEIGHT = 500;

    // Constants for colors
    private static final Color BACKGROUND_COLOR = Color.decode("#011627");
    private static final Color WIN_COLOR = Color.GREEN;
    private static final Color DRAW_COLOR = Color.ORANGE;

    // Constants for fonts
    private static final Font LABEL_FONT = new Font("Dialog", Font.BOLD, 14);
    private static final Font BUTTON_FONT = new Font("Dialog", Font.PLAIN, 100);

    // Controller Adapter for handling user interactions
    private TTT_UIHandler uiHandler;

    // Graphical User Interface components
    private JFrame gameFrame; // The main gameFrame window
    private JButton[][] tiles;// Buttons for the gameFrame grid
    private JButton reset; // Button to reset the gameFrame
    private JButton menu; // Button to access the main menu

    // Labels for displaying player scores and turn information
    private JLabel player1ScoreLabel; // Label for Player 1's score
    private JLabel player2ScoreLabel; // Label for Player 2's score
    private JLabel turnLabel; // Label for displaying the current player's turn

    // Panels
    private JPanel turnPanel;
    private JPanel scorePanel;
    private JPanel gamePanel;
    private JPanel options;
    private JPanel messages;

    /**
     * Constructor to initialize the Tic Tac Toe view.
     */
    public TTT_View() {
        try {
            this.gameFrame = new JFrame("Tic Tac Toe");
            this.tiles = new JButton[3][3];
            this.reset = new JButton("Reset");
            this.menu = new JButton("Menu"); // Create a "Menu" button

            player1ScoreLabel = new JLabel("X: 0 |");
            player2ScoreLabel = new JLabel("O: 0");

            initialize();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "An error occurred during initialization: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Set the action listener for the controller to handle user interactions.
     *
     * @param controller The controller that handles user input.
     */
    public void setActionListener(TTT_Controller controller) {
        this.uiHandler = new TTT_UIHandler(controller, this);
        for (int row = 0; row < 3; row++) {
            for (int column = 0; column < 3; column++) {
                getTiles()[row][column].addActionListener(uiHandler);
            }
        }
        reset.addActionListener(uiHandler);
        menu.addActionListener(uiHandler); // Attach the action listener to the "Menu" button
    }

    /**
     * Initialize the graphical user interface (GUI) components and layout.
     */
    public void initialize() {
        try {
            setupGameFrame();
            setupGamePanels();
            setupGameGrid();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "An error occurred during initialization: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Sets up the game frame with specific attributes.
     */
    private void setupGameFrame() {
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameFrame.setSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        gameFrame.setResizable(false);
        gameFrame.setLocationRelativeTo(null);
    }

    /**
     * Sets up the various game panels and their components.
     */
    private void setupGamePanels() {
        turnPanel = createTurnPanel();
        scorePanel = createScorePanel();
        gamePanel = createGamePanel();
        options = createOptionsPanel();
        messages = createMessagesPanel(scorePanel, turnPanel);

        gameFrame.add(gamePanel, BorderLayout.CENTER);
        gameFrame.add(options, BorderLayout.SOUTH);
        gameFrame.add(messages, BorderLayout.NORTH);
    }

    /**
     * Creates the turn panel and sets its appearance.
     *
     * @return The turn panel with the specified attributes.
     */
    private JPanel createTurnPanel() {
        turnPanel = new JPanel(new FlowLayout());
        setTurnLabel(new JLabel("Player 1 to play 'X'    |   Player 2 to play 'O' "));
        getTurnLabel().setFont(LABEL_FONT);
        turnPanel.add(getTurnLabel());
        turnPanel.setBackground(BACKGROUND_COLOR);
        getTurnLabel().setForeground(Color.WHITE);
        return turnPanel;
    }

    /**
     * Creates the score panel and sets its appearance.
     *
     * @return The score panel with the specified attributes.
     */
    private JPanel createScorePanel() {
        scorePanel = new JPanel(new FlowLayout());

        player1ScoreLabel = new JLabel("X: 0 |");
        player1ScoreLabel.setFont(LABEL_FONT); // Set the font for Player 1's score label

        player2ScoreLabel = new JLabel("O: 0");
        player2ScoreLabel.setFont(LABEL_FONT); // Set the font for Player 2's score label

        scorePanel.add(player1ScoreLabel);
        scorePanel.add(player2ScoreLabel);

        return scorePanel;
    }

    /**
     * Creates the game panel with a 3x3 grid layout.
     *
     * @return The game panel with a grid layout.
     */
    private JPanel createGamePanel() {
        gamePanel = new JPanel(new GridLayout(3, 3)); // Use GridLayout with 3 rows and 3 columns
        gamePanel.setBackground(BACKGROUND_COLOR);
        return gamePanel;
    }

    /**
     * Creates the options panel for game options.
     *
     * @return The options panel with specified options and appearance.
     */
    private JPanel createOptionsPanel() {
        options = new JPanel(new FlowLayout());
        options.setBackground(BACKGROUND_COLOR);
        options.add(reset);
        options.add(menu);
        return options;
    }

    /**
     * Creates the messages panel which includes the score and turn panels.
     *
     * @param scorePanel The panel displaying the scores.
     * @param turnPanel The panel displaying the current player's turn.
     * @return The messages panel with the specified components.
     */
    private JPanel createMessagesPanel(JPanel scorePanel, JPanel turnPanel) {
        messages = new JPanel(new BorderLayout());
        messages.setBackground(Color.WHITE);
        messages.add(scorePanel, BorderLayout.NORTH);
        messages.add(turnPanel, BorderLayout.SOUTH);
        return messages;
    }

    /**
     * Sets up the game grid by creating buttons for each cell in a 3x3 grid
     * layout. It also sets their appearance and adds them to the game panel.
     */
    private void setupGameGrid() {
        for (int row = 0; row < 3; row++) {
            for (int column = 0; column < 3; column++) {
                getTiles()[row][column] = new JButton();
                getTiles()[row][column].setBackground(BACKGROUND_COLOR);
                getTiles()[row][column].setFont(BUTTON_FONT);
                getTiles()[row][column].setText("");

                gamePanel.add(getTiles()[row][column]); // Use the class-level gamePanel
            }
        }
        gameFrame.setVisible(true);
    }

    /**
     * Update the displayed scores on the GUI.
     *
     * @param scores An array containing the scores of both players.
     */
    public void updateScores(int[] scores) {
        player1ScoreLabel.setText("X: " + scores[0] + " |");
        player2ScoreLabel.setText("O: " + scores[1]);
    }

    /**
     * Check if the "Reset" button was pressed.
     *
     * @param e The ActionEvent generated by user interaction.
     * @return True if the "Reset" button was pressed, otherwise false.
     */
    public boolean isReset(ActionEvent e) {
        if (e.getSource() == reset) {
            return true;
        }
        return false;
    }

    /**
     * Get the row and column of the button clicked by the user.
     *
     * @param e The ActionEvent generated by the user clicking a button.
     * @return An ArrayList containing the row and column indices.
     */
    public ArrayList<Integer> getTileClicked(ActionEvent e) {
        ArrayList<Integer> position = new ArrayList<>();
        for (int row = 0; row < 3; row++) {
            for (int column = 0; column < 3; column++) {
                if (e.getSource() == getTiles()[row][column]) {
                    position.add(row);
                    position.add(column);
                    return position; // Return the position when found
                }
            }
        }
        return position; // Return an empty ArrayList if the source is not found.
    }

    /**
     * Update the displayed player's turn on the GUI.
     *
     * @param turnText The text representing the current player's turn.
     */
    private void updatePlayerTurn(String turnText) {
        getTurnLabel().setText(turnText);
    }

    /**
     * Update the GUI to reflect the current state of the gameFrame.
     *
     * @param row The row of the button clicked.
     * @param column The column of the button clicked.
     * @param symbol The symbol (X or O) to display in the clicked button.
     * @param message The message to display indicating the next player's turn.
     */
    public void update(int row, int column, char symbol, String message) {
        getTiles()[row][column].setText(Character.toString(symbol));
        getTiles()[row][column].setEnabled(false);
        updatePlayerTurn(message);
    }

    /**
     * Display the winning player's symbol and message, and disable further
     * interactions.
     *
     * @param row The row of the button clicked that led to the win.
     * @param column The column of the button clicked that led to the win.
     * @param symbol The winning player's symbol (X or O).
     * @param message The winning message to display.
     */
    public void checkWnCondition(int row, int column, char symbol, String message) {
        getTiles()[row][column].setText(Character.toString(symbol));
        getTiles()[row][column].setEnabled(false);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                getTiles()[i][j].setEnabled(false);
            }
        }
        updatePlayerTurn(message);
    }

    /**
     * Highlight the winning tiles on the GUI using a different color.
     *
     * @param winningTiles An array of tile indices representing the winning
     * combination.
     */
    public void highlightWinningTiles(int[] winningTiles) {
        for (int tile : winningTiles) {
            int i = tile / 3;
            int j = tile % 3;
            getTiles()[i][j].setBackground(WIN_COLOR);
        }
    }

    /**
     * Highlight all tiles in the case of a draw (no winner).
     */
    public void highlightDrawTiles() {
        for (int row = 0; row < 3; row++) {
            for (int column = 0; column < 3; column++) {
                getTiles()[row][column].setBackground(DRAW_COLOR);
            }
        }
    }

    /**
     * Reset the gameFrame board to its initial state for a new round.
     */
    public void resetGame() {
        for (int row = 0; row < 3; row++) {
            for (int column = 0; column < 3; column++) {
                getTiles()[row][column].setText("");
                getTiles()[row][column].setEnabled(true);
                getTiles()[row][column].setBackground(BACKGROUND_COLOR);
                getTiles()[row][column].setFont(BUTTON_FONT);
            }
        }
        getTurnLabel().setText("Player 1 to play 'X'    |   Player 2 to play 'O' ");
    }

    /**
     * Get the text (symbol) displayed on a specific button.
     *
     * @param i The row index of the button.
     * @param j The column index of the button.
     * @return The text (symbol) displayed on the button.
     */
    public String getTileText(int i, int j) {
        return getTiles()[i][j].getText();
    }

    /**
     * Close the gameFrame GUI and switch to the main menu.
     */
    public void switchToMainMenu() {
        gameFrame.dispose(); // Close the gameFrame GUI
        TTT_Menu.getInstance().showMainMenu(); // Show the main menu
    }

    /**
     * Get the "Menu" button for controller interaction.
     *
     * @return The "Menu" button.
     */
    public JButton getMenuButton() {
        return menu;
    }

    /**
     * Get the array of button tiles.
     *
     * @return The array of button tiles.
     */
    public JButton[][] getTiles() {
        return tiles;
    }

    /**
     * @param tiles the tiles to set
     */
    public void setTiles(JButton[][] tiles) {
        this.tiles = tiles;
    }

    /**
     * @return the turnLabel
     */
    public JLabel getTurnLabel() {
        return turnLabel;
    }

    /**
     * @param turnLabel the turnLabel to set
     */
    public void setTurnLabel(JLabel turnLabel) {
        this.turnLabel = turnLabel;
    }
}