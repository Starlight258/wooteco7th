package christmas.domain;

import java.util.Arrays;

public enum Promotion {
    Champagne("샴페인", 1, 120_000),
    NONE("없음", 0, 0);
    private final String promotionName;
    private final int giveAmount;
    private final int appliabeCriteriaMoney;

    Promotion(final String promotionName, final int giveAmount, final int appliabeCriteriaMoney) {
        this.promotionName = promotionName;
        this.giveAmount = giveAmount;
        this.appliabeCriteriaMoney = appliabeCriteriaMoney;
    }

    public static Promotion of(final int priceBeforeDiscount) {
        return Arrays.stream(values())
                .filter(promotion -> promotion.appliabeCriteriaMoney <= priceBeforeDiscount)
                .findFirst()
                .orElse(NONE);

    }

}
