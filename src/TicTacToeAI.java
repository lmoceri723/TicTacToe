// By Landon Moceri
// If anyone sees this, please ignore it
import processing.core.PApplet;

import java.util.ArrayList;

public class TicTacToeAI extends PApplet
{
    public Integer[][] board = new Integer[3][3];
    public int round;
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
        if (board[x][y] == 1)
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
            board[x][y] = 1;
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

        if (count % 2 == 0)
        {
            return true;
        }
        return false;
    }

    public int getPos(int coords)
    {
        if (coords > 800)
        {
            return 2;
        }
        else if (coords > 400)
        {
            return 1;
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

    // Returns all the coordinates of the open spaces
    public ArrayList<Integer[]> getValidSpaces(Integer[][] board)
    {
        ArrayList<Integer[]> moves = new ArrayList<Integer[]>();
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
    public Integer[][] doMove(Integer[] pair)
    {
        Integer[][] newBoard = new Integer[3][3];

        for (int i = 0; i < board.length; i++)
        {
            for (int j = 0; j < board.length; j++)
            {
                newBoard[i][j] = board[i][j];
            }
        }

        newBoard[pair[0]][pair[1]] = 2;
        return newBoard;
    }

    public Integer[] aiMove(Integer[][] board)
    {
        // Set of all open squares
        ArrayList<Integer[]> openSquares = getValidSpaces(board);

        // Set of all boards w/ the moves made
        ArrayList<Integer[][]> doneMoves = new ArrayList<Integer[][]>();
        for(Integer[] square : openSquares)
        {
            doneMoves.add(doMove(square));
        }

        // Result of the move, as well as the coordinates it was made at
        ArrayList<Integer[]> results = new ArrayList<Integer[]>();
        for(Integer[][] doneMove : doneMoves)
        {
            results.add(aiMove(doneMove));
        }

        ArrayList<Integer> winCases = new ArrayList<Integer>();
        for(Integer[] result : results)
        {
            winCases.add(result[0]);
        }

        // Something goes wrong when no moves can be made
        // Relative to the AI
        boolean willWin = false;
        boolean willDraw = false;
        for (Integer[] result : results)
        {
            if (result[0] == 2)
            {
                willWin = true;
                break;
            }
            else if (result[0] == 2)
            {
                willDraw = true;
            }
        }

        if (willWin)
        {
            int index = winCases.indexOf(2);
            Integer[] pair = openSquares.get(index);
            Integer[] array = {2, pair[0], pair[1]};
            return array;
        }

        else if (willDraw)
        {
            int index = winCases.indexOf(0);
            Integer[] pair = openSquares.get(index);
            Integer[] array = {0, pair[0], pair[1]};
            return array;
        }
        else
        {
            int index = 0;
            Integer[] pair = openSquares.get(index);
            Integer[] array = {0, pair[0], pair[1]};
            return array;
        }

    }

    public void draw()
    {
        drawBoard();
        if (checkWin(board) == 0)
        {
            if (isAiTurn())
            {
                aiMove(board);;
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
