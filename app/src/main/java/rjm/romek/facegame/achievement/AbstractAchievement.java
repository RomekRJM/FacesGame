package rjm.romek.facegame.achievement;

import rjm.romek.facegame.model.Achievement;

public abstract class AbstractAchievement<U> implements Achievement {
    public abstract void update(U update);
}
