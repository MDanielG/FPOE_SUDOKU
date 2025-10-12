package com.example.fpoe_sudoku.model.game;

import com.example.fpoe_sudoku.model.board.Board;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;

/**
 * An abstract base class for game logic, providing common properties
 * for a Sudoku game.
 */
public class GameAbstract implements IGame {
    /** The UI grid where the board is displayed. */
    protected GridPane boardGridpane;
    /** The underlying data structure and logic for the Sudoku board. */
    protected Board board;
    /** A list of TextFields representing the cells on the board. */
    protected ArrayList<TextField> numberFields;

    /**
     * Constructs a GameAbstract instance, initializing the board and UI components.
     *
     * @param boardGridpane The GridPane that will contain the Sudoku cells.
     */
    public GameAbstract(GridPane boardGridpane) {
        this.boardGridpane = boardGridpane;
        this.board = new Board();
        this.numberFields = new ArrayList<TextField>();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void startGame() {
    }
}
