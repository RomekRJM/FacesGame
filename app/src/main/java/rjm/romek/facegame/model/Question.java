package rjm.romek.facegame.model;

import rjm.romek.source.model.Country;
import rjm.romek.source.model.Person;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public class Question {

    private Person person;
    private List<Country> countries;
    private Date date;
    private Country correctAnswer;
    private Country givenAnswer;
    private final String gameUUID;
    private Difficulty difficulty;
    private long answerTime;
    private boolean timedOut;

    public Question() {
        this.gameUUID = UUID.randomUUID().toString();
    }

    public List<Country> getCountries() {
        return countries;
    }

    public void setCountries(List<Country> countries) {
        this.countries = countries;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Country getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(Country correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public Country getGivenAnswer() {
        return givenAnswer;
    }

    public void setGivenAnswer(Country givenAnswer) {
        this.givenAnswer = givenAnswer;
    }

    public String getGameUUID() {
        return gameUUID;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public boolean isTimedOut() {
        return timedOut;
    }

    public void setTimedOut(boolean timedOut) {
        this.timedOut = timedOut;

        if(difficulty != null) {
            setAnswerTime(difficulty.getTime());
        }
    }

    public boolean isCorrectlyAnswered() {
        return !timedOut && correctAnswer.equals(givenAnswer);
    }

    public void answer(Country country, long answerTime) {
        setGivenAnswer(country);
        setDate(new Date());
        setAnswerTime(answerTime);
    }

    public void answer(int countryIndex, long answerTime) {
        answer(getCountries().get(countryIndex), answerTime);
    }

    public long getAnswerTime() {
        return answerTime;
    }

    public void setAnswerTime(long answerTime) {
        this.answerTime = answerTime;
    }
}
