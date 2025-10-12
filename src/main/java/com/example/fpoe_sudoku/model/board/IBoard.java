package com.example.fpoe_sudoku.model.board;
/**
 * Defines the contract for a Sudoku board. Implementations must provide
 * methods for filling blocks and validating number placements.
 */
public interface IBoard {
    /**
     * Fills the board's blocks according to the implementing class's logic.
     *
     * @param blockIndex The starting index for the filling process.
     * @return {@code true} if the filling was successful, {@code false} otherwise.
     */
    boolean fillBlocks(int blockIndex);
    /**
     * Checks if placing a candidate number at a given position is valid.
     *
     * @param row       The row index of the cell.
     * @param col       The column index of the cell.
     * @param candidate The number to validate.
     * @return {@code true} if the move is valid, {@code false} otherwise.
     */
    boolean isValid(int row, int col, int candidate);
}