package com.aait.chess.core;

public class InvalidNumberOfKingsException extends IllegalArrangementException {
    public InvalidNumberOfKingsException() {
        super("In a game of classic chess, there has to be exactly one king of each color.");
    }

    public InvalidNumberOfKingsException(String message) {
        super(message);
    }
}
