package com.neoteric.avoota;

import java.util.Map;

public class CategoryRatingResult {
    private double averageRating;
    private Map<String, Double> subratings;
    private Map<String, Double> ratingBreakdownPercentages;

    public CategoryRatingResult(double averageRating, Map<String, Double> subratings) {
        this.averageRating = averageRating;
        this.subratings = subratings;
    }

    public CategoryRatingResult(double averageRating, Map<String, Double> subratings,
                                Map<String, Double> ratingBreakdownPercentages) {
        this.averageRating = averageRating;
        this.subratings = subratings;
        this.ratingBreakdownPercentages = ratingBreakdownPercentages;
    }

    public double getAverageRating() {
        return averageRating;
    }

    public Map<String, Double> getSubratings() {
        return subratings;
    }

    public void setAverageRating(double averageRating) {
        this.averageRating = averageRating;
    }

    public void setSubratings(Map<String, Double> subratings) {
        this.subratings = subratings;
    }

    public void setRatingBreakdownPercentages(Map<String, Double> ratingBreakdownPercentages) {
        this.ratingBreakdownPercentages = ratingBreakdownPercentages;
    }

    public Map<String, Double> getRatingBreakdownPercentages() {
        return ratingBreakdownPercentages;
    }
}
