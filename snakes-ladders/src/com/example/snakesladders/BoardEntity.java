package com.example.snakesladders;

/**
 * Represents a snake or ladder on the board.
 * A snake has startCell > endCell (player moves down),
 * a ladder has endCell > startCell (player moves up).
 */
public class BoardEntity {
    private final int startCell;
    private final int endCell;

    public BoardEntity(int startCell, int endCell) {
        this.startCell = startCell;
        this.endCell = endCell;
    }

    public int getFromCell() {
        return startCell;
    }

    public int getToCell() {
        return endCell;
    }

    public boolean isSnake() {
        return endCell < startCell;
    }

    public boolean isLadder() {
        return endCell > startCell;
    }

    @Override
    public String toString() {
        String label = isSnake() ? "Snake" : "Ladder";
        return label + "[" + startCell + " -> " + endCell + "]";
    }
}
