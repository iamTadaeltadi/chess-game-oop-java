package com.aait.chess.ui;

import com.aait.chess.core.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ChessUI extends JFrame {
    private Chess game;
    private BoardSquare[][] boardSquares;
    private Position origin;

    public ChessUI() {
        try {
            this.game = new Chess();
        } catch (IllegalArrangementException e) {
            System.out.println(e.getMessage());
        }
        this.boardSquares = new BoardSquare[Chess.BOARD_RANKS][Chess.BOARD_FILES];

        for (int i = 0; i <Chess.BOARD_RANKS; i++) {
            for (int j = 0; j < Chess.BOARD_FILES; j++) {
                BoardSquare square = new BoardSquare((i + j) % 2 == 0, i, j);

                Piece p = this.game.getPieceAt(new Position(i,j));
                if (p == null)
                    square.setPiece();
                else {
                    square.setPiece(p.toString());
                }
               square.addActionListener(new ActionListener() {
                   @Override
                   public void actionPerformed(ActionEvent e) {
                       boardClicked(square.getCoordinates());
                   }
               });
                this.boardSquares[i][j] = square;

            }
        }
        this.origin = null;

        setSize(BoardSquare.size * Chess.BOARD_FILES, BoardSquare.size * Chess.BOARD_RANKS);

        setTitle("Chess");

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        setLayout(new BorderLayout());

        JPanel jPanel = new JPanel();
        jPanel.setSize(BoardSquare.size * Chess.BOARD_FILES, BoardSquare.size * Chess.BOARD_RANKS);
       // jPanel.setMaximumSize(new Dimension(BoardSquare.size * Chess.BOARD_FILES, BoardSquare.size * Chess.BOARD_RANKS));
        jPanel.setLayout(new GridLayout(Chess.BOARD_RANKS,Chess.BOARD_FILES));

        for (BoardSquare[] b :
                this.boardSquares) {
            for (BoardSquare square :
                    b) {
                jPanel.add(square);            }
        }
        jPanel.setVisible(true);


        add(jPanel);
        setVisible(true);
        setResizable(false);
    }

    private void boardClicked(int[] coordinates){
        if(this.origin == null){
            Position p = new Position(coordinates[0],coordinates[1]);

            Piece piece = this.game.getPieceAt(p);

            if(piece!=null && piece.getPieceColor()==this.game.getTurn()){
                this.origin = p;
                this.highlightSquares();
            }
        }else{
            Position destination = new Position(coordinates[0],coordinates[1]);

            Piece p = this.game.getPieceAt(destination);
            if(p!=null && p.getPieceColor()==this.game.getTurn()){
                this.origin = destination;
                this.turnOffHighlights();
                this.highlightSquares();
                return;
            }

            Move move = new Move(this.origin,destination);
            this.game.performMove(move);
            this.updatePieces();
            this.origin = null;
            this.turnOffHighlights();

        }
    }
    private void highlightSquares(){
        ArrayList<Position> destinations = this.game.reachableFrom(this.origin);
        for (Position p :
                destinations) {
            this.boardSquares[p.getRank()][p.getFile()].setHighlight(true);
        }
    }
    private void turnOffHighlights(){
        for (BoardSquare[] squares :
                this.boardSquares) {
            for (BoardSquare square :
                    squares) {
                square.setHighlight(false);
            }
        }
    }

    private void updatePieces(){
        for (int i = 0; i < Chess.BOARD_RANKS; i++) {
            for (int j = 0; j < Chess.BOARD_FILES; j++) {

                Piece p = this.game.getPieceAt(new Position(i,j));
                if(p==null)
                    this.boardSquares[i][j].setPiece();
                else
                    this.boardSquares[i][j].setPiece(p.toString());

            }
        }
    }
}
