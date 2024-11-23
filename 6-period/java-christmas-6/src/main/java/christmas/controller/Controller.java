package christmas.controller;

import static christmas.exception.ErrorMessage.INVALID_ORDER;

import christmas.domain.Day;
import christmas.domain.Menu;
import christmas.domain.Order;
import christmas.domain.Orders;
import christmas.domain.PromotionProcessor;
import christmas.domain.Quantity;
import christmas.exception.CustomIllegalArgumentException;
import christmas.support.StringFormatter;
import christmas.util.Converter;
import christmas.view.InputView;
import christmas.view.OutputView;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Controller {

    private static final String ORDER_REGEX = "([ㄱ-ㅎ|ㅏ-ㅣ|가-힣]+)-(\\d+)";
    private static final String COMMA = ",";
    private static final Pattern PATTERN = Pattern.compile(ORDER_REGEX);
    private static final int YEAR = 2023;
    private static final int DECEMBER = 12;

    private final InputView inputView;
    private final OutputView outputView;
    private final StringFormatter stringFormatter;

    public Controller(final InputView inputView, final OutputView outputView, final StringFormatter stringFormatter) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.stringFormatter = stringFormatter;
    }

    public void process() {
        outputView.commentWelcomeMessage();
        Day visitDay = createDay();
        Orders orders = createOrders();
        showOrders(orders, visitDay);
        checkPromotion(visitDay, orders);
    }

    private void checkPromotion(final Day visitDay, final Orders orders) {
        PromotionProcessor promotionProcessor = new PromotionProcessor(visitDay, orders);
        Optional<Order> optionalOrder = promotionProcessor.checkGift();
        outputView.showBonusMenu();
        outputView.showMessage(stringFormatter.makeOptionalOrderMessage(optionalOrder));
        outputView.showPromotionList();
        showDiscount(visitDay, promotionProcessor, optionalOrder);
    }

    private void showDiscount(final Day visitDay, final PromotionProcessor promotionProcessor,
                              final Optional<Order> optionalOrder) {
        boolean noPromotion = !promotionProcessor.checkAtLeastPrice();
        BigDecimal untilChristmasDiscount = promotionProcessor.checkUntilChristmasDiscount();
        BigDecimal dayDiscount = promotionProcessor.checkDayDiscount();
        BigDecimal specialDiscount = promotionProcessor.checkSpecialDiscount();
        BigDecimal giftDiscount = promotionProcessor.makeGiftDiscount(optionalOrder);
        outputView.showMessage(
                stringFormatter.makePromotionListMessage(untilChristmasDiscount, dayDiscount, specialDiscount,
                        giftDiscount, visitDay, noPromotion));
        showTotalDiscount(promotionProcessor, noPromotion, untilChristmasDiscount, dayDiscount, specialDiscount, giftDiscount);
    }

    private void showTotalDiscount(final PromotionProcessor promotionProcessor, final boolean noPromotion,
                           final BigDecimal untilChristmasDiscount, final BigDecimal dayDiscount,
                           final BigDecimal specialDiscount, final BigDecimal giftDiscount) {
        BigDecimal totalDiscount = addTotalDiscount(untilChristmasDiscount, dayDiscount, specialDiscount, giftDiscount,
                noPromotion);
        BigDecimal discountPriceExceptBonusPrice = totalDiscount.subtract(giftDiscount);
        BigDecimal expectPrice = promotionProcessor.calculateExpectPrice(discountPriceExceptBonusPrice);
        outputView.showMessage(stringFormatter.makeTotalPriceMessage(totalDiscount, expectPrice, noPromotion));
    }

    private BigDecimal addTotalDiscount(final BigDecimal untilChristmasDiscount, final BigDecimal dayDiscount,
                                        final BigDecimal specialDiscount, final BigDecimal giftDiscount,
                                        final boolean noPromotion) {
        if (noPromotion) {
            return BigDecimal.ZERO;
        }
        BigDecimal totalDiscount = untilChristmasDiscount;
        totalDiscount = totalDiscount.add(dayDiscount);
        totalDiscount = totalDiscount.add(specialDiscount);
        totalDiscount = totalDiscount.add(giftDiscount);
        return totalDiscount;
    }

    private Orders createOrders() {
        outputView.commentOrderMenu();
        String input = inputView.readLine();
        Orders orders = new Orders(new ArrayList<>());
        for (String order : input.split(COMMA)) {
            validateOrder(orders, order.trim());
        }
        return orders;
    }

    private void validateOrder(final Orders orders, final String order) {
        Matcher matcher = PATTERN.matcher(order);
        if (!matcher.matches()) {
            throw new CustomIllegalArgumentException(INVALID_ORDER.getMessage());
        }
        matcher.reset();
        while (matcher.find()) {
            Menu menu = Menu.from(matcher.group(1));
            Quantity quantity = new Quantity(Converter.convertToInteger(matcher.group(2)));
            orders.add(new Order(menu, quantity));
        }
        orders.checkOnlyDrinks();
    }

    private Day createDay() {
        outputView.commentVisitDate();
        String day = inputView.readLine();
        return new Day(YEAR, DECEMBER, Converter.convertToInteger(day));
    }

    private void showOrders(final Orders orders, final Day day) {
        outputView.commentEvent(day.getValue());
        showOrderMenus(orders);
        outputView.showOrderPrice(orders.calculateTotalPrice());
        outputView.showBlankLine();
    }

    private void showOrderMenus(final Orders orders) {
        outputView.showOrderMenu();
        outputView.showMessage(stringFormatter.makeOptionalOrderMessage(orders));
    }
}
