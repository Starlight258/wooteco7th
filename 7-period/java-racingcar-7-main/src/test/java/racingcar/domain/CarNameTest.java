package racingcar.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import racingcar.fake.ForwardNumberGenerator;
import racingcar.fake.StopNumberGenerator;

class CarNameTest {

    @Test
    @DisplayName("차를 정상적으로 생성한다.")
    void 생성_성공() {
        // Given

        // When & Then
        assertThatCode(() -> {
            makeCar(new ForwardNumberGenerator());
        }).doesNotThrowAnyException();
    }

    private Car makeCar(NumberGenerator numberGenerator) {
        return new Car(numberGenerator, new CarName("밍트"), 0);
    }

    @ParameterizedTest
    @MethodSource
    @DisplayName("차를 정상적으로 움직인다.")
    void 이동_성공(NumberGenerator generator, int score) {
        // Given
        Car car = makeCar(generator);

        // When & Then
        car.move();

        assertThat(car.getScore()).isEqualTo(score);
    }

    private static Stream<Arguments> 이동_성공() {
        return Stream.of(
                Arguments.of(new ForwardNumberGenerator(), 1),
                Arguments.of(new StopNumberGenerator(), 0)
        );
    }

    @Test
    @DisplayName("점수가 동일한지 확인한다.")
    void 점수매칭_성공() {
        // Given
        Car car = makeCar(new ForwardNumberGenerator());

        // When & Then
        assertThat(car.isMatchedScore(0)).isTrue();
    }
}
