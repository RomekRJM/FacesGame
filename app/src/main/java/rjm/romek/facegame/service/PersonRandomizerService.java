package rjm.romek.facegame.service;

import rjm.romek.source.model.Country;

public interface PersonRandomizerService {
    rjm.romek.source.model.Person readRandomInhabitant(Country country);
}
