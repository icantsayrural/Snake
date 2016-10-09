public class PowerUp extends Item {
    private boolean active;

    private final int DEFAULT_THRESHOLD = 80;
    private final int FACTOR = 2;

    private int threshold;

    PowerUp(Snake snake) {
        super(snake);

        threshold = DEFAULT_THRESHOLD;
        setActive(false);
    }

    boolean thresholdReached(int score) {
        return score >= threshold;
    }

    void increaseThreshold(int score) {
        threshold = score * FACTOR;
    }

    void setDefaultThreshold() {
        threshold = DEFAULT_THRESHOLD;
    }

    void setActive(boolean active) {
        this.active = active;
    }

    boolean isActive() {
        return active;
    }

    @Override
    protected void erase() {
        if (Board.isPowerUp(location)) {
            toBoard();
        }
    }

    @Override
    protected void transform() {
        Board.toPowerUp(location);
    }
}
