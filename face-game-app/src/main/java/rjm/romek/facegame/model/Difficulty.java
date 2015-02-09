package rjm.romek.facegame.model;

public enum Difficulty {
    EASY(2, 5), NORMAL(2, 4), HARD(4, 4), HARDCORE(4, 2);

    private Integer availableAnswers;
    private Integer radius;

    Difficulty(Integer radius, Integer availableAnswers) {
        this.radius = radius;
        this.availableAnswers = availableAnswers;
    }

    public Integer getAvailableAnswers() {
        return availableAnswers;
    }

    public void setAvailableAnswers(Integer availableAnswers) {
        this.availableAnswers = availableAnswers;
    }

    public Integer getRadius() {
        return radius;
    }

    public void setRadius(Integer radius) {
        this.radius = radius;
    }

}
