package com.example.fpoe_sudoku.utils;

import javafx.scene.control.Alert;

/**
 * Defines the contract for an alert box utility.
 */
public interface IAlertBox {
    /**
     * Displays an alert dialog to the user.
     *
     * @param headerText The text to display in the header area of the dialog.
     * @param contentText The main content message of the dialog.
     * @param alertType The type of alert to display (e.g., ERROR, INFORMATION).
     */
    void showAlert(String headerText, String contentText, Alert.AlertType alertType);
}
