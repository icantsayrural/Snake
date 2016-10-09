import java.awt.*;
import java.util.Random;

public class Hole {
    private Point locationOne = new Point();
    private Point locationTwo = new Point();
    private Random random;

    Hole() {
        random = new Random();
    }

    void erase() {
        if (Board.isBoard(locationOne)) {
            Board.toWall(locationOne);
        }

        if (Board.isBoard(locationTwo)) {
            Board.toWall(locationTwo);
        }
    }

    void setRandomLocation() {
        int randomY = random.nextInt(Board.ROW-2) + 1;

        locationOne = new Point(0, randomY);
        locationTwo = new Point(Board.COL-1, randomY);

        Board.toBoard(locationOne);
        Board.toBoard(locationTwo);
    }
}
