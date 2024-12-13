package calculator.domain;

import calculator.exception.CustomIllegalArgumentException;
import calculator.exception.ErrorMessage;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Delimiters {

    private static final String ESCAPE = "\\";
    private static final String CONCAT = "|";
    private static final List<String> META_CHARACTERS = List.of(".", "^", "$", "*", "+", "?", "{", "}",
            "[", "]", ESCAPE, CONCAT, "(", ")");
    private final List<String> delimiters;

    public Delimiters(final List<String> delimiters) {
        validate(delimiters);
        this.delimiters = new ArrayList<>(delimiters);
    }

    private void validate(final List<String> delimiters) {
        if (delimiters.size() != delimiters.stream().distinct().count()) {
            throw new CustomIllegalArgumentException(ErrorMessage.DUPLICATE_DELIMITER);
        }
    }

    public void addDelimiter(final String delimiter) {
        List<String> temp = new ArrayList<>(delimiters);
        temp.add(delimiter);
        validate(temp);
        delimiters.add(delimiter);
    }

    public void addAll(final List<String> values) {
        for (String value : values) {
            addDelimiter(value);
        }
    }

    public String makeRegex() {
        return delimiters.stream()
                .map(this::escape)
                .collect(Collectors.joining(CONCAT));
    }

    public String escape(final String value) {
        if (META_CHARACTERS.contains(value)) {
            return ESCAPE + value;
        }
        return value;
    }
}
