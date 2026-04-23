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

    private static final String LINE = "============================================================";
    private static final String SUB = "------------------------------------------------------------";

    private String center(String text) {
        int width = LINE.length();
        if (text.length() >= width) {
            return text;
        }

        int left = (width - text.length()) / 2;
        return " ".repeat(left) + text;
    }

    private void appendHeader(StringBuilder sb, String title) {
        sb.append("\n");
        sb.append(LINE).append("\n");
        sb.append(center(title)).append("\n");
        sb.append(LINE).append("\n");
    }

    private void appendSection(StringBuilder sb, String title) {
        sb.append("\n");
        sb.append(title).append("\n");
        sb.append(SUB).append("\n");
    }

    public String printStudentProfile(Student student, List<Offer> offers, List<Request> requests) {
        StringBuilder sb = new StringBuilder();

        appendHeader(sb, "PROFILO STUDENTE");

        sb.append(" ID studente       : ").append(student.getId()).append("\n");
        sb.append(" Nome              : ").append(student.getName()).append("\n");
        sb.append(" Classe            : ").append(student.getStudentClass()).append("\n");
        sb.append(" Email             : ").append(student.getEmail()).append("\n");
        sb.append(" Rating medio      : ").append(student.getRatingAvg()).append("\n");
        sb.append(" Numero recensioni : ").append(student.getRatingCount()).append("\n");

        appendSection(sb, "OFFERS");

        if (offers.isEmpty()) {
            sb.append(" Nessuna offer presente\n");
        } else {
            int i = 1;
            for (Offer offer : offers) {
                sb.append(" [").append(i).append("]\n");
                sb.append("   ID         : ").append(offer.getId()).append("\n");
                sb.append("   Skill      : ").append(offer.getSkill().getName()).append("\n");
                sb.append("   Livello    : ").append(offer.getLevel()).append("\n");
                sb.append("   Attiva     : ").append(offer.isActive() ? "SI" : "NO").append("\n");
                sb.append("   Nota       : ").append(offer.getNote().isBlank() ? "-" : offer.getNote()).append("\n");
                sb.append(SUB).append("\n");
                i++;
            }
        }

        appendSection(sb, "REQUESTS");

        if (requests.isEmpty()) {
            sb.append(" Nessuna request presente\n");
        } else {
            int i = 1;
            for (Request request : requests) {
                sb.append(" [").append(i).append("]\n");
                sb.append("   ID               : ").append(request.getId()).append("\n");
                sb.append("   Skill            : ").append(request.getSkill().getName()).append("\n");
                sb.append("   Livello minimo   : ").append(request.getMinLevel()).append("\n");
                sb.append("   Nota             : ").append(request.getNote().isBlank() ? "-" : request.getNote()).append("\n");
                sb.append(SUB).append("\n");
                i++;
            }
        }

        return sb.toString();
    }

    public String printMatches(String title, List<MatchResult> matches) {
        StringBuilder sb = new StringBuilder();

        appendHeader(sb, title);

        if (matches.isEmpty()) {
            sb.append(" Nessun match trovato\n");
            return sb.toString();
        }

        int posizione = 1;
        for (MatchResult match : matches) {
            sb.append(" MATCH #").append(posizione).append("\n");
            sb.append(SUB).append("\n");
            sb.append(" Studente trovato        : ").append(match.getMatchedStudent().getName()).append("\n");
            sb.append(" ID studente             : ").append(match.getMatchedStudent().getId()).append("\n");
            sb.append(" Offer ID                : ").append(match.getOffer().getId()).append("\n");
            sb.append(" Skill offerta           : ").append(match.getOffer().getSkill().getName()).append("\n");
            sb.append(" Livello offerto         : ").append(match.getOffer().getLevel()).append("\n");
            sb.append(" Request ID              : ").append(match.getRequest().getId()).append("\n");
            sb.append(" Skill richiesta         : ").append(match.getRequest().getSkill().getName()).append("\n");
            sb.append(" Livello minimo richiesto: ").append(match.getRequest().getMinLevel()).append("\n");
            sb.append(" Score                   : ").append(match.getScore()).append("\n");
            sb.append(" Motivo                  : ").append(match.getReason()).append("\n");
            sb.append(LINE).append("\n");
            posizione++;
        }

        return sb.toString();
    }

    public String printExchangeDetails(Exchange exchange) {
        StringBuilder sb = new StringBuilder();

        appendHeader(sb, "DETTAGLIO EXCHANGE");

        sb.append(" Exchange ID : ").append(exchange.getId()).append("\n");
        sb.append(" Stato       : ").append(exchange.getStatus()).append("\n");
        sb.append(" Creato il   : ").append(exchange.getCreatedAt()).append("\n");
        sb.append(" Chiuso il   : ").append(exchange.getClosedAt()).append("\n");

        appendSection(sb, "OFFER");

        sb.append(" ID         : ").append(exchange.getOffer().getId()).append("\n");
        sb.append(" Studente   : ").append(exchange.getOffer().getStudent().getName()).append("\n");
        sb.append(" Skill      : ").append(exchange.getOffer().getSkill().getName()).append("\n");
        sb.append(" Livello    : ").append(exchange.getOffer().getLevel()).append("\n");
        sb.append(" Attiva     : ").append(exchange.getOffer().isActive() ? "SI" : "NO").append("\n");
        sb.append(" Nota       : ").append(exchange.getOffer().getNote().isBlank() ? "-" : exchange.getOffer().getNote()).append("\n");

        appendSection(sb, "REQUEST");

        sb.append(" ID             : ").append(exchange.getRequest().getId()).append("\n");
        sb.append(" Studente       : ").append(exchange.getRequest().getStudent().getName()).append("\n");
        sb.append(" Skill          : ").append(exchange.getRequest().getSkill().getName()).append("\n");
        sb.append(" Livello minimo : ").append(exchange.getRequest().getMinLevel()).append("\n");
        sb.append(" Nota           : ").append(exchange.getRequest().getNote().isBlank() ? "-" : exchange.getRequest().getNote()).append("\n");

        sb.append("\n").append(LINE).append("\n");
        return sb.toString();
    }

    public String printLeaderboard(List<Student> students) {
        StringBuilder sb = new StringBuilder();
        List<Student> ordered = new ArrayList<>(students);

        ordered.sort(
                Comparator.comparingDouble(Student::getRatingAvg).reversed()
                        .thenComparing(Comparator.comparingInt(Student::getRatingCount).reversed())
                        .thenComparing(Student::getName)
        );

        appendHeader(sb, "LEADERBOARD");

        if (ordered.isEmpty()) {
            sb.append(" Nessuno studente presente\n");
            return sb.toString();
        }

        int posizione = 1;
        for (Student student : ordered) {
            sb.append(" #").append(posizione).append("  ").append(student.getName())
                    .append("  [").append(student.getId()).append("]").append("\n");
            sb.append("     Classe       : ").append(student.getStudentClass()).append("\n");
            sb.append("     Rating medio : ").append(student.getRatingAvg()).append("\n");
            sb.append("     Recensioni   : ").append(student.getRatingCount()).append("\n");
            sb.append(SUB).append("\n");
            posizione++;
        }

        return sb.toString();
    }
}