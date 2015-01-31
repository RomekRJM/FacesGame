package rjm.romek.facegame.service;

import rjm.romek.facegame.data.Parameters;
import rjm.romek.facegame.model.Difficulty;
import rjm.romek.facegame.model.Question;
import rjm.romek.source.model.Country;
import rjm.romek.source.randomizer.Randomizer;

import java.util.*;

public class QuestionServiceImpl implements QuestionService {

    private final Randomizer randomizer;
    private final Parameters parameters;

    public QuestionServiceImpl(Randomizer randomizer) {
        this.randomizer = randomizer;
        this.parameters = new Parameters();
    }

    @Override
    public Set<Question> generateQuestions(Difficulty difficulty) {
        // TODO Should be based on randomly choosen Person
        Country country = randomizer.randomCountry();
        Set<Question> questions = new HashSet<Question>();

        for(int i=0; i<parameters.getQuestionsInSet(); i++) {
            List<Country> countries = generateCountries(difficulty, country);
            Question question = new Question();
            question.setCountries(countries);
            question.setCorrectAnswer(country);
            questions.add(question);
        }

        return questions;
    }

    private List<Country> generateCountries(Difficulty difficulty, Country validCountry) {
        List<Country> countries = new ArrayList<Country>(difficulty.getAvailableAnswers());

        for(int i=0; i<difficulty.getAvailableAnswers()-1; ++i) {
            countries.add(randomizer.randomNeighbour(validCountry, difficulty.getRadius()));
        }
        countries.add(validCountry);
        Collections.shuffle(countries);

        return countries;
    }

}
