package com.aait.chess.core;

public class Move {
    //immutable, contains mutable references
    private Position origin;
    private Position destination;

    public Move(Position origin, Position destination) {
        this.origin = new Position(origin);
        this.destination = new Position(destination);
    }

    public Move(Move other) {
        this.origin = new Position(other.origin);
        this.destination = new Position(other.destination);
    }

    public Position getOrigin() {
        return new Position(this.origin);
    }

    public Position getDestination() {
        return new Position(this.destination);
    }

    public String toString() {
        return this.origin.toString() + " " + this.destination.toString();
    }
}
