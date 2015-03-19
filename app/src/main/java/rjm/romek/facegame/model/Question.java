package rjm.romek.facegame.model;

import android.graphics.Bitmap;

import rjm.romek.source.model.Country;
import rjm.romek.source.model.Person;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class Question {

    private Person person;
    private List<Country> countries;
    private Date date;
    private Country correctAnswer;
    private Country givenAnswer;
    private final String gameUUID;
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

    public boolean isTimedOut() {
        return timedOut;
    }

    public void setTimedOut(boolean timedOut) {
        this.timedOut = timedOut;
    }

    public boolean isCorrectlyAnswered() {
        return !timedOut && correctAnswer.equals(givenAnswer);
    }

    public void answer(Country country) {
        setGivenAnswer(country);
        setDate(new Date());
    }

    public void answer(int countryIndex) {
        answer(getCountries().get(countryIndex));
    }

}
