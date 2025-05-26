package com.neoteric.avoota;

import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class RatingCalculator {
    public double calculateAverageRating(AvootaResponseWrapper wrapper) {
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
    public Map<String, Double> calculateAveragePerSubratingCategory(AvootaResponseWrapper wrapper) {
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
    public Map<String, CategoryRatingResult> calculateAveragePerCategory(AvootaResponseWrapper wrapper) {
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
    private String getRatingLabel(int rating) {
        if (rating >= 5) return "Excellent";
        else if (rating == 4) return "Good";
        else if (rating == 3) return "Average";
        else if (rating == 2) return "Poor";
        else return "Bad";
    }
    public Map<String, Map<String, Double>> calculateRatingPercentagePerCategory(AvootaResponseWrapper wrapper) {
        Map<String, Map<String, Double>> result = new HashMap<>();

        if (wrapper == null || wrapper.getResponse() == null || wrapper.getResponse().getCategoryList() == null) {
            return result;
        }

        for (Category category : wrapper.getResponse().getCategoryList()) {
            List<Review> reviews = category.getReviewList();
            if (reviews == null || reviews.isEmpty()) continue;

            // Step 1: Count ratings by label
            Map<String, Long> ratingCounts = reviews.stream()
                    .map(review -> getRatingLabel(review.getRating()))
                    .collect(Collectors.groupingBy(label -> label, Collectors.counting()));

            long total = reviews.size();

            // Step 2: Calculate percentage
            Map<String, Double> ratingPercentages = new LinkedHashMap<>();
            for (String label : Arrays.asList("Excellent", "Good", "Average", "Poor", "Bad")) {
                long count = ratingCounts.getOrDefault(label, 0L);
                double percentage = total == 0 ? 0.0 : (count * 100.0) / total;
                ratingPercentages.put(label, percentage);
            }

            // Add to result with category name
            result.put(category.getCategoryName(), ratingPercentages);
        }

        return result;
    }

    public Map<String, CategoryRatingResult> calculateFullCategoryRatings(AvootaResponseWrapper wrapper) {
        if (wrapper == null || wrapper.getResponse() == null || wrapper.getResponse().getCategoryList() == null) {
            return Collections.emptyMap();
        }

        Map<String, CategoryRatingResult> result = new HashMap<>();

        for (Category category : wrapper.getResponse().getCategoryList()) {
            if (category == null || category.getReviewList() == null) continue;

            List<Review> reviews = category.getReviewList();
            String categoryName = category.getCategoryName();

            // Average rating
            double averageRating = reviews.stream()
                    .filter(Objects::nonNull)
                    .mapToInt(Review::getRating)
                    .average()
                    .orElse(0.0);

            // Average subratings
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

            // Rating percentages (Excellent, Good, Average, Poor, Bad)
            Map<String, Long> ratingCounts = reviews.stream()
                    .map(review -> getRatingLabel(review.getRating()))
                    .collect(Collectors.groupingBy(label -> label, Collectors.counting()));
            long total = reviews.size();

            Map<String, Double> ratingPercentages = new LinkedHashMap<>();
            for (String label : Arrays.asList("Excellent", "Good", "Average", "Poor", "Bad")) {
                long count = ratingCounts.getOrDefault(label, 0L);
                double percentage = total == 0 ? 0.0 : (count * 100.0) / total;
                ratingPercentages.put(label, percentage);
            }

            CategoryRatingResult ratingResult = new CategoryRatingResult(averageRating, averageSubratings, ratingPercentages);
            result.put(categoryName, ratingResult);
        }

        return result;
    }

}
