package rjm.romek.facegame.achievement.condition;

import rjm.romek.facegame.model.Achievement;
import rjm.romek.facegame.model.Difficulty;

public class IsGreaterOrEqualDifficulty implements Condition {

    private Difficulty difficulty;

    public IsGreaterOrEqualDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    @Override
    public boolean meetsCondition(Achievement achievement) {
        return achievement.getData() != null &&
                Difficulty.valueOf(achievement.getData()).ordinal() >= difficulty.ordinal();
    }
}
