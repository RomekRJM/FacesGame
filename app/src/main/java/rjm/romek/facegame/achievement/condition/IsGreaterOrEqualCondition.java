package rjm.romek.facegame.achievement.condition;

import rjm.romek.facegame.model.Achievement;

public class IsGreaterOrEqualCondition implements Condition {

    private long value;

    public IsGreaterOrEqualCondition(long value) {
        this.value = value;
    }

    @Override
    public boolean meetsCondition(Achievement achievement) {
        return achievement.getData() != null && Long.valueOf(achievement.getData()) >= value;
    }
}
