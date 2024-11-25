package christmas.service;

import christmas.domain.Day;
import christmas.domain.Menu;
import christmas.domain.Order;
import christmas.domain.Orders;
import christmas.domain.Quantity;
import christmas.domain.discount.ChristmasDdayDiscount;
import christmas.domain.discount.DayDiscount;
import christmas.domain.discount.Discount;
import christmas.domain.discount.SpecialDiscount;
import christmas.domain.gift.BonusGift;
import christmas.domain.gift.Gift;
import christmas.dto.BenefitDto;
import christmas.dto.GiftDto;
import christmas.dto.OrderMenuDto;
import christmas.exception.CustomIllegalArgumentException;
import christmas.exception.ErrorMessage;
import christmas.util.Converter;
import christmas.util.StringParser;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class Service {

    private static final Pattern PATTERN = Pattern.compile("^([가-힣]+)-(\\d+)$");
    private static final int YEAR = 2023;
    private static final int DECEMBER = 12;

    public Orders createOrders(final List<String> orderInputs) {
        return new Orders(orderInputs.stream()
                .map(this::createOrder)
                .toList());
    }

    public Day createDay(final int day) {
        return new Day(YEAR, DECEMBER, day);
    }

    private Order createOrder(final String order) {
        checkSuitablePattern(order);
        List<String> splitted = StringParser.extractByGroup(order, PATTERN);
        Menu menu = Menu.from(splitted.get(0));
        Quantity quantity = new Quantity(
                Converter.convertToInteger(splitted.get(1), ErrorMessage.INVALID_DAY));
        return new Order(menu, quantity);
    }

    private void checkSuitablePattern(final String order) {
        if (StringParser.isNotSuitablePattern(order, PATTERN)) {
            throw new CustomIllegalArgumentException(ErrorMessage.INVALID_ORDER);
        }
    }

    public List<OrderMenuDto> toOrderMenuDto(final Orders orders) {
        return orders.getOrders().stream()
                .map(OrderMenuDto::of)
                .toList();
    }

    public List<Discount> createDiscount(final Day visitDay, final Orders orders) {
        List<Discount> discounts = List.of(new ChristmasDdayDiscount(visitDay, orders),
                new DayDiscount(visitDay, orders), new SpecialDiscount(visitDay, orders));
        return discounts.stream()
                .filter(Discount::isApplicable)
                .toList();
    }

    public List<Gift> createBonus(final Day visitDay, final Orders orders) {
        Gift gift = new BonusGift(visitDay, orders);
        if (gift.isApplicable()) {
            return List.of(gift);
        }
        return Collections.emptyList();
    }

    public List<GiftDto> createGiftDtos(final List<Gift> gifts) {
        return gifts.stream()
                .flatMap(gift -> gift.provideGiftItems().entrySet().stream())
                .map(entry -> new GiftDto(entry.getKey().name(), entry.getValue().getValue()))
                .toList();
    }

    public List<BenefitDto> createBenefitDtos(final List<Discount> discounts, final List<Gift> gifts) {
        return Stream.concat(
                        discounts.stream()
                                .filter(discount -> !Objects.equals(discount.calculateAmount(), BigDecimal.ZERO))
                                .map(discount -> new BenefitDto(discount.getName(), discount.calculateAmount())),
                        gifts.stream().map(gift -> new BenefitDto(gift.getName(), gift.calculateAmount())))
                .toList();
    }
}
