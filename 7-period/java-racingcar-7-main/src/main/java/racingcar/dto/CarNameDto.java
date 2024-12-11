package racingcar.dto;

import java.util.List;
import racingcar.domain.CarName;

public record CarNameDto(List<String> names) {

    public static CarNameDto of(final List<CarName> names) {
        return new CarNameDto(names.stream()
                .map(CarName::getName)
                .toList());
    }
}
