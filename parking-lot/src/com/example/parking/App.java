package com.example.parking;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Demo application that sets up a multilevel parking lot
 * and simulates several park/exit operations.
 */
public class App {
        public static void main(String[] args) {
                List<ParkingSlot> slots = new ArrayList<>();

                // ground floor — small and medium bays
                slots.add(createSlot("S-1", SlotType.SMALL, 1, 10, 50));
                slots.add(createSlot("S-2", SlotType.SMALL, 1, 20, 40));
                slots.add(createSlot("M-1", SlotType.MEDIUM, 1, 15, 45));
                slots.add(createSlot("M-2", SlotType.MEDIUM, 1, 25, 35));

                // upper floor — medium and large bays
                slots.add(createSlot("M-3", SlotType.MEDIUM, 2, 30, 20));
                slots.add(createSlot("L-1", SlotType.LARGE, 2, 40, 10));
                slots.add(createSlot("L-2", SlotType.LARGE, 2, 45, 15));

                ParkingLot lot = new ParkingLot(slots);

                System.out.println("=== Multilevel Parking Lot ===\n");
                System.out.println("Initial availability: " + lot.status());

                // park a car via gate A
                Vehicle car = new Vehicle("KA-01-1234", VehicleType.CAR);
                LocalDateTime carIn = LocalDateTime.of(2025, 1, 15, 10, 0);
                ParkingTicket carTicket = lot.park(car, carIn, SlotType.MEDIUM, "GATE-A");
                System.out.println("\nParked: " + carTicket);

                // park a two-wheeler via gate B
                Vehicle bike = new Vehicle("KA-02-5678", VehicleType.TWO_WHEELER);
                LocalDateTime bikeIn = LocalDateTime.of(2025, 1, 15, 10, 30);
                ParkingTicket bikeTicket = lot.park(bike, bikeIn, SlotType.SMALL, "GATE-B");
                System.out.println("Parked: " + bikeTicket);

                // park a two-wheeler in a medium slot (smaller vehicle, bigger slot)
                Vehicle bike2 = new Vehicle("KA-03-9999", VehicleType.TWO_WHEELER);
                LocalDateTime bike2In = LocalDateTime.of(2025, 1, 15, 11, 0);
                ParkingTicket bike2Ticket = lot.park(bike2, bike2In, SlotType.MEDIUM, "GATE-A");
                System.out.println("Parked (bike in medium): " + bike2Ticket);

                // park a bus via gate B
                Vehicle bus = new Vehicle("KA-04-0001", VehicleType.BUS);
                LocalDateTime busIn = LocalDateTime.of(2025, 1, 15, 9, 0);
                ParkingTicket busTicket = lot.park(bus, busIn, SlotType.LARGE, "GATE-B");
                System.out.println("Parked: " + busTicket);

                System.out.println("\nAvailability after parking: " + lot.status());

                // car leaves after 3 hours
                LocalDateTime carOut = LocalDateTime.of(2025, 1, 15, 13, 0);
                int carBill = lot.exit(carTicket, carOut);
                System.out.println("\nCar exited. Bill: Rs " + carBill
                                + " (3 hrs x Rs " + SlotType.MEDIUM.getHourlyRate() + "/hr)");

                // bike in medium slot leaves after 2 hours — billed at medium rate
                LocalDateTime bike2Out = LocalDateTime.of(2025, 1, 15, 13, 0);
                int bike2Bill = lot.exit(bike2Ticket, bike2Out);
                System.out.println("Bike (medium slot) exited. Bill: Rs " + bike2Bill
                                + " (2 hrs x Rs " + SlotType.MEDIUM.getHourlyRate() + "/hr)");

                // bus leaves after 5 hours
                LocalDateTime busOut = LocalDateTime.of(2025, 1, 15, 14, 0);
                int busBill = lot.exit(busTicket, busOut);
                System.out.println("Bus exited. Bill: Rs " + busBill
                                + " (5 hrs x Rs " + SlotType.LARGE.getHourlyRate() + "/hr)");

                System.out.println("\nFinal availability: " + lot.status());
        }

        /** Helper to build a ParkingSlot with distances for two gates. */
        private static ParkingSlot createSlot(String id, SlotType type, int floor,
                        int distGateA, int distGateB) {
                Map<String, Integer> distances = new HashMap<>();
                distances.put("GATE-A", distGateA);
                distances.put("GATE-B", distGateB);
                return new ParkingSlot(id, type, floor, distances);
        }
}
