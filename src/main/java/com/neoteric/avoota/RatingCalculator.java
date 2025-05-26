package com.neoteric.avoota;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    public static Map<String, Double> calculateAveragePerSubratingCategory(AvootaResponseWrapper wrapper) {
        if (wrapper == null || wrapper.getResponse() == null || wrapper.getResponse().getCategoryList() == null) {
            return Map.of();
        }

        return wrapper.getResponse().getCategoryList().stream()
                .flatMap(category -> category.getReviewList() != null ? category.getReviewList().stream() : Stream.empty())
                .filter(review -> review != null && review.getSubratings() != null)
                .flatMap(review -> review.getSubratings().values().stream())
                .filter(subrating -> subrating.getLocalized_name() != null)
                .collect(Collectors.groupingBy(
                        Subrating::getLocalized_name,
                        Collectors.averagingInt(Subrating::getValue)
                ));
    }
    public static Map<String, CategoryRatingResult> calculateAveragePerCategory(AvootaResponseWrapper wrapper) {
        if (wrapper == null || wrapper.getResponse() == null || wrapper.getResponse().getCategoryList() == null) {
            return Collections.emptyMap();
        }

        Map<String, CategoryRatingResult> result = new HashMap<>();

        for (Category category : wrapper.getResponse().getCategoryList()) {
            if (category == null || category.getReviewList() == null) continue;

            List<Review> reviews = category.getReviewList();
            String categoryName = category.getCategoryName();

            // Calculate average rating
            double averageRating = reviews.stream()
                    .filter(Objects::nonNull)
                    .mapToInt(Review::getRating)
                    .average()
                    .orElse(0.0);

            // Calculate average subratings
            Map<String, List<Integer>> subratingValues = new HashMap<>();

            for (Review review : reviews) {
                if (review != null && review.getSubratings() != null) {
                    for (Map.Entry<String, Subrating> entry : review.getSubratings().entrySet()) {
                        String subratingName = entry.getValue().getLocalized_name();
                        int value = entry.getValue().getValue();
                        subratingValues.computeIfAbsent(subratingName, k -> new ArrayList<>()).add(value);
                    }
                }
            }

            Map<String, Double> averageSubratings = subratingValues.entrySet().stream()
                    .collect(Collectors.toMap(
                            Map.Entry::getKey,
                            e -> e.getValue().stream().mapToInt(Integer::intValue).average().orElse(0.0)
                    ));

            CategoryRatingResult ratingResult = new CategoryRatingResult(averageRating, averageSubratings);
            result.put(categoryName, ratingResult);
        }

        return result;
    }
}
