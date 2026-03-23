package com.example.snakesladders;

/**
 * Tracks a single player's state during the game — their
 * current cell, finishing rank, and whether they've completed the board.
 */
public class Player {
    private final String name;
    private int currentCell;
    private int finishRank;
    private boolean done;

    public Player(String name) {
        this.name = name;
        this.currentCell = 0;
        this.finishRank = 0;
        this.done = false;
    }

    public String getName() {
        return name;
    }

    public int getPosition() {
        return currentCell;
    }

    public int getRank() {
        return finishRank;
    }

    public boolean isFinished() {
        return done;
    }

    public void setPosition(int cell) {
        this.currentCell = cell;
    }

    public void setRank(int rank) {
        this.finishRank = rank;
    }

    public void setFinished(boolean f) {
        this.done = f;
    }

    @Override
    public String toString() {
        return name + " (cell=" + currentCell + ")";
    }
}
