package com.example.fpoe_sudoku.model.board;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * This class generates a 6x6 board divided into 2x3 blocks.
 * In each 2x3 block exactly one cell is assigned a random number (from 1 to 6),
 * and all the other cells are left as 0. Additionally, the placed number is not repeated
 * in any row or column across the entire board.
 * <p>
 * The board is represented as a list of lists (ArrayLists) rather than using arrays,
 * and the board is generated using a backtracking algorithm that works block by block.
 * <p>
 * Java JDK 17.
 */
public class Board implements IBoard {
    // Board dimensions and block dimensions.
    private final int SIZE = 6;
    private final int BLOCK_ROWS = 2;
    private final int BLOCK_COLS = 3;

    // Number of block rows and block columns.
    private final int TOTAL_BLOCK_ROWS = SIZE / BLOCK_ROWS; // 6/2 = 3
    private final int TOTAL_BLOCK_COLS = SIZE / BLOCK_COLS; // 6/3 = 2
    private final int TOTAL_BLOCKS = TOTAL_BLOCK_ROWS * TOTAL_BLOCK_COLS; // 3 * 2 = 6

    // The board represented as a List of Lists (each inner list is a row)
    private final List<List<Integer>> board;
    private final Random random = new Random();

    /**
     * Constructor initializes the board with zeros and then fills each block with one number.
     */
    public Board() {
        board = new ArrayList<>();
        // Initialize the board with zeros.
        for (int i = 0; i < SIZE; i++) {
            List<Integer> row = new ArrayList<>();
            for (int j = 0; j < SIZE; j++) {
                row.add(0);
            }
            board.add(row);
        }
        // Attempt to fill each block with a valid number.
        if (!fillBlocks(0)) {
            System.out.println("Failed to generate the Sudoku board.");
        }
    }

    /**
     * Recursively fills each 2x3 block with one number.
     *
     * @param blockIndex the index of the current block (ranging from 0 to TOTAL_BLOCKS - 1).
     * @return true if all blocks have been successfully filled; false otherwise.
     */
    @Override
    public boolean fillBlocks(int blockIndex) {
        // If all blocks have been processed, the board is complete.
        if (blockIndex == TOTAL_BLOCKS) {
            return true;
        }

        // Determine the block's position.
        int blockRow = blockIndex / TOTAL_BLOCK_COLS;     // Row index of the block.
        int blockCol = blockIndex % TOTAL_BLOCK_COLS;       // Column index of the block.
        int startRow = blockRow * BLOCK_ROWS;
        int startCol = blockCol * BLOCK_COLS;

        // Prepare a list of candidate numbers [1, 2, 3, 4, 5, 6] in random order.
        List<Integer> numbers = new ArrayList<>();
        for (int i = 1; i <= SIZE; i++) {
            numbers.add(i);
        }
        Collections.shuffle(numbers, random);

        // Iterate over every cell in the current 2x3 block.
        for (int i = startRow; i < startRow + BLOCK_ROWS; i++) {
            for (int j = startCol; j < startCol + BLOCK_COLS; j++) {
                // Try each candidate number in the randomized order.
                for (Integer number : numbers) {
                    // Check if placing 'number' in cell (i, j) does not violate the row and column constraints.
                    if (isValid(i, j, number)) {
                        board.get(i).set(j, number);
                        // Recursively fill the next block.
                        if (fillBlocks(blockIndex + 1)) {
                            return true;
                        }
                        // Backtracking: reset the cell if subsequent placement fails.
                        board.get(i).set(j, 0);
                    }
                }
            }
        }
        // If no valid placement was found for this block, return false.
        return false;
    }

    /**
     * Checks whether placing a candidate number at cell (row, col) violates the row or column uniqueness.
     *
     * @param row       the row index.
     * @param col       the column index.
     * @param candidate the number to place (from 1 to 6).
     * @return true if the candidate can be placed without conflict; false otherwise.
     */
    @Override
    public boolean isValid(int row, int col, int candidate) {
        // Check the current row for an existing occurrence of the candidate.
        for (int j = 0; j < SIZE; j++) {
            if (board.get(row).get(j) == candidate) {
                return false;
            }
        }
        // Check the current column for an existing occurrence of the candidate.
        for (int i = 0; i < SIZE; i++) {
            if (board.get(i).get(col) == candidate) {
                return false;
            }
        }
        return true;
    }

    /**
     * Returns the generated board.
     *
     * @return a list of lists representing the board.
     */
    public List<List<Integer>> getBoard() {
        return board;
    }
}