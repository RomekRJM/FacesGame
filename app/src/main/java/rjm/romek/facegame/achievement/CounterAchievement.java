package rjm.romek.facegame.achievement;

import rjm.romek.facegame.model.Achievement;

public class CounterAchievement extends AbstractAchievement<Long> {
    @Override
    public void update(Long update, Achievement achievement) {
        long counter = Long.valueOf(achievement.getData());
        long newValue = counter + update;
        achievement.setData(String.valueOf(newValue));
    }
}
