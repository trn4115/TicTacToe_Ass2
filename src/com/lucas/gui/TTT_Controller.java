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
import java.util.ArrayList;

/**
 * The Controller class is responsible for facilitating communication between
 * the user interface and the underlying game model. It acts as an intermediary
 * that handles user input and triggers actions in the Model class.
 */
public class TTT_Controller {

    private TTT_Model model;

    /**
     * Sets the model that this Controller instance will interact with. This
     * allows the Controller to communicate with the game logic through the
     * specified Model instance.
     *
     * @param model The TTT_Model instance to be associated with this
     * TTT_Controller.
     */
    public void setModel(TTT_Model model) {
        this.model = model;
    }

    public TTT_Model getModel() {
        return model;
    }

    /**
     * Requests the model to update the game board based on user input.
     *
     * @param position An ArrayList containing the position information provided
     * by the user. Typically, this list would contain two integers, such as
     * [row, column], representing the user's move on the game board.
     */
    public void updateBoard(ArrayList<Integer> position) {
        // Delegates the responsibility of processing the user's move to the model.
        model.makeMove(position.get(0), position.get(1));
    }

    /**
     * Resets the game by instructing the model to reset its state. This is
     * typically used to start a new game or clear the current game board.
     */
    public void resetGame() {
        model.resetGameState();
    }
    
}
