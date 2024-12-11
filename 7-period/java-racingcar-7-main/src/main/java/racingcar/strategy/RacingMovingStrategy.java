package racingcar.strategy;

public class RacingMovingStrategy implements MovingStrategy {

    private static final int MOVE_THRESHOLD = 4;

    @Override
    public boolean canMove(int value) {
        return value > MOVE_THRESHOLD;
    }
}
