package it.skillswap.domain;

import java.time.LocalDateTime;

public class Exchange {
    private final String id;
    private final Offer offer;
    private final Request request;
    private ExchangeStatus status;
    private final LocalDateTime createdAt;
    private LocalDateTime closedAt;

    public Exchange(String id, Offer offer, Request request) {
        this(id, offer, request, ExchangeStatus.PROPOSED, LocalDateTime.now(), null);
    }

    public Exchange(String id, Offer offer, Request request, ExchangeStatus status, LocalDateTime createdAt, LocalDateTime closedAt) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("Id exchange obbligatorio");
        }
        if (offer == null) {
            throw new IllegalArgumentException("Offer obbligatoria");
        }
        if (request == null) {
            throw new IllegalArgumentException("Request obbligatoria");
        }
        if (status == null) {
            throw new IllegalArgumentException("Stato obbligatorio");
        }
        if (createdAt == null) {
            throw new IllegalArgumentException("Data creazione obbligatoria");
        }
        if (!offer.isActive()) {
            throw new IllegalArgumentException("Non puoi creare uno scambio con un'offerta non attiva");
        }
        if (offer.getStudent().getId().equals(request.getStudent().getId())) {
            throw new IllegalArgumentException("Uno studente non può fare match con sé stesso");
        }

        this.id = id;
        this.offer = offer;
        this.request = request;
        this.status = status;
        this.createdAt = createdAt;
        this.closedAt = closedAt;
    }

    public String getId() {
        return id;
    }

    public Offer getOffer() {
        return offer;
    }

    public Request getRequest() {
        return request;
    }

    public ExchangeStatus getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getClosedAt() {
        return closedAt;
    }

    public void accept() {
        if (status != ExchangeStatus.PROPOSED) {
            throw new IllegalStateException("Transizione non valida: si può accettare solo uno scambio PROPOSED");
        }
        status = ExchangeStatus.ACCEPTED;
    }

    public void complete() {
        if (status != ExchangeStatus.ACCEPTED) {
            throw new IllegalStateException("Transizione non valida: si può completare solo uno scambio ACCEPTED");
        }
        status = ExchangeStatus.COMPLETED;
        closedAt = LocalDateTime.now();
    }

    public void cancel() {
        if (status != ExchangeStatus.PROPOSED) {
            throw new IllegalStateException("Transizione non valida: si può annullare solo uno scambio PROPOSED");
        }
        status = ExchangeStatus.CANCELLED;
        closedAt = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return id +
                " - offer=" + offer.getId() +
                ", request=" + request.getId() +
                ", status=" + status +
                ", createdAt=" + createdAt +
                ", closedAt=" + closedAt;
    }
}