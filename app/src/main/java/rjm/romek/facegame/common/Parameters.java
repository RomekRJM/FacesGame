package rjm.romek.facegame.common;

public final class Parameters {
    private final String countryListFile = "list.json";
    private final String photosDir = "photos/";
    private final String flagDir = "flags/";
    private final int questionsInSet = 12;
    private final int changeDifficultyEvery = 3;

    public int getQuestionsInSet() {
        return questionsInSet;
    }

    public String getPhotosDir() {
        return photosDir;
    }

    public String getCountryListFile() {
        return countryListFile;
    }

    public String getFlagDir() {
        return flagDir;
    }

    public int getChangeDifficultyEvery() {
        return changeDifficultyEvery;
    }
}
