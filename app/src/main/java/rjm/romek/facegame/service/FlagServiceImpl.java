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
    private AssetManager assetManager;
    private Parameters parameters;

    public FlagServiceImpl(AssetManager assetManager) {
        this.assetManager = assetManager;
        this.parameters = new Parameters();
    }

    @Override
    public void changeFlagNameToUUID(List<Country> countries) {
        try {
            TranslatorService translatorService =
                    new TranslatorServiceImpl(assetManager.open(parameters.getNamingFile()));

            Map<String, String> map = new HashMap<String, String>();
            for(int i=0; i<countries.size(); ++i) {
                map.put(TranslatorService.EMPTY_PREFIX + i, countries.get(i).getFlag());
            }

            translatorService.translateInBatch(map);

            for(Country country : countries) {
                for(Map.Entry<String, String> entry : map.entrySet()) {
                    if(country.getFlag().equals(entry.getValue())) {
                        country.setFlag(parameters.getFlagDir() + entry.getKey());
                    }
                }
            }

        } catch (IOException e) {
        }
    }
}
