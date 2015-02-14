package rjm.romek.facegame.model;

import android.graphics.Bitmap;

import rjm.romek.source.model.Country;
import rjm.romek.source.model.Person;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class Question {

    private Bitmap personBitmap;
    private Set<Country> countries;
    private Date date;
    private Country correctAnswer;
    private Country givenAnswer;
    private final String gameUUID;

    public Question() {
        this.gameUUID = UUID.randomUUID().toString();
    }

    public Set<Country> getCountries() {
        return countries;
    }

    public void setCountries(Set<Country> countries) {
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

    public Bitmap getPersonBitmap() {
        return personBitmap;
    }

    public void setPersonBitmap(Bitmap personBitmap) {
        this.personBitmap = personBitmap;
    }

    public boolean isCorrectlyAnswered() {
        return correctAnswer.equals(givenAnswer);
    }

    public void answer(Country country) {
        setGivenAnswer(country);
        setDate(new Date());
    }
}
