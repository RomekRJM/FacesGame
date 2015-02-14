package rjm.romek.facegame.utils;

import android.content.res.AssetManager;

import rjm.romek.source.gen.CountriesDeserializer;
import rjm.romek.source.model.Country;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Set;

public class TestUtils {
    public static Set<Country> loadCountries(AssetManager manager) throws IOException {
        CountriesDeserializer countriesDeserializer = new CountriesDeserializer();
        return countriesDeserializer.deserialize(manager.open("list.json"));
    }
}
