package rjm.romek.facegame.achievement;

import java.util.Set;

import rjm.romek.facegame.achievement.condition.Condition;
import rjm.romek.facegame.achievement.updater.Update;
import rjm.romek.facegame.model.Question;

public abstract class QuestionCounterAchievementUpdater extends AchievementUpdater<Set<Question>, Long> {

    public QuestionCounterAchievementUpdater(Condition condition, Update<Long> update) {
        super(condition, update);
    }

    protected boolean changeAffectsAchievement(Object change) {
        return change != null && Set.class.isAssignableFrom(change.getClass());
    }
}
