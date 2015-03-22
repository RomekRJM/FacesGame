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
        scoreService.addQuestion(createQuestion(true, 5000l));
        scoreService.getTotalScore();
    }

    public Question createQuestion(boolean correct, long answerGivenAfterTime) {
        Country country = new Country();
        Country countryWrong = new Country();
        Question question = new Question();
        question.setDifficulty(Difficulty.HARDCORE);
        question.setCorrectAnswer(country);
        if(correct) {
            question.answer(country, answerGivenAfterTime);
        } else {
            question.answer(countryWrong, answerGivenAfterTime);
        }
        return question;
    }
}