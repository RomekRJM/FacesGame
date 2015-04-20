package rjm.romek.facegame.achievement;

import java.util.Set;

import rjm.romek.facegame.achievement.condition.IsGreaterOrEqualCondition;
import rjm.romek.facegame.achievement.updater.SumUpdate;
import rjm.romek.facegame.model.Question;

public class TotalGamesAchievementUpdater extends QuestionCounterAchievementUpdater {

    public TotalGamesAchievementUpdater(long value) {
        super(new IsGreaterOrEqualCondition(value), new SumUpdate());
    }

    @Override
    protected Long transform(Set<Question> change) {
        return 1l;
    }
}
