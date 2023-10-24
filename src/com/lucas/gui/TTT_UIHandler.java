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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * The TTT_UIHandler class acts as an intermediary between the View and Controller components,
 * promoting loose coupling. It listens for UI interactions, such as button clicks,
 * and facilitates communication between the UI elements and the Controller.
 */
public class TTT_UIHandler implements ActionListener {

    private final TTT_Controller controller;
    private final TTT_View view;

    /**
     * Initializes references to the TTT_Controller and TTT_View classes.
     *
     * @param controller The TTT_Controller responsible for game logic.
     * @param view The TTT_View responsible for user interface components.
     */
    public TTT_UIHandler(TTT_Controller controller, TTT_View view) {
        this.controller = controller;
        this.view = view;
    }

    /**
     * Handles the action performed when a button is clicked.
     *
     * @param e The ActionEvent representing the button click.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (view.isReset(e)) {
            // If it's a reset action, request the TTT_Controller to reset the game.
            controller.resetGame();
        } else if (e.getSource() == view.getMenuButton()) {
            // If it's a "Menu" button action, return to the main menu.
            view.switchToMainMenu();
        } else {
            // Otherwise, get the button's position and ask the TTT_Controller to update the board.
            ArrayList<Integer> position = view.getTileClicked(e);
            controller.updateBoard(position);
        }
    }

}
