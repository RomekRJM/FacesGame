package rjm.romek.facegame.achievement;

import rjm.romek.facegame.achievement.condition.IsGreaterCondition;
import rjm.romek.facegame.achievement.updater.MaxUpdate;

public class ScoreAchievementUpdater extends LongAchievementUpdater {

    protected ScoreAchievementUpdater(long value) {
        super(new IsGreaterCondition(value), new MaxUpdate());
    }
}
