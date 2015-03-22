package rjm.romek.facegame.service;

import android.content.res.AssetManager;

import rjm.romek.facegame.data.Parameters;
import rjm.romek.facegame.model.Difficulty;
import rjm.romek.facegame.model.Question;
import rjm.romek.source.model.Country;
import rjm.romek.source.model.Person;
import rjm.romek.source.randomizer.CountryRandomizer;

import java.io.IOException;
import java.util.*;

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
    public Set<Question> generateQuestions(Difficulty difficulty) {
        Set<Question> questions = new LinkedHashSet<Question>();
        for(int i=0; i<parameters.getQuestionsInSet(); i++) {
            Country country = randomizer.randomCountry();
            List<Country> countries = generateCountries(difficulty, country);
            Question question = new Question();
            question.setCountries(countries);
            question.setCorrectAnswer(country);
            question.setPerson(personRandomizer.readRandomInhabitant(country));
            question.setDifficulty(difficulty);
            questions.add(question);
        }

        return questions;
    }

    @Override
    public int countCorrectAnswered(Set<Question> questions) {
        int correct = 0;

        for(Question q : questions) {
            if(q.isCorrectlyAnswered()) {
                ++correct;
            }
        }
        return correct;
    }

    private List<Country> generateCountries(Difficulty difficulty, Country validCountry) {
        List<Country> countries = new ArrayList<Country>(difficulty.getAvailableAnswers());
        countries.addAll(randomizer.randomNeighbours(validCountry,
                difficulty.getRadius(), difficulty.getAvailableAnswers()-1));
        countries.add(validCountry);
        Collections.shuffle(countries);

        return countries;
    }
}
