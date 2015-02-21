package rjm.romek.facegame.service;

import rjm.romek.facegame.model.Difficulty;
import rjm.romek.facegame.model.Question;

import java.util.Set;

public interface QuestionService {
    public Set<Question> generateQuestions(Difficulty difficulty);
    public int countCorrectAnswered(Set<Question> questions);
}
