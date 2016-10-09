import java.awt.Point;

public class Board {
    // coordinate system that the game follows
    final static int ROW = 30;
    final static int COL = 40;

    private static Square[][] squares = new Square[COL][ROW];

    static Square setSquare(Square square) {
        return squares[square.getXCoord()][square.getYCoord()] = square;
    }

    static void toSnake(Point point) {
        getSquare(point).toSnake();
    }

    static void toBoard(Point point) {
        getSquare(point).toBoard();
    }

    static void toFood(Point point) {
        getSquare(point).toFood();
    }

    static void toWall(Point point) {
        getSquare(point).toWall();
    }

    static void toPowerUp(Point point) {
        getSquare(point).toPowerUp();
    }

    static boolean isBoard(Point point) {
        return getSquare(point).isBoard();
    }

    static boolean isBoard(int x, int y) {
        return getSquare(x, y).isBoard();
    }

    static boolean isSnake(Point point) {
        return getSquare(point).isSnake();
    }

    static boolean isWall(Point point) {
        return getSquare(point).isWall();
    }

    static boolean isFood(Point point) {
        return getSquare(point).isFood();
    }

    static boolean isPowerUp(Point point) {
        return getSquare(point).isPowerUp();
    }

    static int getSize() {
        return ROW*COL;
    }

    private static Square getSquare(Point point) {
        return squares[point.x][point.y];
    }

    private static Square getSquare(int x, int y) {
        return squares[x][y];
    }
}