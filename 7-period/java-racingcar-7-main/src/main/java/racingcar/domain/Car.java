package racingcar.domain;

import java.util.Objects;
import racingcar.generator.NumberGenerator;
import racingcar.strategy.MovingStrategy;

public class Car {

    public static final int INITIAL_POSITION = 0;

    private final CarName name;
    private final NumberGenerator numberGenerator;
    private final MovingStrategy movingStrategy;

    private int position;

    public Car(final CarName name, final NumberGenerator numberGenerator, final MovingStrategy movingStrategy) {
        this.name = name;
        this.numberGenerator = numberGenerator;
        this.movingStrategy = movingStrategy;
        this.position = INITIAL_POSITION;
    }

    public void move() {
        int randomValue = numberGenerator.generate();
        if (movingStrategy.canMove(randomValue)) {
            position++;
        }
    }

    public boolean hasSamePosition(final int compared) {
        return position == compared;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Car car)) {
            return false;
        }
        return Objects.equals(name, car.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    public CarName getName() {
        return name;
    }

    public int getPosition() {
        return position;
    }
}
