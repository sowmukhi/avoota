package com.neoteric.avoota;

import java.util.Map;

public class CategoryRatingResult {
    private double averageRating;
    private Map<String, Double> subratings;

    public CategoryRatingResult(double averageRating, Map<String, Double> subratings) {
        this.averageRating = averageRating;
        this.subratings = subratings;
    }

    public double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(double averageRating) {
        this.averageRating = averageRating;
    }

    public Map<String, Double> getSubratings() {
        return subratings;
    }

    public void setSubratings(Map<String, Double> subratings) {
        this.subratings = subratings;
    }
}
