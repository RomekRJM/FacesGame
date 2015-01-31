package rjm.romek.facegame.service;

import junit.framework.TestCase;
import rjm.romek.facegame.data.Parameters;
import rjm.romek.facegame.model.Difficulty;
import rjm.romek.facegame.model.Question;
import rjm.romek.facegame.testutils.TestUtils;
import rjm.romek.source.model.Country;
import rjm.romek.source.randomizer.Randomizer;

import java.util.Set;

public class QuestionServiceImplTest extends TestCase {

    private Randomizer randomizer = null;

    @Override
    public void setUp() throws Exception {
        Set<Country> countries = new TestUtils().loadCountries();
        randomizer = new Randomizer(countries);
    }

    public void testReturnsCorrectNumberOfQuestions() throws Exception {
        QuestionService questionService = new QuestionServiceImpl(randomizer);
        Set<Question> questions = questionService.generateQuestions(Difficulty.EASY);
        assertEquals(new Parameters().getQuestionsInSet(), questions.size());
    }
}