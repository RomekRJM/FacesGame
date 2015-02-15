package rjm.romek.facegame.model;

import android.test.AndroidTestCase;

import java.util.HashSet;
import java.util.Set;

import rjm.romek.source.model.Country;

public class QuestionTest extends AndroidTestCase {

    private Set<Country> set;
    private Country correct;
    private Country wrong;
    private Question question;

    @Override
    public void setUp() {
        set = new HashSet<Country>();
        correct = new Country();
        correct.setName("Poland");
        wrong = new Country();
        wrong.setName("Russia");
        set.add(correct);
        set.add(wrong);

        question = new Question();
        question.setCountries(set);
        question.setCorrectAnswer(correct);
    }

    public void testCorrectlyAnswered() {
        question.answer(correct);
        assertTrue(question.isCorrectlyAnswered());
        assertNotNull(question.getDate());
    }

    public void testWronglyAnswered() {
        question.answer(wrong);
        assertFalse(question.isCorrectlyAnswered());
        assertNotNull(question.getDate());
    }
}
