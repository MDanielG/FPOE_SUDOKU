package com.example.fpoe_sudoku.controller;


import com.example.fpoe_sudoku.model.game.Game;
import com.example.fpoe_sudoku.model.user.User;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller for the main Sudoku game view (sudoku-game-view.fxml).
 * This class is responsible for initializing and managing the game board's UI.
 */
public class SudokuGameController implements Initializable {

    /**
     * The GridPane element from the FXML file that holds the Sudoku board cells.
     */
    @FXML
    private GridPane boardGridPane;

    private Game game;
    private User user;

    /**
     * Initializes the controller class. This method is automatically called
     * after the FXML file has been loaded. It creates a new game instance
     * and starts the game.
     *
     * @param url            The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        game = new Game(boardGridPane);
        game.startGame();
    }

    /**
     * Sets the user for the current game session. This method is called by the
     * welcome controller to pass the user's data.
     *
     * @param user The user object containing player information, such as the nickname.
     */
    public void setUser(User user) {
        this.user = user;
    }
}