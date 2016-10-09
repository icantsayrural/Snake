import javax.swing.JComponent;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Square extends JComponent {
    private final Color SNAKE = new Color(204, 255, 153);
    private final Color FOOD = new Color(255, 102, 102);
    private final Color BOARD = Color.LIGHT_GRAY;
    private final Color WALL = Color.GRAY;
    private final Color POWER_UP = Color.YELLOW;

    private final int SNAKE_PATTERN_SIZE = 3;

    private final int xCoord;
    private final int yCoord;

    private Color color;

    Square(int xCoord, int yCoord) {
        this.xCoord = xCoord;
        this.yCoord = yCoord;
        this.color = BOARD;
    }

    void toSnake(){
        color = SNAKE;
    }

    void toFood() {
        color = FOOD;
    }

    void toBoard() {
        color = BOARD;
    }

    void toPowerUp() {
        color = POWER_UP;
    }

    void toWall() {
        color = WALL;
    }

    boolean isBoard() {
        return color == BOARD;
    }

    boolean isSnake() {
        return color == SNAKE;
    }

    boolean isFood() {
        return color == FOOD;
    }

    boolean isWall() {
        return color == WALL;
    }

    boolean isPowerUp() {
        return color == POWER_UP;
    }

    int getXCoord() {
        return xCoord;
    }

    int getYCoord() {
        return yCoord;
    }

    @Override
    public void paintComponent(Graphics g) {
        if (isBoard() || isFood() || isWall()) {
            g.setColor(color);
        } else {
            Graphics2D g2 = (Graphics2D) g;
            Color gradientColor;

            if (isSnake()) {
                gradientColor = Color.GREEN;
            } else {
                gradientColor = Color.ORANGE;
            }

            g2.setPaint(new GradientPaint(0, 0, color, getWidth(), 0, gradientColor));
        }

        g.fillRect(0, 0, getWidth(), getHeight());

        if (isWall()) {
            g.setColor(Color.BLACK);
            g.drawLine(0, 0, getWidth(), getHeight());
            g.drawLine(getWidth(), 0, 0, getHeight());
        }
    }
}