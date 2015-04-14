package rjm.romek.facegame.achievement;

import rjm.romek.facegame.model.Achievement;

public class MaxAchievementUpdater extends CounterAchievementUpdater {

    public MaxAchievementUpdater(String name, long value) {
        super(name, value);
    }

    @Override
    public void update(Long update, Achievement achievement) {
        long counter = Long.valueOf(achievement.getData());
        long newValue = Math.max(counter, update);
        achievement.setData(String.valueOf(newValue));
    }

}