package it.skillswap.storage;

import it.skillswap.domain.Exchange;
import it.skillswap.domain.Offer;
import it.skillswap.domain.Request;
import it.skillswap.domain.Review;
import it.skillswap.domain.Skill;
import it.skillswap.domain.Student;

import java.util.LinkedHashMap;
import java.util.Map;

public class SkillSwapState {
    private final Map<String, Student> students = new LinkedHashMap<>();
    private final Map<String, Skill> skills = new LinkedHashMap<>();
    private final Map<String, Offer> offers = new LinkedHashMap<>();
    private final Map<String, Request> requests = new LinkedHashMap<>();
    private final Map<String, Exchange> exchanges = new LinkedHashMap<>();
    private final Map<String, Review> reviews = new LinkedHashMap<>();

    public Map<String, Student> getStudents() {
        return students;
    }

    public Map<String, Skill> getSkills() {
        return skills;
    }

    public Map<String, Offer> getOffers() {
        return offers;
    }

    public Map<String, Request> getRequests() {
        return requests;
    }

    public Map<String, Exchange> getExchanges() {
        return exchanges;
    }

    public Map<String, Review> getReviews() {
        return reviews;
    }
}