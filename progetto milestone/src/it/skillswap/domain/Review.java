package it.skillswap.domain;

import java.time.LocalDateTime;

public class Review {
    private final String id;
    private final Exchange exchange;
    private final Student reviewer;
    private final Student reviewee;
    private final int stars;
    private final String comment;
    private final LocalDateTime createdAt;

    public Review(String id, Exchange exchange, Student reviewer, Student reviewee, int stars, String comment) {
        this(id, exchange, reviewer, reviewee, stars, comment, LocalDateTime.now());
    }

    public Review(String id, Exchange exchange, Student reviewer, Student reviewee, int stars, String comment, LocalDateTime createdAt) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("Id review obbligatorio");
        }
        if (exchange == null) {
            throw new IllegalArgumentException("Exchange obbligatorio");
        }
        if (reviewer == null || reviewee == null) {
            throw new IllegalArgumentException("Student obbligatori");
        }
        if (stars < 1 || stars > 5) {
            throw new IllegalArgumentException("Le stelle devono essere tra 1 e 5");
        }
        if (createdAt == null) {
            throw new IllegalArgumentException("Data review obbligatoria");
        }

        this.id = id;
        this.exchange = exchange;
        this.reviewer = reviewer;
        this.reviewee = reviewee;
        this.stars = stars;
        this.comment = comment == null ? "" : comment;
        this.createdAt = createdAt;
    }

    public String getId() {
        return id;
    }

    public Exchange getExchange() {
        return exchange;
    }

    public Student getReviewer() {
        return reviewer;
    }

    public Student getReviewee() {
        return reviewee;
    }

    public int getStars() {
        return stars;
    }

    public String getComment() {
        return comment;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}