package it.skillswap.domain;

public class Request {
    private final String id;
    private final Student student;
    private final Skill skill;
    private final SkillLevel minLevel;
    private final String note;

    public Request(String id, Student student, Skill skill, SkillLevel minLevel, String note) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("Id request obbligatorio");
        }
        if (student == null) {
            throw new IllegalArgumentException("Studente obbligatorio");
        }
        if (skill == null) {
            throw new IllegalArgumentException("Skill obbligatoria");
        }
        if (minLevel == null) {
            throw new IllegalArgumentException("Livello minimo obbligatorio");
        }

        this.id = id;
        this.student = student;
        this.skill = skill;
        this.minLevel = minLevel;
        this.note = note == null ? "" : note;
    }

    public String getId() {
        return id;
    }

    public Student getStudent() {
        return student;
    }

    public Skill getSkill() {
        return skill;
    }

    public SkillLevel getMinLevel() {
        return minLevel;
    }

    public String getNote() {
        return note;
    }

    @Override
    public String toString() {
        return id + " - student=" + student.getName() +
                ", skill=" + skill.getName() +
                ", minLevel=" + minLevel +
                ", note=" + note;
    }
}