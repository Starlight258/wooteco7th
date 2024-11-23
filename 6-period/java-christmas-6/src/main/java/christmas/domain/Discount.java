package christmas.domain;

import static christmas.domain.vo.MenuType.DESSERT;
import static christmas.domain.vo.MenuType.MAIN;

import christmas.domain.vo.Menu;
import christmas.domain.vo.MenuType;
import christmas.domain.vo.MyBadge;
import christmas.domain.vo.MyDiscountResult;
import christmas.domain.vo.MyPromotion;
import christmas.domain.vo.OrderMenus;
import christmas.domain.vo.VisitDate;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum Discount {

    CHRISTMAS_DDAY("크리스마스 디데이 할인",
            LocalDate.of(2023, 12, 1),
            LocalDate.of(2023, 12, 25),
            MenuType.NONE
    ) {
        @Override
        protected boolean isValid(LocalDate visitDate, MenuType menuType) {
            return isValidDateTIme(visitDate, this.getStartDate(), this.getEndDate());
        }

        @Override
        protected DiscountResult calculateDiscountAmount(LocalDate visitDate, int menuCount) {
            int daysSinceStart = visitDate.getDayOfMonth() - 1;
            return new DiscountResult(this,
                    BASE_CHRISTMAS_DISCOUNT + (daysSinceStart * DAILY_INCREASE_AMOUNT)
            );
        }
    },

    Weekday("평일 할인",
            LocalDate.of(2023, 12, 1),
            LocalDate.of(2023, 12, 31),
            DESSERT) {
        @Override
        protected boolean isValid(LocalDate visitDate, MenuType menuType) {
            return isValidDateTIme(visitDate, this.getStartDate(), this.getEndDate())
                    && !Discount.isWeekend(visitDate)
                    && DESSERT.equals(menuType);
        }

        @Override
        protected DiscountResult calculateDiscountAmount(LocalDate visitDate, int dessertCount) {
            return new DiscountResult(this,
                    dessertCount * WEEKDAY_WEEKEND_DISCOUNT);
        }
    },

    Weekend("주말 할인",
            LocalDate.of(2023, 12, 1),
            LocalDate.of(2023, 12, 31),
            MAIN) {
        @Override
        protected boolean isValid(LocalDate visitDate, MenuType menuType) {
            return isValidDateTIme(visitDate, this.getStartDate(), this.getEndDate())
                    && Discount.isWeekend(visitDate)
                    && MAIN.equals(menuType);
        }

        @Override
        protected DiscountResult calculateDiscountAmount(LocalDate visitDate, int mainCount) {
            return new DiscountResult(this,
                    mainCount * WEEKDAY_WEEKEND_DISCOUNT
            );
        }
    },

    SpecialDay("특별 할인",
            LocalDate.of(2023, 12, 1),
            LocalDate.of(2023, 12, 31),
            MenuType.NONE) {
        @Override
        protected boolean isValid(LocalDate visitDate, MenuType menuType) {
            return isValidDateTIme(visitDate, this.getStartDate(), this.getEndDate())
                    && SPECIAL_DAYS.contains(visitDate.getDayOfMonth());
        }

        @Override
        protected DiscountResult calculateDiscountAmount(LocalDate visitDate, int menuCount) {
            return new DiscountResult(this, SPECIAL_DISCOUNT);
        }
    },

    NONE("없음", LocalDate.MIN, LocalDate.MIN, MenuType.NONE) {
        @Override
        protected boolean isValid(LocalDate visitDate, MenuType menuType) {
            return false;
        }

        @Override
        protected DiscountResult calculateDiscountAmount(LocalDate visitDate, int menuCount) {
            return new DiscountResult(this, 0);
        }
    };

    private final String discountName;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final MenuType menuType;

    private static final int BASE_CHRISTMAS_DISCOUNT = 1000;
    private static final int DAILY_INCREASE_AMOUNT = 100;
    private static final int WEEKDAY_WEEKEND_DISCOUNT = 2023;
    private static final int SPECIAL_DISCOUNT = 1000;
    private static final List<Integer> SPECIAL_DAYS = List.of(3, 10, 17, 24, 25, 31);

    protected abstract boolean isValid(LocalDate visitDate, MenuType menuType);

    protected abstract DiscountResult calculateDiscountAmount(LocalDate visitDate, int menuCount);

    Discount(final String discountName, final LocalDate startDate, final LocalDate endDate, final MenuType menuType) {
        this.discountName = discountName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.menuType = menuType;
    }

    public static Discounting discount(VisitDate visitDate, OrderMenus orderMenus) {
        List<DiscountResult> allDiscountResults = orderMenus.getOrderMenus().entrySet().stream()
                .flatMap(entry -> calculateMenuDiscount(visitDate, entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());

        // 전체 before
        int priceBeforeDiscount = calculateTotalPrice(orderMenus);

        return new Discounting(
                orderMenus,
                visitDate,
                priceBeforeDiscount,
                new MyDiscountResult(allDiscountResults),
                new MyPromotion(Promotion.of(priceBeforeDiscount)),
                calculateTotalDiscountAmount(allDiscountResults),
                new MyBadge(Badge.of(calculateTotalDiscountAmount(allDiscountResults)))
        );
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    protected static boolean isWeekend(LocalDate date) {
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        return dayOfWeek == DayOfWeek.FRIDAY || dayOfWeek == DayOfWeek.SATURDAY;
    }

    private static Stream<DiscountResult> calculateMenuDiscount(
            VisitDate visitDate,
            Menu menu,
            int quantity) {
        return Arrays.stream(values())
                .filter(discount -> discount.isValid(visitDate.getVisitDate(), menu.getMenuType()))
                .map(validDiscount -> validDiscount.calculateDiscountAmount(
                        visitDate.getVisitDate(),
                        quantity
                ));
    }

    private static int calculateTotalPrice(OrderMenus orderMenus) {
        return orderMenus.getOrderMenus().entrySet().stream()
                .mapToInt(entry -> entry.getKey().getPrice() * entry.getValue())
                .sum();
    }

    private static int calculateTotalDiscountAmount(List<DiscountResult> discountResults) {
        return discountResults.stream()
                .mapToInt(DiscountResult::getAmount)
                .sum();
    }


    private static boolean isValidDateTIme(LocalDate today, LocalDate startDate, LocalDate endDate) {
        int compareStartDate = today.compareTo(startDate);
        int compareEndDate = today.compareTo(endDate);
        if (compareStartDate >= 0 && compareEndDate >= 0) {
            return true;
        }
        return false;
    }
}
