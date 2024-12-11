package racingcar.domain;

import java.util.Objects;
import racingcar.generator.NumberGenerator;

public class Car {

    public static final int INITIAL_POSITION = 0;
    private static final int MOVE_THRESHOLD = 4;

    private final CarName name;
    private final NumberGenerator numberGenerator;
    private int position;

    public Car(final CarName name, final NumberGenerator numberGenerator) {
        this.name = name;
        this.numberGenerator = numberGenerator;
        this.position = INITIAL_POSITION;
    }

    public void move() {
        if (numberGenerator.generate() >= MOVE_THRESHOLD) {
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
