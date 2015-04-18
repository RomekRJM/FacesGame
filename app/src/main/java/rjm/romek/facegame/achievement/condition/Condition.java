package rjm.romek.facegame.achievement.condition;

import rjm.romek.facegame.model.Achievement;

public interface Condition {
    public boolean meetsCondition(Achievement achievement);
}
