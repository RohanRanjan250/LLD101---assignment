package com.example.pen;

/**
 * Thick-tipped marker pen — uses the most ink per character.
 * Consumes 3 units of ink per character.
 * Markers are disposable and do NOT support refilling.
 */
public class MarkerPen implements Pen {
    private static final int INK_PER_CHAR = 3;

    private final Refill inkSupply;
    private boolean capOff;

    public MarkerPen(Refill inkSupply) {
        this.inkSupply = inkSupply;
        this.capOff = false;
    }

    @Override
    public void start() {
        if (capOff) {
            System.out.println("[Marker] Already uncapped.");
            return;
        }
        capOff = true;
        System.out.println("[Marker] Cap removed. " + inkSupply.getColor() + " marker ready.");
    }

    @Override
    public void write(String text) {
        if (!capOff) {
            System.out.println("[Marker] Cap is on. Remove it first.");
            return;
        }
        if (inkSupply.isEmpty()) {
            System.out.println("[Marker] Ink dried out. Cannot write.");
            return;
        }

        int needed = text.length() * INK_PER_CHAR;
        int used = inkSupply.consumeInk(needed);
        int charsWritten = used / INK_PER_CHAR;

        String output = text.substring(0, Math.min(charsWritten, text.length()));
        System.out.println("[Marker] Wrote: \"" + output + "\" | " + inkSupply);

        if (charsWritten < text.length()) {
            System.out.println("[Marker] Ran out of ink mid-write!");
        }
    }

    @Override
    public void close() {
        if (!capOff) {
            System.out.println("[Marker] Already capped.");
            return;
        }
        capOff = false;
        System.out.println("[Marker] Cap on.");
    }

    /** Markers are single-use — attempting to refill throws an exception. */
    @Override
    public void refill(Refill newRefill) {
        throw new UnsupportedOperationException("Markers are disposable and cannot be refilled.");
    }

    @Override
    public boolean isOpen() {
        return capOff;
    }
}
