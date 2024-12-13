package calculator.view;

import calculator.exception.ErrorMessage;
import calculator.util.InputValidator;
import camp.nextstep.edu.missionutils.Console;

public class InputView {

    public String readText() {
        return readLine(ErrorMessage.INVALID_INPUT);
    }

    private String readLine(ErrorMessage errorMessage) {
        String line = Console.readLine();
        InputValidator.validateNotNull(line, errorMessage);
        return line;
    }
}
