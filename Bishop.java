package com.aait.chess.core;
import java.util.ArrayList;
public class Bishop extends Piece {
    public Bishop() {
        super();
    }

    public Bishop(Chess.PieceColor color) {
        super(color);
    }

    public String toString() {
        if (this.getPieceColor() == Chess.PieceColor.WHITE)
            return "B";
        else
            return "b";
    }

    public ArrayList<Position> allDestinations(Chess chess, Position p) {
        return Bishop.reachablePositions(chess, p);
    }

    static ArrayList<Position> reachablePositions(Chess chess, Position p) {
        //Accessed by Queens, does not have to be public
        ArrayList<Position> result = new ArrayList<Position>();

        int[] rankOffsets = {1, -1, 1, -1};
        int[] fileOffsets = {1, 1, -1, -1};

        for (int d = 0; d < 4; d++) {
            int i = p.getRank() + rankOffsets[d];
            int j = p.getFile() + fileOffsets[d];
            while (i >= 0 && i < Chess.BOARD_RANKS
                    && j >= 0 && j < Chess.BOARD_FILES) {
                Position current = Position.generateFromRankAndFile(i, j);

                if (chess.isEmpty(current))
                    result.add(current);
                else {
                    if (chess.getPieceAt(current).getPieceColor()
                            != chess.getPieceAt(p).getPieceColor())
                        result.add(current);
                    break;
                }
                i += rankOffsets[d];
                j += fileOffsets[d];
            }
        }

        return result;
    }
}
