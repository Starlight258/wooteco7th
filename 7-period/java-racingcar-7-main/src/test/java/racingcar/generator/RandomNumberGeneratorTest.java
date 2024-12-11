package racingcar.generator;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RandomNumberGeneratorTest {

    @Test
    @DisplayName("0과 9 사이의 랜덤값을 생성한다.")
    void generate() {
        // Given
        RandomNumberGenerator randomNumberGenerator = new RandomNumberGenerator();

        // When
        int number = randomNumberGenerator.generate();

        // Then
        assertThat(number).isGreaterThanOrEqualTo(0).isLessThanOrEqualTo(9);
    }
}
