import java.util.ArrayList;

public class Level {
    private static ArrayList<Wall> walls = new ArrayList<Wall>();
    private static Hole hole;

    private final int THRESHOLD = 200;
    private final int WALL_DELTA = 5;

    private int level = 0;
    private Snake snake;

    Level(Snake snake) {
        this.snake = snake;

        hole = new Hole();
    }

    int getLevel() {
        return level;
    }

    void reset() {
        level = 0;

        walls.clear();
    }

    void eraseAll() {
        for(Wall wall : walls) {
            wall.erase();
        }

        hole.erase();
    }

    boolean finishedGame() {
        return level >= 5;
    }

    boolean up(int score) {
        boolean levelUp = false;

        if (score >= THRESHOLD) {
            eraseAll();

            // add new walls
            for(int i=0; i<WALL_DELTA; i++) {
                walls.add(new Wall(snake));
            }
            levelUp = true;

            level++;
        }

        return levelUp;
    }

    void drawWallsAndHoles() {
        for(Wall wall : walls) {
            wall.setRandomLocation();
        }

        hole.setRandomLocation();
    }
}
