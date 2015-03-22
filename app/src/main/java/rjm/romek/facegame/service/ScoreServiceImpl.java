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

    public ScoreServiceImpl() {
        questionList = new ArrayList<Question>();
    }

    @Override
    public void addQuestion(Question question) {
        questionList.add(question);

        Difficulty difficulty = question.getDifficulty();

        if(question.isCorrectlyAnswered()) {
            ++multiplier;
        } else {
            multiplier = 1;
        }

        long totalTime = difficulty.getTime();
        long answerTimeAsPercentageOfTotalTime =  (totalTime - question.getAnswerTime()) * 100 / totalTime;
        multiplicand += Math.round(answerTimeAsPercentageOfTotalTime * difficulty.getLevelPointMultiplier());

        if(multiplier == 1) {
            totalScore += multiplier * multiplicand;
        }
    }

    @Override
    public long getTotalScore() {
        return totalScore;
    }

    @Override
    public long getCurrentScore() {
        return 0;
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
        return getMultiplier() + "*" + getMultiplicand() + " " + getTotalScore();
    }
}
