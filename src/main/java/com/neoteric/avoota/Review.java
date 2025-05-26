package com.neoteric.avoota;

import java.util.Map;

public class Review {
    private String id;
    private String text;
    private String title;
    private int rating;
    private User user;
    private String trip_type;
    private String published_date;
    private Map<String, Subrating> subratings;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getTrip_type() {
        return trip_type;
    }

    public void setTrip_type(String trip_type) {
        this.trip_type = trip_type;
    }

    public String getPublished_date() {
        return published_date;
    }

    public void setPublished_date(String published_date) {
        this.published_date = published_date;
    }

    public Map<String, Subrating> getSubratings() {
        return subratings;
    }

    public void setSubratings(Map<String, Subrating> subratings) {
        this.subratings = subratings;
    }
}
