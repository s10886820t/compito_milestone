package it.skillswap.app;

import it.skillswap.domain.Exchange;
import it.skillswap.domain.MatchResult;
import it.skillswap.domain.Offer;
import it.skillswap.domain.Request;
import it.skillswap.domain.Student;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ConsoleReportPrinter {

    public String printStudentProfile(Student student, List<Offer> offers, List<Request> requests) {
        StringBuilder sb = new StringBuilder();

        sb.append("========================================\n");
        sb.append("            PROFILO STUDENTE\n");
        sb.append("========================================\n");
        sb.append("ID: ").append(student.getId()).append("\n");
        sb.append("Nome: ").append(student.getName()).append("\n");
        sb.append("Classe: ").append(student.getStudentClass()).append("\n");
        sb.append("Email: ").append(student.getEmail()).append("\n");
        sb.append("Rating medio: ").append(student.getRatingAvg()).append("\n");
        sb.append("Numero recensioni: ").append(student.getRatingCount()).append("\n");
        sb.append("----------------------------------------\n");
        sb.append("OFFERS\n");

        if (offers.isEmpty()) {
            sb.append("  Nessuna offer presente\n");
        } else {
            for (Offer offer : offers) {
                sb.append("  - ID: ").append(offer.getId()).append("\n");
                sb.append("    Skill: ").append(offer.getSkill().getName()).append("\n");
                sb.append("    Livello: ").append(offer.getLevel()).append("\n");
                sb.append("    Attiva: ").append(offer.isActive()).append("\n");
                sb.append("    Nota: ").append(offer.getNote()).append("\n");
                sb.append("    ------------------------------------\n");
            }
        }

        sb.append("REQUESTS\n");

        if (requests.isEmpty()) {
            sb.append("  Nessuna request presente\n");
        } else {
            for (Request request : requests) {
                sb.append("  - ID: ").append(request.getId()).append("\n");
                sb.append("    Skill: ").append(request.getSkill().getName()).append("\n");
                sb.append("    Livello minimo: ").append(request.getMinLevel()).append("\n");
                sb.append("    Nota: ").append(request.getNote()).append("\n");
                sb.append("    ------------------------------------\n");
            }
        }

        return sb.toString();
    }

    public String printMatches(String title, List<MatchResult> matches) {
        StringBuilder sb = new StringBuilder();

        sb.append("========================================\n");
        sb.append("            ").append(title).append("\n");
        sb.append("========================================\n");

        if (matches.isEmpty()) {
            sb.append("Nessun match trovato\n");
            return sb.toString();
        }

        for (MatchResult match : matches) {
            sb.append("Studente trovato: ").append(match.getMatchedStudent().getName()).append("\n");
            sb.append("  ID studente: ").append(match.getMatchedStudent().getId()).append("\n");
            sb.append("  Offer ID: ").append(match.getOffer().getId()).append("\n");
            sb.append("  Skill offerta: ").append(match.getOffer().getSkill().getName()).append("\n");
            sb.append("  Livello offerto: ").append(match.getOffer().getLevel()).append("\n");
            sb.append("  Request ID: ").append(match.getRequest().getId()).append("\n");
            sb.append("  Skill richiesta: ").append(match.getRequest().getSkill().getName()).append("\n");
            sb.append("  Livello minimo richiesto: ").append(match.getRequest().getMinLevel()).append("\n");
            sb.append("  Score: ").append(match.getScore()).append("\n");
            sb.append("  Motivo: ").append(match.getReason()).append("\n");
            sb.append("----------------------------------------\n");
        }

        return sb.toString();
    }

    public String printExchangeDetails(Exchange exchange) {
        StringBuilder sb = new StringBuilder();

        sb.append("========================================\n");
        sb.append("            DETTAGLIO EXCHANGE\n");
        sb.append("========================================\n");
        sb.append("Exchange ID: ").append(exchange.getId()).append("\n");
        sb.append("Stato: ").append(exchange.getStatus()).append("\n");
        sb.append("Creato il: ").append(exchange.getCreatedAt()).append("\n");
        sb.append("Chiuso il: ").append(exchange.getClosedAt()).append("\n");
        sb.append("----------------------------------------\n");

        sb.append("OFFER\n");
        sb.append("  ID: ").append(exchange.getOffer().getId()).append("\n");
        sb.append("  Studente: ").append(exchange.getOffer().getStudent().getName()).append("\n");
        sb.append("  Skill: ").append(exchange.getOffer().getSkill().getName()).append("\n");
        sb.append("  Livello: ").append(exchange.getOffer().getLevel()).append("\n");
        sb.append("  Attiva: ").append(exchange.getOffer().isActive()).append("\n");
        sb.append("  Nota: ").append(exchange.getOffer().getNote()).append("\n");
        sb.append("----------------------------------------\n");

        sb.append("REQUEST\n");
        sb.append("  ID: ").append(exchange.getRequest().getId()).append("\n");
        sb.append("  Studente: ").append(exchange.getRequest().getStudent().getName()).append("\n");
        sb.append("  Skill: ").append(exchange.getRequest().getSkill().getName()).append("\n");
        sb.append("  Livello minimo: ").append(exchange.getRequest().getMinLevel()).append("\n");
        sb.append("  Nota: ").append(exchange.getRequest().getNote()).append("\n");

        return sb.toString();
    }

    public String printLeaderboard(List<Student> students) {
        StringBuilder sb = new StringBuilder();
        List<Student> ordered = new ArrayList<>(students);

        ordered.sort(
                Comparapackage it.skillswap.app;

