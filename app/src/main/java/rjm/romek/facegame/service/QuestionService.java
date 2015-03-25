package rjm.romek.facegame.service;

import java.util.Set;

import rjm.romek.facegame.model.Difficulty;
import rjm.romek.facegame.model.Question;

public interface QuestionService {
    public Set<Question> generateQuestions(Difficulty difficulty);

    public int countCorrectAnswered(Set<Question> questions);
}
