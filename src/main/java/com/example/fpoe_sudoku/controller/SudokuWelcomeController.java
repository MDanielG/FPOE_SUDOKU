package com.example.fpoe_sudoku.controller;

import com.example.fpoe_sudoku.model.user.User;
import com.example.fpoe_sudoku.utils.AlertBox;
import com.example.fpoe_sudoku.view.SudokuGameStage;
import com.example.fpoe_sudoku.view.SudokuWelcomeStage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.io.IOException;

/**
 * Controller for the welcome screen (sudoku-welcome-view.fxml).
 * It handles user interaction for starting a new game.
 */
public class SudokuWelcomeController {

    /**
     * The TextField where the user enters their nickname.
     */
    @FXML
    private TextField nicknameTxt;

    /**
     * Handles the "Play" button action. It validates the user's nickname,
     * creates a new User, passes it to the game controller, and transitions
     * from the welcome stage to the game stage.
     *
     * @param event The action event triggered by clicking the button.
     * @throws IOException If the SudokuGameStage FXML file cannot be loaded.
     */
    @FXML
    void handlePlay(ActionEvent event) throws IOException {
        String nickname = nicknameTxt.getText().trim();

        if (!nickname.equals("")) {
            SudokuGameStage.getInstance().getController().setUser(new User(nickname));
            SudokuWelcomeStage.deleteInstance();
        } else {
            new AlertBox().showAlert("Error", "Ingresa un nickname", Alert.AlertType.ERROR);
        }
    }
}