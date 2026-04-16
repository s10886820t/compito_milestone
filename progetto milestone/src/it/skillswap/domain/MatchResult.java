package it.skillswap.domain;

public class MatchResult {
    private final Student matchedStudent;
    private final Offer offer;
    private final Request request;
    private final int score;
    private final String reason;

    public MatchResult(Student matchedStudent, Offer offer, Request request, int score, String reason) {
        if (matchedStudent == null) {
            throw new IllegalArgumentException("Studente match obbligatorio");
        }
        if (offer == null) {
            throw new IllegalArgumentException("Offer obbligatoria");
        }
        if (request == null) {
            throw new IllegalArgumentException("Request obbligatoria");
        }

        this.matchedStudent = matchedStudent;
        this.offer = offer;
        this.request = request;
        this.score = score;
        this.reason = reason == null ? "" : reason;
    }

    public Student getMatchedStudent() {
        return matchedStudent;
    }

    public Offer getOffer() {
        return offer;
    }

    public Request getRequest() {
        return request;
    }

    public int getScore() {
        return score;
    }

    public String getReason() {
        return reason;
    }

    @Override
    public String toString() {
        return "Match con " + matchedStudent.getName() +
                " | offerId=" + offer.getId() +
                " | requestId=" + request.getId() +
                " | score=" + score +
                " | reason=" + reason;
    }
}