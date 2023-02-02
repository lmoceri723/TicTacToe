import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;

public class TicTacToeViewer extends JFrame
{
    private Image[] images;
    public static final int WINDOW_WIDTH = 750;
    public static final int WINDOW_HEIGHT = 750;
    public static final int ROW_SIZE = 150;
    public static final Font FONT = new Font("SansSerif", Font.PLAIN, ROW_SIZE / 3);;
    private TicTacToe t;
    public TicTacToeViewer(TicTacToe t) {

        images = new Image[2];
        images[0] = new ImageIcon("Resources/X.png").getImage();;
        images[1] = new ImageIcon("Resources/O.png").getImage();;
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

        if (t.getWinner().equals(TicTacToe.TIE))
        {
            // print tie
        }
        else if (t.getWinner().equals(TicTacToe.X_MARKER))
        {
            // print x wins
        }
        else if (t.getWinner().equals(TicTacToe.O_MARKER))
        {
            // print o wins
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
//
//        g.drawString("0", (ROW_SIZE * 3 / 2) - width / 2, 125);
//        g.drawString("1", 210 + 150, 125);
//        g.drawString("2", 210 + 300, 125);
//
//        g.drawString("0", 90, 240);
//        g.drawString("1", 90, 240 + 150);
//        g.drawString("2", 90, 240 + 300);
    }
}

