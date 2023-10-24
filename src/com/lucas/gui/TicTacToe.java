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
import javax.swing.SwingUtilities;

/**
 * The `TicTacToe` class serves as the entry point for the Tic Tac Toe game. It
 * initializes and launches the game by creating a main menu within the Swing
 * Event Dispatch Thread (EDT).
 */
public class TicTacToe {

    public static void main(String[] args) {
        // Execute the game initialization within the Swing EDT
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // Create and display the main menu to start the game
                TTT_Menu mainMenu = new TTT_Menu();
                mainMenu.showMainMenu();
            }
        });
    }
}
