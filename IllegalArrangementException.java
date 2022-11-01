package com.aait.chess.core;

public class IllegalArrangementException extends Exception {
    public IllegalArrangementException() {
        super("The string does not represent a valid arrangement of pieces on a board.");
    }

    public IllegalArrangementException(String message) {
        super(message);
    }
}
