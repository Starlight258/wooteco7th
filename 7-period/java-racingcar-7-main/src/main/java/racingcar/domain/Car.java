package racingcar.domain;

import java.util.Objects;
import racingcar.generator.NumberGenerator;

public class Car {

    private static final int MOVE_THRESHOLD = 4;
    private final CarName name;
    private final NumberGenerator numberGenerator;
    private int position;

    public Car(final CarName name, final NumberGenerator numberGenerator, final int position) {
        this.name = name;
        this.numberGenerator = numberGenerator;
        this.position = position;
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

    public String getName() {
        return name.getName();
    }

    public int getPosition() {
        return position;
    }
}
