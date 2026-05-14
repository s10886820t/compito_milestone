package it.skillswap.storage;

public class FileStorage implements Storage {

    @Override
    public SkillSwapState load() {
        return new SkillSwapState();
    }

    @Override
    public void save(SkillSwapState state) {

    }
}
