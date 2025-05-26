package com.neoteric.avoota;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        // Mock User
        User user1 = new User();
        user1.setUsername("Alice");

        User user2 = new User();
        user2.setUsername("Bob");

        // Mock Reviews
        Review review1 = new Review();
        review1.setId("r1");
        review1.setRating(4);
        review1.setUser(user1);
        review1.setText("Nice place");
        review1.setTitle("Good Stay");

        Review review2 = new Review();
        review2.setId("r2");
        review2.setRating(5);
        review2.setUser(user2);
        review2.setText("Excellent service");
        review2.setTitle("Fantastic!");

        Review review3 = new Review();
        review3.setId("r3");
        review3.setRating(3);
        review3.setUser(user1);
        review3.setText("Okayish");
        review3.setTitle("Average");

        // Mock Categories
        Category category1 = new Category();
        category1.setCategoryName("Food");
        category1.setReviewList(Arrays.asList(review1, review2));

        Category category2 = new Category();
        category2.setCategoryName("Service");
        category2.setReviewList(Arrays.asList(review3));

        // AvootaResponse
        AvootaResponse response = new AvootaResponse();
        response.setCategoryList(Arrays.asList(category1, category2));

        // AvootaResponseWrapper
        AvootaResponseWrapper wrapper = new AvootaResponseWrapper();
        wrapper.setAvootaStatus("success");
        wrapper.setResponse(response);

        // Calculate average rating
        RatingCalculator ratingCalculator = new RatingCalculator();
        double avgRating = ratingCalculator.calculateAverageRating(wrapper);
        System.out.println("Average Rating Across All Categories: " + avgRating);
    }
}
