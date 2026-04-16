package it.skillswap.service;

import it.skillswap.domain.Exchange;
import it.skillswap.domain.Offer;
import it.skillswap.domain.Request;
import it.skillswap.domain.Skill;
import it.skillswap.domain.SkillCategory;
import it.skillswap.domain.SkillLevel;
import it.skillswap.domain.Student;
import it.skillswap.storage.SkillSwapState;
import it.skillswap.storage.Storage;

import java.util.ArrayList;
import java.util.List;

public class SkillSwapService {
    private final Storage storage;
    private final SkillSwapState state;
    private final IdGenerator idGenerator;

    public SkillSwapService(Storage storage) {
        this.storage = storage;
        this.state = storage.load();
        this.idGenerator = new IdGenerator();
    }

    public Student registerStudent(String name, String studentClass, String email) {
        String id = idGenerator.next("S");
        Student student = new Student(id, name, studentClass, email);
        state.getStudents().put(id, student);
        storage.save(state);
        return student;
    }

    public Skill addSkill(String name, SkillCategory category) {
        String id = idGenerator.next("K");
        Skill skill = new Skill(id, name, category);
        state.getSkills().put(id, skill);
        storage.save(state);
        return skill;
    }

    public Offer addOffer(String studentId, String skillId, SkillLevel level, String note) {
        Student student = state.getStudents().get(studentId);
        if (student == null) {
            throw new IllegalArgumentException("Studente non trovato");
        }

        Skill skill = state.getSkills().get(skillId);
        if (skill == null) {
            throw new IllegalArgumentException("Skill non trovata");
        }

        String id = idGenerator.next("O");
        Offer offer = new Offer(id, student, skill, level, note, true);
        state.getOffers().put(id, offer);
        storage.save(state);
        return offer;
    }

    public Request addRequest(String studentId, String skillId, SkillLevel minLevel, String note) {
        Student student = state.getStudents().get(studentId);
        if (student == null) {
            throw new IllegalArgumentException("Studente non trovato");
        }

        Skill skill = state.getSkills().get(skillId);
        if (skill == null) {
            throw new IllegalArgumentException("Skill non trovata");
        }

        String id = idGenerator.next("R");
        Request request = new Request(id, student, skill, minLevel, note);
        state.getRequests().put(id, request);
        storage.save(state);
        return request;
    }

    public List<Student> listStudents() {
        return new ArrayList<>(state.getStudents().values());
    }

    public List<Skill> listSkills() {
        return new ArrayList<>(state.getSkills().values());
    }

    public List<Offer> listOffers() {
        return new ArrayList<>(state.getOffers().values());
    }

    public List<Request> listRequests() {
        return new ArrayList<>(state.getRequests().values());
    }

    public List<Exchange> listExchanges() {
        return new ArrayList<>(state.getExchanges().values());
    }

    public List<Offer> listOffersByStudent(String studentId) {
        List<Offer> result = new ArrayList<>();

        for (Offer offer : state.getOffers().values()) {
            if (offer.getStudent().getId().equals(studentId)) {
                result.add(offer);
            }
        }

        return result;
    }

    public List<Request> listRequestsByStudent(String studentId) {
        List<Request> result = new ArrayList<>();

        for (Request request : state.getRequests().values()) {
            if (request.getStudent().getId().equals(studentId)) {
                result.add(request);
            }
        }

        return result;
    }

    public SkillSwapState getState() {
        return state;
    }

    public Storage getStorage() {
        return storage;
    }

    public void seedSkills() {
        if (!state.getSkills().isEmpty()) {
            return;
        }

        addSkill("Programmazione C", SkillCategory.SUBJECT);
        addSkill("Matematica", SkillCategory.SUBJECT);
        addSkill("Inglese", SkillCategory.LANGUAGE);
        addSkill("Public Speaking", SkillCategory.SOFT_SKILL);
    }
}