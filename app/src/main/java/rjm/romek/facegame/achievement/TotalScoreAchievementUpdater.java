package rjm.romek.facegame.achievement;

import rjm.romek.facegame.achievement.condition.IsGreaterOrEqualCondition;
import rjm.romek.facegame.achievement.updater.SumUpdate;

public class TotalScoreAchievementUpdater extends LongAchievementUpdater {

    protected TotalScoreAchievementUpdater(long value) {
        super(new IsGreaterOrEqualCondition(value), new SumUpdate());
    }
}
