package rjm.romek.facegame.service;

import java.util.Set;

import rjm.romek.facegame.model.Question;

public interface QuestionService {
    public Question generateQuestion(Set<Question> previous);

    public int countCorrectAnswered(Set<Question> questions);
}
