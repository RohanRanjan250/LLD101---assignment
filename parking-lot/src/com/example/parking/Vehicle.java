package com.example.parking;

/**
 * Represents a vehicle entering the parking lot,
 * identified by its registration plate and category.
 */
public class Vehicle {
    private final String regNumber;
    private final VehicleType category;

    public Vehicle(String regNumber, VehicleType category) {
        this.regNumber = regNumber;
        this.category = category;
    }

    public String getLicensePlate() {
        return regNumber;
    }

    public VehicleType getType() {
        return category;
    }

    @Override
    public String toString() {
        return category + " [" + regNumber + "]";
    }
}
