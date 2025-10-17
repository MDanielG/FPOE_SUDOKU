package com.example.fpoe_sudoku.controller;

import com.example.fpoe_sudoku.model.game.Game;
import com.example.fpoe_sudoku.model.user.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller for the main Sudoku game view (sudoku-game-view.fxml).
 * This class is responsible for initializing and managing the game board's UI
 * and handling user interactions such as help requests.
 */
public class SudokuGameController implements Initializable {

    /**
     * The GridPane element from the FXML file that holds the Sudoku board cells.
     */
    @FXML
    private GridPane boardGridPane;

    /**
     * Button to request help/hints from the game.
     */
    @FXML
    private Button helpButton;

    /**
     * Label to display the player's nickname.
     */
    @FXML
    private Label playerLabel;

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
     * welcome controller to pass the user's data. It also updates the UI to
     * display the player's nickname.
     *
     * @param user The user object containing player information, such as the nickname.
     */
    public void setUser(User user) {
        this.user = user;
        if (playerLabel != null && user != null) {
            playerLabel.setText("Jugador: " + user.getNickname());
        }
    }

    /**
     * Handles the help button action. Requests a hint from the game
     * to assist the player. Implements HU-4 (help feature).
     *
     * @param event The action event triggered by clicking the help button.
     */

    @FXML
    private void handleHelpButton(ActionEvent event) {
        game.provideHint(); // simplemente llamas al método
    }

}



