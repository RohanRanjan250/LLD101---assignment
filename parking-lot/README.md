# Multilevel Parking Lot — Low Level Design

## Overview

A multi-floor parking lot system built in Java that handles vehicle entry, nearest-slot allocation, hourly billing, and multiple entry gates. The design emphasizes clean separation between slot management, billing logic, and vehicle compatibility rules.

## Architecture

```
┌────────────────┐     ┌────────────────┐
│   SlotType     │     │  VehicleType   │
│ (enum: SMALL,  │     │ (enum: TWO_    │
│  MEDIUM, LARGE)│     │  WHEELER, CAR, │
│  + hourlyRate  │     │  BUS)          │
└───────┬────────┘     │  + fitsIn()    │
        │              └───────┬────────┘
        ▼                      ▼
┌────────────────┐     ┌────────────────┐
│  ParkingSlot   │     │   Vehicle      │
│  - id, type    │     │  - regNumber   │
│  - floor       │     │  - category    │
│  - gateDistMap │     └───────┬────────┘
│  - inUse       │             │
└───────┬────────┘             │
        │                      │
        ▼                      ▼
┌──────────────────────────────────────┐
│           ParkingTicket              │
│  - id, vehicle, slot, entryTime     │
└──────────────────┬───────────────────┘
                   │
                   ▼
┌──────────────────────────────────────┐
│            ParkingLot                │
│  + park(vehicle, time, type, gate)  │
│  + exit(ticket, time) → bill        │
│  + status() → free counts           │
└──────────────────────────────────────┘

┌────────────────┐
│   EntryGate    │  (identifier only)
└────────────────┘
```

## Key Design Decisions

### Nearest-Slot Allocation
Each `ParkingSlot` stores a map of distances from every entry gate. When parking, the system scans all compatible, unoccupied slots and picks the one closest to the vehicle's entry gate — giving O(n) lookup per park request.

### Vehicle-Slot Compatibility
Handled via `VehicleType.fitsIn(SlotType)`:
| Vehicle       | SMALL | MEDIUM | LARGE |
|-------------- |-------|--------|-------|
| TWO_WHEELER   | ✔     | ✔      | ✔     |
| CAR           | ✘     | ✔      | ✔     |
| BUS           | ✘     | ✘      | ✔     |

### Billing Model
Billing is based on **slot type**, not vehicle type. A two-wheeler parked in a MEDIUM slot pays the MEDIUM rate (Rs 20/hr). Duration is rounded down to whole hours with a 1-hour minimum.

| Slot   | Rate     |
|--------|----------|
| SMALL  | Rs 10/hr |
| MEDIUM | Rs 20/hr |
| LARGE  | Rs 50/hr |

## How to Build & Run

```bash
cd parking-lot
javac -d out src/com/example/parking/*.java
java -cp out com.example.parking.App
```
