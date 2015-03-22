package rjm.romek.facegame.service;

import rjm.romek.facegame.model.Question;

public interface ScoreService {
    public void addQuestion(Question question);
    public long getTotalScore();
    public long getCurrentScore();
    public int getMultiplier();
    public long getMultiplicand();
}
