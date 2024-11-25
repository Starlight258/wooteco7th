package christmas.domain.gift;

import christmas.domain.Menu;
import christmas.domain.Quantity;
import java.math.BigDecimal;
import java.util.Map;

public interface Gift {

    boolean isApplicable();

    BigDecimal calculateAmount();

    Map<Menu, Quantity> provideGiftItems();

    String getName();
}
