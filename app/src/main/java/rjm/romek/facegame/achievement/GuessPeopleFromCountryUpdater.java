package rjm.romek.facegame.achievement;

import rjm.romek.facegame.service.QuestionService;

public class GuessPeopleFromCountryUpdater extends SqlPoweredUpdater {

    private String country;

    public GuessPeopleFromCountryUpdater(long value, String country) {
        super(value);
        this.country = country;
    }

    @Override
    protected Long transform(QuestionService change) {
        return change.countUniqueRightGuessesForCountry(country);
    }
}
