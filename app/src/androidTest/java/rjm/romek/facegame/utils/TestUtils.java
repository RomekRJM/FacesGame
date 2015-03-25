package rjm.romek.facegame.utils;

import android.content.res.AssetManager;

import java.io.IOException;
import java.util.Set;

import rjm.romek.source.gen.CountriesDeserializer;
import rjm.romek.source.model.Country;

public class TestUtils {
    public static Set<Country> loadCountries(AssetManager manager) throws IOException {
        CountriesDeserializer countriesDeserializer = new CountriesDeserializer();
        return countriesDeserializer.deserialize(manager.open("list.json"));
    }
}
