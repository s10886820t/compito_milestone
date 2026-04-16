package it.skillswap.storage;

public interface Storage {
    SkillSwapState load();
    void save(SkillSwapState state);
}