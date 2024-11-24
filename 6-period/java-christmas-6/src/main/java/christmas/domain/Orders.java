package christmas.domain;

import christmas.domain.Menu.MenuType;
import christmas.exception.CustomIllegalArgumentException;
import christmas.exception.ErrorMessage;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Orders {

    private final List<Order> orders;

    public Orders(final List<Order> orders) {
        checkDuplicated(orders);
        checkHasDrinkMenus(orders);
        this.orders = new ArrayList<>(orders);
    }

    private void checkDuplicated(final List<Order> orders) {
        int uniqueMenuCount = (int) orders.stream()
                .map(Order::getMenu)
                .distinct()
                .count();
        if (uniqueMenuCount != orders.size()) {
            throw new CustomIllegalArgumentException(ErrorMessage.INVALID_ORDER.getMessage());
        }
    }

    private void checkHasDrinkMenus(final List<Order> orders) {
        if (countDrinkMenu(orders) == countTotalMenu(orders)) {
            throw new CustomIllegalArgumentException(ErrorMessage.INVALID_ORDER.getMessage());
        }
    }

    private int countDrinkMenu(final List<Order> orders) {
        return (int) orders.stream()
                .filter(order -> order.hasMenuType(MenuType.DRINK))
                .count();
    }

    private int countTotalMenu(final List<Order> orders) {
        return (int) orders.stream()
                .map(Order::getMenu)
                .count();
    }

    public int countSameTypeMenu(MenuType menuType) {
        int count = 0;
        for (Order order : orders) {
            if (order.hasMenuType(menuType)) {
                count += order.getQuantity().getValue();
            }
        }
        return count;
    }

    public BigDecimal calculateTotalPrice() {
        BigDecimal price = BigDecimal.ZERO;
        for (Order order : orders) {
            price = price.add(order.getMenu().getPrice().multiply(new BigDecimal(order.getQuantity().getValue())));
        }
        return price;
    }

    public List<Order> getOrders() {
        return Collections.unmodifiableList(orders);
    }
}
