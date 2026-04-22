package it.skillswap.domain;

public class Offer {

    private final String id;
    private final Student student;
    private final Skill skill;
    private final SkillLevel level;
    private final String note;
    private boolean active;

    public Offer(String id, Student student, Skill skill, SkillLevel level, String note) {
        this(id, student, skill, level, note, true);
    }

    public Offer(String id, Student student, Skill skill, SkillLevel level, String note, boolean active) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("Id offer obbligatorio");
        }
        if (student == null) {
            throw new IllegalArgumentException("Studente obbligatorio");
        }
        if (skill == null) {
            throw new IllegalArgumentException("Skill obbligatoria");
        }
        if (level == null) {
            throw new IllegalArgumentException("Livello obbligatorio");
        }

        this.id = id;
        this.student = student;
        this.skill = skill;
        this.level = level;
        this.note = note == null ? "" : note;
        this.active = active;
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

    public SkillLevel getLevel() {
        return level;
    }

    public String getNote() {
        return note;
    }

    public boolean isActive() {
        return active;
    }

    public void deactivate() {
        active = false;
    }

    public void activate() {
        active = true;
    }

    @Override
    public String toString() {
        return id + " - student=" + student.getName()
                + ", skill=" + skill.getName()
                + ", level=" + level
                + ", active=" + active
                + ", note=" + note;
    }
}
