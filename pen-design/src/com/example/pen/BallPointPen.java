package com.example.pen;

/**
 * Retractable ball-point pen that uses a click mechanism.
 * Consumes 1 unit of ink per character written.
 * Refilling swaps the entire cartridge with a new one.
 */
public class BallPointPen implements Pen {
    private static final int INK_PER_CHAR = 1;

    private Refill cartridge;
    private boolean clicked;

    public BallPointPen(Refill cartridge) {
        this.cartridge = cartridge;
        this.clicked = false;
    }

    @Override
    public void start() {
        if (clicked) {
            System.out.println("[BallPoint] Already clicked open.");
            return;
        }
        clicked = true;
        System.out.println("[BallPoint] Clicked open. Writing with " + cartridge.getColor() + " ink.");
    }

    @Override
    public void write(String text) {
        if (!clicked) {
            System.out.println("[BallPoint] Pen is retracted. Click it open first.");
            return;
        }
        if (cartridge.isEmpty()) {
            System.out.println("[BallPoint] Cartridge empty. Cannot write.");
            return;
        }

        int needed = text.length() * INK_PER_CHAR;
        int used = cartridge.consumeInk(needed);
        int charsWritten = used / INK_PER_CHAR;

        System.out.println("[BallPoint] Wrote: \"" + text.substring(0, charsWritten) + "\" | " + cartridge);

        if (charsWritten < text.length()) {
            System.out.println("[BallPoint] Ran out of ink mid-write!");
        }
    }

    @Override
    public void close() {
        if (!clicked) {
            System.out.println("[BallPoint] Already retracted.");
            return;
        }
        clicked = false;
        System.out.println("[BallPoint] Clicked closed.");
    }

    /** Replaces the old cartridge entirely with a new one. */
    @Override
    public void refill(Refill newRefill) {
        this.cartridge = newRefill;
        System.out.println("[BallPoint] Cartridge swapped. New refill: " + newRefill);
    }

    @Override
    public boolean isOpen() {
        return clicked;
    }
}
