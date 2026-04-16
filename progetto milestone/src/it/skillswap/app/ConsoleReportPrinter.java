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

import it.skillswap.domain.Exchange;
import it.skillswap.domain.MatchResult;
import it.skillswap.domain.Offer;
import it.skillswap.domain.Request;
import it.skillswap.domain.Review;
import it.skillswap.domain.Skill;
import it.skillswap.domain.SkillLevel;
import it.skillswap.domain.Student;
import it.skillswap.service.ExchangeService;
import it.skillswap.service.MatchingService;
import it.skillswap.service.ReviewService;
import it.skillswap.service.SkillSwapService;
import it.skillswap.storage.InMemoryStorage;
import java.util.List;
import java.util.Scanner;

public class SkillSwapApp {
    public static void main(String[] args) {
        SkillSwapService service = new SkillSwapService(new InMemoryStorage());
        service.seedSkills();
        ReviewService reviewService = new ReviewService(service.getStorage(), service.getState());

        Scanner sc = new Scanner(System.in);
        boolean running = true;

        while (running) {
            printMenu();
            String choice = sc.nextLine();

            try {
                switch (choice) {
                    case "1" -> createStudent(sc, service);
                    case "2" -> listStudents(service);
                    case "3" -> listSkills(service);
                    case "4" -> addOffer(sc, service);
                    case "5" -> addRequest(sc, service);
                    case "6" -> listOffers(service);
                    case "7" -> listRequests(service);
                    case "8" -> listStudentData(sc, service);
                    case "9" -> findOneWayMatches(sc, service);
                    case "10" -> findSwapMatches(sc, service);
                    case "11" -> proposeExchange(sc, service);
                    case "12" -> acceptExchange(sc, service);
                    case "13" -> completeExchange(sc, service);
                    case "14" -> cancelExchange(sc, service);
                    case "15" -> listExchanges(service);
                    case "16" -> addReview(sc, reviewService);
                    case "17" -> listReviews(reviewService);
                    case "0" -> running = false;
                    default -> System.out.println("Scelta non valida");
                }
            } catch (Exception e) {
                System.out.println("Errore: " + e.getMessage());
            }

            System.out.println();
        }

        sc.close();
    }

    private static void printMenu() {
        System.out.println("=== SkillSwap School ===");
        System.out.println("1. Crea studente");
        System.out.println("2. Lista studenti");
        System.out.println("3. Lista skill");
        System.out.println("4. Aggiungi offer");
        System.out.println("5. Aggiungi request");
        System.out.println("6. Lista offers");
        System.out.println("7. Lista requests");
        System.out.println("8. Lista offer/request per studente");
        System.out.println("9. One-way matches");
        System.out.println("10. Swap matches");
        System.out.println("11. Propose exchange");
        System.out.println("12. Accept exchange");
        System.out.println("13. Complete exchange");
        System.out.println("14. Cancel exchange");
        System.out.println("15. Lista exchanges");
        System.out.println("16. Aggiungi review");
        System.out.println("17. Lista reviews");
        System.out.println("0. Esci");
        System.out.print("Scelta: ");
    }

    private static void createStudent(Scanner sc, SkillSwapService service) {
        System.out.print("Nome: ");
        String name = sc.nextLine();

        System.out.print("Classe: ");
        String studentClass = sc.nextLine();

        System.out.print("Email: ");
        String email = sc.nextLine();

        Student student = service.registerStudent(name, studentClass, email);
        System.out.println("Creato: " + student);
    }

    private static void listStudents(SkillSwapService service) {
        List<Student> students = service.listStudents();
        if (students.isEmpty()) {
            System.out.println("Nessuno studente presente");
            return;
        }

        for (Student s : students) {
            System.out.println(s);
        }
    }

    private static void listSkills(SkillSwapService service) {
        List<Skill> skills = service.listSkills();
        if (skills.isEmpty()) {
            System.out.println("Nessuna skill presente");
            return;
        }

        for (Skill skill : skills) {
            System.out.println(skill);
        }
    }

    private static void addOffer(Scanner sc, SkillSwapService service) {
        System.out.print("Student ID: ");
        String studentId = sc.nextLine();

        System.out.print("Skill ID: ");
        String skillId = sc.nextLine();

        System.out.print("Level (BEGINNER, INTERMEDIATE, ADVANCED): ");
        SkillLevel level = SkillLevel.valueOf(sc.nextLine().toUpperCase());

        System.out.print("Nota: ");
        String note = sc.nextLine();

        Offer offer = service.addOffer(studentId, skillId, level, note);
        System.out.println("Offer creata: " + offer);
    }

    private static void addRequest(Scanner sc, SkillSwapService service) {
        System.out.print("Student ID: ");
        String studentId = sc.nextLine();

        System.out.print("Skill ID: ");
        String skillId = sc.nextLine();

        System.out.print("Min level (BEGINNER, INTERMEDIATE, ADVANCED): ");
        SkillLevel minLevel = SkillLevel.valueOf(sc.nextLine().toUpperCase());

        System.out.print("Nota: ");
        String note = sc.nextLine();

        Request request = service.addRequest(studentId, skillId, minLevel, note);
        System.out.println("Request creata: " + request);
    }

    private static void listOffers(SkillSwapService service) {
        List<Offer> offers = service.listOffers();
        if (offers.isEmpty()) {
            System.out.println("Nessuna offer presente");
            return;
        }

        for (Offer offer : offers) {
            System.out.println(offer);
        }
    }

    private static void listRequests(SkillSwapService service) {
        List<Request> requests = service.listRequests();
        if (requests.isEmpty()) {
            System.out.println("Nessuna request presente");
            return;
        }

        for (Request request : requests) {
            System.out.println(request);
        }
    }

    private static void listStudentData(Scanner sc, SkillSwapService service) {
        System.out.print("Student ID: ");
        String studentId = sc.nextLine();

        System.out.println("--- Offers ---");
        for (Offer offer : service.listOffersByStudent(studentId)) {
            System.out.println(offer);
        }

        System.out.println("--- Requests ---");
        for (Request request : service.listRequestsByStudent(studentId)) {
            System.out.println(request);
        }
    }

    private static void findOneWayMatches(Scanner sc, SkillSwapService service) {
        System.out.print("Student ID: ");
        String studentId = sc.nextLine();

        MatchingService matchingService = new MatchingService(service.getState());
        List<MatchResult> matches = matchingService.findOneWayMatches(studentId);

        if (matches.isEmpty()) {
            System.out.println("Nessun match trovato");
            return;
        }

        for (MatchResult match : matches) {
            System.out.println(match);
        }
    }

    private static void findSwapMatches(Scanner sc, SkillSwapService service) {
        System.out.print("Student ID: ");
        String studentId = sc.nextLine();

        MatchingService matchingService = new MatchingService(service.getState());
        List<MatchResult> matches = matchingService.findSwapMatches(studentId);

        if (matches.isEmpty()) {
            System.out.println("Nessun match reciproco trovato");
            return;
        }

        for (MatchResult match : matches) {
            System.out.println(match);
        }
    }

    private static void proposeExchange(Scanner sc, SkillSwapService service) {
        System.out.print("Offer ID: ");
        String offerId = sc.nextLine();

        System.out.print("Request ID: ");
        String requestId = sc.nextLine();

        ExchangeService exchangeService = new ExchangeService(service.getStorage(), service.getState());
        Exchange exchange = exchangeService.propose(offerId, requestId);

        System.out.println("Exchange creato: " + exchange);
    }

    private static void acceptExchange(Scanner sc, SkillSwapService service) {
        System.out.print("Exchange ID: ");
        String exchangeId = sc.nextLine();

        ExchangeService exchangeService = new ExchangeService(service.getStorage(), service.getState());
        Exchange exchange = exchangeService.accept(exchangeId);

        System.out.println("Exchange aggiornato: " + exchange);
    }

    private static void completeExchange(Scanner sc, SkillSwapService service) {
        System.out.print("Exchange ID: ");
        String exchangeId = sc.nextLine();

        ExchangeService exchangeService = new ExchangeService(service.getStorage(), service.getState());
        Exchange exchange = exchangeService.complete(exchangeId);

        System.out.println("Exchange aggiornato: " + exchange);
    }

    private static void cancelExchange(Scanner sc, SkillSwapService service) {
        System.out.print("Exchange ID: ");
        String exchangeId = sc.nextLine();

        ExchangeService exchangeService = new ExchangeService(service.getStorage(), service.getState());
        Exchange exchange = exchangeService.cancel(exchangeId);

        System.out.println("Exchange aggiornato: " + exchange);
    }

    private static void listExchanges(SkillSwapService service) {
        List<Exchange> exchanges = service.listExchanges();

        if (exchanges.isEmpty()) {
            System.out.println("Nessun exchange presente");
            return;
        }

        for (Exchange exchange : exchanges) {
            System.out.println(exchange);
        }

    }
    private static void addReview(Scanner sc, ReviewService reviewService) {
System.out.print("Exchange ID: ");
String exchangeId = sc.nextLine();

System.out.print("Reviewer student ID: ");
String reviewerId = sc.nextLine();

System.out.print("Stars (1-5): ");
int stars = Integer.parseInt(sc.nextLine());

System.out.print("Commento: ");
String comment = sc.nextLine();

Review review = reviewService.addReview(exchangeId, reviewerId, stars, comment);
System.out.println("Review creata: " + review.getId());
}

private static void listReviews(ReviewService reviewService) {
List<Review> reviews = reviewService.listReviews();

if (reviews.isEmpty()) {
System.out.println("Nessuna review presente");
return;
}

for (Review review : reviews) {
System.out.println(
review.getId()
+ " - exchange=" + review.getExchange().getId()
+ ", reviewer=" + review.getReviewer().getId()
+ ", reviewee=" + review.getReviewee().getId()
+ ", stars=" + review.getStars()
+ ", comment=" + review.getComment()
);
}
}
}tor.comparingDouble(Student::getRatingAvg).reversed()
                        .thenComparing(Comparator.comparingInt(Student::getRatingCount).reversed())
                        .thenComparing(Student::getName)
        );

        sb.append("========================================\n");
        sb.append("              LEADERBOARD\n");
        sb.append("========================================\n");

        if (ordered.isEmpty()) {
            sb.append("Nessuno studente presente\n");
            return sb.toString();
        }

        int posizione = 1;
        for (Student student : ordered) {
            sb.append(posizione).append(". ")
                    .append(student.getName())
                    .append(" [").append(student.getId()).append("]")
                    .append("\n");
            sb.append("   Classe: ").append(student.getStudentClass()).append("\n");
            sb.append("   Rating medio: ").append(student.getRatingAvg()).append("\n");
            sb.append("   Reviews: ").append(student.getRatingCount()).append("\n");
            sb.append("----------------------------------------\n");
            posizione++;
        }

        return sb.toString();
    }
}