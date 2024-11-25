package christmas.controller;

import christmas.domain.Day;
import christmas.domain.EventCalculator;
import christmas.domain.Orders;
import christmas.domain.discount.Discount;
import christmas.domain.gift.Gift;
import christmas.dto.BenefitDto;
import christmas.dto.GiftDto;
import christmas.dto.OrderMenuDto;
import christmas.exception.ExceptionHandler;
import christmas.service.Service;
import christmas.view.InputView;
import christmas.view.OutputView;
import java.math.BigDecimal;
import java.util.List;

public class Controller {

    private final InputView inputView;
    private final OutputView outputView;
    private final ExceptionHandler exceptionHandler;
    private final Service service;

    public Controller(final InputView inputView, final OutputView outputView, final ExceptionHandler exceptionHandler,
                      final Service service) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.exceptionHandler = exceptionHandler;
        this.service = service;
    }

    public void process() {
        outputView.informWelcome();
        Day visitDay = createDay();
        Orders orders = createOrders();
        showOrders(orders, visitDay);
        processBenefit(visitDay, orders);
    }

    private void processBenefit(final Day visitDay, final Orders orders) {
        List<Gift> gifts = service.createBonus(visitDay, orders);
        showGifts(gifts);
        List<Discount> discounts = service.createDiscount(visitDay, orders);
        showBenefit(gifts, discounts);
        EventCalculator calculator = new EventCalculator(discounts, gifts);
        showFinalResult(calculator, orders);
    }

    private void showFinalResult(final EventCalculator calculator, final Orders orders) {
        showBenefitPrice(calculator.calculateTotalBenefitPrice());
        showEstimatedPrice(calculator.calculateEstimatedPrice(orders));
        showBadgeName(calculator.getBadgeName());
    }

    private void showBadgeName(final String badgeName) {
        outputView.showBadgeName(badgeName);
    }

    private void showEstimatedPrice(final BigDecimal estimatedPrice) {
        outputView.showEstimatedPrice(estimatedPrice);
    }

    private void showBenefitPrice(final BigDecimal totalBenefitPrice) {
        outputView.showTotalBenefitPrice(totalBenefitPrice);
    }


    private void showBenefit(final List<Gift> gifts, final List<Discount> discounts) {
        List<BenefitDto> benefitDtos = service.createBenefitDtos(discounts, gifts);
        outputView.showBenefit(benefitDtos);
    }

    private void showGifts(final List<Gift> gifts) {
        List<GiftDto> giftDtos = service.createGiftDtos(gifts);
        outputView.showGift(giftDtos);
    }

    private Orders createOrders() {
        outputView.requestOrderMenu();
        return exceptionHandler.retryOn(() -> {
            List<String> orderInputs = inputView.readMenu();
            return service.createOrders(orderInputs);
        });
    }

    private Day createDay() {
        return exceptionHandler.retryOn(() -> {
            outputView.requestVisitDay();
            int day = inputView.readDay();
            return service.createDay(day);
        });
    }


    private void showOrders(final Orders orders, final Day day) {
        outputView.informPreview(day.getValue());
        showOrderMenus(orders);
        outputView.informDiscountPrice(orders.calculateTotalPrice());
    }

    private void showOrderMenus(final Orders orders) {
        List<OrderMenuDto> orderMenuDtos = service.toOrderMenuDto(orders);
        outputView.informOrderMenu(orderMenuDtos);
    }
}
