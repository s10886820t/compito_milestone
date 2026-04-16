package it.skillswap.service;

import it.skillswap.domain.Exchange;
import it.skillswap.domain.Offer;
import it.skillswap.domain.Request;
import it.skillswap.storage.SkillSwapState;
import it.skillswap.storage.Storage;

import java.util.ArrayList;
import java.util.List;

public class ExchangeService {
    private final Storage storage;
    private final SkillSwapState state;
    private final IdGenerator idGenerator;

    public ExchangeService(Storage storage, SkillSwapState state) {
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

    public Exchange propose(String offerId, String requestId) {
        Offer offer = state.getOffers().get(offerId);
        if (offer == null) {
            throw new IllegalArgumentException("Offer non trovata");
        }

        Request request = state.getRequests().get(requestId);
        if (request == null) {
            throw new IllegalArgumentException("Request non trovata");
        }

        if (!offer.isActive()) {
            throw new IllegalArgumentException("Un'offerta non attiva non può essere usata per creare uno scambio");
        }

        if (offer.getStudent().getId().equals(request.getStudent().getId())) {
            throw new IllegalArgumentException("Uno studente non può fare match con sé stesso");
        }

        String id = idGenerator.next("E");
        Exchange exchange = new Exchange(id, offer, request);

        state.getExchanges().put(id, exchange);
        storage.save(state);

        return exchange;
    }

    public Exchange accept(String exchangeId) {
        Exchange exchange = getExchangeOrThrow(exchangeId);
        exchange.accept();
        storage.save(state);
        return exchange;
    }

    public Exchange complete(String exchangeId) {
        Exchange exchange = getExchangeOrThrow(exchangeId);
        exchange.complete();
        storage.save(state);
        return exchange;
    }

    public Exchange cancel(String exchangeId) {
        Exchange exchange = getExchangeOrThrow(exchangeId);
        exchange.cancel();
        storage.save(state);
        return exchange;
    }

    public List<Exchange> listExchanges() {
        return new ArrayList<>(state.getExchanges().values());
    }

    public Exchange findById(String exchangeId) {
        return state.getExchanges().get(exchangeId);
    }

    private Exchange getExchangeOrThrow(String exchangeId) {
        Exchange exchange = state.getExchanges().get(exchangeId);
        if (exchange == null) {
            throw new IllegalArgumentException("Exchange non trovato");
        }
        return exchange;
    }
}