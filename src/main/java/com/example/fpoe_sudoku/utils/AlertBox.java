package com.example.fpoe_sudoku.utils;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * A utility class for creating and displaying standard JavaFX alerts.
 */
public class AlertBox implements IAlertBox {
    private Alert alert;

    /**
     * {@inheritDoc}
     */
    @Override
    public void showAlert(String headerText, String contentText, AlertType alertType) {
        alert = new Alert(alertType);
        alert.setTitle("Sudoku");
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }
}
