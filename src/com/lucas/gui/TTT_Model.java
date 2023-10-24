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
import javax.swing.JOptionPane;

/**
 * The TTT_Model class is part of a Tic-Tac-Toe game application. This class
 * represents the model, which contains the game state and winning logic. It
 * communicates with the view to update the graphical user interface based on
 * the current game state.
 */
public class TTT_Model {

    // Singleton Database Management
    private TTT_Database dbManager = TTT_Database.getInstance();

    // Players names
    private String player1Name;
    private String player2Name;

    // View Management
    private TTT_View view;

    // Game State
    private int currentPlayer;
    private int movesCounter;
    private int[] playerScores; // Add this field to store scores for each player
    private char[][] board;

    // Messages
    private String gameMessage;

    /**
     * Constructor for the TTT_Model class. Initializes the game's initial
     * state.
     */
    public TTT_Model() {
        // Set default player names
        player1Name = "playerX";
        player2Name = "playerO";

        // Create a 3x3 game board
        this.board = new char[3][3];

        // Initialize the number of available moves
        this.movesCounter = 9;

        // Set the initial player ID to 1
        this.currentPlayer = 1;

        // Initialize player scores for two players
        this.playerScores = new int[2];
        this.playerScores[0] = 0; // Player 1's score
        this.playerScores[1] = 0; // Player 2's score

        // Initialize the game gameMessage
        gameMessage = "";

        // Create an instance of the TTT_Database class
        dbManager = TTT_Database.getInstance();

        // Prompt players to enter their names
        promptPlayersForNames();
    }

    /**
     * Updates the score of the winning player and records the score in the
     * database.
     *
     * @param currentPlayer The ID of the winning player.
     */
    public void updateScore(int currentPlayer) {
        int winningPlayerIndex = currentPlayer - 1;
        incrementPlayerScore(winningPlayerIndex);
        updateViewWithScores();
        recordPlayerScoreInDatabase(currentPlayer);
    }

    /**
     * Increments the score of the winning player.
     *
     * @param winningPlayerIndex The index of the winning player.
     */
    private void incrementPlayerScore(int winningPlayerIndex) {
        playerScores[winningPlayerIndex]++;
    }

    /**
     * Updates the view to display the updated scores.
     */
    private void updateViewWithScores() {
        view.updateScores(playerScores);
    }

    /**
     * Records the winning player's score in the database.
     *
     * @param currentPlayer The ID of the winning player.
     */
    private void recordPlayerScoreInDatabase(int currentPlayer) {
        String playerName = (currentPlayer == 1) ? player1Name : player2Name;
        dbManager.insertPlayerScore(playerName, playerScores[currentPlayer - 1]);
    }

    /**
     * Handles a player's move on the game board and updates the game state.
     *
     * @param x The x-coordinate of the move.
     * @param y The y-coordinate of the move.
     */
    public void makeMove(int x, int y) {
        if (getMovesCounter() > 0) {
            markBoard(x, y);
            reduceMovesCount();
            checkGameStatus(x, y);
        }
    }

    /**
     * Marks the board with 'X' or 'O' depending on the current player's turn.
     *
     * @param x The x-coordinate of the move.
     * @param y The y-coordinate of the move.
     */
    private void markBoard(int x, int y) {
        if (currentPlayer % 2 != 0) {
            board[x][y] = 'X';
        } else {
            board[x][y] = 'O';
        }
    }

    /**
     * Reduces the count of available moves.
     */
    private void reduceMovesCount() {
        setMovesCounter(getMovesCounter() - 1);
    }

    /**
     * Checks if the current player has won or if the game has ended in a tie
     * and updates the game message accordingly.
     *
     * @param x The x-coordinate of the move.
     * @param y The y-coordinate of the move.
     */
    private void checkGameStatus(int x, int y) {
        String currentPlayerName = (currentPlayer % 2 != 0) ? getPlayer1Name() : getPlayer2Name();

        if (checkWinCondition(x, y)) {
            setGameMessage(currentPlayerName + " is the Winner!");

            int[] winningTiles = getWinningTiles(x, y);
            view.highlightWinningTiles(winningTiles);
            view.checkWnCondition(x, y, board[x][y], getGameMessage());
        } else if (getMovesCounter() == 0) {
            setGameMessage("No Winner! The game ended in a Tie");

            view.highlightDrawTiles();
            view.checkWnCondition(x, y, board[x][y], getGameMessage());
        } else {
            togglePlayerTurn();
            updateMessageForNextPlayer(x, y);
        }
    }

    /**
     * Toggles the current player to the next player's turn.
     */
    private void togglePlayerTurn() {
        if (currentPlayer % 2 != 0) {
            setCurrentPlayer(2);
        } else {
            setCurrentPlayer(1);
        }
    }

    /**
     * Updates the game message for the next player's turn.
     *
     * @param x The x-coordinate of the move.
     * @param y The y-coordinate of the move.
     */
    private void updateMessageForNextPlayer(int x, int y) {
        char nextPlayerSymbol = (currentPlayer % 2 != 0) ? 'X' : 'O';
        String nextPlayerName = (currentPlayer % 2 != 0) ? getPlayer1Name() : getPlayer2Name();
        setGameMessage("'" + nextPlayerSymbol + "': Player " + getCurrentPlayer() + ": " + nextPlayerName);
        view.update(x, y, board[x][y], getGameMessage());
    }

    /**
     * Checks if a player has won the game based on their latest move.
     *
     * @param x The x-coordinate of the latest move.
     * @param y The y-coordinate of the latest move.
     * @return True if a player has won, false otherwise.
     */
    public boolean checkWinCondition(int x, int y) {
        char symbol = getCurrentPlayerSymbol();
        int countRow = countMatchingCellsInRow(x, symbol);
        int countCol = countMatchingCellsInColumn(y, symbol);
        int countLDiag = countMatchingCellsInLeftDiagonal(symbol);
        int countRDiag = countMatchingCellsInRightDiagonal(symbol);

        if (isWinningPattern(countRow) || isWinningPattern(countCol) || isWinningPattern(countLDiag) || isWinningPattern(countRDiag)) {
            handleWin(x, y, symbol);
            return true;
        }

        return false;
    }

    /**
     * Get the symbol ('X' or 'O') of the current player.
     *
     * @return The symbol of the current player.
     */
    private char getCurrentPlayerSymbol() {
        return (getCurrentPlayer() % 2 != 0) ? 'X' : 'O';
    }

    /**
     * Counts the number of cells in the specified row that match the given
     * symbol.
     *
     * @param x The row to check.
     * @param symbol The symbol (X or O) to match.
     * @return The count of matching cells.
     */
    private int countMatchingCellsInRow(int x, char symbol) {
        int count = 0;
        for (int i = 0; i < board.length; i++) {
            if (board[x][i] == symbol) {
                count++;
            }
        }
        return count;
    }

    /**
     * Counts the number of cells in the specified column that match the given
     * symbol.
     *
     * @param y The column to check.
     * @param symbol The symbol (X or O) to match.
     * @return The count of matching cells.
     */
    private int countMatchingCellsInColumn(int y, char symbol) {
        int count = 0;
        for (int i = 0; i < board.length; i++) {
            if (board[i][y] == symbol) {
                count++;
            }
        }
        return count;
    }

    /**
     * Counts the number of cells in the left diagonal that match the given
     * symbol.
     *
     * @param symbol The symbol (X or O) to match.
     * @return The count of matching cells in the left diagonal.
     */
    private int countMatchingCellsInLeftDiagonal(char symbol) {
        int count = 0;
        for (int i = 0; i < board.length; i++) {
            if (board[i][i] == symbol) {
                count++;
            }
        }
        return count;
    }

    /**
     * Counts the number of cells in the right diagonal that match the given
     * symbol.
     *
     * @param symbol The symbol (X or O) to match.
     * @return The count of matching cells in the right diagonal.
     */
    private int countMatchingCellsInRightDiagonal(char symbol) {
        int count = 0;
        for (int i = 0; i < board.length; i++) {
            if (board[i][board.length - 1 - i] == symbol) {
                count++;
            }
        }
        return count;
    }

    /**
     * Checks if a count indicates a winning pattern.
     *
     * @param count The count of matching cells.
     * @return True if the count equals the board's size, indicating a win,
     * false otherwise.
     */
    private boolean isWinningPattern(int count) {
        return count == board.length;
    }

    /**
     * Handles a win by updating the view, player score, and more.
     *
     * @param x The x-coordinate of the winning move.
     * @param y The y-coordinate of the winning move.
     * @param symbol The winning symbol (X or O).
     */
    private void handleWin(int x, int y, char symbol) {
        view.checkWnCondition(x, y, symbol, getGameMessage());
        updateScore(currentPlayer);
    }

    /**
     * Determines the indices of winning tiles that form a winning combination.
     *
     * @param x The x-coordinate of the latest move.
     * @param y The y-coordinate of the latest move.
     * @return An array of indices representing the winning tiles.
     */
    private int[] getWinningTiles(int x, int y) {
        int[] winningTiles = new int[3];

        // Check horizontal, vertical, and diagonal combinations to find the winning tiles
        if (checkRow(x, board[x][y])) {
            for (int i = 0; i < 3; i++) {
                winningTiles[i] = x * 3 + i;
            }
        } else if (checkColumn(y, board[x][y])) {
            for (int i = 0; i < 3; i++) {
                winningTiles[i] = i * 3 + y;
            }
        } else if (x == y && checkDiagonal(board[x][y])) {
            for (int i = 0; i < 3; i++) {
                winningTiles[i] = i * 3 + i;
            }
        } else if (x + y == 2 && checkAntiDiagonal(board[x][y])) {
            for (int i = 0; i < 3; i++) {
                winningTiles[i] = i * 3 + (2 - i);
            }
        }

        return winningTiles;
    }

    /**
     * Checks if the specified row contains the same symbol (X or O).
     *
     * @param row The row to check.
     * @param symbol The symbol (X or O) to match.
     * @return True if the row contains the same symbol, false otherwise.
     */
    private boolean checkRow(int row, char symbol) {
        for (int i = 0; i < 3; i++) {
            if (board[row][i] != symbol) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if the specified column contains the same symbol (X or O).
     *
     * @param col The column to check.
     * @param symbol The symbol (X or O) to match.
     * @return True if the column contains the same symbol, false otherwise.
     */
    private boolean checkColumn(int col, char symbol) {
        for (int i = 0; i < 3; i++) {
            if (board[i][col] != symbol) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if the diagonal from the top-left to the bottom-right contains the
     * same symbol (X or O).
     *
     * @param symbol The symbol (X or O) to match.
     * @return True if the diagonal contains the same symbol, false otherwise.
     */
    private boolean checkDiagonal(char symbol) {
        for (int i = 0; i < 3; i++) {
            if (board[i][i] != symbol) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if the anti-diagonal from the top-right to the bottom-left
     * contains the same symbol (X or O).
     *
     * @param symbol The symbol (X or O) to match.
     * @return True if the anti-diagonal contains the same symbol, false
     * otherwise.
     */
    private boolean checkAntiDiagonal(char symbol) {
        for (int i = 0; i < 3; i++) {
            if (board[i][2 - i] != symbol) {
                return false;
            }
        }
        return true;
    }

    /**
     * Resets the game model to its initial state, clearing the board and
     * resetting the game parameters.
     */
    public void resetGameState() {
        movesCounter = 9;          // Reset the number of moves left
        setCurrentPlayer(1);          // Set the starting player ID
        setGameMessage("");          // Clear the game gameMessage
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                board[i][j] = '\0';  // Clear the board cells
            }
        }
        view.resetGame();        // Notify the view to reset the graphical representation of the game.
    }

    /**
     * Registers the view for this model.
     *
     * @param view The view to register.
     */
    public void setView(TTT_View view) {
        this.view = view;
    }

    /**
     * Get the current player's ID.
     *
     * @return The current player's ID.
     */
    public int getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * Set the current player's ID.
     *
     * @param playerId The ID of the current player.
     */
    public void setCurrentPlayer(int playerId) {
        this.currentPlayer = playerId;
    }

    /**
     * Get the remaining moves in the game.
     *
     * @return The remaining moves.
     */
    public int getMovesCounter() {
        return movesCounter;
    }

    /**
     * Set the remaining moves in the game.
     *
     * @param movesCount The remaining moves.
     */
    public void setMovesCounter(int movesCount) {
        this.movesCounter = movesCount;
    }

    /**
     * Get the current game board.
     *
     * @return The game board.
     */
    public char[][] getBoard() {
        return board;
    }

    /**
     * Set the game board.
     *
     * @param board The game board to set.
     */
    public void setBoard(char[][] board) {
        this.board = board;
    }

    /**
     * Get the in-game gameMessage.
     *
     * @return The in-game gameMessage.
     */
    public String getGameMessage() {
        return gameMessage;
    }

    /**
     * Set the in-game gameMessage.
     *
     * @param message The in-game gameMessage to set.
     */
    public void setGameMessage(String message) {
        this.gameMessage = message;
    }

    /**
     * Get the name of Player 1.
     *
     * @return The name of Player 1.
     */
    public String getPlayer1Name() {
        return player1Name;
    }

    /**
     * Get the name of Player 2.
     *
     * @return The name of Player 2.
     */
    public String getPlayer2Name() {
        return player2Name;
    }

    /**
     * Set the name of Player 1.
     *
     * @param name The name to set for Player 1.
     */
    public void setPlayer1Name(String name) {
        player1Name = name;
        System.out.println("Player 1's Name set to: " + player1Name); // Add this line for debugging
    }

    /**
     * Set the name of Player 2.
     *
     * @param name The name to set for Player 2.
     */
    public void setPlayer2Name(String name) {
        player2Name = name;
        System.out.println("Player 2's Name set to: " + player2Name); // Add this line for debugging
    }

    /**
     * Get the score of Player 1.
     *
     * @return The score of Player 1.
     */
    public int getPlayer1Score() {
        return playerScores[0];
    }

    /**
     * Get the score of Player 2.
     *
     * @return The score of Player 2.
     */
    public int getPlayer2Score() {
        return playerScores[1];
    }

    /**
     * Set the score of Player 1.
     *
     * @param score The score to set for Player 1.
     */
    public void setPlayer1Score(int score) {
        playerScores[0] = score;
    }

    /**
     * Set the score of Player 2.
     *
     * @param score The score to set for Player 2.
     */
    public void setPlayer2Score(int score) {
        playerScores[1] = score;
    }

    public void promptPlayersForNames() {
        String name1 = JOptionPane.showInputDialog("Enter Player 1's Name:");
        String name2 = "";

        if (name1 != null && !name1.isEmpty()) {
            setPlayer1Name(name1);

            do {
                name2 = JOptionPane.showInputDialog("Enter Player 2's Name:");

                if (name2 == null) {
                    // The user pressed the "Cancel" button, so exit the game
                    System.exit(0);
                } else if (name2.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Player 2's name cannot be empty. Please enter a name for Player 2.");
                } else if (name1.equals(name2)) {
                    JOptionPane.showMessageDialog(null, "Player 1 and Player 2 cannot have the same name. Please enter a different name for Player 2.");
                }
            } while (name2.isEmpty() || name1.equals(name2));

            setPlayer2Name(name2);
        } else {
            // The user pressed the "Cancel" button for Player 1, so exit the game
            System.exit(0);
        }
    }
}
