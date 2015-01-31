package rjm.romek.facegame.data;

public final class Parameters {
	public final String countryListFile = "list.json";
    public final int qusetionsInSet = 3;

    public int getQuestionsInSet() {
        return qusetionsInSet;
    }

	public String getCountryListFile() {
		return countryListFile;
	}
}
