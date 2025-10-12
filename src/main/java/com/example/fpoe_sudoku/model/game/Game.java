package com.example.fpoe_sudoku.model.game;

import com.example.fpoe_sudoku.utils.AlertBox;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the concrete implementation of the Sudoku game logic.
 * This class is responsible for setting up the game board UI and handling user input.
 */
public class Game extends GameAbstract {

    private static final int SIZE = 6;
    private static final int BLOCK_ROWS = 2;
    private static final int BLOCK_COLS = 3;

    // Store the current state of the board (user + pre-filled)
    private List<List<Integer>> currentBoard;

    // Store which cells are pre-filled (not editable)
    private List<List<Boolean>> preFilledCells;

    /**
     * Constructs a new Game instance.
     *
     * @param boardGridpane The GridPane from the view where the Sudoku board will be rendered.
     */
    public Game(GridPane boardGridpane) {
        super(boardGridpane);
        initializeGameState();
    }

    /**
     * Initializes the game state structures.
     */
    private void initializeGameState() {
        currentBoard = new ArrayList<>();
        preFilledCells = new ArrayList<>();

        for (int i = 0; i < SIZE; i++) {
            List<Integer> row = new ArrayList<>();
            List<Boolean> preFilledRow = new ArrayList<>();
            for (int j = 0; j < SIZE; j++) {
                row.add(0);
                preFilledRow.add(false);
            }
            currentBoard.add(row);
            preFilledCells.add(preFilledRow);
        }
    }

    /**
     * Starts the game by generating a board, creating UI components (TextFields) for each cell,
     * and adding them to the GridPane. It also sets properties for each cell, such as editability.
     */
    @Override
    public void startGame() {
        System.out.println("=== Generated Sudoku Board ===");

        for (int i = 0; i < board.getBoard().size(); i++) {
            for (int j = 0; j < board.getBoard().get(i).size(); j++) {
                int number = board.getBoard().get(i).get(j);
                System.out.print(number + " ");

                TextField textField = new TextField();
                textField.setAlignment(Pos.CENTER);
                textField.setPrefWidth(50);
                textField.setPrefHeight(50);

                // Style for all cells
                textField.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

                if (number != 0) {
                    // Pre-filled cell
                    textField.setText(String.valueOf(number));
                    textField.setEditable(false);
                    textField.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; " +
                            "-fx-background-color: #e8f4f8; -fx-text-fill: #142850;");
                    preFilledCells.get(i).set(j, true);
                    currentBoard.get(i).set(j, number);
                } else {
                    // Editable cell
                    textField.setText("");
                    textField.setEditable(true);
                    textField.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; " +
                            "-fx-background-color: white; -fx-text-fill: #00A8CC;");
                    preFilledCells.get(i).set(j, false);
                }

                // Store the TextField reference
                numberFields.add(textField);

                // Attach event handlers
                handleNumberField(textField, i, j);

                boardGridpane.add(textField, j, i);
            }
            System.out.println();
        }

        System.out.println("============================");
    }

    /**
     * Attaches event handlers to a TextField cell for input validation and real-time feedback.
     * Implements HU-2 (number input) and HU-3 (real-time validation).
     *
     * @param txt The TextField to which the handlers will be attached.
     * @param row The row index of the cell in the board.
     * @param col The column index of the cell in the board.
     */
    private void handleNumberField(TextField txt, int row, int col) {

        // KEY TYPED: Restrict input to numbers 1-6 only (HU-2)
        txt.setOnKeyTyped(event -> {
            String character = event.getCharacter();

            // Allow only digits 1-6
            if (!character.matches("[1-6]")) {
                event.consume(); // Block the input
            }

            // Limit to single character
            if (txt.getText().length() >= 1 && !character.isEmpty()) {
                event.consume();
            }
        });

        // KEY PRESSED: Handle deletion (HU-2)
        txt.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.BACK_SPACE ||
                    event.getCode() == KeyCode.DELETE) {
                txt.setText("");
                currentBoard.get(row).set(col, 0);
                resetCellStyle(txt);
                event.consume();
            }
        });

        // KEY RELEASED: Validate input in real-time (HU-3)
        txt.setOnKeyReleased(event -> {
            String input = txt.getText().trim();

            if (input.isEmpty()) {
                currentBoard.get(row).set(col, 0);
                resetCellStyle(txt);
                return;
            }

            try {
                int number = Integer.parseInt(input);

                // Validate the number (1-6)
                if (number < 1 || number > 6) {
                    txt.setText("");
                    currentBoard.get(row).set(col, 0);
                    return;
                }

                // Temporarily set to 0 to avoid self-conflict
                currentBoard.get(row).set(col, 0);

                // Validate against Sudoku rules
                boolean isValid = validateMove(row, col, number);

                if (isValid) {
                    // Valid move
                    currentBoard.get(row).set(col, number);
                    setValidCellStyle(txt);
                    System.out.println("Valid move: " + number + " at (" + row + ", " + col + ")");

                    // Check if game is complete
                    checkGameComplete();
                } else {
                    // Invalid move - show error
                    currentBoard.get(row).set(col, number);
                    setInvalidCellStyle(txt);
                    System.out.println("Invalid move: " + number + " at (" + row + ", " + col + ")");
                }

            } catch (NumberFormatException e) {
                txt.setText("");
                currentBoard.get(row).set(col, 0);
            }
        });

        // FOCUS LOST: Re-validate when user moves to another cell
        txt.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue && !txt.getText().isEmpty()) {
                // Lost focus
                String input = txt.getText().trim();
                if (!input.isEmpty()) {
                    int number = Integer.parseInt(input);
                    currentBoard.get(row).set(col, 0);
                    boolean isValid = validateMove(row, col, number);
                    currentBoard.get(row).set(col, number);

                    if (isValid) {
                        setValidCellStyle(txt);
                    } else {
                        setInvalidCellStyle(txt);
                    }
                }
            }
        });
    }

    /**
     * Validates if a number can be placed at the specified position according to Sudoku rules.
     * Checks row, column, and 2x3 block constraints.
     *
     * @param row The row index.
     * @param col The column index.
     * @param number The number to validate.
     * @return true if the move is valid, false otherwise.
     */
    private boolean validateMove(int row, int col, int number) {
        // Check row
        for (int j = 0; j < SIZE; j++) {
            if (j != col && currentBoard.get(row).get(j) == number) {
                return false;
            }
        }

        // Check column
        for (int i = 0; i < SIZE; i++) {
            if (i != row && currentBoard.get(i).get(col) == number) {
                return false;
            }
        }

        // Check 2x3 block
        int blockRowStart = (row / BLOCK_ROWS) * BLOCK_ROWS;
        int blockColStart = (col / BLOCK_COLS) * BLOCK_COLS;

        for (int i = blockRowStart; i < blockRowStart + BLOCK_ROWS; i++) {
            for (int j = blockColStart; j < blockColStart + BLOCK_COLS; j++) {
                if ((i != row || j != col) && currentBoard.get(i).get(j) == number) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * Sets the style for a valid cell (no border highlight).
     *
     * @param txt The TextField to style.
     */
    private void setValidCellStyle(TextField txt) {
        txt.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; " +
                "-fx-background-color: white; -fx-text-fill: #00A8CC; " +
                "-fx-border-color: transparent; -fx-border-width: 2px;");
    }

    /**
     * Sets the style for an invalid cell (red border as per HU-3).
     *
     * @param txt The TextField to style.
     */
    private void setInvalidCellStyle(TextField txt) {
        txt.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; " +
                "-fx-background-color: white; -fx-text-fill: #00A8CC; " +
                "-fx-border-color: red; -fx-border-width: 3px;");
    }

    /**
     * Resets the cell style to default (for empty cells).
     *
     * @param txt The TextField to style.
     */
    private void resetCellStyle(TextField txt) {
        txt.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; " +
                "-fx-background-color: white; -fx-text-fill: #00A8CC;");
    }

    /**
     * Checks if the game board is complete and all entries are valid.
     * Shows a congratulations message if the player wins.
     */
    private void checkGameComplete() {
        // Check if all cells are filled
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (currentBoard.get(i).get(j) == 0) {
                    return; // Not complete yet
                }
            }
        }

        // Check if all entries are valid
        boolean allValid = true;
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                int num = currentBoard.get(i).get(j);
                currentBoard.get(i).set(j, 0);
                if (!validateMove(i, j, num)) {
                    allValid = false;
                }
                currentBoard.get(i).set(j, num);
            }
        }

        if (allValid) {
            System.out.println("¡Felicitaciones! Has completado el Sudoku correctamente.");
            new AlertBox().showAlert(
                    "¡Victoria!",
                    "¡Felicitaciones! Has completado el Sudoku correctamente.",
                    Alert.AlertType.INFORMATION
            );
        }
    }

    /**
     * Provides a hint to the player by suggesting a valid number for an empty cell.
     * Implements HU-4 (help feature).
     *
     * @return true if a hint was provided, false if no empty cells exist.
     */
    public boolean provideHint() {
        List<int[]> emptyCells = new ArrayList<>();

        // Find all empty cells
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (currentBoard.get(i).get(j) == 0 && !preFilledCells.get(i).get(j)) {
                    emptyCells.add(new int[]{i, j});
                }
            }
        }

        if (emptyCells.isEmpty()) {
            new AlertBox().showAlert(
                    "Sin ayuda disponible",
                    "No hay celdas vacías para sugerir.",
                    Alert.AlertType.INFORMATION
            );
            return false;
        }

        // Pick a random empty cell
        int[] cell = emptyCells.get((int)(Math.random() * emptyCells.size()));
        int row = cell[0];
        int col = cell[1];

        // Find a valid number for this cell
        for (int num = 1; num <= SIZE; num++) {
            if (validateMove(row, col, num)) {
                // Found a valid number
                int index = row * SIZE + col;
                TextField cellField = numberFields.get(index);

                cellField.setText(String.valueOf(num));
                currentBoard.get(row).set(col, num);
                setValidCellStyle(cellField);

                // Highlight the hint temporarily
                cellField.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; " +
                        "-fx-background-color: #90EE90; -fx-text-fill: #142850; " +
                        "-fx-border-color: #00A8CC; -fx-border-width: 3px;");

                System.out.println("Hint provided: " + num + " at (" + row + ", " + col + ")");

                // Check if game is complete after hint
                checkGameComplete();

                return true;
            }
        }

        // No valid number found (shouldn't happen in a solvable puzzle)
        new AlertBox().showAlert(
                "Error",
                "No se encontró una sugerencia válida para esta celda.",
                Alert.AlertType.ERROR
        );
        return false;
    }

    /**
     * Gets the current board state.
     *
     * @return The current board as a list of lists.
     */
    public List<List<Integer>> getCurrentBoard() {
        return currentBoard;
    }
}