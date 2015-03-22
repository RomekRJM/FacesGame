package rjm.romek.facegame.model;

import android.test.AndroidTestCase;

import java.util.ArrayList;
import java.util.List;

import rjm.romek.source.model.Country;

public class QuestionTest extends AndroidTestCase {

    private List<Country> list;
    private Country correct;
    private Country wrong;
    private Question question;

    @Override
    public void setUp() {
        list = new ArrayList<Country>();
        correct = new Country();
        correct.setName("Poland");
        wrong = new Country();
        wrong.setName("Russia");
        list.add(correct);
        list.add(wrong);

        question = new Question();
        question.setCountries(list);
        question.setCorrectAnswer(correct);
    }

    public void testCorrectlyAnswered() {
        question.answer(correct, 100l);
        assertTrue(question.isCorrectlyAnswered());
        assertNotNull(question.getDate());
        assertEquals(100l, question.getAnswerTime());
    }

    public void testWronglyAnswered() {
        question.answer(wrong, 100l);
        assertFalse(question.isCorrectlyAnswered());
        assertNotNull(question.getDate());
        assertEquals(100l, question.getAnswerTime());
    }
}
