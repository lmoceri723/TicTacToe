/**
 * A TicTacToe game adopted from CodeHS
 * Written to be used for either the console or
 * the TicTacToeView class
 * Each position on the board is a Square object
 * which keeps track of the marker ("X", "O")
 *
 * @author: Nandhini Namasivayam
 * @version: Jan 2023
 */

import java.util.Scanner;

public class TicTacToe
{
    /** Board Markers **/
    public static final String X_MARKER = "X";
    public static final String O_MARKER = "O";
    public static final String BLANK = "-";

    /** Winning Directions **/
    public static final int ROW_WIN = 1;
    public static final int COL_WIN = 2;
    public static final int DIAGONAL_RIGHT_WIN = 3;
    public static final int DIAGONAL_LEFT_WIN = 4;

    /** Winning Stats **/
    private String winner;      // Provides the marker of the winner
    private int winDirection;   // Provides the direction of the win
                                // following the win direction final variables above
    private int winIndex;       // Provides the index of the row/col of the win
    private int turn;

    private final Square[][] board;
    private boolean isGameOver;

    private final TicTacToeViewer window;

    /**
     * Constructor which initialized the board with BLANKS.
     * The winner is also initialized to BLANK.
     * The view is initialized with this TicTacToe object
     */
    public TicTacToe() {
        // Initializes an instance of the viewer
        this.window = new TicTacToeViewer(this);

        // Initialize Squares in the board
        this.board = new Square[3][3];
        for(int row = 0; row < this.board.length; row++) {
            for(int col = 0; col< this.board[row].length; col++) {
                this.board[row][col] = new Square(row, col, window);
            }
        }

        // Initialize winning stats variables
        this.isGameOver = false;
        this.turn = 0;
        this.winner = BLANK;
        this.winIndex = -1;
        this.winDirection = -1;
    }

    /******************** Methods You May Find Helpful ********************/
    public Square[][] getBoard() {
        return this.board;
    }

    public String getWinner() {
        return this.winner;
    }

    public boolean getGameOver() {
        return this.isGameOver;
    }

    public boolean checkTie() {
        return this.isGameOver && this.winner.equals(BLANK);
    }

    /**
     * Gets the direction and index of the win
     * and marks the winning squares
     */
    private void markWinningSquares() {
        for(int i=0; i<3; i++) {
            switch (this.winDirection) {
                case TicTacToe.ROW_WIN -> this.board[this.winIndex][i].setWinningSquare();
                case TicTacToe.COL_WIN -> this.board[i][this.winIndex].setWinningSquare();
                case TicTacToe.DIAGONAL_RIGHT_WIN -> this.board[i][i].setWinningSquare();
                case TicTacToe.DIAGONAL_LEFT_WIN -> this.board[i][2 - i].setWinningSquare();
            }
        }
    }

    /**
     * Maintains the main game loop
     * Runs until a player wins or there is a tie.
     */
    public void run() {
        Scanner input = new Scanner(System.in);

        System.out.println("Welcome to Tic Tac Toe!");

        // Loop until there is a winner or no more turns
        while(this.checkWin() && this.checkTurn()) {
            this.printBoard();
            window.repaint();
            System.out.println("Enter your Row Pick:" );
            int row = input.nextInt();
            System.out.println("Enter your Col Pick:" );
            int col = input.nextInt();
            if(this.pickLocation(row, col)) {
                this.takeTurn(row, col);
            } else {
                System.out.println("That space is taken, or you entered an invalid row/col");
            }
        }

        window.repaint();
        this.printBoard();
        this.isGameOver = true;

        // Determine if there was a winner
        if(this.checkWin()) {
            System.out.println("Game ends in a tie!");
        } else {
            this.markWinningSquares();
            if (this.turn%2 == 0) {
                this.winner = O_MARKER;
                System.out.println("O Wins!");
            } else {
                this.winner = X_MARKER;
                System.out.println("X Wins!");
            }
        }
    }


    /******************* Do NOT Modify Code Below this Line ********************
     * Places an X or an O in the provided row and col
     * @param row row to place character in indexed at 0
     * @param col column to place character in indexed at 0
     */
    private void takeTurn(int row, int col) {
        if(this.turn % 2 == 0) {
            this.board[row][col].setMarker(X_MARKER);
        }
        else {
            this.board[row][col].setMarker(O_MARKER);
        }
        this.turn++;
    }

    /**
     * Checks if the selected location is valid.
     * A location is valid if it is empty "-"
     * @param row selected row number indexed at 0
     * @param col selected column number indexed at 0
     * @return True if the location is available, False otherwise
     */
    private boolean pickLocation(int row, int col) {
        if(row < 3 && col < 3) {
            return this.board[row][col].isEmpty();
        }
        return false;
    }

    /**
     * Check if there's a win on the board
     * @return True if there's a win and False otherwise
     */
    public boolean checkWin() {
        int rowIndex = checkRow();
        int colIndex = checkCol();
        int diag = checkDiag();

        if (rowIndex != -1) {
            this.winDirection = ROW_WIN;
            this.winIndex = rowIndex;
            return false;
        } else if (colIndex != -1) {
            this.winDirection = COL_WIN;
            this.winIndex = colIndex;
            return false;
        } else if (diag != -1) {
            this.winDirection = diag;
            return false;
        }

        return true;
    }

    /**
     * Checks for a win in the row
     * @return the row index if there's a win and -1 otherwise
     */
    private int checkRow() {
        int win = -1;
        for (int i = 0; i < this.board.length; i++) {
            if (!this.board[i][0].isEmpty()) {
                if (this.board[i][0].getMarker().equals(this.board[i][1].getMarker()) &&
                        this.board[i][0].getMarker().equals(this.board[i][2].getMarker())) {
                    win = i;
                }
            }
        }
        return win;
    }

    /**
     * Checks for a column win
     * @return The column index if there's a win and -1 otherwise
     */
    private int checkCol() {
        int win = -1;
        for (int i = 0; i < this.board[0].length; i++)
        {
            if (!this.board[0][i].isEmpty())  {
                if (this.board[0][i].getMarker().equals(this.board[1][i].getMarker()) &&
                        this.board[0][i].getMarker().equals(this.board[2][i].getMarker())) {
                    win = i;
                }
            }
        }
        return win;
    }

    /**
     * Checks for a diagonal win
     * @return DIAGONAL_RIGHT, DIAGONAL_LEFT or -1 if there is no win
     */
    private int checkDiag() {
        int win = -1;

        // Hard coded right diagonal check
        if (!this.board[0][0].isEmpty()) {
            if (this.board[0][0].getMarker().equals(this.board[1][1].getMarker()) &&
                    this.board[0][0].getMarker().equals(this.board[2][2].getMarker())) {
                win = DIAGONAL_RIGHT_WIN;
            }
        }

        // Hard coded left diagonal check
        if (!this.board[0][2].isEmpty()) {
            if (this.board[0][2].getMarker().equals(this.board[1][1].getMarker()) &&
                    this.board[0][2].getMarker().equals(this.board[2][0].getMarker())) {
                win = DIAGONAL_LEFT_WIN;
            }
        }

        return win;
    }

    /**
     * Check if there are any turns left
     * There can only be a max of 9 turns taken
     * @return True if there is a turn left, False otherwise
     */
    private boolean checkTurn() {
        return this.turn < 9;
    }

    /**
     * Prints the board to the console
     */
    private void printBoard() {
        System.out.println("  0 1 2");
        int row = 0;
        for(Square[] array : this.board) {
            System.out.print(row + " ");
            for(Square item : array) {
                System.out.print(item + " ");

            }
            row++;
            System.out.println();
        }
    }

    public static void main(String[] args) {
        TicTacToe game = new TicTacToe();
        game.run();
    }
}