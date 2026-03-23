package com.example.snakesladders;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Entry point for the Snakes & Ladders application.
 * Collects user input and launches the game.
 */
public class App {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter board dimension (n for nxn board): ");
        int dimension = sc.nextInt();

        System.out.print("Enter number of players: ");
        int playerCount = sc.nextInt();
        sc.nextLine(); // consume leftover newline

        List<Player> players = new ArrayList<>();
        for (int i = 1; i <= playerCount; i++) {
            System.out.print("Enter name for Player " + i + ": ");
            players.add(new Player(sc.nextLine()));
        }

        System.out.print("Enter difficulty (easy/medium/hard): ");
        String diffChoice = sc.nextLine().trim().toUpperCase();
        DifficultyLevel difficulty = DifficultyLevel.valueOf(diffChoice);

        Board board = new BoardBuilder().build(dimension, difficulty);
        GameEngine game = new GameEngine(board, players, new Dice());
        game.play();

        sc.close();
    }
}
