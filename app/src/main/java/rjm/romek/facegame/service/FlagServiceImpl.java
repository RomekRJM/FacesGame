package rjm.romek.facegame.service;

import android.content.res.AssetManager;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rjm.romek.facegame.data.Parameters;
import rjm.romek.source.model.Country;
import rjm.romek.source.model.Person;

public class FlagServiceImpl implements FlagService {
    private Parameters parameters;
    private TranslatorService translatorService;

    public FlagServiceImpl() {
        this.parameters = new Parameters();
        this.translatorService = new TranslatorServiceImpl();
    }

    @Override
    public String changeNameToFileName(Country country) {
        return parameters.getFlagDir() + translatorService.translateToFileName(country.getFlag());
    }
}
