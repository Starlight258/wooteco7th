package racingcar.domain;

import java.util.Collections;
import java.util.List;
import racingcar.exception.ErrorMessage;
import racingcar.generator.NumberGenerator;

public class Cars {

    private final List<Car> cars;

    public Cars(final List<Car> cars) {
        this.cars = cars;
    }

    public static Cars of(final List<CarName> values, final NumberGenerator numberGenerator) {
        return new Cars(values.stream()
                .map(carName -> new Car(carName, numberGenerator))
                .toList());
    }

    public void moveAll() {
        for (Car car : cars) {
            car.move();
        }
    }

    public List<CarName> calculateWinners() {
        int maxDistance = getMaxPositions();
        return cars.stream()
                .filter(car -> car.hasSamePosition(maxDistance))
                .map(Car::getName)
                .toList();
    }

    public int getMaxPositions() {
        return cars.stream()
                .mapToInt(Car::getPosition)
                .max()
                .orElseThrow(() -> new IllegalStateException(ErrorMessage.NOT_FOUND_CAR.getMessage()));
    }

    public List<Car> getCars() {
        return Collections.unmodifiableList(cars);
    }
}
