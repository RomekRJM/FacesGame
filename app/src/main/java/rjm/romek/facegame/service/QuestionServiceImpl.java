package rjm.romek.facegame.service;

import android.content.res.AssetManager;

import rjm.romek.facegame.data.Parameters;
import rjm.romek.facegame.model.Difficulty;
import rjm.romek.facegame.model.Question;
import rjm.romek.source.model.Country;
import rjm.romek.source.randomizer.CountryRandomizer;

import java.io.IOException;
import java.util.*;

public class QuestionServiceImpl implements QuestionService {

    private final CountryRandomizer randomizer;
    private final Parameters parameters;
    private final PhotoService photoService;

    public QuestionServiceImpl(AssetManager assetManager, Set<Country> countries) throws IOException {
        this.randomizer = new CountryRandomizer(countries);
        this.parameters = new Parameters();
        this.photoService = new PhotoServiceImpl(assetManager);
    }

    @Override
    public Set<Question> generateQuestions(Difficulty difficulty) {
        Country country = randomizer.randomCountry();
        Set<Question> questions = new LinkedHashSet<Question>();

        for(int i=0; i<parameters.getQuestionsInSet(); i++) {
            Set<Country> countries = generateCountries(difficulty, country);
            Question question = new Question();
            question.setCountries(countries);
            question.setCorrectAnswer(country);
            question.setPersonBitmap(photoService.readRandomInhabitantBitmap(country));
            questions.add(question);
        }

        return questions;
    }

    private Set<Country> generateCountries(Difficulty difficulty, Country validCountry) {
        List<Country> countries = new ArrayList<Country>(difficulty.getAvailableAnswers());

        for(int i=0; i<difficulty.getAvailableAnswers()-1; ++i) {
            countries.add(randomizer.randomNeighbour(validCountry, difficulty.getRadius()));
        }
        countries.add(validCountry);
        Collections.shuffle(countries);

        Set<Country> countriesSet = new LinkedHashSet();
        countriesSet.addAll(countries);

        return countriesSet;
    }

}
