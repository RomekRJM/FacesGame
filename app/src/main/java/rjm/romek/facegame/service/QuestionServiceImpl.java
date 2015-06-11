package rjm.romek.facegame.service;

import android.content.Context;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import rjm.romek.facegame.common.Parameters;
import rjm.romek.facegame.data.QuestionContract;
import rjm.romek.facegame.model.CountryAlgorithm;
import rjm.romek.facegame.model.Difficulty;
import rjm.romek.facegame.model.Question;
import rjm.romek.source.model.Country;
import rjm.romek.source.model.Person;
import rjm.romek.source.randomizer.CountryRandomizer;

public class QuestionServiceImpl implements QuestionService {

    private final CountryRandomizer randomizer;
    private final Parameters parameters;
    private final PersonRandomizerService personRandomizer;
    private final QuestionContract questionContract;

    public QuestionServiceImpl(Context context, Set<Country> countries) throws IOException {
        this.randomizer = new CountryRandomizer(countries);
        this.parameters = new Parameters();
        personRandomizer = new PersonRandomizerServiceImpl(context.getAssets());
        questionContract = new QuestionContract(context);
    }

    @Override
    public Question generateQuestion(Set<Question> previous) {
        Question question;

        do {
            Difficulty difficulty = getDifficultyForQuestion(previous);
            Country country = randomizer.randomCountry();
            List<Country> countries = generateCountries(difficulty, country);
            question = new Question(getUUID(previous));
            question.setCountries(countries);
            question.setCorrectAnswer(country);
            question.setPerson(personRandomizer.readRandomInhabitant(country));
            question.setDifficulty(difficulty);
        } while (personAlreadyInSet(question.getPerson(), previous));

        return question;
    }

    @Override
    public int countCorrectAnswered(Set<Question> questions) {
        int correct = 0;

        for (Question q : questions) {
            if (q.isCorrectlyAnswered()) {
                ++correct;
            }
        }
        return correct;
    }

    @Override
    public void saveQuestions(Set<Question> questions) {
        questionContract.saveQuestions(questions);
    }

    @Override
    public long countUniqueRightGuessesForCountry(String country) {
        return questionContract.countUniqueRightGuessesForCountry(country);
    }

    @Override
    public long countConsecutiveDaysPlaying(int target) {
        return questionContract.countConsecutiveDaysPlaying(target);
    }

    @Override
    public long countRightGuessesThatWerePreviouslyWrong() {
        return questionContract.countRightGuessesThatWerePreviouslyWrong();
    }

    @Override
    public long countCountriesCorrectlyGuessed() {
        return questionContract.countCountriesCorrectlyGuessed();
    }

    private List<Country> generateCountries(Difficulty difficulty, Country validCountry) {
        List<Country> countries = new ArrayList<>(difficulty.getAvailableAnswers());

        if (difficulty.getCountryAlgorithm() == CountryAlgorithm.RANDOM_COUNTRY_IN_RADIUS) {
            countries.addAll(randomizer.randomNeighbours(validCountry,
                    difficulty.getRadius(), difficulty.getAvailableAnswers() - 1));
        } else if (difficulty.getCountryAlgorithm() == CountryAlgorithm.RANDOM_COUNTRY_FROM_DIFFERENT_CONTINENT) {
            countries.addAll(randomizer.randomCountriesFromDifferentContinents(validCountry,
                    difficulty.getAvailableAnswers() - 1));
        }

        countries.add(validCountry);
        Collections.shuffle(countries);

        return countries;
    }

    private Difficulty getDifficultyForQuestion(Set<Question> prevoius) {

        int spree = 0;

        for (Question question : prevoius) {
            if (question.isCorrectlyAnswered()) {
                ++spree;
            } else {
                spree = spree > 0 ? --spree : 0;
            }
        }

        switch (spree / parameters.getChangeDifficultyEvery()) {
            case 1:
                return Difficulty.EASY;
            case 2:
                return Difficulty.NORMAL;
            case 3:
                return Difficulty.HARD;
            case 4:
                return Difficulty.HARDCORE;
            default:
                return Difficulty.NOOB;
        }
    }

    private boolean personAlreadyInSet(Person person, Set<Question> previous) {
        for (Question question : previous) {
            if (question.getPerson().equals(person)) {
                return true;
            }
        }

        return false;
    }

    private String getUUID(Set<Question> previous) {

        if (previous.size() > 0) {
            return previous.iterator().next().getGameUUID();
        }

        return UUID.randomUUID().toString().substring(0, 8);
    }
}
