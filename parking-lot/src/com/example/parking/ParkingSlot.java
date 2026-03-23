package com.example.parking;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents a single parking space on a specific floor.
 * Each slot knows its distance from every entry gate for
 * nearest-slot allocation.
 */
public class ParkingSlot {
    private final String id;
    private final SlotType slotType;
    private final int floorNumber;
    private final Map<String, Integer> gateDistances;
    private boolean inUse;

    public ParkingSlot(String id, SlotType slotType, int floorNumber,
            Map<String, Integer> gateDistances) {
        this.id = id;
        this.slotType = slotType;
        this.floorNumber = floorNumber;
        this.gateDistances = Collections.unmodifiableMap(new HashMap<>(gateDistances));
        this.inUse = false;
    }

    public String getSlotId() {
        return id;
    }

    public SlotType getType() {
        return slotType;
    }

    public int getFloor() {
        return floorNumber;
    }

    public boolean isOccupied() {
        return inUse;
    }

    /**
     * Returns distance from the specified gate, or MAX_VALUE if gate is unknown.
     */
    public int getDistanceFrom(String gateId) {
        return gateDistances.getOrDefault(gateId, Integer.MAX_VALUE);
    }

    public void occupy() {
        this.inUse = true;
    }

    public void release() {
        this.inUse = false;
    }

    @Override
    public String toString() {
        return id + " (" + slotType + ", floor=" + floorNumber + ", occupied=" + inUse + ")";
    }
}
