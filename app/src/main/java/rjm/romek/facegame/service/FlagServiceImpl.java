package rjm.romek.facegame.service;

import android.content.res.AssetManager;

import java.io.IOException;

import rjm.romek.facegame.data.Parameters;
import rjm.romek.source.model.Country;
import rjm.romek.source.model.Person;

public class FlagServiceImpl implements FlagService {
    private AssetManager assetManager;
    private Parameters parameters;

    public FlagServiceImpl(AssetManager assetManager) {
        this.assetManager = assetManager;
        this.parameters = new Parameters();
    }

    @Override
    public void changeFlagNameToUUID(Country country) {
        try {
            TranslatorService translatorService =
                    new TranslatorServiceImpl(assetManager.open(parameters.getNamingFile()));
            String flagUUID = translatorService.translateToUUID(country.getFlag());
            String flagPath = parameters.getFlagDir() + flagUUID;
            country.setFlag(flagPath);
        } catch (IOException e) {
        }
    }
}
