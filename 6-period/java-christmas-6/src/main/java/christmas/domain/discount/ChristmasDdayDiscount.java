package christmas.domain.discount;

import christmas.domain.Day;
import christmas.domain.Orders;
import java.math.BigDecimal;

public class ChristmasDdayDiscount extends Discount {

    private static final int DISCOUNT_START_PRICE = 1_000;
    private static final int DISCOUNT_DAY_UNIT = 100;

    public ChristmasDdayDiscount(final Day day, final Orders orders) {
        super(day, orders);
    }

    @Override
    public BigDecimal calculateAmount() {
        if (isApplicable()) {
            BigDecimal discount = BigDecimal.valueOf(DISCOUNT_START_PRICE);
            BigDecimal untilChristmas = new BigDecimal(DISCOUNT_DAY_UNIT).multiply(
                    new BigDecimal(day.diffFromFirstDay()));
            return discount.add(untilChristmas);
        }
        return BigDecimal.ZERO;
    }

    @Override
    public String getName() {
        return "크리스마스 디데이 할인";
    }

    @Override
    protected boolean isWithinDeadline() {
        return !day.isExceedChristmas();
    }
}
