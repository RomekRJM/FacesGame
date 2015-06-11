package rjm.romek.facegame.service;

import junit.framework.TestCase;

import rjm.romek.facegame.model.Difficulty;

import static rjm.romek.facegame.utils.TestUtils.*;

public class ScoreServiceImplTest extends TestCase {
    ScoreService scoreService;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        scoreService = new ScoreServiceImpl();
    }

    public void test1CorrectAnswerOnNoob() {
        scoreService.addQuestion(createQuestion(true, 0l, Difficulty.NOOB));
        assertParamsCorrect(1, 10l, 10l, 0l);
    }

    public void test1WrongAnswerOnNoob() {
        scoreService.addQuestion(createQuestion(false, 0l, Difficulty.NOOB));
        assertParamsCorrect(0, 0l, 0l, 0l);
    }

    public void test1CorrectLateAnswerOnHardcore() {
        scoreService.addQuestion(createQuestion(true, Difficulty.HARDCORE.getTime() - 1l, Difficulty.HARDCORE));
        assertParamsCorrect(1, 3l, 3l, 0l);
    }

    public void test2CorrectAnswersOnNoobSecondTookLonger() {
        scoreService.addQuestion(createQuestion(true, 0l, Difficulty.NOOB));
        scoreService.addQuestion(createQuestion(true, Difficulty.NOOB.getTime() / 2, Difficulty.NOOB));
        assertParamsCorrect(2, 15l, 30l, 0l);
    }

    public void test4CorrectAnswersAnd1WrongOnNoob() {
        for (int i = 0; i < 4; ++i) {
            scoreService.addQuestion(createQuestion(true, 0l, Difficulty.NOOB));
        }
        scoreService.addQuestion(createQuestion(false, 0l, Difficulty.NOOB));

        assertParamsCorrect(0, 0l, 160l, 160l);
    }

    public void test2CorrectAnswersAnd1WrongAnd1CorrectAnd1WrongOnNoob() {
        for (int i = 0; i < 2; ++i) {
            scoreService.addQuestion(createQuestion(true, 0l, Difficulty.NOOB));
        }
        scoreService.addQuestion(createQuestion(false, 0l, Difficulty.NOOB));
        scoreService.addQuestion(createQuestion(true, 0l, Difficulty.NOOB));
        scoreService.addQuestion(createQuestion(false, 0l, Difficulty.NOOB));

        assertParamsCorrect(0, 0l, 50l, 50l);
    }

    public void test5CorrectAnswersOnNoob2CorrectAnd1WrongOnEasy() {
        for (int i = 0; i < 5; ++i) {
            scoreService.addQuestion(createQuestion(true, 0l, Difficulty.NOOB));
        }
        for (int i = 0; i < 2; ++i) {
            scoreService.addQuestion(createQuestion(true, 0l, Difficulty.EASY));
        }
        scoreService.addQuestion(createQuestion(false, 0l, Difficulty.EASY));

        assertParamsCorrect(0, 0l, 560l, 560l);
    }

    public void assertParamsCorrect(int multiplier, long multiplicand,
                                    long totalScore, long currentScore) {
        assertEquals(multiplier, scoreService.getMultiplier());
        assertEquals(multiplicand, scoreService.getMultiplicand());
        assertEquals(totalScore, scoreService.getTotalScore());
        assertEquals(currentScore, scoreService.getCurrentScore());
    }
}