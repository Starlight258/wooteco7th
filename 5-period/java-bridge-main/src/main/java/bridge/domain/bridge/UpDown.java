package bridge.domain.bridge;

import bridge.exception.CustomIllegalArgumentException;
import bridge.exception.ErrorMessage;
import java.util.Arrays;

public enum UpDown {

    UP("U", 1), DOWN("D", 0);

    private final String direction;
    private final int value;

    UpDown(final String direction, final int value) {
        this.direction = direction;
        this.value = value;
    }

    public static UpDown from(String value) {
        return Arrays.stream(UpDown.values())
                .filter(upDown -> upDown.direction.equals(value))
                .findFirst()
                .orElseThrow(() -> new CustomIllegalArgumentException(ErrorMessage.INVALID_DIRECTION));
    }

    public static UpDown from(int value) {
        return Arrays.stream(UpDown.values())
                .filter(upDown -> upDown.value == value)
                .findFirst()
                .orElseThrow(() -> new CustomIllegalArgumentException(ErrorMessage.INVALID_DIRECTION));
    }

    public boolean isUp() {
        return this == UP;
    }

    public String getDirection() {
        return direction;
    }
}
