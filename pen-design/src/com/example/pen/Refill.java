package com.example.pen;

/**
 * Manages ink state for a pen — tracks colour, current level,
 * and maximum capacity. Provides methods to consume and
 * replenish ink.
 */
public class Refill {
    private final InkColor colour;
    private int remaining;
    private final int capacity;

    public Refill(InkColor colour, int capacity) {
        this.colour = colour;
        this.remaining = capacity;
        this.capacity = capacity;
    }

    public InkColor getColor() {
        return colour;
    }

    public int getInkLevel() {
        return remaining;
    }

    public int getMaxInk() {
        return capacity;
    }

    /** Returns true when no ink is left. */
    public boolean isEmpty() {
        return remaining <= 0;
    }

    /**
     * Attempts to use the requested amount of ink.
     * Returns the number of units actually consumed (may be less if ink runs out).
     */
    public int consumeInk(int amount) {
        int used = Math.min(amount, remaining);
        remaining -= used;
        return used;
    }

    /** Refills ink back to its full capacity. */
    public void refillInk() {
        this.remaining = capacity;
    }

    @Override
    public String toString() {
        return colour + " ink [" + remaining + "/" + capacity + "]";
    }
}
