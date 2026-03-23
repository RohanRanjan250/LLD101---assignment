package com.example.snakesladders;

import java.util.Random;

/**
 * Simulates a fair dice with a configurable number of faces.
 * Defaults to a standard 6-sided die.
 */
public class Dice {
    private final Random rng;
    private final int faces;

    public Dice() {
        this(6);
    }

    public Dice(int faces) {
        this.rng = new Random();
        this.faces = faces;
    }

    /** Returns a random value between 1 and the number of faces (inclusive). */
    public int roll() {
        return rng.nextInt(faces) + 1;
    }

    public int getFaces() {
        return faces;
    }
}
