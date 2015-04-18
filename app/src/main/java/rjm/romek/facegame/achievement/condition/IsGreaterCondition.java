package rjm.romek.facegame.achievement.condition;

import rjm.romek.facegame.model.Achievement;

public class IsGreaterCondition implements Condition {

    private long value;

    public IsGreaterCondition(long value) {
        this.value = value;
    }

    @Override
    public boolean meetsCondition(Achievement achievement) {
        return achievement.getData() != null && Long.valueOf(achievement.getData()) >= value;
    }
}
