package com.neoteric.avoota;

import java.util.List;
import java.util.OptionalDouble;

public class RatingCalculator {
    public static double calculateAverageRating(AvootaResponseWrapper wrapper) {
        if (wrapper == null || wrapper.getResponse() == null || wrapper.getResponse().getCategoryList() == null) {
            return 0.0;
        }

        List<Category> categories = wrapper.getResponse().getCategoryList();

        OptionalDouble average = categories.stream()
                .flatMap(category -> category.getReviewList() != null ? category.getReviewList().stream() : null)
                .filter(review -> review != null)
                .mapToInt(Review::getRating)
                .average();

        return average.orElse(0.0);
    }
}
