package rjm.romek.facegame.achievement;

import java.util.Set;

import rjm.romek.facegame.model.Question;

public class TotalGamesAchievementUpdater extends SumAchievementUpdater {

    public TotalGamesAchievementUpdater(long value) {
        super(value);
    }

    @Override
    protected Long transform(Set<Question> change) {
        return 1l;
    }
}
