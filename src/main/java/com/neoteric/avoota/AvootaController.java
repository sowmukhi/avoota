package com.neoteric.avoota;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class AvootaController {
    RatingCalculator ratingCalculator = new RatingCalculator();
    @PostMapping("/average-rating")
    public double getAverageRating(@RequestBody AvootaResponseWrapper wrapper) {
        return ratingCalculator.calculateAverageRating(wrapper);
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
