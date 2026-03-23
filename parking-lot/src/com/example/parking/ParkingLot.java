package com.example.parking;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Core parking lot manager — handles slot allocation, billing,
 * and availability tracking across all floors and gates.
 */
public class ParkingLot {
    private final List<ParkingSlot> allSlots;
    private int ticketSeq = 0;

    public ParkingLot(List<ParkingSlot> slots) {
        this.allSlots = new ArrayList<>(slots);
    }

    /**
     * Parks the given vehicle in the nearest available slot of the
     * requested type from the specified entry gate.
     *
     * @throws IllegalStateException if no matching slot is available
     */
    public ParkingTicket park(Vehicle vehicle, LocalDateTime entryTime,
            SlotType requestedType, String gateId) {
        ParkingSlot nearest = findNearestSlot(vehicle, requestedType, gateId);

        if (nearest == null) {
            throw new IllegalStateException(
                    "No available " + requestedType + " slot for " + vehicle);
        }

        nearest.occupy();
        ticketSeq++;
        return new ParkingTicket("T-" + ticketSeq, vehicle, nearest, entryTime);
    }

    /**
     * Processes vehicle exit — releases the slot and returns the
     * total bill (duration in hours × slot rate). Minimum 1 hour.
     */
    public int exit(ParkingTicket ticket, LocalDateTime exitTime) {
        long hours = Duration.between(ticket.getEntryTime(), exitTime).toHours();
        if (hours < 1)
            hours = 1;

        int charge = (int) (hours * ticket.getSlot().getType().getHourlyRate());
        ticket.getSlot().release();
        return charge;
    }

    /** Returns a snapshot of how many slots are free for each type. */
    public Map<SlotType, Integer> status() {
        Map<SlotType, Integer> freeCount = new LinkedHashMap<>();
        for (SlotType type : SlotType.values()) {
            freeCount.put(type, 0);
        }
        for (ParkingSlot slot : allSlots) {
            if (!slot.isOccupied()) {
                freeCount.merge(slot.getType(), 1, Integer::sum);
            }
        }
        return freeCount;
    }

    /** Scans all slots and returns the nearest compatible one, or null. */
    private ParkingSlot findNearestSlot(Vehicle vehicle, SlotType type, String gateId) {
        ParkingSlot best = null;
        int shortestDist = Integer.MAX_VALUE;

        for (ParkingSlot slot : allSlots) {
            if (slot.isOccupied())
                continue;
            if (slot.getType() != type)
                continue;
            if (!vehicle.getType().fitsIn(slot.getType()))
                continue;

            int dist = slot.getDistanceFrom(gateId);
            if (dist < shortestDist) {
                shortestDist = dist;
                best = slot;
            }
        }
        return best;
    }
}
