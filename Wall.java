public class Wall extends Item {
    Wall(Snake snake) {
        super(snake);
    }

    @Override
    protected void erase() {
        if (Board.isWall(location)) {
            toBoard();
        }
    }

    @Override
    protected void transform() {
        Board.toWall(location);
    }

    @Override
    protected void setRandomLocation() {
        do {
            // wall shouldn't get too close with a snake's starting position
            // wall shouldn't get too close to holes
            location.setLocation(random.nextInt(Board.COL-3) + 3, random.nextInt(Board.ROW-3) + 3);
            //New placement of food shouldn't overlap with the current position of another object
        } while (!Board.isBoard(location));

        transform();
    }
}
