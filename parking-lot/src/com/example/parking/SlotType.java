package com.example.parking;

/**
 * Defines the available parking slot sizes along with
 * their per-hour billing rates (in Rs).
 */
public enum SlotType {
    SMALL(10),
    MEDIUM(20),
    LARGE(50);

    private final int ratePerHour;

    SlotType(int ratePerHour) {
        this.ratePerHour = ratePerHour;
    }

    /** Returns the billing rate charged per hour for this slot size. */
    public int getHourlyRate() {
        return ratePerHour;
    }
}
