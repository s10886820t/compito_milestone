package it.skillswap.service;

import it.skillswap.domain.Exchange;
import it.skillswap.domain.ExchangeStatus;
import it.skillswap.domain.Review;
import it.skillswap.domain.Student;
import it.skillswap.storage.SkillSwapState;
import it.skillswap.storage.Storage;

import java.util.ArrayList;
import java.util.List;

public class ReviewService {
private final Storage storage;
private final SkillSwapState state;
private final IdGenerator idGenerator;

public ReviewService(Storage storage, SkillSwapState state) {
if (storage == null) {
throw new IllegalArgumentException("Storage obbligatorio");
}
if (state == null) {
throw new IllegalArgumentException("State obbligatorio");
}

this.storage = storage;
this.state = state;
this.idGenerator = new IdGenerator();
}

public Review addReview(String exchangeId, String reviewerId, int stars, String comment) {
Exchange exchange = state.getExchanges().get(exchangeId);
if (exchange == null) {
throw new IllegalArgumentException("Exchange non trovato");
}

if (exchange.getStatus() != ExchangeStatus.COMPLETED) {
throw new IllegalStateException("La review si può inserire solo se l'exchange è COMPLETED");
}

Student reviewer = state.getStudents().get(reviewerId);
if (reviewer == null) {
throw new IllegalArgumentException("Reviewer non trovato");
}

Student offerStudent = exchange.getOffer().getStudent();
Student requestStudent = exchange.getRequest().getStudent();

Student reviewee;
if (reviewer.getId().equals(offerStudent.getId())) {
reviewee = requestStudent;
} else if (reviewer.getId().equals(requestStudent.getId())) {
reviewee = offerStudent;
} else {
throw new IllegalArgumentException("Il reviewer deve essere uno dei due studenti dello scambio");
}

for (Review review : state.getReviews().values()) {
if (review.getExchange().getId().equals(exchangeId)
&& review.getReviewer().getId().equals(reviewerId)) {
throw new IllegalStateException("Questo studente ha già lasciato una review per questo exchange");
}
}

String id = idGenerator.next("V");
Review review = new Review(id, exchange, reviewer, reviewee, stars, comment);

state.getReviews().put(id, review);
reviewee.addRating(stars);
storage.save(state);

return review;
}

public List<Review> listReviews() {
return new ArrayList<>(state.getReviews().values());
}
}