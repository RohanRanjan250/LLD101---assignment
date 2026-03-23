package com.example.snakesladders;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Immutable representation of the game board.
 * Stores all snakes and ladders and provides O(1) lookup
 * via a pre-built transition map.
 */
public class Board {
    private final int totalCells;
    private final List<BoardEntity> snakes;
    private final List<BoardEntity> ladders;
    private final Map<Integer, Integer> transitionMap;

    public Board(int totalCells, List<BoardEntity> snakes, List<BoardEntity> ladders) {
        this.totalCells = totalCells;
        this.snakes = Collections.unmodifiableList(snakes);
        this.ladders = Collections.unmodifiableList(ladders);

        // build a lookup table for instant destination resolution
        Map<Integer, Integer> transitions = new HashMap<>();
        for (BoardEntity snake : snakes) {
            transitions.put(snake.getFromCell(), snake.getToCell());
        }
        for (BoardEntity ladder : ladders) {
            transitions.put(ladder.getFromCell(), ladder.getToCell());
        }
        this.transitionMap = Collections.unmodifiableMap(transitions);
    }

    public int getSize() {
        return totalCells;
    }

    public int getFinalCell() {
        return totalCells;
    }

    public List<BoardEntity> getSnakes() {
        return snakes;
    }

    public List<BoardEntity> getLadders() {
        return ladders;
    }

    /**
     * Resolves where a player ends up after landing on the given cell.
     * If there is no snake or ladder, the same cell is returned.
     */
    public int getDestination(int cell) {
        return transitionMap.getOrDefault(cell, cell);
    }
}
