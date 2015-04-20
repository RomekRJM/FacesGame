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

    public void test1CorrectAnswerOnEasy() {
        scoreService.addQuestion(createQuestion(true, 0l, Difficulty.EASY));
        assertParamsCorrect(1, 10l, 10l, 0l);
    }

    public void test1WrongAnswerOnEasy() {
        scoreService.addQuestion(createQuestion(false, 0l, Difficulty.EASY));
        assertParamsCorrect(0, 0l, 0l, 0l);
    }

    public void test1CorrectLateAnswerOnHardcore() {
        scoreService.addQuestion(createQuestion(true, Difficulty.HARDCORE.getTime() - 1l, Difficulty.HARDCORE));
        assertParamsCorrect(1, 3l, 3l, 0l);
    }

    public void test2CorrectAnswersOnEasySecondTookLonger() {
        scoreService.addQuestion(createQuestion(true, 0l, Difficulty.EASY));
        scoreService.addQuestion(createQuestion(true, Difficulty.EASY.getTime() / 2, Difficulty.EASY));
        assertParamsCorrect(2, 15l, 30l, 0l);
    }

    public void test4CorrectAnswersAnd1WrongOnEasy() {
        for (int i = 0; i < 4; ++i) {
            scoreService.addQuestion(createQuestion(true, 0l, Difficulty.EASY));
        }
        scoreService.addQuestion(createQuestion(false, 0l, Difficulty.EASY));

        assertParamsCorrect(0, 0l, 160l, 160l);
    }

    public void test2CorrectAnswersAnd1WrongAnd1CorrectAnd1WrongOnEasy() {
        for (int i = 0; i < 2; ++i) {
            scoreService.addQuestion(createQuestion(true, 0l, Difficulty.EASY));
        }
        scoreService.addQuestion(createQuestion(false, 0l, Difficulty.EASY));
        scoreService.addQuestion(createQuestion(true, 0l, Difficulty.EASY));
        scoreService.addQuestion(createQuestion(false, 0l, Difficulty.EASY));

        assertParamsCorrect(0, 0l, 50l, 50l);
    }

    public void test5CorrectAnswersOnEasy2CorrectAnd1WrongOnNormal() {
        for (int i = 0; i < 5; ++i) {
            scoreService.addQuestion(createQuestion(true, 0l, Difficulty.EASY));
        }
        for (int i = 0; i < 2; ++i) {
            scoreService.addQuestion(createQuestion(true, 0l, Difficulty.NORMAL));
        }
        scoreService.addQuestion(createQuestion(false, 0l, Difficulty.NORMAL));

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