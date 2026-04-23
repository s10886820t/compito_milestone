package it.skillswap.domain;

import java.time.LocalDateTime;
import java.util.Objects;

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
        this.id = validateId(id);
        this.exchange = Objects.requireNonNull(exchange, "Exchange obbligatorio");
        this.reviewer = Objects.requireNonNull(reviewer, "Reviewer obbligatorio");
        this.reviewee = Objects.requireNonNull(reviewee, "Reviewee obbligatorio");
        this.stars = validateStars(stars);
        this.comment = comment == null ? "" : comment;
        this.createdAt = Objects.requireNonNull(createdAt, "Data review obbligatoria");

        if (this.reviewer.equals(this.reviewee)) {
            throw new IllegalArgumentException("Reviewer e reviewee non possono coincidere");
        }
    }

    private String validateId(String id) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("Id review non valido");
        }
        return id;
    }

    private int validateStars(int stars) {
        if (stars < 1 || stars > 5) {
            throw new IllegalArgumentException("Le stelle devono essere tra 1 e 5");
        }
        return stars;
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

    @Override
    public String toString() {
        return "Review{" +
                "id='" + id + '\'' +
                ", exchange=" + exchange.getId() +
                ", reviewer=" + reviewer.getName() +
                ", reviewee=" + reviewee.getName() +
                ", stars=" + stars +
                ", comment='" + comment + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}