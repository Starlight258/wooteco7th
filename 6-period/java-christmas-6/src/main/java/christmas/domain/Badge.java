package christmas.domain;

import java.math.BigDecimal;
import java.util.Arrays;

public enum Badge {

    별("별", new BigDecimal(5000)),
    토끼("토끼", new BigDecimal(10000)),
    산타("산타", new BigDecimal(20000));

    public static final String NONE = "없음";

    private final String name;
    private final BigDecimal atLeastPrice;

    Badge(final String name, final BigDecimal atLeastPrice) {
        this.name = name;
        this.atLeastPrice = atLeastPrice;
    }

    public static String showName(BigDecimal atLeastPrice) {
        Badge[] values = Badge.values();
        Arrays.sort(values, (v1, v2) -> v2.atLeastPrice.compareTo(v1.atLeastPrice));
        for (Badge badge : values) {
            if (atLeastPrice.compareTo(badge.atLeastPrice) >= 0) {
                return badge.name();
            }
        }
        return NONE;
    }
}
