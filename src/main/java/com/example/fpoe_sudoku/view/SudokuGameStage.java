package com.example.fpoe_sudoku.view;
import com.example.fpoe_sudoku.controller.SudokuGameController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * A singleton Stage for the main Sudoku game window.
 * This class ensures that only one instance of the game window can exist.
 */
public class SudokuGameStage extends Stage {
    private SudokuGameController controller;

    /**
     * Private constructor to enforce the singleton pattern. It loads the FXML view,
     * sets up the scene, and configures the stage properties.
     *
     * @throws IOException if the FXML file cannot be loaded.
     */
    private SudokuGameStage() throws IOException {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/com/example/demosudoku/sudoku-game-view.fxml")
        );
        Parent root = loader.load();
        controller = loader.getController();

        Scene scene = new Scene(root);
        setScene(scene);
        setTitle("Sudoku");
        setResizable(false);
        getIcons().add(
                new Image(String.valueOf(getClass().getResource("/com/example/demosudoku/favicon.png")))
        );
        show();
    }

    /**
     * Returns the controller associated with this stage's view.
     *
     * @return The SudokuGameController instance.
     */
    public SudokuGameController getController() {
        return controller;
    }

    /**
     * Inner static class to hold the singleton instance (lazy initialization).
     */
    private static class Holder {
        private static SudokuGameStage INSTANCE = null;
    }

    /**
     * Provides global access to the singleton SudokuGameStage instance.
     * Creates the instance if it doesn't exist yet.
     *
     * @return The single instance of SudokuGameStage.
     * @throws IOException if the FXML file cannot be loaded during the first creation.
     */
    public static SudokuGameStage getInstance() throws IOException {
        Holder.INSTANCE = Holder.INSTANCE != null ?
                Holder.INSTANCE : new SudokuGameStage();
        return Holder.INSTANCE;
    }

    /**
     * Closes the stage, effectively deleting the instance from view.
     */
    public static void deleteInstance() {
        Holder.INSTANCE.close();
        Holder.INSTANCE = null;
    }
}
