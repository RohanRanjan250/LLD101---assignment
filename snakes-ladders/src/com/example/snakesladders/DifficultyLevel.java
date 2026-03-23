package com.example.snakesladders;

/**
 * Represents the difficulty setting for the game.
 * Each level defines percentage-based ranges that control
 * how far snakes drop and ladders climb relative to board size.
 */
public enum DifficultyLevel {
    //              snakeMin%, snakeMax%, ladderMin%, ladderMax%
    EASY(           1,         5,         5,          15),
    MEDIUM(         5,         12,        3,          10),
    HARD(           10,        20,        1,          5);

    private final int snakeMinDrop;
    private final int snakeMaxDrop;
    private final int ladderMinClimb;
    private final int ladderMaxClimb;

    DifficultyLevel(int snakeMinDrop, int snakeMaxDrop,
                    int ladderMinClimb, int ladderMaxClimb) {
        this.snakeMinDrop = snakeMinDrop;
        this.snakeMaxDrop = snakeMaxDrop;
        this.ladderMinClimb = ladderMinClimb;
        this.ladderMaxClimb = ladderMaxClimb;
    }

    public int getSnakeMinDrop()   { return snakeMinDrop; }
    public int getSnakeMaxDrop()   { return snakeMaxDrop; }
    public int getLadderMinClimb() { return ladderMinClimb; }
    public int getLadderMaxClimb() { return ladderMaxClimb; }
}
