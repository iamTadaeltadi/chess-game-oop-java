package com.aait.chess.core;

import java.util.ArrayList;

public class Chess implements Cloneable {
    public enum PieceColor {WHITE, BLACK}

    public static final int BOARD_RANKS = 8;
    public static final int BOARD_FILES = 8;

    public static final int WHITE_PAWN_STARTING_RANK = 6;
    public static final int BLACK_PAWN_STARTING_RANK = 1;


    private Piece[][] board;
    private int numberOfMoves;


    public Chess() throws IllegalArrangementException {
        //has to declare even though that exception can never happen here
        this("rnbqkbnr" +
             "pppppppp" +
             "--------" +
             "--------" +
             "--------" +
             "--------" +
             "PPPPPPPP" +
             "RNBQKBNR",
             PieceColor.WHITE);
    }

    public static void verifyArrangement(String s) throws IllegalArrangementException {
        boolean whiteKingPresent = false, blackKingPresent = false;
        if (s.length() != 64)
            throw new IllegalArrangementException("The length of the arrangement must be 64");
        for (int i = 0; i < 64; i++) {
            if (s.charAt(i) == 'K' || s.charAt(i) == 'L')
                if (!whiteKingPresent)
                    whiteKingPresent = true;
                else
                    throw new InvalidNumberOfKingsException();
            else if (s.charAt(i) == 'k' || s.charAt(i) == 'l')
                if (!blackKingPresent)
                    blackKingPresent = true;
                else
                    throw new InvalidNumberOfKingsException();
        }
        if (!whiteKingPresent || !blackKingPresent)
            throw new InvalidNumberOfKingsException();
    }

    public Chess(String arrangement, PieceColor turn) throws IllegalArrangementException {
        verifyArrangement(arrangement);
        this.numberOfMoves = turn.ordinal();
        this.board = new Piece[BOARD_RANKS][BOARD_FILES];

        for (int i = 0; i < arrangement.length(); i++) {
            switch (arrangement.charAt(i)) {
                case 'R':
                    this.board[i/BOARD_RANKS][i%BOARD_FILES] = new Rook(PieceColor.WHITE);
                    break;
                case 'r':
                    this.board[i/BOARD_RANKS][i%BOARD_FILES] = new Rook(PieceColor.BLACK);
                    break;
                case 'S':
                    this.board[i/BOARD_RANKS][i%BOARD_FILES] = new Rook(PieceColor.WHITE, true);
                    break;
                case 's':
                    this.board[i/BOARD_RANKS][i%BOARD_FILES] = new Rook(PieceColor.BLACK, true);
                    break;
                case 'N':
                    this.board[i/BOARD_RANKS][i%BOARD_FILES] = new Knight(PieceColor.WHITE);
                    break;
                case 'n':
                    this.board[i/BOARD_RANKS][i%BOARD_FILES] = new Knight(PieceColor.BLACK);
                    break;
                case 'B':
                    this.board[i/BOARD_RANKS][i%BOARD_FILES] = new Bishop(PieceColor.WHITE);
                    break;
                case 'b':
                    this.board[i/BOARD_RANKS][i%BOARD_FILES] = new Bishop(PieceColor.BLACK);
                    break;
                case 'K':
                    this.board[i/BOARD_RANKS][i%BOARD_FILES] = new King(PieceColor.WHITE);
                    break;
                case 'k':
                    this.board[i/BOARD_RANKS][i%BOARD_FILES] = new King(PieceColor.BLACK);
                    break;
                case 'L':
                    this.board[i/BOARD_RANKS][i%BOARD_FILES] = new King(PieceColor.WHITE, true);
                    break;
                case 'l':
                    this.board[i/BOARD_RANKS][i%BOARD_FILES] = new King(PieceColor.BLACK, true);
                    break;
                case 'Q':
                    this.board[i/BOARD_RANKS][i%BOARD_FILES] = new Queen(PieceColor.WHITE);
                    break;
                case 'q':
                    this.board[i/BOARD_RANKS][i%BOARD_FILES] = new Queen(PieceColor.BLACK);
                    break;
                case 'P':
                    this.board[i/BOARD_RANKS][i%BOARD_FILES] = new Pawn(PieceColor.WHITE);
                    break;
                case 'p':
                    this.board[i/BOARD_RANKS][i%BOARD_FILES] = new Pawn(PieceColor.BLACK);
                    break;
            }
        }
    }

    public Piece[][] getBoard() {
        Piece[][] boardCopy = new Piece[BOARD_RANKS][BOARD_FILES];
        for (int i = 0; i < BOARD_RANKS; i++)
            for (int j = 0; j < BOARD_FILES; j++)
                if (this.board[i][j] != null)
                    boardCopy[i][j] = (Piece) this.board[i][j].clone();
        return boardCopy;
    }

    public Chess clone() {
        try {
            Chess copy = (Chess) super.clone();
            copy.board = this.getBoard();
            return copy;
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }

    public PieceColor getTurn() {
        return PieceColor.values()[this.numberOfMoves % 2];
    }

    public boolean isGameOver() {
        return false;
    }

    public boolean isEmpty(Position p) {
        return this.board[p.getRank()][p.getFile()] == null;
    }

    public Piece getPieceAt(Position p) {
        return this.board[p.getRank()][p.getFile()];
    }

    public ArrayList<Position> reachableFrom(Position origin) {
        if (origin == null || this.isEmpty(origin))
            return null;

        return board[origin.getRank()][origin.getFile()].allDestinations(this,
                origin);
    }

    public boolean performMove(Move m) {
        Position o = m.getOrigin();
        Position d = m.getDestination();

        if (this.getPieceAt(o).getPieceColor() != this.getTurn())
            return false;

        ArrayList<Position> reachable = this.reachableFrom(o);

        // A backup is created first. If the king is exposed to a threat after a potential move,
        // the backup is restored. Hence, the following copy needs to be a deep copy.
        Piece[][] boardCopy = this.getBoard();

        for (int i = 0; i < reachable.size(); i++)
            if (d.getRank() == reachable.get(i).getRank()
                    && d.getFile() == reachable.get(i).getFile()) {
                this.board[d.getRank()][d.getFile()] = this.board[o.getRank()][o.getFile()];
                this.board[o.getRank()][o.getFile()] = null;
                if (this.board[d.getRank()][d.getFile()] instanceof Rook)
                    ((Rook)this.board[d.getRank()][d.getFile()]).setHasMoved(true);
                else if (this.board[d.getRank()][d.getFile()] instanceof King)
                    ((King)this.board[d.getRank()][d.getFile()]).setHasMoved(true);

                if (isKingUnderAttack(this.getTurn())) {
                    this.board = boardCopy;
                    return false;
                }

                this.numberOfMoves++;
                return true;
            }

        return false;
    }

    /**
     * Determines whether the king of the given color is in check.
     * @param kingColor The color of the king in question.
     * @return True, if the king in question is under attack by the opponent.
     */
    public boolean isKingUnderAttack(PieceColor kingColor) {
        Position kingPosition = null;
        PieceColor opponentColor;
        ArrayList<Position> p = null;

        //find the king
        for (int i = 0; i < BOARD_RANKS; i++)
            for (int j = 0; j < BOARD_FILES; j++)
                if (board[i][j] != null
                        && board[i][j].getPieceColor() == kingColor
                        && board[i][j] instanceof King)
                    kingPosition = Position.generateFromRankAndFile(i, j);

        //determine the opposite color
        if (kingColor == PieceColor.WHITE)
            opponentColor = PieceColor.BLACK;
        else
            opponentColor = PieceColor.WHITE;

        p = getAllDestinationsByColor(opponentColor);

        for (int i = 0; i < p.size(); i++)
            if (p.get(i).equals(kingPosition))
                return true;

        return false;
    }

    /**
     * A method that accumulates every square that can be reached by every piece of the given
     * color.
     *
     * @param color
     * @return An array with all reachable squares by all pieces of a color
     */
    public ArrayList<Position> getAllDestinationsByColor(PieceColor color) {
        ArrayList<Position> result = new ArrayList<Position>();

        for (int i = 0; i < BOARD_RANKS; i++)
            for (int j = 0; j < BOARD_FILES; j++)
                if (board[i][j] != null && board[i][j].getPieceColor() == color) {
                    ArrayList<Position> current = board[i][j].allDestinations(this,
                            Position.generateFromRankAndFile(i, j));

                    duplicates:
                    for (int k = 0; k < current.size(); k++) {
                        for (int l = 0; l < result.size(); l++)
                            if (current.get(k).equals(result.get(l)))
                                continue duplicates;
                        result.addAll(current);
                    }
                }

        return result;
    }
}
