package rjm.romek.facegame.service;

import android.content.res.AssetManager;
import rjm.romek.facegame.data.Parameters;
import rjm.romek.source.model.Country;

import java.io.IOException;
import java.io.InputStream;

public class AssetProviderImpl implements AssetProvider {

    private final AssetManager assetManager;
    private Parameters parameters;

    public AssetProviderImpl(AssetManager assetManager, Parameters parameters) {
        this.assetManager = assetManager;
        this.parameters = parameters;
    }

    @Override
    public String[] list(String path) throws IOException {
        return assetManager.list(path);
    }

    @Override
    public InputStream open(String fileName) throws IOException {
        return assetManager.open(fileName);
    }

    @Override
    public String getCountryDirPath() {
        return parameters.getPhotosDir();
    }
}
