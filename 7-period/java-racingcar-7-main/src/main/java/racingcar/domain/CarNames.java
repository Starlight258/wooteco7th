package racingcar.domain;

import java.util.Collections;
import java.util.List;
import racingcar.exception.AppException;
import racingcar.exception.ErrorMessage;

public class CarNames {

    private final List<CarName> names;

    public CarNames(final List<CarName> names) {
        validate(names);
        this.names = names;
    }

    public static CarNames of(final List<String> values) {
        return new CarNames(values.stream()
                .map(CarName::new)
                .toList());
    }

    private void validate(final List<CarName> names) {
        if (isDuplicated(names)) {
            throw new AppException(ErrorMessage.INVALID_INPUT);
        }
    }

    private boolean isDuplicated(final List<CarName> names) {
        return names.size() != names.stream().distinct().count();
    }

    public List<CarName> getNames() {
        return Collections.unmodifiableList(names);
    }
}
