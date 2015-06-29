package rjm.romek.facegame.model;

public enum Difficulty {
    NOOB(2, 40000l, 0.1f), EASY(2, 7, 20000l, 0.15f), NORMAL(4, 20000l, 0.2f),
    HARD(4, 5, 15000l, 0.25f), HARDCORE(4, 3, 10000l, 0.3f);

    private CountryAlgorithm countryAlgorithm;
    private Integer availableAnswers;
    private Integer radius;
    private Long time;
    private Float levelPointMultiplier;

    Difficulty(Integer availableAnswers, Integer radius, Long time, Float levelPointMultiplier) {
        this.availableAnswers = availableAnswers;
        this.radius = radius;
        this.time = time;
        this.levelPointMultiplier = levelPointMultiplier;
        this.countryAlgorithm = CountryAlgorithm.RANDOM_COUNTRY_IN_RADIUS;
    }

    Difficulty(Integer availableAnswers, Long time, Float levelPointMultiplier) {
        this.availableAnswers = availableAnswers;
        this.time = time;
        this.levelPointMultiplier = levelPointMultiplier;
        this.countryAlgorithm = CountryAlgorithm.RANDOM_COUNTRY_FROM_DIFFERENT_CONTINENT;
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

    public CountryAlgorithm getCountryAlgorithm() {
        return countryAlgorithm;
    }
}
