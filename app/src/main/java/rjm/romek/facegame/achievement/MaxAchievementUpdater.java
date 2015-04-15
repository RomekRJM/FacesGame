package rjm.romek.facegame.achievement;

import rjm.romek.facegame.model.Achievement;

public abstract class MaxAchievementUpdater extends QuestionCounterAchievementUpdater {

    public MaxAchievementUpdater(long value) {
        super(value);
    }

    @Override
    protected void update(Long update, Achievement achievement) {
        long counter = Long.valueOf(achievement.getData());
        long newValue = Math.max(counter, update);
        achievement.setData(String.valueOf(newValue));
    }

}