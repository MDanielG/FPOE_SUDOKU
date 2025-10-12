package com.example.fpoe_sudoku;

import com.example.fpoe_sudoku.view.SudokuWelcomeStage;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * The main class for the Sudoku application.
 * This class serves as the entry point for launching the JavaFX application.
 */
public class Main extends Application {

    /**
     * The main entry point for the Java application.
     * This method launches the JavaFX runtime.
     *
     * @param args command line arguments passed to the application.
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * The main entry point for all JavaFX applications.
     * The start method is called after the init method has returned,
     * and after the system is ready for the application to begin running.
     * <p>
     * This implementation initializes and displays the welcome screen of the Sudoku game.
     *
     * @param primaryStage the primary stage for this application, onto which
     * the application scene can be set. It is provided by the platform.
     * @throws IOException if the FXML file for the welcome stage cannot be loaded.
     */
    @Override
    public void start(Stage primaryStage) throws IOException {
        SudokuWelcomeStage.getInstance();
    }
}
