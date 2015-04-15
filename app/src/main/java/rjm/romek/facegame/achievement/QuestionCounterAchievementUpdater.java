package rjm.romek.facegame.achievement;

import java.util.Set;

import rjm.romek.facegame.model.Achievement;
import rjm.romek.facegame.model.Question;

public abstract class QuestionCounterAchievementUpdater extends AchievementUpdater<Set<Question>, Long> {

    protected final long value;

    public QuestionCounterAchievementUpdater(long value) {
        this.value = value;
    }

    @Override
    protected boolean meetsCondition(Achievement achievement) {
        return Long.valueOf(achievement.getData()) >= value;
    }

    protected boolean changeAffectsAchievement(Object change) {
        return change != null && Set.class.isAssignableFrom(change.getClass());
    }
}
