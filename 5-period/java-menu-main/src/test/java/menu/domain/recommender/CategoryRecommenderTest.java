package menu.domain.recommender;

import static menu.domain.support.CustomAssert.assertIllegalArgument;
import static menu.exception.ErrorMessage.INVALID_CATEGORY_DUPLICATED;
import static org.junit.jupiter.api.Assertions.*;

import camp.nextstep.edu.missionutils.Randoms;
import java.util.List;
import java.util.Random;
import menu.domain.menu.Category;
import menu.domain.random.CategoryGenerator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CategoryRecommenderTest {

//    @Test
//    @DisplayName("카테고리를 추천한다.")
//    void makeWeekDayCategories() {
//        // Given
//        CategoryRecommender categoryRecommender = new CategoryRecommender(() -> Category.한식);
//
//        // When
//        assertIllegalArgument(() -> categoryRecommender.makeWeekDayCategories(), INVALID_CATEGORY_DUPLICATED);
//
//
//        // Then
//    }
    @Test
    void test() {
        // Given


        // When
        String number = Randoms.shuffle(List.of("a", "b", "c")).get(0);
        System.out.println(number);

        // Then
    }
}