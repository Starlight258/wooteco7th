package christmas.domain.discount;

import christmas.domain.Day;
import christmas.domain.Menu.MenuType;
import christmas.domain.Orders;
import java.math.BigDecimal;

public class DayDiscount extends Discount {

    private static final int DISCOUNT_AMOUNT = 2_023;
    private static final String WEEKDAYS_DISCOUNT = "평일 할인";

    public DayDiscount(final Day day, final Orders orders) {
        super(day, orders);
    }

    @Override
    public BigDecimal calculateAmount() {
        if (isApplicable()) {
            if (day.isWeekday()) {
                return BigDecimal.valueOf(DISCOUNT_AMOUNT * orders.countSameTypeMenu(MenuType.DESSERT));
            }
            return BigDecimal.valueOf(DISCOUNT_AMOUNT * orders.countSameTypeMenu(MenuType.MAIN));
        }
        return BigDecimal.ZERO;
    }

    @Override
    public String getName() {
        return WEEKDAYS_DISCOUNT;
    }

    @Override
    protected boolean isWithinDeadline() {
        return day.isInDecember();
    }
}
