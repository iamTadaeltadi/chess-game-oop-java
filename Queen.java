package com.aait.chess.core;

import java.util.ArrayList;

public class Queen extends Piece {
    public Queen() {
        super();
    }

    public Queen(Chess.PieceColor color) {
        super(color);
    }

    public String toString() {
        if (this.getPieceColor() == Chess.PieceColor.WHITE)
            return "Q";
        else
            return "q";
    }

    public ArrayList<Position> allDestinations(Chess chess, Position p) {
        ArrayList<Position> result = Rook.reachablePositions(chess, p);
        result.addAll(Bishop.reachablePositions(chess, p));
        return result;

    }
}
