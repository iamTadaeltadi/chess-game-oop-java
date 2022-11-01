package com.aait.chess.puzzles;

public class MalformedPuzzleException extends Exception {
    public MalformedPuzzleException() {
        super("Malformed puzzle.");
    }

    public MalformedPuzzleException(String message) {
        super(message);
    }
}
