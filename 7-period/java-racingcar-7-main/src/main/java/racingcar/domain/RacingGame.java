package racingcar.domain;

import java.util.List;

public class RacingGame {

    private final Cars cars;
    private final Attempt attempt;

    public RacingGame(final Cars cars, final Attempt attempt) {
        this.cars = cars;
        this.attempt = attempt;
    }

    public List<Car> race() {
        cars.moveAll();
        attempt.decreaseAttempt();
        return cars.getCars();
    }

    public List<String> calculateWinners() {
        return cars.calculateWinners();
    }

    public boolean isEnd() {
        return attempt.isZero();
    }


}
