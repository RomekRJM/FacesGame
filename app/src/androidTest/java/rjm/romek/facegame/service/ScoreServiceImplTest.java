package rjm.romek.facegame.service;

import junit.framework.TestCase;

import rjm.romek.facegame.model.Difficulty;
import rjm.romek.facegame.model.Question;
import rjm.romek.source.model.Country;

public class ScoreServiceImplTest extends TestCase {
    ScoreService scoreService;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        scoreService = new ScoreServiceImpl();
    }

    public void testSingleAnswer() {
        scoreService.addQuestion(createQuestion(true, 0l, Difficulty.EASY));
        assertParamsCorrect(100l, 1, 100l);
    }

    public Question createQuestion(boolean correct, long answerGivenAfterTime, Difficulty difficulty) {
        Country country = new Country();
        Country countryWrong = new Country();
        Question question = new Question();
        question.setDifficulty(difficulty);
        question.setCorrectAnswer(country);
        if(correct) {
            question.answer(country, answerGivenAfterTime);
        } else {
            question.answer(countryWrong, answerGivenAfterTime);
        }
        return question;
    }

    public void assertParamsCorrect(long totalScore, int multiplier, long multiplicand) {
        assertEquals(totalScore, scoreService.getTotalScore());
        assertEquals(multiplier, scoreService.getMultiplier());
        assertEquals(multiplicand, scoreService.getMultiplicand());
    }
}