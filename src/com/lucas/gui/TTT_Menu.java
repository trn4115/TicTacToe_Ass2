/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lucas.gui;

/**
 * Student Name:    Lucas Hashemi 
 * Student ID:      14885318 
 * Assignment:      PDC/SC_Assignment_2
 * Group: 40
 *
 * @author lucas
 */
import javax.swing.*;
import java.awt.*;

/**
 * The `TTT_Menu` class represents the main menu of the Tic Tac Toe game.
 */
public class TTT_Menu {

    // Constants for screen dimensions
    private static final int SCREEN_WIDTH = 400;
    private static final int SCREEN_HEIGHT = 500;

    // Constants for colors
    private static final Color BACKGROUND_COLOR = Color.decode("#011627");
    private static final Color TITLE_TEXT_COLOR = Color.WHITE;

    // Constants for fonts
    private static final Font TITLE_FONT = new Font("Dialog", Font.BOLD, 40);
    private static final Font BUTTON_FONT = new Font("Dialog", Font.BOLD, 20);

    // The main JFrame for the Tic Tac Toe main menu.
    private JFrame frame;

    private static TTT_Menu instance; // Singleton instance of TTT_Menu

    /**
     * Constructs the Tic Tac Toe main menu.
     */
    public TTT_Menu() {
        createFrame();
        createTitlePanel();
        createButtonPanel();
        frame.setVisible(true);
    }

    private void createFrame() {
        frame = new JFrame("Tic Tac Toe - Main Menu");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());
    }

    private void createTitlePanel() {
        JLabel titleLabel = new JLabel("Tic Tac Toe");
        titleLabel.setFont(TITLE_FONT);
        titleLabel.setForeground(TITLE_TEXT_COLOR);

        JPanel titlePanel = new JPanel(new GridBagLayout());
        titlePanel.setBackground(BACKGROUND_COLOR);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(150, 0, 150, 0);
        titlePanel.add(titleLabel, gbc);

        frame.add(titlePanel, BorderLayout.NORTH);
    }

    private void createButtonPanel() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(3, 1));

        JButton newGameButton = createButton("New Game", this::startNewGame);
        newGameButton.setFont(BUTTON_FONT);  // Set the font for the "New Game" button

        JButton leaderboardButton = createButton("Leaderboard", this::openLeaderboard);
        leaderboardButton.setFont(BUTTON_FONT);  // Set the font for the "Leaderboard" button

        JButton exitButton = createButton("Exit", () -> System.exit(0));
        exitButton.setFont(BUTTON_FONT);  // Set the font for the "Exit" button

        buttonPanel.add(newGameButton);
        buttonPanel.add(leaderboardButton);
        buttonPanel.add(exitButton);

        frame.add(buttonPanel, BorderLayout.CENTER);
    }

    private JButton createButton(String label, Runnable action) {
        JButton button = new JButton(label);
        button.addActionListener(e -> action.run());
        return button;
    }

    /**
     * Get the singleton instance of TTT_Menu.
     *
     * @return The TTT_Menu instance.
     */
    public static TTT_Menu getInstance() {
        if (instance == null) {
            instance = new TTT_Menu();
        }
        return instance;
    }

    /**
     * Show the main menu.
     */
    public void showMainMenu() {
        frame.setVisible(true);
    }

    /**
     * Hide the main menu.
     */
    public void hideMainMenu() {
        frame.setVisible(false);
    }

    /**
     * Starts a new game by instantiating the Model, View, and Controller, and
     * connecting them.
     */
    private void startNewGame() {
        TTT_Controller controller = new TTT_Controller();
        TTT_View view = new TTT_View();
        TTT_Model model = new TTT_Model();

        model.setView(view);
        controller.setModel(model);
        view.setActionListener(controller);
        hideMainMenu();
    }

    /**
     * Opens the leaderboard, allowing users to view high scores.
     */
    private void openLeaderboard() {
        TTT_Leaderboard leaderboard = new TTT_Leaderboard();
        leaderboard.showLeaderboard();
    }

}
