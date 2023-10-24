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

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JLabel;
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
public class TTT_ViewTest {

    public TTT_ViewTest() {
    }

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
     * Test the game state by updating a tile and checking its text.
     */
    @Test
    public void testGameState_UpdatedTileText() {
        // Arrange - Create a TTT_Controller and a TTT_View
        TTT_Controller controller = new TTT_Controller(); // Create a TTT_Controller for interaction
        TTT_View view = new TTT_View(); // Create a TTT_View instance to test
        view.setActionListener(controller); // Attach the TTT_Controller to the TTT_View

        // Act - Update a tile with 'O' and 'Player 1' message
        view.update(0, 2, 'O', "'X': Player 1"); // Simulate updating a tile to 'O' with a message

        // Assert - Check if the tile text is 'O'
        String tileText = view.getTileText(0, 2); // Get the text of the updated tile

        // Verify that the tile's text matches the expected value ('O')
        assertEquals("Tile text should be 'O' after an update.", "O", tileText);
    }

    /**
     * Test the setActionListener method. Ensure that it doesn't throw any
     * errors.
     */
    @Test
    public void testSetActionListener() {
        // Arrange - Create a TTT_View and a null TTT_Controller
        TTT_View view = new TTT_View();
        TTT_Controller controller = null;

        // Act - Set the action listener
        view.setActionListener(controller);

        // Assert - No assertion is made, we're just testing that no errors occur.
    }

    /**
     * Test the initialize method. Ensure that it doesn't throw any errors.
     */
    @Test
    public void testInitialize() {
        // Arrange - Create a new TTT_View instance
        TTT_Controller controller = new TTT_Controller(); // Create a TTT_Controller for interaction
        TTT_View view = new TTT_View(); // Create a TTT_View instance to test
       
        view.setActionListener(controller); // Attach the TTT_Controller to the TTT_View

        // Act - Call the initialize method
        view.initialize();

        // Assert - No assertion is made, we're just testing that no errors occur.
    }

    /**
     * Test the updateScores method with a valid scores array. Ensure that it
     * updates the scores without errors.
     */
    @Test
    public void testUpdateScores() {
        // Arrange - Create a TTT_View instance and an array of scores
        TTT_Controller controller = new TTT_Controller(); // Create a TTT_Controller for interaction
        TTT_View view = new TTT_View(); // Create a TTT_View instance to test
        
        
        view.setActionListener(controller); // Attach the TTT_Controller to the TTT_View

        int[] scores = new int[]{3, 5}; // Example scores, change as needed

        // Act - Call the updateScores method with valid scores
        view.updateScores(scores);

        // Assert - No assertion is made, we're testing that the method doesn't throw errors.
    }

    /**
     * Test the isReset method when the action event is not the reset button. It
     * should return false.
     */
    @Test
    public void testIsResetFalse() {
        // Arrange - Create a TTT_View instance
        TTT_Controller controller = new TTT_Controller(); // Create a TTT_Controller for interaction
        TTT_View view = new TTT_View(); // Create a TTT_View instance to test
        
        view.setActionListener(controller); // Attach the TTT_Controller to the TTT_View
        ActionEvent e = new ActionEvent(new JButton("NotReset"), ActionEvent.ACTION_PERFORMED, "NotReset");

        // Act - Call the isReset method
        boolean isReset = view.isReset(e);

        // Assert - It should return false
        assertFalse("isReset should return false when the event is not the reset button.", isReset);
    }

    @Test
    public void testGetTileClicked() {
        // Create an instance of the TTT_View class
        TTT_Controller controller = new TTT_Controller(); // Create a TTT_Controller for interaction
        TTT_View view = new TTT_View(); // Create a TTT_View instance to test
        view.setActionListener(controller); // Attach the TTT_Controller to the TTT_View

        // Create a mock ActionEvent with a JButton as the source
        JButton sourceButton = new JButton();
        ActionEvent e = new ActionEvent(sourceButton, ActionEvent.ACTION_PERFORMED, "Command");

        // Set the sourceButton's position (simulating a button at row 1, column 2)
        int expectedRow = 1;
        int expectedColumn = 2;
        view.getTiles()[expectedRow][expectedColumn] = sourceButton;

        // Call the getTileClicked method and get the actual result
        ArrayList<Integer> result = view.getTileClicked(e);

        // Create an ArrayList with the expected result
        ArrayList<Integer> expectedPosition = new ArrayList<>();
        expectedPosition.add(expectedRow);
        expectedPosition.add(expectedColumn);

        // Assert that the actual result matches the expected result
        assertEquals(expectedPosition, result);
    }

    /**
     * Test of update method, of class TTT_View.
     */
    @Test
    public void testUpdate() {
        // Create an instance of the TTT_View class
        TTT_View view = new TTT_View();

        // Create a mock button for testing
        JButton button = new JButton();
        int row = 1;
        int column = 2;
        char symbol = 'X';
        String message = "Player 2's turn";

        // Set the button at the specified row and column
        view.getTiles()[row][column] = button;

        // Call the update method
        view.update(row, column, symbol, message);

        // Check if the button text is updated to 'X'
        assertEquals("X", button.getText());

        // Check if the button is disabled
        assertFalse(button.isEnabled());

        // Check if the player's turn message is updated
        assertEquals(message, view.getTurnLabel().getText());
    }

    /**
     * Test of checkWnCondition method, of class TTT_View.
     */
    @Test
    public void testCheckWinCondition() {
        // Create an instance of the TTT_View class
        TTT_View view = new TTT_View();

        // Create a mock button for testing
        JButton button = new JButton();
        int row = 1;
        int column = 2;
        char symbol = 'X';
        String message = "Player 1 wins";

        // Set the button at the specified row and column
        view.getTiles()[row][column] = button;

        // Call the checkWnCondition method
        view.checkWnCondition(row, column, symbol, message);

        // Check if the button text is updated to 'X'
        assertEquals("X", button.getText());

        // Check if the button is disabled
        assertFalse(button.isEnabled());

        // Check if all buttons are disabled
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                assertFalse(view.getTiles()[i][j].isEnabled());
            }
        }

        // Check if the player's turn message is updated
        assertEquals(message, view.getTurnLabel().getText());
    }

    /**
     * Test of highlightWinningTiles method, of class TTT_View.
     */
    @Test
    public void testHighlightWinningTiles() {
        // Create an instance of the TTT_View class
        TTT_Controller controller = new TTT_Controller(); // Create a TTT_Controller for interaction
        TTT_View view = new TTT_View(); // Create a TTT_View instance to test
        view.setActionListener(controller); // Attach the TTT_Controller to the TTT_View

        // Create an array of winning tile indices
        int[] winningTiles = {0, 4, 8};

        // Create a mock button for testing
        JButton[] buttons = new JButton[9];
        for (int i = 0; i < 9; i++) {
            buttons[i] = new JButton();
        }

        // Set the buttons in the specified winning tiles
        for (int tile : winningTiles) {
            buttons[tile] = new JButton();
        }

        // Set the buttons in the TTT_View
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                view.getTiles()[i][j] = buttons[i * 3 + j];
            }
        }

        // Call the highlightWinningTiles method
        view.highlightWinningTiles(winningTiles);

        // Check if the background color of the winning tiles is Color.GREEN
        for (int tile : winningTiles) {
            int i = tile / 3;
            int j = tile % 3;
            assertEquals(Color.GREEN, buttons[tile].getBackground());
        }
    }

    /**
     * Test of highlightDrawTiles method, of class TTT_View.
     */
    @Test
    public void testHighlightDrawTiles() {
        // Create an instance of the TTT_View class
        TTT_Controller controller = new TTT_Controller(); // Create a TTT_Controller for interaction
        TTT_View view = new TTT_View(); // Create a TTT_View instance to test
        view.setActionListener(controller); // Attach the TTT_Controller to the TTT_View

        // Create a 2D array of mock buttons for testing
        JButton[][] buttons = new JButton[3][3];
        for (int row = 0; row < 3; row++) {
            for (int column = 0; column < 3; column++) {
                buttons[row][column] = new JButton();
            }
        }

        // Set the buttons in the TTT_View
        view.setTiles(buttons);

        // Call the highlightDrawTiles method
        view.highlightDrawTiles();

        // Check if the background and foreground colors of all buttons are Color.ORANGE
        for (int row = 0; row < 3; row++) {
            for (int column = 0; column < 3; column++) {
                assertEquals(Color.ORANGE, buttons[row][column].getBackground());
            }
        }
    }

    /**
     * Test of resetGame method, of class TTT_View.
     */
    @Test
    public void testResetGame() {
        // Create an instance of the TTT_View class
        TTT_Controller controller = new TTT_Controller(); // Create a TTT_Controller for interaction
        TTT_View view = new TTT_View(); // Create a TTT_View instance to test
        view.setActionListener(controller); // Attach the TTT_Controller to the TTT_View

        // Create a 2D array of mock buttons for testing
        JButton[][] buttons = new JButton[3][3];
        for (int row = 0; row < 3; row++) {
            for (int column = 0; column < 3; column++) {
                JButton button = new JButton();
                button.setText("X"); // Set initial text for testing
                button.setEnabled(false); // Set initial state for testing
                button.setBackground(Color.RED); // Set initial background color for testing
                button.setFont(new Font("Arial", Font.BOLD, 20)); // Set initial font for testing
                buttons[row][column] = button;
            }
        }

        // Set the buttons and turnLabel in the TTT_View
        view.setTiles(buttons);
        view.setTurnLabel(new JLabel("Player 2 to play 'O' "));

        // Call the resetGame method
        view.resetGame();

        // Check if all buttons are reset
        for (int row = 0; row < 3; row++) {
            for (int column = 0; column < 3; column++) {
                JButton button = buttons[row][column];
                assertEquals("", button.getText()); // Text should be empty
                assertTrue(button.isEnabled()); // Buttons should be enabled
                assertEquals(Color.DARK_GRAY, button.getBackground()); // Background color should be DARK_GRAY
                Font expectedFont = new Font("Arial", Font.PLAIN, 100); // Expected font after reset
                assertEquals(expectedFont, button.getFont());
            }
        }

        // Check if the turnLabel is reset (exactly match the format)
        assertEquals("Player 1 to play 'X'    |   Player 2 to play 'O' ", view.getTurnLabel().getText());

    }

    /**
     * Test of getTileText method, of class TTT_View.
     */
    @Test
    public void testGetButtonText() {
        // Create an instance of the TTT_View class
        TTT_Controller controller = new TTT_Controller(); // Create a TTT_Controller for interaction
        TTT_View view = new TTT_View(); // Create a TTT_View instance to test
        view.setActionListener(controller); // Attach the TTT_Controller to the TTT_View

        // Create a 2D array of mock buttons for testing
        JButton[][] buttons = new JButton[3][3];
        for (int row = 0; row < 3; row++) {
            for (int column = 0; column < 3; column++) {
                JButton button = new JButton();
                button.setText("X" + row + column); // Set unique text for each button
                buttons[row][column] = button;
            }
        }

        // Set the buttons in the TTT_View
        view.setTiles(buttons);

        // Test the getTileText method for each button
        for (int row = 0; row < 3; row++) {
            for (int column = 0; column < 3; column++) {
                String expectedText = "X" + row + column;
                String actualText = view.getTileText(row, column);
                assertEquals(expectedText, actualText);
            }
        }
    }

}
