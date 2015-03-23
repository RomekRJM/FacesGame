package rjm.romek.facegame.service;

import java.util.ArrayList;
import java.util.List;

import rjm.romek.facegame.model.Difficulty;
import rjm.romek.facegame.model.Question;

public class ScoreServiceImpl implements ScoreService {

    private List<Question> questionList;
    private long totalScore;
    private int multiplier;
    private long multiplicand;
    private long currentScore;

    public ScoreServiceImpl() {
        questionList = new ArrayList<Question>();
    }

    @Override
    public void addQuestion(Question question) {
        questionList.add(question);

        Difficulty difficulty = question.getDifficulty();

        if(question.isCorrectlyAnswered()) {
            ++multiplier;
            long totalTime = difficulty.getTime();
            long answerTimeAsPercentageOfTotalTime =  (totalTime - question.getAnswerTime()) * 100 / totalTime;
            multiplicand += Math.round(answerTimeAsPercentageOfTotalTime * difficulty.getLevelPointMultiplier());
            totalScore = currentScore + multiplier * multiplicand;
        } else {
            multiplier = 0;
            multiplicand = 0;
            currentScore += totalScore;
        }
    }

    @Override
    public long getTotalScore() {
        return totalScore;
    }

    @Override
    public long getCurrentScore() {
        return currentScore;
    }

    @Override
    public int getMultiplier() {
        return multiplier;
    }

    @Override
    public long getMultiplicand() {
        return multiplicand;
    }

    @Override
    public String toString() {
        return getMultiplier() + "x" + getMultiplicand()
                + " (" + getTotalScore() + ") " + currentScore;
    }
}
