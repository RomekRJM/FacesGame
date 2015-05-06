package rjm.romek.facegame.achievement.condition;

import rjm.romek.facegame.model.Achievement;

public interface Condition {
    boolean meetsCondition(Achievement achievement);
}
