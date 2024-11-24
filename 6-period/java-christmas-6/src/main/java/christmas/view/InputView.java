package christmas.view;

import camp.nextstep.edu.missionutils.Console;
import christmas.exception.ErrorMessage;
import christmas.util.Converter;
import christmas.util.InputValidator;
import christmas.util.StringParser;
import java.util.List;

public class InputView {

    private static final String COMMA = ",";

    public int readDay() {
        String input = readLine(ErrorMessage.INVALID_DAY);
        return Converter.convertToInteger(input, ErrorMessage.INVALID_DAY);
    }

    public List<String> readMenu() {
        String input = readLine(ErrorMessage.INVALID_ORDER);
        return StringParser.parseByDelimiter(input, COMMA);
    }

    private String readLine(ErrorMessage errorMessage) {
        String line = Console.readLine();
        InputValidator.validateNotNullOrBlank(line, errorMessage);
        return line;
    }
}
