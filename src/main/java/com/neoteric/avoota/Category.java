package com.neoteric.avoota;

import java.util.List;

public class Category {
    private String categoryName;
    private List<ReviewSummary> reviewSummary;
    private List<CategoryReview> categoryReview;
    private List<Review> reviewList;

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public List<ReviewSummary> getReviewSummary() {
        return reviewSummary;
    }

    public void setReviewSummary(List<ReviewSummary> reviewSummary) {
        this.reviewSummary = reviewSummary;
    }

    public List<CategoryReview> getCategoryReview() {
        return categoryReview;
    }

    public void setCategoryReview(List<CategoryReview> categoryReview) {
        this.categoryReview = categoryReview;
    }

    public List<Review> getReviewList() {
        return reviewList;
    }

    public void setReviewList(List<Review> reviewList) {
        this.reviewList = reviewList;
    }
}
