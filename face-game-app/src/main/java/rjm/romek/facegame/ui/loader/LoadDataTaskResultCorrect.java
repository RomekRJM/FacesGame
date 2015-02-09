package rjm.romek.facegame.ui.loader;

import java.util.Set;

import rjm.romek.source.model.Country;

public class LoadDataTaskResultCorrect implements LoadDataTaskResult<Set<Country>> {
	
	private Set<Country> country;

	public LoadDataTaskResultCorrect(Set<Country> country) {
		this.country = country;
	}
	
	@Override
	public Set<Country> getResult() {
		return country;
	}

}