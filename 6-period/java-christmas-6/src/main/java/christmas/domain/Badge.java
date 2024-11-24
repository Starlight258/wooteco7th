package christmas.domain;

import java.math.BigDecimal;
import java.util.Arrays;

public enum Badge {

    없음(new BigDecimal(0)),
    별(new BigDecimal(5000)),
    트리(new BigDecimal(10000)),
    산타(new BigDecimal(20000));

    public static final String NONE = "없음";

    private final BigDecimal atLeastPrice;

    Badge(final BigDecimal atLeastPrice) {
        this.atLeastPrice = atLeastPrice;
    }

    public static String showName(BigDecimal atLeastPrice) {
        return Arrays.stream(Badge.values())
                .sorted((v1, v2) -> v2.atLeastPrice.compareTo(v1.atLeastPrice))
                .filter(badge -> atLeastPrice.compareTo(badge.atLeastPrice) >= 0)
                .findFirst()
                .map(Badge::name)
                .orElse(NONE);
    }
}
