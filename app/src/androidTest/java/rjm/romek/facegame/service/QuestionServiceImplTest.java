package rjm.romek.facegame.service;

import android.test.AndroidTestCase;

import java.util.Set;

import rjm.romek.facegame.data.Parameters;
import rjm.romek.facegame.model.Difficulty;
import rjm.romek.facegame.model.Question;
import rjm.romek.facegame.utils.TestUtils;
import rjm.romek.source.model.Country;
import rjm.romek.source.randomizer.CountryRandomizer;

public class QuestionServiceImplTest extends AndroidTestCase {

    private QuestionService questionService;
    private Parameters parameters;

    @Override
    public void setUp() throws Exception {
        Set<Country> countries = TestUtils.loadCountries(getContext().getAssets());
        PhotoService photoService = new PhotoServiceImpl(getContext().getAssets());
        CountryRandomizer randomizer = new CountryRandomizer(countries);
        questionService = new QuestionServiceImpl(randomizer, photoService);
        parameters = new Parameters();
    }

    public void testReturnsCorrectNumberOfQuestions() throws Exception {
        Set<Question> questions = questionService.generateQuestions(Difficulty.EASY);
        assertEquals(parameters.getQuestionsInSet(), questions.size());
    }

    public void testReturnsValidQuestions() throws Exception {
        Set<Question> questions = questionService.generateQuestions(Difficulty.NORMAL);

        for(Question q: questions) {
            assertNotNull(q.getGameUUID());
            assertNotNull(q.getPersonBitmap());
            assertNotNull(q.getCorrectAnswer());
            assertNull(q.getDate());
            assertNull(q.getGivenAnswer());
            assertTrue(q.getCountries().contains(q.getCorrectAnswer()));
        }
    }

    public void testReturnsValidQuestionsWithProperCountries() throws Exception {
        Set<Question> questions = questionService.generateQuestions(Difficulty.HARD);

        for(Question q: questions) {
            assertEquals((int)Difficulty.HARD.getAvailableAnswers(), q.getCountries().size());
            assertTrue(q.getCountries().contains(q.getCorrectAnswer()));
        }
    }
}