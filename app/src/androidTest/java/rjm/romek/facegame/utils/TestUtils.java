package rjm.romek.facegame.utils;

import android.content.res.AssetManager;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

import rjm.romek.facegame.model.Difficulty;
import rjm.romek.facegame.model.Question;
import rjm.romek.source.gen.CountriesDeserializer;
import rjm.romek.source.model.Country;

public class TestUtils {
    public static Set<Country> loadCountries(AssetManager manager) throws IOException {
        CountriesDeserializer countriesDeserializer = new CountriesDeserializer();
        return countriesDeserializer.deserialize(manager.open("list.json"));
    }

    public static Question createQuestion(boolean correct, long answerGivenAfterTime, Difficulty difficulty) {
        Country country = new Country();
        country.setName("Poland");
        Country countryWrong = new Country();
        countryWrong.setName("CCCP");
        Question question = new Question("abcd");
        question.setDifficulty(difficulty);
        question.setCorrectAnswer(country);
        if (correct) {
            question.answer(country, answerGivenAfterTime);
        } else {
            question.answer(countryWrong, answerGivenAfterTime);
        }
        return question;
    }

    public static Set<Question> createQuestionSet(boolean [] correct) {
        Set<Question> questions = new LinkedHashSet<>();

        for(boolean c : correct) {
            questions.add(createQuestion(c, 0l, Difficulty.NORMAL));
        }

        return questions;
    }

    public static Set<Question> createCorrectQuestions(int length) {
        boolean [] correct = new boolean[length];
        Arrays.fill(correct, true);

        return createQuestionSet(correct);
    }

}
