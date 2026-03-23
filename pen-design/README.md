# Pen Design — Low Level Design

## Overview

An object-oriented pen simulation in Java demonstrating **interface-driven polymorphism**. Three pen types (ball-point, fountain, marker) share a common `Pen` interface but differ in how they open/close, how much ink they consume, and whether/how they can be refilled.

## Architecture

```
┌──────────────┐
│   InkColor   │  (enum: BLACK, BLUE, RED, GREEN)
└──────┬───────┘
       │ used by
       ▼
┌──────────────┐
│    Refill    │  colour, remaining, capacity
│  consumeInk  │  refillInk, isEmpty
└──────┬───────┘
       │ held by
       ▼
┌──────────────────────────────────┐
│       <<interface>> Pen          │
│  start · write · close · refill │
└──────┬──────────┬────────┬──────┘
       │          │        │
  BallPointPen  FountainPen  MarkerPen
  (1 ink/char)  (2 ink/char)  (3 ink/char)
```

## Pen Comparison

| Feature         | BallPointPen       | FountainPen          | MarkerPen            |
|----------------|--------------------|----------------------|----------------------|
| Open / Close    | Click mechanism    | Cap on / off         | Cap on / off         |
| Ink per char    | 1 unit             | 2 units              | 3 units              |
| Refill strategy | Swap cartridge     | Replenish reservoir  | Not supported (throws exception) |

## Key Design Decisions

1. **Single `Pen` interface** — all pens expose the same four operations (`start`, `write`, `close`, `refill`), so calling code doesn't need to know the concrete type.
2. **`Refill` as composition** — ink state is decoupled from pen logic. The refill tracks colour, current level, and capacity independently.
3. **Graceful ink depletion** — if ink runs out mid-write, the pen writes as many characters as possible rather than failing entirely.
4. **Marker non-refillability** — enforced at runtime via `UnsupportedOperationException`, matching real-world behaviour where markers are disposable.

## How to Build & Run

```bash
cd pen-design
javac -d out src/com/example/pen/*.java
java -cp out com.example.pen.App
```
