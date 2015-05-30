package rjm.romek.facegame.achievement;

import rjm.romek.facegame.service.QuestionService;

public class ConsecutiveDaysPlayingAchievementUpdater extends SqlPoweredUpdater {
    private int numberOfDaysToUnlock;

    public ConsecutiveDaysPlayingAchievementUpdater(long value) {
        super(value);
        numberOfDaysToUnlock = (int)value;
    }

    @Override
    protected Long transform(QuestionService change) {
        return change.countConsecutiveDaysPlaying(numberOfDaysToUnlock);
    }
}
