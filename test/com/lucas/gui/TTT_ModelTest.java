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
public class TTT_ModelTest {

    public TTT_ModelTest() {
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
     * Test the game state by simulating a sequence of moves and checking the
     * model's properties.
     */
    @Test
    public void testGameState() {
        // Arrange
        TTT_Model model = new TTT_Model();
        TTT_View view = new TTT_View();
        model.setView(view);

        // Act - Make a sequence of moves by players on different tiles
        model.makeMove(1, 1); // Player 1 takes the center tile
        assertEquals("Move count should be 8 after the first move.", 8, model.getMovesCounter());

        model.makeMove(0, 0); // Player 2 takes the top-left tile
        assertEquals("Move count should be 7 after the second move.", 7, model.getMovesCounter());

        model.makeMove(2, 2); // Player 1 takes the bottom-right tile
        assertEquals("Move count should be 6 after the third move.", 6, model.getMovesCounter());

        model.makeMove(1, 0); // Player 2 takes the middle-left tile
        assertEquals("Move count should be 5 after the fourth move.", 5, model.getMovesCounter());

        // Assert - Check the content of specific tiles after the moves
        assertEquals("Center tile should contain 'X' after player 1's move.", 'X', model.getBoard()[1][1]);
        assertEquals("Top-left tile should contain 'O' after player 2's move.", 'O', model.getBoard()[0][0]);
        assertEquals("Bottom-right tile should contain 'X' after player 1's move.", 'X', model.getBoard()[2][2]);
        assertEquals("Middle-left tile should contain 'O' after player 2's move.", 'O', model.getBoard()[1][0]);

        // Act - Reset the model
        model.resetGameState();

        // Assert - Check if the count of moves is reset to 9
        assertEquals("Move count should be reset to 9 after a game reset.", 9, model.getMovesCounter());
    }

    /**
     * Test of getCurrentPlayer method, of class TTT_Model.
     */
    @Test
    public void testGetCurrentPlayer() {
        // Arrange
        TTT_Model model = new TTT_Model();

        // Act
        int playerId = model.getCurrentPlayer();

        // Assert
        // Verify that the default player ID is 1
        assertEquals("Default player ID should be 1.", 1, playerId);
    }

    /**
     * Test of setCurrentPlayer method, of class TTT_Model.
     */
    @Test
    public void testSetCurrentPlayer() {
        // Arrange
        TTT_Model model = new TTT_Model();

        // Act
        model.setCurrentPlayer(2);

        // Assert
        // Verify that the player ID is set to 2
        assertEquals("Player ID should be set to 2.", 2, model.getCurrentPlayer());
    }

    /**
     * Test of getMovesCounter method, of class TTT_Model.
     */
    @Test
    public void testGetMovesCounter() {
        // Arrange
        TTT_Model model = new TTT_Model();

        // Act
        int movesCount = model.getMovesCounter();

        // Assert
        // Verify that the default moves count is 9, representing the maximum number of moves in Tic-Tac-Toe.
        assertEquals("Default moves count should be 9.", 9, movesCount);
    }

    /**
     * Test of setMovesCounter method, of class TTT_Model.
     */
    @Test
    public void testSetMovesCounter() {
        // Arrange
        TTT_Model model = new TTT_Model();

        // Act
        model.setMovesCounter(5);

        // Assert
        // Verify that the moves count is set to 5
        assertEquals("Moves count should be set to 5.", 5, model.getMovesCounter());
    }

    /**
     * Test of getBoard method, of class TTT_Model.
     */
    @Test
    public void testGetBoard() {
        // Arrange
        TTT_Model model = new TTT_Model();

        // Act
        char[][] board = model.getBoard();

        // Assert
        // Verify that the default board is not null
        assertNotNull("Default board should not be null.", board);
    }

    /**
     * Test of setBoard method, of class TTT_Model.
     */
    @Test
    public void testSetBoard() {
        // Arrange
        TTT_Model model = new TTT_Model();
        char[][] newBoard = new char[3][3];

        // Act
        model.setBoard(newBoard);

        // Assert
        // Verify that the board is set to the new board
        assertSame("Board should be set to the new board.", newBoard, model.getBoard());
    }

    /**
     * Test of getGameMessage method, of class TTT_Model.
     */
    @Test
    public void testGetMessage() {
        // Arrange
        TTT_Model model = new TTT_Model();

        // Act
        String message = model.getGameMessage();

        // Assert
        // Verify that the default message is an empty string
        assertEquals("Default message should be an empty string.", "", message);
    }

    /**
     * Test of setGameMessage method, of class TTT_Model.
     */
    @Test
    public void testSetMessage() {
        // Arrange
        TTT_Model model = new TTT_Model();
        String newMessage = "Test Message";

        // Act
        model.setGameMessage(newMessage);

        // Assert
        // Verify that the message is set to the new message
        assertEquals("Message should be set to the new message.", newMessage, model.getGameMessage());
    }

    /**
     * Test of updateScore method, of class TTT_Model.
     */
    @Test
    public void testUpdateScore() {
        // Arrange
        TTT_Model model = new TTT_Model();
        TTT_View view = new TTT_View();
        model.setView(view);

        int playerId = 1; // Set the playerId for testing

        // Act
        model.updateScore(playerId);

        // Assert
        // Verify that the player's score is updated correctly
        int expectedScore = 1; // Expected score after the update
        int actualScore = model.getPlayer1Score();
        assertEquals("Player's score should be updated correctly.", expectedScore, actualScore);
    }

    /**
     * Test of makeMove method, of class TTT_Model.
     */
    @Test
    public void testMakeMove() {
        // Arrange
        TTT_Model model = new TTT_Model();
        TTT_View view = new TTT_View();
        model.setView(view);
        int x = 1; // Set the X coordinate as needed
        int y = 1; // Set the Y coordinate as needed

        // Act
        model.makeMove(x, y);

        // Assert
        // Verify that the move is played correctly on the board
        assertEquals("Board should be updated with the player's move.", 'X', model.getBoard()[x][y]);
    }

    /**
     * Test of checkWinCondition method, of class TTT_Model.
     */
    @Test
    public void testCheckWinCondition() {
        // Arrange
        TTT_Model model = new TTT_Model();
        TTT_View view = new TTT_View();
        model.setView(view);
        int x = 0;
        int y = 0;
        char currentPlayerSymbol = model.getCurrentPlayer() == 1 ? 'X' : 'O';

        // Act
        boolean isWinner = model.checkWinCondition(x, y);

        // Assert
        // Verify if the game is won with the given move (x, y)
        assertFalse("No winner with this move.", isWinner);

        // Simulate a win on the board
        model.getBoard()[0][0] = currentPlayerSymbol;
        model.getBoard()[0][1] = currentPlayerSymbol;
        model.getBoard()[0][2] = currentPlayerSymbol;

        isWinner = model.checkWinCondition(x, y);
        assertTrue("Player should win with this move.", isWinner);
    }

    /**
     * Test of resetGameState method, of class TTT_Model.
     */
    @Test
    public void testResetGameState() {
        // Arrange
        TTT_Model model = new TTT_Model();
        TTT_View view = new TTT_View();
        model.setView(view);

        // Act
        model.resetGameState();

        // Assert
        // Verify that the model is reset as expected (check different model properties)
        assertEquals("Player 1's score should be reset to 0.", 0, model.getPlayer1Score());
        assertEquals("Player 2's score should be reset to 0.", 0, model.getPlayer2Score());
        assertEquals("Move count should be reset to 9.", 9, model.getMovesCounter());

        // Add assertions to check the board, player names, or other properties as needed
    }

    /**
     * Test of setPlayer1Name method, of class TTT_Model.
     */
    @Test
    public void testSetPlayer1Name() {
        // Arrange
        TTT_Model model = new TTT_Model();
        String name = "Lucas1";

        // Act
        model.setPlayer1Name(name);

        // Assert
        // Verify that Player 1's name is correctly set
        assertEquals("Player 1's name should be 'Lucas1'.", name, model.getPlayer1Name());
    }

    /**
     * Test of setPlayer2Name method, of class TTT_Model.
     */
    @Test
    public void testSetPlayer2Name() {
        // Arrange
        TTT_Model model = new TTT_Model();
        String name = "Lucas2";

        // Act
        model.setPlayer2Name(name);

        // Assert
        // Verify that Player 2's name is correctly set
        assertEquals("Player 2's name should be 'Lucas2'.", name, model.getPlayer2Name());
    }

    /**
     * Test of getPlayer1Name method, of class TTT_Model.
     */
    @Test
    public void testGetPlayer1Name() {
        // Arrange
        TTT_Model model = new TTT_Model();
        model.setPlayer1Name("Lucas1"); // Set Player 2's name

        // Act
        String player1Name = model.getPlayer1Name();

        // Assert
        // Verify that the correct name is retrieved
        assertEquals("Player 2's name should be 'Lucas1'.", "Lucas1", player1Name);
    }

    /**
     * Test of getPlayer2Name method, of class TTT_Model.
     */
    @Test
    public void testGetPlayer2Name() {
        // Arrange
        TTT_Model model = new TTT_Model();
        model.setPlayer2Name("Lucas2"); // Set Player 2's name

        // Act
        String player2Name = model.getPlayer2Name();

        // Assert
        // Verify that the correct name is retrieved
        assertEquals("Player 2's name should be 'Lucas2'.", "Lucas2", player2Name);
    }

    /**
     * Test of promptPlayersForNames method, of class TTT_Model.
     */
    @Test
    public void testPromptPlayersForNames() {
        // This method involves user input, verify if the names are not empty.

        // Arrange
        TTT_Model model = new TTT_Model();

        // Act
        //model.promptPlayersForNames(); 
        // Prompt is done automatically.....
        
        // Assert
        // Assertions to ensure that the names are not empty
        assertNotNull("Player 1's name should not be null.", model.getPlayer1Name());
        assertNotNull("Player 2's name should not be null.", model.getPlayer2Name());
    }

}
