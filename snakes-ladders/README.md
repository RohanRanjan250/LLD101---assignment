# Snakes & Ladders — Low Level Design

## Overview

A console-based Snakes & Ladders simulation built in Java. The game supports multiple players, three difficulty levels (Easy / Medium / Hard), and dynamically generates the board with randomly placed snakes and ladders while preventing infinite loops.

## Architecture

```
┌───────────────────┐
│        App        │  ← Entry point, reads user input
└────────┬──────────┘
         │ creates
         ▼
┌───────────────────┐      ┌────────────────┐
│    GameEngine     │─────▸│     Dice       │
│  (turn loop,      │      │  (configurable │
│   ranking logic)  │      │   faces)       │
└────────┬──────────┘      └────────────────┘
         │ uses
         ▼
┌───────────────────┐  built by  ┌──────────────────┐
│      Board        │◂───────────│   BoardBuilder   │
│  (transition map) │            │  (random place-  │
└────────┬──────────┘            │   ment + cycle   │
         │ contains              │   prevention)    │
         ▼                       └──────────────────┘
┌───────────────────┐
│   BoardEntity     │  ← Snake or Ladder (fromCell → toCell)
└───────────────────┘

┌───────────────────┐
│  DifficultyLevel  │  ← Enum: EASY / MEDIUM / HARD
│  (% ranges for    │
│   drop & climb)   │
└───────────────────┘
```

## Key Design Decisions

### 1. Board & Transition Map
- Cells are numbered 1 to `n × n`.
- Snakes and ladders share the same `BoardEntity` class — the direction (up or down) is determined by comparing `fromCell` and `toCell`.
- The `Board` pre-builds an immutable `HashMap` so that looking up whether a cell triggers a transition is **O(1)**.

### 2. Random Placement with Cycle Prevention
- `BoardBuilder` generates `n` snakes and `n` ladders per board.
- Each entity's drop/climb range is derived from the chosen `DifficultyLevel`, which stores percentage-based bounds scaled to the board size.
- During placement, we verify that an entity's destination doesn't land on the start of another entity — this eliminates chain reactions and infinite loops.

### 3. Difficulty Levels
| Level  | Snake Drop | Ladder Climb |
|--------|-----------|--------------|
| EASY   | 1 – 5 %   | 5 – 15 %    |
| MEDIUM | 5 – 12 %  | 3 – 10 %    |
| HARD   | 10 – 20 % | 1 – 5 %     |

### 4. Game Loop
1. Each player rolls the dice on their turn.
2. If the resulting position exceeds the final cell, the player stays put.
3. If the cell has a snake or ladder, the player is moved accordingly.
4. The first player to land exactly on the final cell receives rank #1, and so on.
5. The game ends when fewer than 2 active players remain; the last player is auto-ranked.

## How to Build & Run

```bash
cd snakes-ladders
javac -d out src/com/example/snakesladders/*.java
java -cp out com.example.snakesladders.App
```

### Example Session
```
Enter board dimension (n for nxn board): 10
Enter number of players: 3
Enter name for Player 1: Rohan
Enter name for Player 2: Arjun
Enter name for Player 3: Priya
Enter difficulty (easy/medium/hard): medium

=== Snakes & Ladders ===
Board: 100 cells
Snakes:  [Snake[72 -> 65], Snake[54 -> 47], ...]
Ladders: [Ladder[8 -> 18], Ladder[31 -> 39], ...]
Players: [Rohan (cell=0), Arjun (cell=0), Priya (cell=0)]

Rohan rolled 5 | 0 -> 5
Arjun rolled 3 | 0 -> 3
Priya rolled 6 | 0 -> 6
Rohan rolled 2 | 5 -> 7 Ladder! -> 18
...
  Rohan finishes! Rank: #1

=== Final Rankings ===
#1 Rohan
#2 Priya
#3 Arjun
```
