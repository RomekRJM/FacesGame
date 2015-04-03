package rjm.romek.facegame.service;

import android.test.AndroidTestCase;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import rjm.romek.facegame.common.Parameters;
import rjm.romek.facegame.model.Question;
import rjm.romek.facegame.utils.TestUtils;
import rjm.romek.source.model.Country;

public class QuestionServiceImplTest extends AndroidTestCase {

    private QuestionService questionService;
    private Parameters parameters;

    @Override
    public void setUp() throws Exception {
        Set<Country> countries = TestUtils.loadCountries(getContext().getAssets());
        questionService = new QuestionServiceImpl(getContext().getAssets(), countries);
        parameters = new Parameters();
    }

    public void testReturnsCorrectNumberOfQuestions() throws Exception {
        Set<Question> questions = questionService.generateQuestions();
        assertEquals(parameters.getQuestionsInSet(), questions.size());
    }

    public void testReturnsValidQuestions() throws Exception {
        Set<Question> questions = questionService.generateQuestions();

        for (Question q : questions) {
            assertNotNull(q.getGameUUID());
            assertNotNull(q.getPerson());
            assertNotNull(q.getCorrectAnswer());
            assertNull(q.getDate());
            assertNull(q.getGivenAnswer());
            assertTrue(q.getCountries().contains(q.getCorrectAnswer()));
        }
    }

    public void testReturnsValidQuestionsWithProperCountries() throws Exception {
        for (int i = 0; i < 100; ++i) {
            Set<Question> questions = questionService.generateQuestions();

            for (Question q : questions) {
                assertEquals(q.getDifficulty().getAvailableAnswers().intValue(), q.getCountries().size());
                assertTrue(q.getCountries().contains(q.getCorrectAnswer()));
                assertCountriesHaveValidFields(q.getCountries());
            }
        }
    }

    public void testReturnsQuestionsWithGrowingDifficulty() throws Exception {
        Set<Question> questions = questionService.generateQuestions();
        Iterator<Question> iterator = questions.iterator();
        Question lastQuestion = iterator.next();

        for (int i = 1; i < questions.size(); ++i) {
            Question question = iterator.next();
            if (i % parameters.getChangeDifficultyEvery() == 0) {
                assertEquals(question.getDifficulty().ordinal(), lastQuestion.getDifficulty().ordinal() + 1);
            }
            assertTrue(question.getDifficulty().ordinal() >= lastQuestion.getDifficulty().ordinal());
            lastQuestion = question;
        }
    }

    private void assertCountriesHaveValidFields(List<Country> countries) {
        for (Country country : countries) {
            assertNotNull(country.getName());
            assertNotNull(country.getFlag());
            assertNotNull(country.getBorders());
        }
    }
}