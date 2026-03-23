package com.example.parking;

/**
 * Represents a physical entry gate in the parking lot.
 * Each gate is identified by a unique string ID.
 */
public class EntryGate {
    private final String id;

    public EntryGate(String id) {
        this.id = id;
    }

    public String getGateId() {
        return id;
    }

    @Override
    public String toString() {
        return "Gate[" + id + "]";
    }
}
