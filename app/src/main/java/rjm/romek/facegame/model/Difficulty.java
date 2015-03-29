package rjm.romek.facegame.model;

public enum Difficulty {
    EASY(2, 7, 40000l, 0.1f), NORMAL(2, 5, 20000l, 0.15f), HARD(4, 4, 15000l, 0.2f), HARDCORE(4, 2, 10000l, 0.3f);

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

    public Integer getRadius() {
        return radius;
    }

    public Long getTime() {
        return time;
    }

    public Float getLevelPointMultiplier() {
        return levelPointMultiplier;
    }
}
