package rjm.romek.facegame.data;

public final class Parameters {
	public final String countryListFile = "list.json";
    public final String photosDir = "photos/";
    public final int qusetionsInSet = 3;
    public int getQuestionsInSet() {
        return qusetionsInSet;
    }

    public String getPhotosDir() {
        return photosDir;
    }

	public String getCountryListFile() {
		return countryListFile;
	}
}
