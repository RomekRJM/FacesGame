package rjm.romek.facegame.service;

import rjm.romek.source.model.Country;

import java.io.IOException;
import java.io.InputStream;

public interface AssetProvider {
    public String [] list(String path) throws IOException;
    public InputStream open(String fileName) throws IOException;
    public String getCountryDirPath();
}
