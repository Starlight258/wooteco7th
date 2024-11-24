package christmas.domain;

import christmas.domain.Menu.MenuType;
import java.math.BigDecimal;
import java.util.Objects;

public class Order {

    private final Menu menu;
    private final Quantity quantity;

    public Order(final Menu menu, final Quantity quantity) {
        this.menu = menu;
        this.quantity = quantity;
    }

    public boolean hasSameMenu(final Order order) {
        return this.menu.equals(order.getMenu());
    }

    public boolean hasMenuType(MenuType menuType) {
        return menu.getType().equals(menuType);
    }

    public BigDecimal getPrice() {
        return menu.getPrice().multiply(new BigDecimal(quantity.getValue()));
    }

    public Menu getMenu() {
        return menu;
    }

    public Quantity getQuantity() {
        return quantity;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Order order = (Order) o;
        return menu == order.menu && Objects.equals(quantity, order.quantity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(menu, quantity);
    }
}
