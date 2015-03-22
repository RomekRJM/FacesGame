package rjm.romek.facegame.model;

public enum Difficulty {
    EASY(2, 5, 30000l), NORMAL(2, 4, 25000l), HARD(4, 4, 15000l), HARDCORE(4, 2, 10000l);

    private Integer availableAnswers;
    private Integer radius;
    private Long time;

    Difficulty(Integer availableAnswers, Integer radius, Long time) {
        this.availableAnswers = availableAnswers;
        this.radius = radius;
        this.time = time;
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

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }
}
