import java.awt.Point;
import java.util.Random;

abstract public class Object {
    protected Point location;
    protected Random random;
    protected Snake snake;

    Object(Snake snake) {
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
        // In case snake gets too big
        do {
            location.setLocation(random.nextInt(Board.COL), random.nextInt(Board.ROW));
            //New placement of food shouldn't overlap with the current position of another object
        } while (!Board.isBoard(location));

        transform();
    }

    // abstract methods that must be implemented
    abstract protected void transform();

    abstract protected void erase();
}