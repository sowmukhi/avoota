package com.neoteric.avoota;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class AvootaController {

    RatingCalculator ratingCalculator = new RatingCalculator();

    @PostMapping("/average-rating")
    public double getAverageRating(@RequestBody AvootaResponseWrapper wrapper) {
        return ratingCalculator.calculateAverageRating(wrapper);
    }

    @PostMapping("/average-rating-per-category")
    public Map<String, Double> getAveragePerCategory(@RequestBody AvootaResponseWrapper wrapper) {
        return ratingCalculator.calculateAveragePerSubratingCategory(wrapper);
    }

    @PostMapping("/average-rating-and-subratings")
    public Map<String, CategoryRatingResult> getAverageRatingAndSubratings(@RequestBody AvootaResponseWrapper wrapper) {
        return ratingCalculator.calculateAveragePerCategory(wrapper);
    }

//    private double calculateAverageRating(AvootaResponseWrapper wrapper) {
//        if (wrapper == null || wrapper.getResponse() == null || wrapper.getResponse().getCategoryList() == null) {
//            return 0.0;
//        }
//
//        return wrapper.getResponse().getCategoryList().stream()
//                .flatMap(category -> {
//                    List<Review> reviews = category.getReviewList();
//                    return (reviews != null) ? reviews.stream() : java.util.stream.Stream.empty();
//                })
//                .filter(review -> review != null)
//                .mapToInt(Review::getRating)
//                .average()
//                .orElse(0.0);
//    }


}
