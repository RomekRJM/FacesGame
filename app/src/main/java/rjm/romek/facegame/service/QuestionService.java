package rjm.romek.facegame.service;

import rjm.romek.facegame.model.Difficulty;
import rjm.romek.facegame.model.Question;
import rjm.romek.source.model.Country;

import java.util.Set;

public interface QuestionService {
    public Set<Question> generateQuestions(Difficulty difficulty);
}
