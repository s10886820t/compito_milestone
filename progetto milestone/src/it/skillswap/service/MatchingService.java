package it.skillswap.service;

import it.skillswap.domain.MatchResult;
import it.skillswap.domain.Offer;
import it.skillswap.domain.Request;
import it.skillswap.domain.SkillLevel;
import it.skillswap.domain.Student;
import it.skillswap.storage.SkillSwapState;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MatchingService {

    private final SkillSwapState state;

    public MatchingService(SkillSwapState state) {
        if (state == null) {
            throw new IllegalArgumentException("State obbligatorio");
        }
        this.state = state;
    }

    public List<MatchResult> findOneWayMatches(String studentId) {
        Student me = state.getStudents().get(studentId);
        if (me == null) {
            throw new IllegalArgumentException("Studente non trovato");
        }

        List<MatchResult> results = new ArrayList<>();
        Set<String> seen = new HashSet<>();

        for (Request myRequest : state.getRequests().values()) {
            if (!myRequest.getStudent().getId().equals(studentId)) {
                continue;
            }

            for (Offer otherOffer : state.getOffers().values()) {
                if (!otherOffer.isActive()) {
                    continue;
                }

                if (otherOffer.getStudent().getId().equals(studentId)) {
                    continue;
                }

                if (!otherOffer.getSkill().getId().equals(myRequest.getSkill().getId())) {
                    continue;
                }

                int score = 0;
                StringBuilder reason = new StringBuilder();

                score += 3;
                reason.append("+3 skill uguale");

                if (isLevelSufficient(otherOffer.getLevel(), myRequest.getMinLevel())) {
                    score += 2;
                    reason.append(", +2 livello sufficiente");
                }

                if (otherOffer.getStudent().getStudentClass().equalsIgnoreCase(me.getStudentClass())) {
                    score += 1;
                    reason.append(", +1 stessa classe");
                }

                MatchResult result = new MatchResult(
                        otherOffer.getStudent(),
                        otherOffer,
                        myRequest,
                        score,
                        reason.toString()
                );

                results.add(result);
            }
        }

        results.sort(Comparator.comparingInt(MatchResult::getScore).reversed());
        return results;
    }

    public List<MatchResult> findSwapMatches(String studentId) {
        Student me = state.getStudents().get(studentId);
        if (me == null) {
            throw new IllegalArgumentException("Studente non trovato");
        }

        List<MatchResult> results = new ArrayList<>();

        for (Request myRequest : state.getRequests().values()) {
            if (!myRequest.getStudent().getId().equals(studentId)) {
                continue;
            }

            for (Offer myOffer : state.getOffers().values()) {
                if (!myOffer.getStudent().getId().equals(studentId)) {
                    continue;
                }

                if (!myOffer.isActive()) {
                    continue;
                }

                for (Offer otherOffer : state.getOffers().values()) {
                    if (!otherOffer.isActive()) {
                        continue;
                    }

                    if (otherOffer.getStudent().getId().equals(studentId)) {
                        continue;
                    }

                    if (!otherOffer.getSkill().getId().equals(myRequest.getSkill().getId())) {
                        continue;
                    }

                    for (Request otherRequest : state.getRequests().values()) {
                        if (!otherRequest.getStudent().getId().equals(otherOffer.getStudent().getId())) {
                            continue;
                        }

                        if (!otherRequest.getSkill().getId().equals(myOffer.getSkill().getId())) {
                            continue;
                        }

                        int score = 0;
                        StringBuilder reason = new StringBuilder();

                        score += 3;
                        reason.append("+3 skill uguale per il mio bisogno");

                        if (isLevelSufficient(otherOffer.getLevel(), myRequest.getMinLevel())) {
                            score += 2;
                            reason.append(", +2 livello sufficiente");
                        }

                        if (otherOffer.getStudent().getStudentClass().equalsIgnoreCase(me.getStudentClass())) {
                            score += 1;
                            reason.append(", +1 stessa classe");
                        }

                        if (isLevelSufficient(myOffer.getLevel(), otherRequest.getMinLevel())) {
                            score += 2;
                            reason.append(", +2 reciproco valido");
                        }

                        MatchResult result = new MatchResult(
                                otherOffer.getStudent(),
                                otherOffer,
                                myRequest,
                                score,
                                reason.toString()
                        );

                        String key = otherOffer.getStudent().getId() + "_" + otherOffer.getId();
                        if (seen.add(key)) {
                            results.add(result);
                        }
                    }
                }
            }
        }

        results.sort(Comparator.comparingInt(MatchResult::getScore).reversed());
        return results;
    }

    private boolean isLevelSufficient(SkillLevel offerLevel, SkillLevel requestLevel) {
        return offerLevel.ordinal() >= requestLevel.ordinal();
    }
}
