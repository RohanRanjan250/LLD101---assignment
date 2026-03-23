package com.example.parking;

/**
 * Enumerates vehicle categories and defines which slot
 * sizes each category is allowed to park in.
 */
public enum VehicleType {
    TWO_WHEELER,
    CAR,
    BUS;

    /**
     * Returns true if this vehicle type is allowed to occupy the given slot.
     * A smaller vehicle can always fit into a bigger slot.
     */
    public boolean fitsIn(SlotType slot) {
        switch (this) {
            case TWO_WHEELER:
                return true; // fits anywhere
            case CAR:
                return slot != SlotType.SMALL; // MEDIUM or LARGE
            case BUS:
                return slot == SlotType.LARGE; // LARGE only
            default:
                return false;
        }
    }
}
