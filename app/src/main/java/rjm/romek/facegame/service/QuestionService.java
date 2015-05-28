package rjm.romek.facegame.service;

import java.util.Set;

import rjm.romek.facegame.model.Question;

public interface QuestionService {
    Question generateQuestion(Set<Question> previous);

    int countCorrectAnswered(Set<Question> questions);

    void saveQuestions(Set<Question> questions);

    long countUniqueRightGuessesForCountry(String country);

    int countConsecutiveDaysPlaying(int target);
}
