package rjm.romek.facegame.data;

public final class Parameters {
	private final String countryListFile = "list.json";
    private final String photosDir = "photos/";
    private final String flagDir = "flags/";
    private final int questionsInSet = 10;

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
}
