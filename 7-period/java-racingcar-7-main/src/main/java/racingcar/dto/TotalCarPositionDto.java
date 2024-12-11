package racingcar.dto;

import java.util.List;
import racingcar.domain.Car;

public record TotalCarPositionDto(List<CarPositionDto> dtos) {

    public static TotalCarPositionDto of(final List<Car> cars) {
        return new TotalCarPositionDto(cars.stream()
                .map(CarPositionDto::of)
                .toList());
    }

    public record CarPositionDto(String name, int position) {
        public static CarPositionDto of(final Car car) {
            return new CarPositionDto(car.getName(), car.getPosition());
        }
    }
}
