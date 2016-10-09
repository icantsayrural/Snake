import java.awt.Point;
import java.util.Random;

abstract public class Item {
    protected Point location;
    protected Random random;
    protected Snake snake;

    Item(Snake snake) {
        random = new Random();
        location = new Point();

        this.snake = snake;
    }

    boolean isEaten() {
        return snake.getHead().equals(location);
    }

    protected void toBoard() {
        Board.toBoard(location);
    }

    protected void setRandomLocation() {
        do {
            location.setLocation(random.nextInt(Board.COL-2) + 1, random.nextInt(Board.ROW-2) + 1);
            //New placement of food shouldn't overlap with the current position of another object
        } while (!Board.isBoard(location));

        transform();
    }

    // abstract methods that must be implemented
    abstract protected void transform();

    abstract protected void erase();
}