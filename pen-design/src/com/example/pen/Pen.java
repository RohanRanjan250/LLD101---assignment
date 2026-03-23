package com.example.pen;

/**
 * Abstracts the common operations shared by all pen types.
 * Each implementation defines its own open/close mechanism,
 * ink consumption rate, and refill strategy.
 */
public interface Pen {
    /** Opens the pen so it can write (click, uncap, etc.). */
    void start();

    /** Writes the given text, consuming ink based on the pen type's rate. */
    void write(String text);

    /** Closes/caps the pen. */
    void close();

    /** Replaces or replenishes ink depending on the pen type. */
    void refill(Refill newRefill);

    /** Returns whether the pen is currently open. */
    boolean isOpen();
}
