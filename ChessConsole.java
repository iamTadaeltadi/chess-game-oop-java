package com.aait.chess.cli;

import com.aait.chess.core.Chess;
import com.aait.chess.core.Move;
import com.aait.chess.core.Position;
import com.aait.chess.puzzles.Puzzle;
import com.aait.chess.puzzles.PuzzleDatabase;

import java.util.ArrayList;
import java.util.Scanner;

public class ChessConsole {
    private Chess game;
    private PuzzleDatabase database;

    public ChessConsole() {
        database = new PuzzleDatabase();
    }

    public void run() {
        Scanner sc = new Scanner(System.in);
        String inputLine;

        System.out.println("Welcome to Chess Console!");
        printInstructions();
        inputLine = sc.nextLine();
        while (!inputLine.equals("q")) {
            try {
                if (inputLine.equals("p")) {
                    game = new Chess();
                    play();
                }
                else if (inputLine.equals("l")) {
                    int databaseSize = database.getSize();
                    for (int i = 0; i < databaseSize; i++)
                        System.out.println(i + ": " + database.getPuzzle(i));
                }
                else if (inputLine.startsWith("a "))
                    database.addPuzzlesFromFile(inputLine.substring(2));
                else if (inputLine.startsWith("p ")) {
                    int puzzleNumber = Integer.parseInt(inputLine.substring(2));
                    Puzzle puzzle = database.getPuzzle(puzzleNumber);
                    game = new Chess(puzzle.getArrangement(), puzzle.getTurn());
                    play();
                }
                else
                    System.out.println("Unknown instruction. Please try again.");
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            printInstructions();
            inputLine = sc.nextLine();
        }
        database.save();
    }

    public void play() {
        Scanner sc = new Scanner(System.in);
        String inputLine;

        print();

        while (!game.isGameOver()) {
            if (game.getTurn() == Chess.PieceColor.WHITE)
                System.out.println("White's move: ");
            else
                System.out.println("Black's move: ");

            inputLine = sc.nextLine();
            String[] input = inputLine.split(" ");

            Position p1 = null, p2 = null;

            if (input.length >= 1) {
                if (input[0].equals("resign")) {
                    System.out.println(game.getTurn() + " has resigned.");
                    return;
                }

                if (input[0].equals("debug")) {
                    debug();
                    print();
                    continue;
                }

                p1 = Position.generateFromString(input[0]);

                if (p1 == null || game.getPieceAt(p1) == null) {
                    System.out.println("Invalid position. Please try again.");
                    continue;
                }

                if (input.length == 1) {
                    // Players are informed about wrong turns, but the squares for
                    // the opponent's piece are still highlighted
                    if (game.getPieceAt(p1).getPieceColor() != game.getTurn())
                        System.out.println("That piece belongs to the opponent.");
                    print(p1);
                }
                else if (input.length == 2) {
                    if (game.getPieceAt(p1).getPieceColor() != game.getTurn()) {
                        System.out.println("That piece belongs to the opponent.");
                        continue;
                    }

                    boolean success = false;

                    p2 = Position.generateFromString(input[1]);

                    // checking if p1 != null is not necessary
                    // its negation is already checked on line 59
                    if (p2 != null) {
                        Move m = new Move(p1, p2);
                        success = game.performMove(m);
                    }
                    if (!success)
                        System.out.println("Invalid move. Please try again.");

                    print();
                }
            }
        }
    }

    public void print(Position origin) {
        ArrayList<Position> highlights = null;
        if (origin != null)
            highlights = game.reachableFrom(origin);

        for (int i = 0; i < Chess.BOARD_RANKS; i++) {
            System.out.print((Chess.BOARD_RANKS - i) + " ");

            for (int j = 0; j < Chess.BOARD_FILES; j++) {
                boolean isHighlighted = false;

                //include the square the piece is currently at
                if (origin != null
                        && origin.getRank() == i && origin.getFile() == j)
                    isHighlighted = true;

                for (int k = 0; highlights != null && k < highlights.size(); k++)
                    if (highlights.get(k).getRank() == i
                            && highlights.get(k).getFile() == j)
                    {
                        isHighlighted = true;
                        break;
                    }

                if (isHighlighted)
                    System.out.print("\u001b[31m");

                System.out.print("[");

                Position current = Position.generateFromRankAndFile(i, j);
                if (game.isEmpty(current))
                    System.out.print(" ");
                else
                    System.out.print(game.getPieceAt(current));

                System.out.print("]");

                if (isHighlighted)
                    System.out.print("\u001b[0m");
            }
            System.out.println();
        }
        System.out.println("   A  B  C  D  E  F  G  H ");
        System.out.println();
    }

    public void print() {
        print(null);
    }

    private void debug() {
        System.out.println("This method facilitates tests.");
        //System.out.println(game.getAllDestinationsByColor(Chess.PieceColor.WHITE).length);
    }

    private void printInstructions() {
        System.out.println("Input 'p' to play chess.");
        System.out.println("Input 'l' to list the puzzles in the database.");
        System.out.println("Input 'a <filename>' to add new puzzles into the"
                + " database.");
        System.out.println("Input 'p <number>' to play a puzzle.");
        System.out.println("If you want to end the program, input 'q'.");
    }
}
