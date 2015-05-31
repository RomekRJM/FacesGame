package rjm.romek.facegame.utils;

import android.content.Context;
import android.content.res.AssetManager;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import rjm.romek.facegame.data.QuestionContract;
import rjm.romek.facegame.model.Difficulty;
import rjm.romek.facegame.model.Question;
import rjm.romek.source.gen.CountriesDeserializer;
import rjm.romek.source.model.Country;
import rjm.romek.source.model.Person;

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

    public static Question createAndSaveQuestion(Context context,
                                                 String correctAnswer, String givenAnswer,
                                                 String person, Date date) {
        QuestionContract questionContract = new QuestionContract(context);
        Country country1 = new Country();
        country1.setName(correctAnswer);
        Country country2 = new Country();
        country2.setName(givenAnswer);
        Question question = new Question("abcd");
        person = StringUtils.defaultString(person, RandomStringUtils.randomAlphabetic(8));
        question.setPerson(new Person(person));
        question.setCorrectAnswer(country1);
        question.answer(country2, 0);
        List<Country> countries = new ArrayList<>();
        countries.add(country1);
        countries.add(country2);
        question.setCountries(countries);
        question.setDifficulty(Difficulty.NORMAL);
        question.setDate(date);
        Set<Question> questions = new LinkedHashSet<>();
        questions.add(question);

        questionContract.saveQuestions(questions);

        return question;
    }

}
