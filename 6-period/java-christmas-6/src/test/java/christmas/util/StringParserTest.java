package christmas.util;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.regex.Pattern;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class StringParserTest {

    @Test
    @DisplayName("정규식 패턴으로 문자열에서 그룹을 추출할 수 있다")
    void extractByGroup() {
        // given
        String input = "티본스테이크-1";
        Pattern pattern = Pattern.compile("^([가-힣]+)-(\\d+)$");

        // when
        List<String> result = StringParser.extractByGroup(input, pattern);

        // then
        assertThat(result).containsExactly("티본스테이크", "1");
    }

    @Test
    @DisplayName("정규식 패턴으로 여러 매칭 그룹을 추출할 수 있다")
    void extractMultipleGroups() {
        // given
        String input = "이름: 홍길동, 나이: 20\n이름: 김철수, 나이: 25";
        Pattern pattern = Pattern.compile("이름: (.*), 나이: (\\d+)");

        // when
        List<String> result = StringParser.extractByGroup(input, pattern);

        // then
        assertThat(result).containsExactly("홍길동", "20", "김철수", "25");
    }

    @Test
    @DisplayName("패턴이 매칭되지 않으면 빈 리스트를 반환한다")
    void returnEmptyListWhenNoMatch() {
        // given
        String input = "잘못된 형식의 문자열";
        Pattern pattern = Pattern.compile("이름: (.*), 나이: (\\d+)");

        // when
        List<String> result = StringParser.extractByGroup(input, pattern);

        // then
        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("주어진 패턴이 입력 문자열과 일치하지 않는지 확인할 수 있다")
    void isNotSuitablePattern() {
        // given
        String input = "잘못된 형식의 문자열";
        Pattern pattern = Pattern.compile("이름: (.*), 나이: (\\d+)");

        // when & then
        assertThat(StringParser.isNotSuitablePattern(input, pattern)).isTrue();
    }

    @Test
    @DisplayName("주어진 패턴이 입력 문자열과 일치하는지 확인할 수 있다")
    void isSuitablePattern() {
        // given
        String input = "이름: 홍길동, 나이: 20";
        Pattern pattern = Pattern.compile("이름: (.*), 나이: (\\d+)");

        // when & then
        assertThat(StringParser.isNotSuitablePattern(input, pattern)).isFalse();
    }

    @Test
    @DisplayName("구분자로 문자열을 분리하여 리스트로 변환할 수 있다")
    void parseByDelimiter() {
        // given
        String input = "apple, banana, orange";

        // when
        List<String> result = StringParser.parseByDelimiter(input, ",");

        // then
        assertThat(result).containsExactly("apple", "banana", "orange");
    }

    @Test
    @DisplayName("구분자로 문자열을 분리할 때 앞뒤 공백을 제거한다")
    void parseByDelimiterWithTrim() {
        // given
        String input = "apple ,  banana  ,   orange  ";

        // when
        List<String> result = StringParser.parseByDelimiter(input, ",");

        // then
        assertThat(result).containsExactly("apple", "banana", "orange");
    }
}
