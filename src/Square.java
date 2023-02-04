import java.awt.*;

/**
 * A class written to support the TicTacToe Game.
 * Each Square object is one position of the TicTacToe
 * board. It maintains information on the marker, its
 * location on the board, and whether it is part
 * of the winning set.
 *
 * @author: Nandhini Namasivayam
 * @version: Jan 2023
 */

public class Square {

    // Instance variables
    private String marker;
    private final TicTacToeViewer board;
    private final Image[] images;
    private final int row;
    private final int col;
    private boolean isWinningSquare;

    /**
     * Constructor to initialize one Square of the
     * TicTacToe board
     * @param row the row the square is in
     * @param col the column the square is in
     */

    // Constructor, initializes instance variables
    public Square(int row, int col, TicTacToeViewer board) {
        this.board = board;
        images = board.getImages();

        this.row = row;
        this.col = col;

        this.marker = TicTacToe.BLANK;
        this.isWinningSquare = false;
    }

    /******************** Getters and Setters ********************/
    public String getMarker() {
        return this.marker;
    }

    public void setMarker(String marker) {
        this.marker = marker;
    }

    public void setWinningSquare() {
        this.isWinningSquare = true;
    }

    /**
     * Checks if the square has the BLANK marker
     * @return True if the square is empty, False otherwise
     */
    public boolean isEmpty() {
        return this.marker.equals(TicTacToe.BLANK);
    }

    /**
     * @return the marker for the square
     */
    public String toString() {
        return this.marker;
    }

    // Draws the square on the board
    public void draw(Graphics g)
    {
        // Fills in the green background for winning squares
        if (isWinningSquare)
        {
            g.setColor(Color.GREEN);
            g.fillRect((col + 1) * TicTacToeViewer.ROW_SIZE, (row + 1) * TicTacToeViewer.ROW_SIZE, TicTacToeViewer.ROW_SIZE, TicTacToeViewer.ROW_SIZE);
        }

        // Draws the image if the square has a value
        if (marker.equals(TicTacToe.X_MARKER))
        {
            g.drawImage(images[0], (col + 1) * TicTacToeViewer.ROW_SIZE, (row + 1) * TicTacToeViewer.ROW_SIZE, TicTacToeViewer.ROW_SIZE, TicTacToeViewer.ROW_SIZE, board);
        }
        else if (marker.equals(TicTacToe.O_MARKER))
        {
            g.drawImage(images[1], (col + 1) * TicTacToeViewer.ROW_SIZE, (row + 1) * TicTacToeViewer.ROW_SIZE, TicTacToeViewer.ROW_SIZE, TicTacToeViewer.ROW_SIZE, board);
        }
        // Draws the border of the square
        g.setColor(Color.BLACK);
        g.drawRect((row + 1) * TicTacToeViewer.ROW_SIZE, (col + 1) * TicTacToeViewer.ROW_SIZE, TicTacToeViewer.ROW_SIZE, TicTacToeViewer.ROW_SIZE);
    }
}

