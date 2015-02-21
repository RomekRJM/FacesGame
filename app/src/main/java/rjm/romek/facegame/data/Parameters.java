package rjm.romek.facegame.data;

public final class Parameters {
	public final String countryListFile = "list.json";
    public final String namingFile = "naming.json";
    public final String photosDir = "photos/";
    public final String flagDir = "flags/";
    public final int questionsInSet = 3;

    public String getNamingFile() {
        return namingFile;
    }

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
