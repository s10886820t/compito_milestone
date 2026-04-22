package it.skillswap.domain;

public class Student {

    private final String id;
    private final String name;
    private final String studentClass;
    private final String email;
    private double ratingAvg;
    private int ratingCount;

    public Student(String id, String name, String studentClass, String email) {
        this(id, name, studentClass, email, 0.0, 0);
    }

    public Student(String id, String name, String studentClass, String email, double ratingAvg, int ratingCount) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("Id studente obbligatorio");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Nome obbligatorio");
        }
        if (studentClass == null || studentClass.isBlank()) {
            throw new IllegalArgumentException("Classe obbligatoria");
        }
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email obbligatoria");
        }
        if (!email.contains("@")) {
            throw new IllegalArgumentException("Email non valida");
        }
        if (ratingAvg < 0 || ratingAvg > 5) {
            throw new IllegalArgumentException("Rating medio non valido");
        }
        if (ratingCount < 0) {
            throw new IllegalArgumentException("Numero recensioni non valido");
        }

        this.id = id;
        this.name = name;
        this.studentClass = studentClass;
        this.email = email;
        this.ratingAvg = ratingAvg;
        this.ratingCount = ratingCount;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getStudentClass() {
        return studentClass;
    }

    public String getEmail() {
        return email;
    }

    public double getRatingAvg() {
        return ratingAvg;
    }

    public int getRatingCount() {
        return ratingCount;
    }

    public void addRating(int stars) {
        if (stars < 1 || stars > 5) {
            throw new IllegalArgumentException("Il voto deve essere tra 1 e 5");
        }

        double total = ratingAvg * ratingCount;
        total += stars;
        ratingCount++;
        ratingAvg = total / ratingCount;
    }

    @Override
    public String toString() {
        return id + " - " + name + " (" + studentClass + ", " + email + ") rating=" + ratingAvg + " [" + ratingCount + "]";
    }
}
