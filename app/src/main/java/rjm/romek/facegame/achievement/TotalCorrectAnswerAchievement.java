package rjm.romek.facegame.achievement;

import java.util.Set;

import rjm.romek.facegame.model.Question;

public class TotalCorrectAnswerAchievement extends SumAchievementUpdater {
    public TotalCorrectAnswerAchievement(long value) {
        super(value);
    }

    @Override
    protected Long transform(Set<Question> change) {
        long totalCorrectAnswers = 0;

        for(Question q : change) {
            if(q.isCorrectlyAnswered()) {
                ++totalCorrectAnswers;
            }
        }

        return totalCorrectAnswers;
    }
}
