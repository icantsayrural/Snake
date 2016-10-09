public class Food extends Item {

    Food(Snake snake) {
        super(snake);

        setRandomLocation();
    }

    @Override
    protected void erase() {
        if (Board.isFood(location)) {
            toBoard();
        }
    }

    @Override
    protected void transform() {
        Board.toFood(location);
    }
}