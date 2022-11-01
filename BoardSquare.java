package com.aait.chess.ui;


import javax.swing.*;
import java.awt.*;

public class BoardSquare extends JButton {
    private final Color LIGHT = Color.gray;
    private final Color DARK = Color.green;
    private int x;
    private int y;
    private Color board;
    public static final int size = 80;

    public BoardSquare(boolean flag, int x, int y) {
        this.x = x;
        this.y = y;
        if (flag)
            this.board = LIGHT;
        else
            this.board = DARK;
        setBackground(this.board);
        setSize(size, size);
    }

    public int[] getCoordinates() {
        return new int[]{this.x, this.y};
    }

    public void setPiece(String piece) {
        switch (piece.charAt(0)) {
            case 'R':
                setIcon(new ImageIcon("src/resource/RookW.png"));
                break;
            case 'r':
                setIcon(new ImageIcon("src/resource/RookB.png"));
                break;
            case 'S':
                setIcon(new ImageIcon("src/resource/RookW.png"));
                break;
            case 's':
                setIcon(new ImageIcon("src/resource/RookB.png"));
                break;
            case 'N':
                setIcon(new ImageIcon("src/resource/KnightW.png"));
                break;
            case 'n':
                setIcon(new ImageIcon("src/resource/KnightB.png"));
                break;
            case 'B':
                setIcon(new ImageIcon("src/resource/BishopW.png"));
                break;
            case 'b':
                setIcon(new ImageIcon("src/resource/BishopB.png"));
                break;
            case 'K':
                setIcon(new ImageIcon("src/resource/KingW.png"));
                break;
            case 'k':
                setIcon(new ImageIcon("src/resource/KingB.png"));
                break;
            case 'L':
                setIcon(new ImageIcon("src/resource/KingW.png"));
                break;
            case 'l':
                setIcon(new ImageIcon("src/resource/KingB.png"));
                break;
            case 'Q':
                setIcon(new ImageIcon("src/resource/QueenW.png"));
                break;
            case 'q':
                setIcon(new ImageIcon("src/resource/QueenB.png"));
                break;
            case 'P':
                setIcon(new ImageIcon("src/resource/PawnW.png"));
                break;
            case 'p':
                setIcon(new ImageIcon("src/resource/PawnB.png"));
                break;
        }
    }

    public void setPiece(){
        setIcon(null);
    }

    public void setHighlight(boolean highlight){
        if(highlight)
            setBackground(Color.red);
        else
            setBackground(this.board);
    }
}
