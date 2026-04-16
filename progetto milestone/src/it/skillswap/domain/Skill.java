package it.skillswap.domain;

public class Skill {
    private final String id;
    private final String name;
    private final SkillCategory category;

    public Skill(String id, String name, SkillCategory category) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("Id skill obbligatorio");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Nome skill obbligatorio");
        }
        if (category == null) {
            throw new IllegalArgumentException("Categoria obbligatoria");
        }

        this.id = id;
        this.name = name;
        this.category = category;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public SkillCategory getCategory() {
        return category;
    }

    @Override
    public String toString() {
        return id + " - " + name + " [" + category + "]";
    }
}