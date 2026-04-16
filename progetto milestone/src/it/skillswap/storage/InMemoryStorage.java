package it.skillswap.storage;

public class InMemoryStorage implements Storage {
    private final SkillSwapState state = new SkillSwapState();

    @Override
    public SkillSwapState load() {
        return state;
    }

    @Override
    public void save(SkillSwapState state) {
    }
}