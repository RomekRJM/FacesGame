package rjm.romek.facegame.achievement.updater;

import rjm.romek.facegame.model.Achievement;

public class MaxUpdate implements Update<Long> {

    @Override
    public void update(Long update, Achievement achievement) {
        long counter = Long.valueOf(achievement.getData());
        long newValue = Math.max(counter, update);
        achievement.setData(String.valueOf(newValue));
    }
}
