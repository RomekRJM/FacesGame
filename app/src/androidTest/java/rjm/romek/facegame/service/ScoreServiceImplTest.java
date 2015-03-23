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

    public void test1CorrectAnswerOnEasy() {
        scoreService.addQuestion(createQuestion(true, 0l, Difficulty.EASY));
        assertParamsCorrect(1, 100l, 100l, 0l);
    }

    public void test1WrongAnswerOnEasy() {
        scoreService.addQuestion(createQuestion(false, 0l, Difficulty.EASY));
        assertParamsCorrect(1, 0l, 0l, 0l);
    }

    public void test2CorrectAnswersOnEasySecondTookLonger() {
        scoreService.addQuestion(createQuestion(true, 0l, Difficulty.EASY));
        scoreService.addQuestion(createQuestion(true, Difficulty.EASY.getTime()/2, Difficulty.EASY));
        assertParamsCorrect(2, 150l, 300l, 0l);
    }

    public void test4CorrectAnswersAnd1WrongOnEasy() {
        for(int i=0; i<4; ++i) {
            scoreService.addQuestion(createQuestion(true, 0l, Difficulty.EASY));
        }
        scoreService.addQuestion(createQuestion(false, 0l, Difficulty.EASY));

        assertParamsCorrect(1, 0l, 1600l, 1600l);
    }

    public void test5CorrectAnswersOnEasy2CorrectAnd1WrongOnNormal() {
        for(int i=0; i<5; ++i) {
            scoreService.addQuestion(createQuestion(true, 0l, Difficulty.EASY));
        }
        for(int i=0; i<2; ++i) {
            scoreService.addQuestion(createQuestion(true, 0l, Difficulty.NORMAL));
        }
        scoreService.addQuestion(createQuestion(false, 0l, Difficulty.NORMAL));

        assertParamsCorrect(1, 0l, 5600l, 5600l);
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