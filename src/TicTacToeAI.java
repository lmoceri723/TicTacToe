/**
 * By Landon Moceri
 * If anyone sees this, please ignore it
 * Current Errors:
 * Should use JFrame instead of processing
 * Should extend TicTacToe
 * Game bugs out after 4 moves
 * AI always loses
 */

import java.util.ArrayList;
import java.util.Scanner;

public class TicTacToeAI extends TicTacToe
{
    /** Returns an arraylist of coordinate pairs represented by arrays
     * This represents all spaces that the AI can move in **/
    public ArrayList<Integer[]> getValidSpaces(Square[][] board)
    {
        // Iterates through the board
        ArrayList<Integer[]> moves = new ArrayList<>();
        for (int i = 0; i < board.length; i++)
        {
            for (int j = 0; j < board.length; j++)
            {
                // If the space at the board is
                if (this.getBoard()[i][j].getMarker().equals(BLANK))
                {
                    // Adds the pair to the ArrayList
                    Integer[] pair = new Integer[2];
                    pair[0] = i;
                    pair[1] = j;
                    moves.add(pair);
                }
            }
        }
        return moves;
    }

    // Creates a new board with a move at coordinates pair and returns it
    public Square[][] doMove(Square[][] board, Integer[] pair)
    {
        Square[][] newBoard = new Square[3][3];

        // Copies over the new board
        for (int i = 0; i < board.length; i++)
        {
            for (int j = 0; j < board.length; j++)
            {
                newBoard[i][j].setMarker(board[i][j].getMarker());
            }
        }
        // Makes a move at the coordinates of the pair
        newBoard[pair[0]][pair[1]].setMarker(O_MARKER);
        return newBoard;
    }

    public Integer[] aiMove(Square[][] board)
    {
        // Set of all open squares
        ArrayList<Integer[]> openSquares = getValidSpaces(board);
        // Returns -1 if there are no open squares
        if (openSquares.isEmpty())
        {
            return new Integer[]{-1};
        }

        // Set of all boards w/ the moves made
        ArrayList<Square[][]> doneMoves = new ArrayList<>();
        for(Integer[] pair : openSquares)
        {
            doneMoves.add(doMove(board, pair));
        }

        // WINNING IS NEVER CHECKED
        // Result of the move, as well as the coordinates it was made at
        // Where recursion happens
        ArrayList<Integer[]> results = new ArrayList<>();
        for(Square[][] doneMove : doneMoves)
        {
            results.add(aiMove(doneMove));
        }

        ArrayList<Integer> winCases = new ArrayList<>();
        for(Integer[] result : results)
        {
            winCases.add(result[0]);
        }

        // Relative to the AI
        // 2 = AI win
        // 1 = Player win
        // 0 = Tie
        boolean willWin = false;
        boolean willDraw = false;
        // Goes over win cases, if there's a win in any case, willWin is true, and it stops iterating\
        // If a draw is present, set willDraw to true and keep iterating
        // Unnecessary for loop
        for (Integer winCase : winCases)
        {
            if (winCase == 2)
            {
                willWin = true;
                break;
            }
            else if (winCase == 0)
            {
                willDraw = true;
            }
        }

        if (willWin)
        {
            int index = winCases.indexOf(2);
            Integer[] pair = openSquares.get(index);
            return new Integer[]{2, pair[0], pair[1]};
        }

        else if (willDraw)
        {
            int index = winCases.indexOf(0);
            Integer[] pair = openSquares.get(index);
            return new Integer[]{0, pair[0], pair[1]};
        }
        else
        {
            // Issue could be here, what happens if it's empty
            int index = 0;
            Integer[] pair = openSquares.get(index);
            return new Integer[]{0, pair[0], pair[1]};
        }

    }

    @Override
    public void takeTurn(int row, int col)
    {
            getBoard()[row][col].setMarker(X_MARKER);
    }

    @Override
    public void run()
    {
//        Scanner input = new Scanner(System.in);
//
//        System.out.println("Welcome to Tic Tac Toe!");
//
//        // Loop until there is a winner or no more turns
//        while(this.checkWin() && this.checkTurn())
//        {
//            // Collect input
//            this.printBoard();
//            System.out.println("Enter your Row Pick:" );
//            int row = input.nextInt();
//            System.out.println("Enter your Col Pick:" );
//            int col = input.nextInt();
//            // Marks board with X
//            this.takeTurn(row, col);
//        }
//
//        this.printBoard();
//        setGameOver();
//
//        // Determine if there was a winner
//        if(this.checkWin()) {
//            System.out.println("Game ends in a tie!");
//        } else {
//            this.markWinningSquares();
//            if (this.turn%2 == 0) {
//                this.winner = O_MARKER;
//                System.out.println("O Wins!");
//            } else {
//                this.winner = X_MARKER;
//                System.out.println("X Wins!");
//            }
//        }
//
//
//
//        printBoard();
//        if (checkWin(this.board) == 0)
//        {
//            if (isAiTurn())
//            {
//                // returns the pair of coordinates and win state, doesn't make the move
//                Integer[] result = aiMove(board);
//                if (result[0] != -1)
//                {
//                    board[result[1]][result[2]] = 2;
//                }
//            }
//        }
    }
}