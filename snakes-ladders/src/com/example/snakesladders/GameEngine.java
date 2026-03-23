package com.example.snakesladders;

import java.util.ArrayList;
import java.util.List;

/**
 * Runs the Snakes & Ladders game simulation.
 * Manages turn order, dice rolls, board transitions, and ranking.
 */
public class GameEngine {
    private final Board board;
    private final List<Player> players;
    private final Dice dice;
    private int nextRank;

    public GameEngine(Board board, List<Player> players, Dice dice) {
        this.board = board;
        this.players = new ArrayList<>(players);
        this.dice = dice;
        this.nextRank = 1;
    }

    /** Starts and runs the game until all players have finished. */
    public void play() {
        displayBoardInfo();

        // main game loop — keep going while at least 2 players are still playing
        while (countActivePlayers() >= 2) {
            for (Player player : players) {
                if (player.isFinished())
                    continue;
                if (countActivePlayers() < 2)
                    break;
                executeTurn(player);
            }
        }

        // assign final rank to the last remaining player
        for (Player player : players) {
            if (!player.isFinished()) {
                player.setRank(nextRank++);
                player.setFinished(true);
            }
        }

        displayFinalRankings();
    }

    /** Handles a single player's turn: roll, move, handle transitions. */
    private void executeTurn(Player player) {
        int diceValue = dice.roll();
        int currentPos = player.getPosition();
        int targetPos = currentPos + diceValue;

        System.out.print(player.getName() + " rolled " + diceValue + " | " + currentPos);

        // player cannot move beyond the last cell
        if (targetPos > board.getFinalCell()) {
            System.out.println(" -> stays (exceeds " + board.getFinalCell() + ")");
            return;
        }

        // resolve any snake or ladder at the target cell
        int resolvedPos = board.getDestination(targetPos);
        if (resolvedPos != targetPos) {
            String entity = (resolvedPos < targetPos) ? "Snake!" : "Ladder!";
            System.out.println(" -> " + targetPos + " " + entity + " -> " + resolvedPos);
            targetPos = resolvedPos;
        } else {
            System.out.println(" -> " + targetPos);
        }

        player.setPosition(targetPos);

        // check if player reached the finish
        if (targetPos == board.getFinalCell()) {
            player.setRank(nextRank++);
            player.setFinished(true);
            System.out.println("  " + player.getName() + " finishes! Rank: #" + player.getRank());
        }
    }

    /** Returns how many players have not yet finished. */
    private int countActivePlayers() {
        int active = 0;
        for (Player p : players) {
            if (!p.isFinished())
                active++;
        }
        return active;
    }

    private void displayBoardInfo() {
        System.out.println("\n=== Snakes & Ladders ===");
        System.out.println("Board: " + board.getSize() + " cells");
        System.out.println("Snakes:  " + board.getSnakes());
        System.out.println("Ladders: " + board.getLadders());
        System.out.println("Players: " + players);
        System.out.println();
    }

    private void displayFinalRankings() {
        System.out.println("\n=== Final Rankings ===");
        for (int rank = 1; rank <= players.size(); rank++) {
            for (Player p : players) {
                if (p.getRank() == rank) {
                    System.out.println("#" + rank + " " + p.getName());
                }
            }
        }
    }
}
