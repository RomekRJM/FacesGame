package rjm.romek.facegame.achievement;

import rjm.romek.facegame.achievement.activator.OnLongActivate;
import rjm.romek.facegame.achievement.condition.Condition;
import rjm.romek.facegame.achievement.updater.Update;

public abstract class LongAchievementUpdater extends AchievementUpdater<Long, Long> {

    protected LongAchievementUpdater(Condition condition, Update<Long> update) {
        super(condition, update, new OnLongActivate());
    }

    @Override
    protected Long transform(Long change) {
        return change;
    }
}
