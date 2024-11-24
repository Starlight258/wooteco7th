package christmas.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringParser {

    private static final int CONTAINS_EMPTY = -1;

    private StringParser() {
    }

    /**
     * 정규식 패턴으로 문자열을 파싱하여 매칭되는 그룹들의 리스트를 반환
     */
    public static List<String> extractByGroup(String input, Pattern pattern) {
        Matcher matcher = pattern.matcher(input);
        List<String> groups = new ArrayList<>();
        while (matcher.find()) {
            for (int i = 1; i <= matcher.groupCount(); i++) {
                groups.add(matcher.group(i));
            }
        }
        return Collections.unmodifiableList(groups);
    }

    public static boolean isNotSuitablePattern(String input, Pattern pattern) {
        return !pattern.matcher(input).find();
    }


    /**
     * 구분자로 문자열을 분리하여 리스트로 반환
     */
    public static List<String> parseByDelimiter(String input, String delimiter) {
        return Arrays.stream(input.split(delimiter, CONTAINS_EMPTY))
                .map(String::trim)
                .toList();
    }
}
