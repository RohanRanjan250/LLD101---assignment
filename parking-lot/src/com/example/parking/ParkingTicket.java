package com.example.parking;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Immutable receipt generated when a vehicle is parked.
 * Contains all the data needed to compute billing at exit.
 */
public class ParkingTicket {
    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private final String id;
    private final Vehicle vehicle;
    private final ParkingSlot assignedSlot;
    private final LocalDateTime entryTime;

    public ParkingTicket(String id, Vehicle vehicle,
            ParkingSlot assignedSlot, LocalDateTime entryTime) {
        this.id = id;
        this.vehicle = vehicle;
        this.assignedSlot = assignedSlot;
        this.entryTime = entryTime;
    }

    public String getTicketId() {
        return id;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public ParkingSlot getSlot() {
        return assignedSlot;
    }

    public LocalDateTime getEntryTime() {
        return entryTime;
    }

    @Override
    public String toString() {
        return "Ticket#" + id
                + " | " + vehicle
                + " | slot=" + assignedSlot.getSlotId()
                + " (" + assignedSlot.getType() + ")"
                + " | entry=" + entryTime.format(FMT);
    }
}
