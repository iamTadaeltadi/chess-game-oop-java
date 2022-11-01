package com.aait.chess.puzzles;

import com.aait.chess.core.Chess;

public final class Puzzle implements Comparable<Puzzle> {
    public enum Difficulty {EASY, MEDIUM, HARD, UNSPECIFIED}

    private String description;
    private String arrangement;

    private Difficulty difficulty;
    private Chess.PieceColor turn;

    public Puzzle(String details, String description) throws MalformedPuzzleException {
        this.description = description;

        try {
            String[] components = details.split(",");
            Chess.verifyArrangement(components[0]);
            this.arrangement = components[0];
            this.turn = Chess.PieceColor.valueOf(components[1]);
            this.difficulty = Difficulty.valueOf(components[2]);
        } catch (Exception e) {
            throw new MalformedPuzzleException();
        }
    }

    public Puzzle(Puzzle other) {
        this.difficulty = other.difficulty;
        this.turn = other.turn;
        this.description = other.description;
        this.arrangement = other.arrangement;
    }

    public int compareTo(Puzzle other) {
        if (this.difficulty != other.difficulty)
            return this.difficulty.ordinal() - other.difficulty.ordinal();
        if (this.turn != other.turn)
            return this.turn.ordinal() - other.turn.ordinal();
        return this.arrangement.compareTo(other.arrangement);
    }

    public boolean equals(Object other) {
        if (other != null && other.getClass() == Puzzle.class) {
            Puzzle otherPuzzle = (Puzzle) other;
            return otherPuzzle.arrangement.equals(this.arrangement)
                    && otherPuzzle.difficulty == this.difficulty
                    && otherPuzzle.turn == this.turn;
        }
        return false;
    }


    public String getDescription() {
        return description;
    }

    public String getArrangement() {
        return arrangement;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public Chess.PieceColor getTurn() {
        return turn;
    }

    public String toString() {
        return arrangement + "," + turn + "," + difficulty + "\n" + description;
    }
}
