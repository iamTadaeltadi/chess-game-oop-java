package com.aait.chess.puzzles;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;

public class PuzzleDatabase {
    public static final String databasePath = "database.txt";
    private Puzzle[] puzzles;

    public PuzzleDatabase() {
        load();
    }
    public void load() {
        try {
            Scanner sc = new Scanner(new FileInputStream(databasePath));
            puzzles = new Puzzle[sc.nextInt()];
            sc.nextLine();
            for (int i = 0; i < puzzles.length; i++)
                puzzles[i] = new Puzzle(sc.nextLine(), sc.nextLine());
            Arrays.sort(puzzles);
            sc.close();
        } catch (FileNotFoundException e) {
            System.out.println("Cannot open the database file.");
            System.exit(0);
        } catch (MalformedPuzzleException e) {
            System.out.println("Malformed puzzle in the database.");
            System.exit(0);
        }
    }
    public void save() {
        try {
            PrintWriter pw = new PrintWriter(databasePath);
            pw.println(puzzles.length);
            for (int i = 0; i < puzzles.length; i++)
                pw.println(puzzles[i]);
            pw.close();
        } catch (FileNotFoundException e) {
            System.out.println("Cannot save into the database file.");
            System.exit(0);
        }
    }
    public void addPuzzlesFromFile(String filename) {
        try {
            Scanner sc = new Scanner(new FileInputStream(filename));
            while (sc.hasNextLine()) {
                Puzzle current = new Puzzle(sc.nextLine(), sc.nextLine());
                if (!contains(current))
                    appendPuzzle(current);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Cannot open the specified file with puzzles.");
            System.exit(0);
        }
        catch (MalformedPuzzleException e) {
            System.out.println("Malformed puzzle in the database.");
            System.exit(0);
        }
    }
    public int getSize() {
        return puzzles.length;
    }
    public Puzzle getPuzzle(int i) {
        return puzzles[i];
    }

    private boolean contains(Puzzle p) {
        for (int i = 0; i < puzzles.length; i++)
            if (puzzles[i].equals(p))
                return true;
        return false;
    }
    private void appendPuzzle(Puzzle p) {
        Puzzle[] newPuzzles = new Puzzle[puzzles.length + 1];
        int i;
        for (i = 0; i < puzzles.length; i++)
            newPuzzles[i] = puzzles[i];
        newPuzzles[i] = p;
        puzzles = newPuzzles;
    }
}
