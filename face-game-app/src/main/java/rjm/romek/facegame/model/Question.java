package rjm.romek.facegame.model;

import rjm.romek.source.model.Country;
import rjm.romek.source.model.Person;

import java.util.Date;
import java.util.List;

public class Question {

    private Person person;
    private List<Country> countries;
    private Date date;
    private Country correctAnswer;
    private Country givenAnswer;
    private String gameUUID;

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
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

    public void setGameUUID(String gameUUID) {
        this.gameUUID = gameUUID;
    }

    public boolean isCorrectlyAnswered() {
        return correctAnswer.equals(givenAnswer);
    }

    public void answer(Country country) {
        setGivenAnswer(country);
        setDate(new Date());
    }
}
