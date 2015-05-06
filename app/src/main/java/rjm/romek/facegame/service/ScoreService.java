package rjm.romek.facegame.service;

import rjm.romek.facegame.model.Question;

public interface ScoreService {
    void addQuestion(Question question);

    long getTotalScore();

    long getCurrentScore();

    int getMultiplier();

    long getMultiplicand();
}
