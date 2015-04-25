package rjm.romek.facegame.service;

import android.content.res.AssetManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import rjm.romek.facegame.common.Parameters;
import rjm.romek.facegame.model.Difficulty;
import rjm.romek.facegame.model.Question;
import rjm.romek.source.model.Country;
import rjm.romek.source.randomizer.CountryRandomizer;

public class QuestionServiceImpl implements QuestionService {

    private final CountryRandomizer randomizer;
    private final Parameters parameters;
    private final PersonRandomizerService personRandomizer;

    public QuestionServiceImpl(AssetManager assetManager, Set<Country> countries) throws IOException {
        this.randomizer = new CountryRandomizer(countries);
        this.parameters = new Parameters();
        personRandomizer = new PersonRandomizerServiceImpl(assetManager);
    }

    @Override
    public Question generateQuestion(Set<Question> previous) {
        Difficulty difficulty = getDifficultyForQuestion(previous);
        Country country = randomizer.randomCountry();
        List<Country> countries = generateCountries(difficulty, country);
        Question question = new Question();
        question.setCountries(countries);
        question.setCorrectAnswer(country);
        question.setPerson(personRandomizer.readRandomInhabitant(country));
        question.setDifficulty(difficulty);

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

    private List<Country> generateCountries(Difficulty difficulty, Country validCountry) {
        List<Country> countries = new ArrayList<>(difficulty.getAvailableAnswers());
        countries.addAll(randomizer.randomNeighbours(validCountry,
                difficulty.getRadius(), difficulty.getAvailableAnswers() - 1));
        countries.add(validCountry);
        Collections.shuffle(countries);

        return countries;
    }

    private Difficulty getDifficultyForQuestion(Set<Question> prevoius) {

        switch (prevoius.size() / parameters.getChangeDifficultyEvery()) {
            case 0:
                return Difficulty.EASY;
            case 1:
                return Difficulty.NORMAL;
            case 2:
                return Difficulty.HARD;
            default:
                return Difficulty.HARDCORE;
        }
    }
}
