/**
 * By Landon Moceri
 * If anyone sees this, please ignore it
 * Current Errors:
 * Should use JFrame instead of processing
 * Should extend TicTacToe
 * Game bugs out after 4 moves
 * AI always loses
 */

import processing.core.PApplet;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class TicTacToeAI extends PApplet
{
    public static final int X = 1;
    public Integer[][] board = new Integer[3][3];
    public int[] winningPos = new int[4];

    public void settings(){
        size(1200, 1200);
    }

    public void setup()
    {
        background(255);
        noFill();
        strokeWeight(10);

        line(400, 0, 400, 1200);
        line(800, 0, 800, 1200);
        line(0, 400, 1200, 400);
        line(0, 800, 1200, 800);

        strokeWeight(20);

        for (int i = 0; i < 3; i++)
        {
            for (int j = 0; j < 3; j++)
            {
                board[i][j] = 0;
            }
        }
    }

    public void drawBoard()
    {
        for (int i = 0; i < 3; i++)
        {
            for (int j = 0; j < 3; j++)
            {
                drawSquare(i, j);
            }
        }
    }

    public void drawSquare(int x, int y)
    {
        if (board[x][y] == X)
        {
            drawX(x * 400, y * 400);
        }
        else if (board[x][y] == 2)
        {
            drawO(x * 400, y * 400);
        }
    }

    public void drawX(int x, int y)
    {
        line(x + 50, y + 50,  x + 350, y + 350);
        line(x + 350, y + 50, x + 50, y + 350);
    }

    public void drawO(int x, int y)
    {
        circle(x + 200, y + 200, 300);
    }

    public void mousePressed()
    {
        if (!(checkWin(board) == 0))
        {
            return;
        }

        int x = getPos(mouseX);
        int y = getPos(mouseY);
        if (board[x][y] == 0)
        {
            board[x][y] = X;
            drawBoard();
        }
    }

    public boolean isAiTurn()
    {
        int count = 0;

        for (int i = 0; i < 3; i++)
        {
            for (int j = 0; j < 3; j++)
            {
                if (board[i][j] == 0)
                {
                    count++;
                }
            }
        }

        return count % 2 == 0;
    }

    public int getPos(int coords)
    {
        if (coords > 800)
        {
            return 2;
        }
        else if (coords > 400)
        {
            return X;
        }

        return 0;
    }

    public int checkRows(Integer[][] board)
    {
        for (int i = 0; i < 3; i++)
        {
            winningPos = new int[]{i * 400 + 200, 0, i * 400 + 200, 1200};
            if (board[i][0] == 1 && board[i][1] == 1  && board[i][2] == 1)
            {
                return 1;
            }
            else if (board[i][0] == 2 && board[i][1] == 2  && board[i][2] == 2)
            {
                return 2;
            }
        }
        return 0;
    }

    public int checkCols(Integer[][] board)
    {
        for (int i = 0; i < 3; i++)
        {
            winningPos = new int[]{0, i * 400 + 200, 1200, i * 400 + 200};
            if (board[0][i] == 1 && board[1][i] == 1  && board[2][i] == 1)
            {
                return 1;
            }
            else if (board[0][i] == 2 && board[1][i] == 2  && board[2][i] == 2)
            {
                return 2;
            }
        }
        return 0;
    }

    public int checkDiag(Integer[][] board)
    {
        winningPos = new int[]{0, 0, 1200, 1200};
        if (board[0][0]== 1 && board[1][1] == 1 && board[2][2] == 1)
        {
            return 1;
        }
        else if (board[0][0]== 2 && board[1][1] == 2 && board[2][2] == 2)
        {
            return 2;
        }

        winningPos = new int[]{0, 1200, 1200, 0};
        if (board[0][2]== 1 && board[1][1] == 1 && board[2][0] == 1)
        {
            return 1;
        }
        else if (board[0][2]== 2 && board[1][1] == 2 && board[2][0] == 2)
        {
            return 2;
        }

        return 0;
    }

    public int checkWin(Integer[][] board)
    {
        if (checkRows(board) != 0)
        {
            return checkRows(board);
        }
        if (checkCols(board) != 0)
        {
            return checkCols(board);
        }

        return checkDiag(board);
    }
    /**
    Start of recursive/AI methods
     **/
    // Returns an arraylist of coordinate pairs in arrays
    // Represents all the coordinates of the open spaces
    public ArrayList<Integer[]> getValidSpaces(Integer[][] board)
    {
        ArrayList<Integer[]> moves = new ArrayList<>();
        for (int i = 0; i < board.length; i++)
        {
            for (int j = 0; j < board.length; j++)
            {
                if (board[i][j] == 0)
                {
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
    public Integer[][] doMove(Integer[][] board, Integer[] pair)
    {
        Integer[][] newBoard = new Integer[3][3];

        for (int i = 0; i < board.length; i++)
        {
            System.arraycopy(board[i], 0, newBoard[i], 0, board.length);
        }

        newBoard[pair[0]][pair[1]] = 2;
        return newBoard;
    }

    // Try each move, then recursively try all other moves after that, and so on
    // Returns the earliest move resulting in a win
    public Integer[] aiMove(Integer[][] board)
    {
        if (checkWin(board) == 2)
        {
            //return new Integer[]{2, , };
        }

        if (checkWin(board) == 1)
        {
            //return new Integer[]{1, , };
        }

        if (getValidSpaces(board).isEmpty())
        {
            //return new Integer[]{0, , };
        }

        ArrayList<Integer[]> openSquares = getValidSpaces(board);

        ArrayList<Integer[][]> possibleMoves = new ArrayList<>();
        for (Integer[] pair : openSquares) {
            possibleMoves.add(doMove(board, pair));
        }

        ArrayList<Integer[]> values = new ArrayList<>();
        for (Integer[][] newBoard : possibleMoves) {
            values.add(aiMove(newBoard));
        }

        // Iterate through the values, checks if any value has a 2 as its first index
        // represents a win for the AI
        // return the coordinates of the move from openSquares at the corresponding index
        return new Integer[]{0, 0, 0};
    }

    public void draw()
    {
        drawBoard();
        if (checkWin(board) == 0)
        {
            if (isAiTurn())
            {
                // returns the pair of coordinates and win state, doesn't make the move
                Integer[] result = aiMove(board);
                if (result[0] != -0)
                {
                    board[result[1]][result[2]] = 2;
                }
            }
        }
        else
        {
            strokeWeight(30);
            stroke(255, 0, 0);
            line(winningPos[0], winningPos[1], winningPos[2], winningPos[3]);
            stroke(0, 0, 0);
            strokeWeight(20);
            drawBoard();
        }
    }

    public static void main(String[] args)
    {
        String[] appletArgs = new String[] { "TicTacToeAI" };
        PApplet.main(appletArgs);
    }
}

/** Set of all open squares
        // If open squares is empty ...
        if (checkWin(board) == 2)
        {
        return new Integer[2, ];
        }

        // Checks draw, if this is true, then there are no open spaces
        // Must be checked AFTER EITHER PLAYER WINS
        ArrayList<Integer[]> openSquares = getValidSpaces(board);
        if (openSquares.isEmpty())
        {
        return new Integer[]{-1, -1, -1};
        }

        // Set of all boards w/ the moves made
        ArrayList<Integer[][]> possibleMoves = new ArrayList<>();
        for(Integer[] pair : openSquares)
        {
        possibleMoves.add(doMove(board, pair));
        }

        // Result of the move, as well as the coordinates it was made at
        // CheckWin is never called
        // Where recursion happens
        ArrayList<Integer[]> results = new ArrayList<>();
        for(Integer[][] doneMove : possibleMoves)
        {
        results.add(aiMove(doneMove));
        }

        ArrayList<Integer> winCases = new ArrayList<>();
        for(Integer[] result : results)
        {
        winCases.add(result[0]);
        }

        // Something goes wrong when no moves can be made
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
        return new Integer[]{0, pair[0], pair[1]};**/