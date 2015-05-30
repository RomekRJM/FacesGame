package rjm.romek.facegame.achievement;

import rjm.romek.facegame.service.QuestionService;

public class RightGuessesThatWerePreviouslyWrongAchievementUpdater extends SqlPoweredUpdater {
    public RightGuessesThatWerePreviouslyWrongAchievementUpdater(long value) {
        super(value);
    }

    @Override
    protected Long transform(QuestionService change) {
        return change.countRightGuessesThatWerePreviouslyWrong();
    }
}
