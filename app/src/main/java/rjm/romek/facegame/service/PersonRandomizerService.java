package rjm.romek.facegame.service;

import rjm.romek.source.model.Country;

public interface PersonRandomizerService {
    public rjm.romek.source.model.Person readRandomInhabitant(Country country);
}
