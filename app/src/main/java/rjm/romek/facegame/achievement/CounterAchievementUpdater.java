package rjm.romek.facegame.achievement;

import rjm.romek.facegame.model.Achievement;

public class CounterAchievementUpdater extends AchievementUpdater<Long> {

    protected final long value;

    public CounterAchievementUpdater(long value) {
        this.value = value;
    }

    @Override
    public void update(Long update, Achievement achievement) {
        long counter = Long.valueOf(achievement.getData());
        long newValue = counter + update;
        achievement.setData(String.valueOf(newValue));
    }

    @Override
    public boolean meetsCondition(Achievement achievement) {
        return Long.valueOf(achievement.getData()) >= value;
    }
}
