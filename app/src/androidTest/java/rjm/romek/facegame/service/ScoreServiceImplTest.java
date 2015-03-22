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

    public void testSingleCorrectAnswerOnEasy() {
        scoreService.addQuestion(createQuestion(true, 0l, Difficulty.EASY));
        assertParamsCorrect(1, 100l, 100l, 0l);
    }

    public void testSingleWrongAnswerOnEasy() {
        scoreService.addQuestion(createQuestion(false, 0l, Difficulty.EASY));
        assertParamsCorrect(1, 0l, 0l, 0l);
    }

    public void testTwoCorrectAnswersOnEasy() {
        scoreService.addQuestion(createQuestion(true, 0l, Difficulty.EASY));
        scoreService.addQuestion(createQuestion(true, 0l, Difficulty.EASY));
        assertParamsCorrect(2, 200l, 400l, 0l);
    }

    public void testFourCorrectAnswersAndOneWrongOnEasy() {
        for(int i=0; i<4; ++i) {
            scoreService.addQuestion(createQuestion(true, 0l, Difficulty.EASY));
        }
        scoreService.addQuestion(createQuestion(false, 0l, Difficulty.EASY));

        assertParamsCorrect(1, 0l, 1600l, 1600l);
    }

    public Question createQuestion(boolean correct, long answerGivenAfterTime, Difficulty difficulty) {
        Country country = new Country();
        country.setName("Poland");
        Country countryWrong = new Country();
        countryWrong.setName("CCCP");
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

    public void assertParamsCorrect(int multiplier, long multiplicand,
                                    long totalScore, long currentScore) {
        assertEquals(multiplier, scoreService.getMultiplier());
        assertEquals(multiplicand, scoreService.getMultiplicand());
        assertEquals(totalScore, scoreService.getTotalScore());
        assertEquals(currentScore, scoreService.getCurrentScore());
    }
}