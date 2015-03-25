package rjm.romek.facegame.service;

import java.util.ArrayList;
import java.util.List;

import rjm.romek.facegame.model.Difficulty;
import rjm.romek.facegame.model.Question;

import static java.lang.Math.ceil;
import static java.lang.Math.max;

public class ScoreServiceImpl implements ScoreService {

    private List<Question> questionList;
    private long totalScore;
    private int multiplier;
    private long multiplicand;
    private long currentScore;

    public ScoreServiceImpl() {
        questionList = new ArrayList<>();
    }

    @Override
    public void addQuestion(Question question) {
        questionList.add(question);

        Difficulty difficulty = question.getDifficulty();

        if(question.isCorrectlyAnswered()) {
            ++multiplier;
            long totalTime = difficulty.getTime();
            long answerTimeAsPercentageOfTotalTime =  max(((totalTime - question.getAnswerTime()) * 100 / totalTime) - 1, 10);
            multiplicand += ceil(answerTimeAsPercentageOfTotalTime * difficulty.getLevelPointMultiplier());
            totalScore = currentScore + multiplier * multiplicand;
        } else {
            currentScore += multiplier * multiplicand;
            multiplier = 0;
            multiplicand = 0;
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
