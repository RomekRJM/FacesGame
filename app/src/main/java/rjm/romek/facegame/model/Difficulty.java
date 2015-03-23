package rjm.romek.facegame.model;

public enum Difficulty {
    EASY(2, 5, 30000l, 0.1f), NORMAL(2, 4, 25000l, 0.15f), HARD(4, 4, 15000l, 0.2f), HARDCORE(4, 2, 10000l, 0.3f);

    private Integer availableAnswers;
    private Integer radius;
    private Long time;
    private Float levelPointMultiplier;

    Difficulty(Integer availableAnswers, Integer radius, Long time, Float levelPointMultiplier) {
        this.availableAnswers = availableAnswers;
        this.radius = radius;
        this.time = time;
        this.levelPointMultiplier = levelPointMultiplier;
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

    public Float getLevelPointMultiplier() {
        return levelPointMultiplier;
    }

    public void setLevelPointMultiplier(Float levelPointMultiplier) {
        this.levelPointMultiplier = levelPointMultiplier;
    }
}
