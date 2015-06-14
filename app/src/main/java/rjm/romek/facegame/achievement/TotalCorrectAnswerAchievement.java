package rjm.romek.facegame.achievement;

import java.util.Set;

import rjm.romek.facegame.achievement.condition.IsGreaterOrEqualCondition;
import rjm.romek.facegame.achievement.updater.SumUpdate;
import rjm.romek.facegame.model.Question;

public class TotalCorrectAnswerAchievement extends QuestionCounterAchievementUpdater {

    public TotalCorrectAnswerAchievement(long value) {
        super(new IsGreaterOrEqualCondition(value), new SumUpdate());
    }

    @Override
    protected Long transform(Set<Question> change) {
        long totalCorrectAnswers = 0;

        for (Question q : change) {
            if (q.isCorrectlyAnswered()) {
                ++totalCorrectAnswers;
            }
        }

        return totalCorrectAnswers;
    }
}
