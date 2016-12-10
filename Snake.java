import java.util.ArrayList;
import java.awt.Point;

public class Snake {
    final static int NORTH = 1;
    final static int SOUTH = 2;
    final static int EAST = 3;
    final static int WEST = 4;

    final private int DEFAULT_LIFE = 3;

    private ArrayList<Point> body = new ArrayList<Point>();
    private int direction;

    private boolean dead;
    private boolean calledMoveSinceDirectionChange;
    private int life;

    Snake() {
        resetLife();
        resetPosition();
    }

    int getSize(){
        return body.size();
    }

    Point getHead() {
        return body.get(0);
    }

    Point getTail() {
        return body.get(body.size()-1);
    }

    int getLife() {
        return life;
    }

    void lifeDown() {
        life--;
    }

    void lifeUp() {
        life++;
    }

    void resetLife() {
        life = DEFAULT_LIFE;
    }

    void resetPosition() {
        for (Point point : body) {
            Board.toBoard(point);
        }

        body.clear();

        for (int i=3; i>=1; i--) {
            Point newPoint = new Point(i, 1);
            body.add(newPoint);
        }

        direction = EAST;
        calledMoveSinceDirectionChange = false;
        dead = false;

        drawSnake();
    }

    void move() {
        Point newHead = getNewHead();

        // if the snake will die in the next step, do not continue to move the snake
        if (!dead) {
            Point tailPoint = new Point(getTail());
            int lastTailPosition = body.size() - 1;

            // the snake moves by having the body parts replace the preceding body part
            // if the body part is a head, then the direction determines its next location
            for (int i=lastTailPosition; i>=1; i--) {
                body.get(i).setLocation(body.get(i-1));
            }
            getHead().setLocation(newHead);

            //abandon previous tail
            Board.toBoard(tailPoint);

            drawSnake();
        }
    }

    void changeDirection(int direction) {
        if (!isInvalidDirectionChange(direction, this.direction) && calledMoveSinceDirectionChange) {
            this.direction = direction;

            // this is to prevent the case when the directions change too fast and the snake ends up eating itself backwards
            calledMoveSinceDirectionChange = false;
        }
    }

    void grow() {
        if (getSize() < Board.getSize()) {
            // position of new tail is determined by the position of the last two points in body
            // however, if there isn't enough room, you should grow in the direction perpendicular to it
            Point tail = getTail();
            Point secondTail = body.get(body.size()-2);

            int firstTailX = tail.x;
            int firstTailY = tail.y;

            int secondTailX = secondTail.x;
            int secondTailY = secondTail.y;

            Point newTail;

            if (firstTailX == secondTailX) {
                newTail = new Point(firstTailX, firstTailY + (firstTailY - secondTailY));

                if (Board.isWall(newTail)) {
                    int newTailY = firstTailY;

                    if (Board.isBoard(firstTailX-1, newTailY)) {
                        newTail.setLocation(firstTailX-1, newTailY);
                    } else if (Board.isBoard(firstTailX+1, newTailY)) {
                        newTail.setLocation(firstTailX+1, newTailY);
                    } else {
                        dead = true;
                    }
                }
            } else {
                newTail = new Point(firstTailX + (firstTailX - secondTailX), firstTailY);

                if (Board.isWall(newTail)) {
                    int newTailX = firstTailX;

                    if (Board.isBoard(newTailX, firstTailY+1)) {
                        newTail.setLocation(newTailX, firstTailY+1);
                    } else if (Board.isBoard(newTailX, firstTailY-1)) {
                        newTail.setLocation(newTailX, firstTailY-1);
                    } else {
                        dead = true;
                    }
                }
            }

            body.add(newTail);
            drawSnake();
        }
    }

    boolean isDead(){
        return dead;
    }

    boolean hasLife() {
        return life > 0;
    }

    private boolean isInvalidDirectionChange(int initDirection, int newDirection) {
        boolean isInvalid = false;

        if (initDirection == newDirection) {
            isInvalid = true;
        } else {
            switch (initDirection) {
                case NORTH:
                    isInvalid = newDirection == SOUTH;
                    break;
                case SOUTH:
                    isInvalid = newDirection == NORTH;
                    break;
                case EAST:
                    isInvalid = newDirection == WEST;
                    break;
                case WEST:
                    isInvalid = newDirection == EAST;
                    break;
            }
        }

        return isInvalid;
    }

    private Point getNewHead() {
        Point newPoint = new Point(getHead().x, getHead().y);

        // Calculates the new head location
        // and checks for collision with snake itself and the wall
        switch (direction) {
            case NORTH:
                newPoint.translate(0, -1);
                break;
            case EAST:
                newPoint.translate(1, 0);
                break;
            case WEST:
                newPoint.translate(-1, 0);
                break;
            case SOUTH:
                newPoint.translate(0, 1);
                break;
        }

        dead = Board.isSnake(newPoint) || Board.isWall(newPoint);

        // handle going through walls
        if (newPoint.x <= 0) {
            newPoint.setLocation(Board.COL-1, newPoint.y);
            changeDirection(EAST);
        } else if (newPoint.x >= (Board.COL-1)) {
            newPoint.setLocation(0, newPoint.y);
            changeDirection(WEST);
        }

        calledMoveSinceDirectionChange = true;

        return newPoint;
    }

    private void drawSnake() {
        for (int i=0; i<getSize(); i++) {
            Board.toSnake(body.get(i));
        }
    }
}
