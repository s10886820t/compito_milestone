package it.skillswap.app;

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
        ConsoleReportPrinter printer = new ConsoleReportPrinter();

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
                    case "8" -> listStudentData(sc, service, printer);
                    case "9" -> findOneWayMatches(sc, service, printer);
                    case "10" -> findSwapMatches(sc, service, printer);
                    case "11" -> proposeExchange(sc, service, printer);
                    case "12" -> acceptExchange(sc, service, printer);
                    case "13" -> completeExchange(sc, service, printer);
                    case "14" -> cancelExchange(sc, service, printer);
                    case "15" -> listExchanges(service, printer);
                    case "16" -> addReview(sc, reviewService);
                    case "17" -> listReviews(reviewService);
                    case "18" -> showLeaderboard(service, printer);
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
        System.out.println("8. Profilo studente");
        System.out.println("9. One-way matches");
        System.out.println("10. Swap matches");
        System.out.println("11. Propose exchange");
        System.out.println("12. Accept exchange");
        System.out.println("13. Complete exchange");
        System.out.println("14. Cancel exchange");
        System.out.println("15. Lista exchanges");
        System.out.println("16. Aggiungi review");
        System.out.println("17. Lista reviews");
        System.out.println("18. Leaderboard");
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

    private static void listStudentData(Scanner sc, SkillSwapService service, ConsoleReportPrinter printer) {
        System.out.print("Student ID: ");
        String studentId = sc.nextLine();

        Student student = service.getState().getStudents().get(studentId);
        if (student == null) {
            System.out.println("Studente non trovato");
            return;
        }

        List<Offer> offers = service.listOffersByStudent(studentId);
        List<Request> requests = service.listRequestsByStudent(studentId);

        System.out.println(printer.printStudentProfile(student, offers, requests));
    }

    private static void findOneWayMatches(Scanner sc, SkillSwapService service, ConsoleReportPrinter printer) {
        System.out.print("Student ID: ");
        String studentId = sc.nextLine();

        MatchingService matchingService = new MatchingService(service.getState());
        List<MatchResult> matches = matchingService.findOneWayMatches(studentId);

        System.out.println(printer.printMatches("ONE WAY MATCHES", matches));
    }

    private static void findSwapMatches(Scanner sc, SkillSwapService service, ConsoleReportPrinter printer) {
        System.out.print("Student ID: ");
        String studentId = sc.nextLine();

        MatchingService matchingService = new MatchingService(service.getState());
        List<MatchResult> matches = matchingService.findSwapMatches(studentId);

        System.out.println(printer.printMatches("SWAP MATCHES", matches));
    }

    private static void proposeExchange(Scanner sc, SkillSwapService service, ConsoleReportPrinter printer) {
        System.out.print("Offer ID: ");
        String offerId = sc.nextLine();

        System.out.print("Request ID: ");
        String requestId = sc.nextLine();

        ExchangeService exchangeService = new ExchangeService(service.getStorage(), service.getState());
        Exchange exchange = exchangeService.propose(offerId, requestId);

        System.out.println(printer.printExchangeDetails(exchange));
    }

    private static void acceptExchange(Scanner sc, SkillSwapService service, ConsoleReportPrinter printer) {
        System.out.print("Exchange ID: ");
        String exchangeId = sc.nextLine();

        ExchangeService exchangeService = new ExchangeService(service.getStorage(), service.getState());
        Exchange exchange = exchangeService.accept(exchangeId);

        System.out.println(printer.printExchangeDetails(exchange));
    }

    private static void completeExchange(Scanner sc, SkillSwapService service, ConsoleReportPrinter printer) {
        System.out.print("Exchange ID: ");
        String exchangeId = sc.nextLine();

        ExchangeService exchangeService = new ExchangeService(service.getStorage(), service.getState());
        Exchange exchange = exchangeService.complete(exchangeId);

        System.out.println(printer.printExchangeDetails(exchange));
    }

    private static void cancelExchange(Scanner sc, SkillSwapService service, ConsoleReportPrinter printer) {
        System.out.print("Exchange ID: ");
        String exchangeId = sc.nextLine();

        ExchangeService exchangeService = new ExchangeService(service.getStorage(), service.getState());
        Exchange exchange = exchangeService.cancel(exchangeId);

        System.out.println(printer.printExchangeDetails(exchange));
    }

    private static void listExchanges(SkillSwapService service, ConsoleReportPrinter printer) {
        List<Exchange> exchanges = service.listExchanges();
        if (exchanges.isEmpty()) {
            System.out.println("Nessun exchange presente");
            return;
        }

        for (Exchange exchange : exchanges) {
            System.out.println(printer.printExchangeDetails(exchange));
        }
    }

    private static void addReview(Scanner sc, ReviewService reviewService) {
        System.out.print("Exchange ID: ");
        String exchangeId = sc.nextLine();

        System.out.print("Reviewer ID: ");
        String reviewerId = sc.nextLine();

        System.out.print("Stars (1-5): ");
        int stars = Integer.parseInt(sc.nextLine());

        System.out.print("Commento: ");
        String comment = sc.nextLine();

        Review review = reviewService.addReview(exchangeId, reviewerId, stars, comment);
        System.out.println("Review aggiunta: " + review.getId());
    }

    private static void listReviews(ReviewService reviewService) {
        List<Review> reviews = reviewService.listReviews();
        if (reviews.isEmpty()) {
            System.out.println("Nessuna review presente");
            return;
        }

        for (Review review : reviews) {
            System.out.println(
                    review.getId() +
                    " - exchange=" + review.getExchange().getId() +
                    ", reviewer=" + review.getReviewer().getName() +
                    ", reviewee=" + review.getReviewee().getName() +
                    ", stars=" + review.getStars() +
                    ", comment=" + review.getComment() +
                    ", createdAt=" + review.getCreatedAt()
            );
        }
    }

    private static void showLeaderboard(SkillSwapService service, ConsoleReportPrinter printer) {
        System.out.println(printer.printLeaderboard(service.listStudents()));
    }
}