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
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * The TTT_Database class manages database connections and provides methods for
 * interacting with a database used for a Tic-Tac-Toe leaderboard.
 */
public class TTT_Database {

    private static final String USER_NAME = "pdc"; // Replace with your DB username
    private static final String PASSWORD = "pdc"; // Replace with your DB password
    private static final String URL = "jdbc:derby:TicTacToeDB_Ebd; create=true";

    private Connection conn;
    private static TTT_Database instance;
    Statement statement;

    private TTT_Database() {
        establishConnection();
    }

    public static synchronized TTT_Database getInstance() {
        if (instance == null) {
            instance = new TTT_Database();
        }
        return instance;
    }

    public Connection getConnection() {
        return conn;
    }

    public void establishConnection() {
        if (this.conn == null) {
            try {
                conn = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
                System.out.println(URL + " Get Connected Successfully ....");
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    public void closeConnections() {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    /**
     * Executes a SQL query on the database.
     *
     * @param sql The SQL query to execute.
     */
    public void executeSQL(String sql) {
        try {
            Statement stmt = conn.createStatement();
            stmt.execute(sql);
        } catch (SQLException ex) {
            System.out.println("Error executing SQL: " + ex.getMessage());
        }
    }

    /**
     * Connects to the Tic-Tac-Toe database and creates the leaderboard table if
     * it doesn't exist.
     */
    public void connectTicTacToeDB() {
        try {
            this.statement = conn.createStatement();
            this.createLeaderboardTable();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * Creates the leaderboard table in the database.
     */
    public void createLeaderboardTable() {
        try {
            this.statement = conn.createStatement();
            checkExistedTable("LEADERBOARD");
            this.statement.addBatch("CREATE TABLE LEADERBOARD ("
                    + "PLAYER_NAME VARCHAR(50), "
                    + "SCORE INT)");
            this.statement.addBatch("CREATE INDEX idx_leaderboard_score ON LEADERBOARD (SCORE DESC)");

            this.statement.executeBatch();
            System.out.println("LEADERBOARD table created successfully.");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * Inserts or updates a player's score in the leaderboard table.
     *
     * @param playerName The name of the player.
     * @param score The player's score.
     */
    public void insertPlayerScore(String playerName, int score) {
        try {
            statement = conn.createStatement();
            String selectQuery = "SELECT SCORE FROM LEADERBOARD WHERE PLAYER_NAME = '" + playerName + "'";
            ResultSet resultSet = statement.executeQuery(selectQuery);

            if (resultSet.next()) {
                int currentScore = resultSet.getInt("SCORE");
                int newScore = currentScore + 1;
                String updateQuery = "UPDATE LEADERBOARD SET SCORE = " + newScore + " WHERE PLAYER_NAME = '" + playerName + "'";
                statement.executeUpdate(updateQuery);
                System.out.println("Player " + playerName + "'s score has been updated to " + newScore);
            } else {
                String insertQuery = "INSERT INTO LEADERBOARD (PLAYER_NAME, SCORE) VALUES ('" + playerName + "', 1)";
                statement.executeUpdate(insertQuery);
                System.out.println("Player " + playerName + "'s score has been added to the leaderboard with a score of 1");
            }

            resultSet.close();
        } catch (SQLException ex) {
            System.out.println("Error inserting/updating player score: " + ex.getMessage());
        }
    }

    /**
     * Retrieves the leaderboard from the database.
     *
     * @return A ResultSet containing the leaderboard data, ordered by score in
     * descending order.
     */
    public ResultSet getLeaderboard() {
        try {
            Statement statement = conn.createStatement();
            return statement.executeQuery("SELECT PLAYER_NAME, SCORE FROM LEADERBOARD ORDER BY SCORE DESC");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }

    /**
     * Checks if a table with the specified name exists in the database, and
     * creates it if it doesn't.
     *
     * @param name The name of the table to check or create.
     */
    public void checkExistedTable(String name) {
        try {
            DatabaseMetaData dbmd = this.conn.getMetaData();
            String[] types = {"TABLE"};
            statement = this.conn.createStatement();
            ResultSet rs = dbmd.getTables(null, null, null, types);

            boolean tableExists = false;
            while (rs.next()) {
                String table_name = rs.getString("TABLE_NAME");
                System.out.println(table_name);
                if (table_name.equalsIgnoreCase(name)) {
                    tableExists = true;
                    System.out.println("Table " + name + " already exists.");
                    break;
                }
            }
            rs.close();

            if (!tableExists) {
                this.statement.addBatch("CREATE TABLE LEADERBOARD ("
                        + "PLAYER_NAME VARCHAR(50), "
                        + "SCORE INT)");
                this.statement.addBatch("CREATE INDEX idx_leaderboard_score ON LEADERBOARD (SCORE DESC)");
                this.statement.executeBatch();
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * Deletes a player from the leaderboard table.
     *
     * @param playerName The name of the player to delete.
     */
    public void deletePlayer(String playerName) {
        try {
            statement = conn.createStatement();
            String deleteQuery = "DELETE FROM LEADERBOARD WHERE PLAYER_NAME = '" + playerName + "'";
            int deletedRows = statement.executeUpdate(deleteQuery);

            if (deletedRows > 0) {
                System.out.println("Player " + playerName + " deleted successfully.");
            } else {
                System.out.println("Player " + playerName + " not found in the leaderboard.");
            }
        } catch (SQLException ex) {
            System.out.println("Error deleting player: " + ex.getMessage());
        }
    }

    /**
     * Retrieves a player's score from the database.
     *
     * @param playerName The name of the player.
     * @return The player's score, or 0 if the player is not found.
     */
    public int getPlayerScore(String playerName) {
        int score = 0;

        try {
            statement = conn.createStatement();
            String query = "SELECT SCORE FROM LEADERBOARD WHERE PLAYER_NAME = '" + playerName + "'";
            ResultSet resultSet = statement.executeQuery(query);

            if (resultSet.next()) {
                score = resultSet.getInt("SCORE");
            }

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return score;
    }

    /**
     * Updates a player's score in the database.
     *
     * @param playerName The name of the player.
     * @param newScore The new score to set for the player.
     */
    public void updatePlayerScore(String playerName, int newScore) {
        try {
            statement = conn.createStatement();
            String query = "UPDATE LEADERBOARD SET SCORE = " + newScore + " WHERE PLAYER_NAME = '" + playerName + "'";
            statement.executeUpdate(query);
        } catch (SQLException ex) {
            System.out.println("Error updating player score: " + ex.getMessage());
        }
    }
}
