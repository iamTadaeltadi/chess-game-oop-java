package com.aait.chess.core;

/**
 * <p>A mutable class for storing and representing a position on a chess board. Contains static methods
 * that generate valid objects after ensuring that the given arguments are valid.
 * Also note how <b>javadoc can contain HTML tags.</b></p>
 * @author Hovak Abramian <a href="mailto:hovag.abramian@aua.am">hovag.abramian@aua.am</a>
 * @author Varduhi Yeghiazaryan <a href="mailto:vyeghiazaryan@aua.am">vyeghiazaryan@aua.am</a>
 * @version 1.0
 * @since 1.0
 */
public class Position {
    //mutable
    /** <p>Position rank on the board, starting from the top.
     * Classic chessboard squares are ranked from 1 to 8. The value 0 corresponds with the 8th rank and
     * a value of 7 corresponds with the 1st rank.</p>
     */
    private int rank;
    /** <p>Position file on the board, starting from the left.
     * Classic chessboard files are from A to H. The value 0 corresponds with A and
     * a value of 7 corresponds with H.</p>
     */
    private int file;


    /** <p>A no-arg constructor, initializes with 0's, corresponds with A8</p>
     */
    public Position() {
        this.rank = 0;
        this.file = 0;
    }

    /** <p>A copy constructor, initializes according to another Position object.</p>
     * @param other The original position, according to which the new object is created.
     */
    public Position(Position other) {
        this.setRank(other.rank);
        this.setFile(other.file);
    }

    /**
     * A parameterized consturctor, initializes the fields with given values.
     * @param rank An integer representing the rank.
     * @param file An integer representing the file.
     */
    public Position(int rank, int file) {
        this.setRank(rank);
        this.setFile(file);
    }

    /**
     * Accessor method for the rank of the position.
     * @return The rank of the position.
     */
    public int getRank() {
        return this.rank;
    }

    /**
     * Accessor method for the file of the position.
     * @return The file of the position.
     */
    public int getFile() {
        return this.file;
    }

    /**
     * Mutator method for the rank of the position.
     * @param rank The new rank.
     */
    public void setRank(int rank) {
        if (rank >= 0 && rank < Chess.BOARD_RANKS)
            this.rank = rank;
    }

    /**
     * Mutator method for the file of the position.
     * @param file The new file.
     */
    public void setFile(int file) {
        if (file >= 0 && file < Chess.BOARD_FILES)
            this.file = file;
    }

    /**
     * Mutator method for the file of the position.
     * @return A string representation of the position on the board.
     */
    public String toString() {
        return "" + (char)('A' + this.file) + (Chess.BOARD_RANKS - this.rank);
    }

    /**
     * <p>A static method that given a valid string indicating a chessboard square in chess notation,
     * e.g. "a8" or "A8", creates a position object after verification. Returns null otherwise.
     * Such methods are known as static factory methods and are advantageous in a multitude of ways.</p>
     *
     * @param s The name of the chessboard square, such as "A8"
     * @return A position object or null.
     */
    public static Position generateFromString(String s) {
        s = s.toLowerCase();
        if (s.length() != 2
                || (s.charAt(0) < 'a' || s.charAt(0) >= 'a' + Chess.BOARD_FILES)
                || (s.charAt(1) < '1' || s.charAt(1) >= '1' + Chess.BOARD_RANKS)
        )
            return null;
        return generateFromRankAndFile(Chess.BOARD_RANKS - s.charAt(1) + '0',
                s.charAt(0) - 'a');
    }

    /**
     * <p>A static method that takes two valid integers representing rank and file,
     * creates a position object after verification. Returns null otherwise.
     * Such methods are known as static factory methods and are advantageous in a multitude of ways.</p>
     *
     * @param rank The name of the chessboard square, such as "A8"
     * @param file An integer, representing
     * @return A position object or null.
     */
    public static Position generateFromRankAndFile(int rank, int file) {
        Position result = null;
        if (rank >= 0 && rank < Chess.BOARD_RANKS
                && file >= 0 && file < Chess.BOARD_FILES
        )
            result = new Position(rank, file);
        return result;
    }



    public boolean equals(Object other) {
        if (other == null || other.getClass() != Position.class)
            return false;
        Position otherPosition = (Position) other;
        return this.rank == otherPosition.rank && this.file == otherPosition.file;
    }
}
