package rjm.romek.facegame.service;

import rjm.romek.facegame.data.Parameters;
import rjm.romek.source.model.Country;

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
