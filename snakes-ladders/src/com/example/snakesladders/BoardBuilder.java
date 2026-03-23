package com.example.snakesladders;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

/**
 * Responsible for constructing a Board by randomly placing
 * snakes and ladders while ensuring no cycles exist.
 */
public class BoardBuilder {
    private static final int MAX_PLACEMENT_ATTEMPTS = 100;
    private final Random rng;

    public BoardBuilder() {
        this.rng = new Random();
    }

    /**
     * Creates a new board of dimension n x n with snakes and ladders
     * placed according to the given difficulty level.
     */
    public Board build(int dimension, DifficultyLevel difficulty) {
        int cells = dimension * dimension;

        // convert percentage ranges to absolute values based on board size
        int minSnakeDrop = Math.max(1, cells * difficulty.getSnakeMinDrop() / 100);
        int maxSnakeDrop = Math.max(minSnakeDrop + 1, cells * difficulty.getSnakeMaxDrop() / 100);
        int minLadderRise = Math.max(1, cells * difficulty.getLadderMinClimb() / 100);
        int maxLadderRise = Math.max(minLadderRise + 1, cells * difficulty.getLadderMaxClimb() / 100);

        // track which cells are already used by an entity endpoint
        Set<Integer> usedCells = new HashSet<>();
        usedCells.add(1);
        usedCells.add(cells);

        // used for cycle detection during placement
        Map<Integer, Integer> transitions = new HashMap<>();

        List<BoardEntity> snakes = new ArrayList<>();
        List<BoardEntity> ladders = new ArrayList<>();

        // place snakes first, then ladders
        for (int i = 0; i < dimension; i++) {
            BoardEntity snake = tryPlaceSnake(cells, minSnakeDrop, maxSnakeDrop, usedCells, transitions);
            if (snake != null)
                snakes.add(snake);
        }

        for (int i = 0; i < dimension; i++) {
            BoardEntity ladder = tryPlaceLadder(cells, minLadderRise, maxLadderRise, usedCells, transitions);
            if (ladder != null)
                ladders.add(ladder);
        }

        return new Board(cells, snakes, ladders);
    }

    /** Attempts to place a single snake; returns null if placement fails. */
    private BoardEntity tryPlaceSnake(int cells, int minDrop, int maxDrop,
            Set<Integer> usedCells, Map<Integer, Integer> transitions) {
        for (int attempt = 0; attempt < MAX_PLACEMENT_ATTEMPTS; attempt++) {
            int drop = minDrop + rng.nextInt(maxDrop - minDrop + 1);
            int head = drop + 1 + rng.nextInt(cells - drop - 1);
            int tail = head - drop;

            if (tail < 1 || head >= cells)
                continue;
            if (usedCells.contains(head) || usedCells.contains(tail))
                continue;
            if (transitions.containsKey(tail))
                continue; // avoid cycles

            usedCells.add(head);
            usedCells.add(tail);
            transitions.put(head, tail);
            return new BoardEntity(head, tail);
        }
        return null;
    }

    /** Attempts to place a single ladder; returns null if placement fails. */
    private BoardEntity tryPlaceLadder(int cells, int minRise, int maxRise,
            Set<Integer> usedCells, Map<Integer, Integer> transitions) {
        for (int attempt = 0; attempt < MAX_PLACEMENT_ATTEMPTS; attempt++) {
            int rise = minRise + rng.nextInt(maxRise - minRise + 1);
            int bottom = 2 + rng.nextInt(cells - rise - 2);
            int top = bottom + rise;

            if (top >= cells || bottom < 2)
                continue;
            if (usedCells.contains(bottom) || usedCells.contains(top))
                continue;
            if (transitions.containsKey(top))
                continue; // avoid cycles

            usedCells.add(bottom);
            usedCells.add(top);
            transitions.put(bottom, top);
            return new BoardEntity(bottom, top);
        }
        return null;
    }
}
