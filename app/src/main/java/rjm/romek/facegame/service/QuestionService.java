package rjm.romek.facegame.service;

import java.util.Set;

import rjm.romek.facegame.model.Question;

public interface QuestionService {
    public Set<Question> generateQuestions();

    public int countCorrectAnswered(Set<Question> questions);
}
