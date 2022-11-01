package com.aait.chess.core;

import java.util.ArrayList;

public class King extends Piece {
    private boolean hasMoved;

    public King() {
        this(Chess.PieceColor.WHITE);
    }

    public King(Chess.PieceColor color) {
        this(color, false);
    }

    public King(Chess.PieceColor color, boolean hasMoved) {
        super(color);
        this.hasMoved = hasMoved;
    }

    public boolean getHasMoved() {
        return this.hasMoved;
    }

    public void setHasMoved(boolean hasMoved) {
        this.hasMoved = hasMoved;
    }

    public String toString() {
        if (this.getPieceColor() == Chess.PieceColor.WHITE)
            if (this.hasMoved)
                return "L";
            else
                return "K";
        else
            if (this.hasMoved)
                return "l";
            else
                return "k";
    }

    public ArrayList<Position> allDestinations(Chess chess, Position p) {
        //preliminary, check mechanism not implemented.
        int[][] pattern = {
                {p.getRank() - 1, p.getFile() - 1},
                {p.getRank(),     p.getFile() - 1},
                {p.getRank() + 1, p.getFile() - 1},

                {p.getRank() - 1, p.getFile()},
                {p.getRank() + 1, p.getFile()},

                {p.getRank() - 1, p.getFile() + 1},
                {p.getRank(),     p.getFile() + 1},
                {p.getRank() + 1, p.getFile() + 1}
        };

        ArrayList<Position> result = new ArrayList<Position>();

        for (int i = 0; i < pattern.length; i++) {
            Position potential = Position.generateFromRankAndFile(pattern[i][0],
                    pattern[i][1]);
            if (potential != null && (chess.isEmpty(potential)
                    || chess.getPieceAt(potential).getPieceColor()
                    != chess.getPieceAt(p).getPieceColor()))
                result.add(potential);
        }

        return result;
    }

    public King clone() {
        // This method is not really necessary
        return (King) super.clone();
    }
}
