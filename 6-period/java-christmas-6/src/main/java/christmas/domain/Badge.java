package christmas.domain;

import java.math.BigDecimal;
import java.util.Arrays;

public enum Badge {

    없음(new BigDecimal(0)),
    별(new BigDecimal(5000)),
    트리(new BigDecimal(10000)),
    산타(new BigDecimal(20000));

    private final BigDecimal minimumValue;

    Badge(final BigDecimal atLeastPrice) {
        this.minimumValue = atLeastPrice;
    }

    public static String showName(BigDecimal totalPrice) {
        return Arrays.stream(Badge.values())
                .sorted((v1, v2) -> v2.minimumValue.compareTo(v1.minimumValue))
                .filter(badge -> totalPrice.compareTo(badge.minimumValue) >= 0)
                .findFirst()
                .map(Badge::name)
                .orElse(없음.name());
    }
}
