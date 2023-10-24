/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package com.lucas.gui;

/**
 * Student Name:    Lucas Hashemi
 * Student ID:      14885318
 * Assignment:      PDC/SC_Assignment_2
 * Group:           40 
 * 
 */

import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author lucas
 */
public class TTT_ControllerTest {

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {

    }

    @After
    public void tearDown() {
    }

    /**
     * Test the setModel method to ensure it correctly sets the TTT_Model in the
     * TTT_Controller.
     */
    @Test
    public void testSetModel() {
        TTT_Controller controller = new TTT_Controller();
        TTT_Model model = new TTT_Model();
        controller.setModel(model);

        // Verify that the TTT_Model should be set in the controller.
        assertEquals("Model should be set in the controller.", model, controller.getModel());
    }

    /**
     * Test the updateBoard method to ensure it correctly updates the game
     * board.
     */
    @Test
    public void testUpdateBoard() {
        TTT_Controller controller = new TTT_Controller();
        TTT_Model model = new TTT_Model();
        TTT_View view = new TTT_View();
        model.setView(view);
        controller.setModel(model);

        ArrayList<Integer> position = new ArrayList<>();
        position.add(1);
        position.add(1);

        controller.updateBoard(position);

        // Verify that the board should be updated with 'X' at (1,1).
        assertEquals("The board should be updated with 'X' at (1,1).", 'X', model.getBoard()[1][1]);
    }

    /**
     * Test the resetGame method to ensure it correctly resets the game state.
     */
    @Test
    public void testResetGame() {
        TTT_Controller controller = new TTT_Controller();
        TTT_Model model = new TTT_Model();
        TTT_View view = new TTT_View();
        model.setView(view);
        controller.setModel(model);

        // Make some moves
        ArrayList<Integer> position = new ArrayList<>();
        position.add(0);
        position.add(0);
        controller.updateBoard(position);
        position.set(0, 0);
        position.set(1, 1);
        controller.updateBoard(position);

        // Reset the game
        controller.resetGame();

        // Verify that the board is reset
        char[][] board = model.getBoard();
        assertEquals("The board should be reset to '\0' at (0,0).", '\0', board[0][0]);
        assertEquals("The board should be reset to '\0' at (0,1).", '\0', board[0][1]);

        // Verify that the moves count is reset to 9.
        assertEquals("The moves count should be reset to 9.", 9, model.getMovesCounter());

        // Verify that the player ID is reset to 1.
        assertEquals("The player ID should be reset to 1.", 1, model.getCurrentPlayer());

        // Verify that the message is cleared.
        assertEquals("The message should be empty.", "", model.getGameMessage());

    }
}
