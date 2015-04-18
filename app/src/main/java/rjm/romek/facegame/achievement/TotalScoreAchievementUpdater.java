package rjm.romek.facegame.achievement;

import rjm.romek.facegame.achievement.condition.IsGreaterCondition;
import rjm.romek.facegame.achievement.updater.SumUpdate;

public class TotalScoreAchievementUpdater extends LongAchievementUpdater {

    protected TotalScoreAchievementUpdater(long value) {
        super(new IsGreaterCondition(value), new SumUpdate());
    }
}
