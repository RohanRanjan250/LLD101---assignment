package com.example.pen;

/**
 * Cap-based fountain pen with a broad nib.
 * Consumes 2 units of ink per character.
 * Refilling replenishes the existing reservoir (like using an ink bottle).
 */
public class FountainPen implements Pen {
    private static final int INK_PER_CHAR = 2;

    private Refill reservoir;
    private boolean uncapped;

    public FountainPen(Refill reservoir) {
        this.reservoir = reservoir;
        this.uncapped = false;
    }

    @Override
    public void start() {
        if (uncapped) {
            System.out.println("[Fountain] Already uncapped.");
            return;
        }
        uncapped = true;
        System.out.println("[Fountain] Cap off. Writing with " + reservoir.getColor() + " ink.");
    }

    @Override
    public void write(String text) {
        if (!uncapped) {
            System.out.println("[Fountain] Pen is capped. Uncap it first.");
            return;
        }
        if (reservoir.isEmpty()) {
            System.out.println("[Fountain] Reservoir empty. Cannot write.");
            return;
        }

        int needed = text.length() * INK_PER_CHAR;
        int used = reservoir.consumeInk(needed);
        int charsWritten = used / INK_PER_CHAR;

        String output = text.substring(0, Math.min(charsWritten, text.length()));
        System.out.println("[Fountain] Wrote: \"" + output + "\" | " + reservoir);

        if (charsWritten < text.length()) {
            System.out.println("[Fountain] Ran out of ink mid-write!");
        }
    }

    @Override
    public void close() {
        if (!uncapped) {
            System.out.println("[Fountain] Already capped.");
            return;
        }
        uncapped = false;
        System.out.println("[Fountain] Cap on.");
    }

    /** Refills the reservoir from an ink bottle (existing refill is topped up). */
    @Override
    public void refill(Refill newRefill) {
        reservoir.refillInk();
        System.out.println("[Fountain] Reservoir replenished from ink bottle. " + reservoir);
    }

    @Override
    public boolean isOpen() {
        return uncapped;
    }
}
