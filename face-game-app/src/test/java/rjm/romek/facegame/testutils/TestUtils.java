package rjm.romek.facegame.testutils;

import rjm.romek.source.gen.CountriesDeserializer;
import rjm.romek.source.model.Country;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Set;

public class TestUtils {
    public Set<Country> loadCountries() throws FileNotFoundException {
        CountriesDeserializer countriesDeserializer = new CountriesDeserializer();
        return countriesDeserializer.deserialize(new File("resources/list.json"));
    }
}
