import javax.swing.*;
import java.awt.*;

public class TicTacToeViewer extends JFrame
{
    // Instance variables
    private final Image[] images;
    public static final int WINDOW_WIDTH = 750;
    public static final int WINDOW_HEIGHT = 750;
    public static final int ROW_SIZE = 150;
    public static final Font FONT = new Font("SansSerif", Font.ITALIC, ROW_SIZE / 3);
    public static final Font WIN_FONT = new Font("SansSerif", Font.BOLD, ROW_SIZE / 2);
    private final TicTacToe t;
    public TicTacToeViewer(TicTacToe t)
    {
        // Initialize images

        images = new Image[2];
        images[0] = new ImageIcon("Resources/X.png").getImage();
        images[1] = new ImageIcon("Resources/O.png").getImage();
        this.t = t;

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setTitle("TicTacToe");
        this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        this.setVisible(true);
    }

    public Image[] getImages() {
        return images;
    }

    public void paint(Graphics g) {
        g.setColor(Color.WHITE);
        g.drawRect(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);

        drawBoard(g);
        for (Square[] row : t.getBoard())
        {
            for (Square s : row)
            {
                s.draw(g);
            }
        }


        if (t.getGameOver())
        {
            if (t.checkTie())
            {
                String msg = "It's a Tie!";
                g.setFont(WIN_FONT);
                int width = g.getFontMetrics().stringWidth(msg);
                g.drawString(msg, WINDOW_WIDTH / 2 - (width / 2), WINDOW_HEIGHT - WIN_FONT.getSize());
            }
            String winner = t.getWinner() + " Wins!";
            g.setFont(WIN_FONT);
            int width = g.getFontMetrics().stringWidth(winner);
            g.drawString(winner, WINDOW_WIDTH / 2 - (width / 2), WINDOW_HEIGHT - WIN_FONT.getSize());
        }
    }

    public void drawBoard(Graphics g)
    {
        g.setFont(FONT);
        g.setColor(Color.RED);
        for (int i = 0; i < 3; i++)
        {
            int width = g.getFontMetrics().stringWidth(Integer.toString(i));
            int x_offset = (ROW_SIZE * ( 2 * i + 3)) / 2;
            int y_offset = ROW_SIZE * 2 / 3;

            g.drawString(Integer.toString(i), x_offset - width / 2, y_offset);
        }

        for (int i = 0; i < 3; i++)
        {
            int height = FONT.getSize();
            int x_offset = ROW_SIZE * 2 / 3;
            int y_offset = (ROW_SIZE * ( 2 * i + 3)) / 2;

            g.drawString(Integer.toString(i), x_offset - height / 2, y_offset + height / 3);
        }
    }
}

