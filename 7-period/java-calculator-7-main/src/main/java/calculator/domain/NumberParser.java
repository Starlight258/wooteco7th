package calculator.domain;

import calculator.exception.ErrorMessage;
import calculator.util.StringParser;
import java.util.List;
import java.util.regex.Pattern;

public class NumberParser {

    private static final String COMMA = ",";
    private static final String COLON = ":";
    private static final String CUSTOM_DELIMITER_REGEX = "\\/\\/(.+?)\\\\n";
    private static final Pattern PATTERN = Pattern.compile(CUSTOM_DELIMITER_REGEX);
    private static final int ZERO = 0;

    private final Delimiters delimiters;
    private String text;

    public NumberParser(final String text, final List<String> delimiters) {
        this.text = text;
        this.delimiters = initialize();
    }

    private Delimiters initialize() {
        return new Delimiters(List.of(COMMA, COLON));
    }

    public List<Integer> parse() {
        // 커스텀 구분자 선언 확인
        findCustomDelimiter();
        if (checkIfEmpty()) {
            return List.of(ZERO);
        }
        // 기본 구분자 + 커스텀 구분자로 분리
        List<String> tokens = parseByDelimiters();
        return parseToInteger(tokens);
    }

    private boolean checkIfEmpty() {
        return text.isEmpty();
    }

    private List<Integer> parseToInteger(final List<String> tokens) {
        return tokens.stream()
                .map(token -> StringParser.parseToInteger(token, ErrorMessage.INVALID_INPUT_INTEGER))
                .toList();
    }

    private List<String> parseByDelimiters() {
        String delimitersRegex = delimiters.makeRegex();
        return List.of(text.split(delimitersRegex));
    }

    private void findCustomDelimiter() {
        List<String> matchingGroups = StringParser.findMatchingGroups(text, PATTERN);
        delimiters.addAll(matchingGroups);
        text = StringParser.removePattern(text, CUSTOM_DELIMITER_REGEX);
    }
}
