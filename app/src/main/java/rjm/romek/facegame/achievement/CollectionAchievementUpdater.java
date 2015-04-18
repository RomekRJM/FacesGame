package rjm.romek.facegame.achievement;

import rjm.romek.facegame.achievement.condition.Condition;
import rjm.romek.facegame.achievement.updater.Update;

public abstract class CollectionAchievementUpdater extends AchievementUpdater {
    public CollectionAchievementUpdater(Condition condition, Update update) {
        super(condition, update);
    }
}
