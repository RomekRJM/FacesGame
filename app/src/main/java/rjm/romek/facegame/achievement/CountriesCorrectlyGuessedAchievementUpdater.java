package rjm.romek.facegame.achievement;

import rjm.romek.facegame.service.QuestionService;

public class CountriesCorrectlyGuessedAchievementUpdater extends SqlPoweredUpdater {
    public CountriesCorrectlyGuessedAchievementUpdater(long value) {
        super(value);
    }

    @Override
    protected Long transform(QuestionService change) {
        return change.countCountriesCorrectlyGuessed();
    }
}
