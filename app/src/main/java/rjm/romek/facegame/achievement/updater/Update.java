package rjm.romek.facegame.achievement.updater;

import rjm.romek.facegame.model.Achievement;

public interface Update<U> {
    void update(U update, Achievement achievement);
}
