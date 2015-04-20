package rjm.romek.facegame.achievement;

import rjm.romek.facegame.achievement.condition.IsGreaterOrEqualCondition;
import rjm.romek.facegame.achievement.updater.MaxUpdate;

public class ScoreAchievementUpdater extends LongAchievementUpdater {

    protected ScoreAchievementUpdater(long value) {
        super(new IsGreaterOrEqualCondition(value), new MaxUpdate());
    }
}
