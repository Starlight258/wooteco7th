package racingcar.view;

import camp.nextstep.edu.missionutils.Console;
import java.util.List;
import racingcar.exception.ErrorMessage;
import racingcar.util.InputValidator;
import racingcar.util.StringParser;

public class InputView {

    private static final String DELIMITER = ",";

    public List<String> readRequestName() {
        String line = readLine(ErrorMessage.INVALID_INPUT);
        return StringParser.parseByDelimiter(line, DELIMITER);
    }

    public int readRequestAttempt() {
        return StringParser.parseToInteger(readLine(ErrorMessage.INVALID_INPUT), ErrorMessage.INVALID_INPUT);
    }

    private String readLine(ErrorMessage errorMessage) {
        String line = Console.readLine();
        InputValidator.validateNotNullOrBlank(line, errorMessage);
        return line;
    }
}
