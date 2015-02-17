package rjm.romek.facegame.service;

import android.content.res.AssetManager;

import java.io.IOException;
import java.util.Random;

import rjm.romek.facegame.data.Parameters;
import rjm.romek.source.model.Country;
import rjm.romek.source.model.Person;

public class PersonRandomizerServiceImpl implements PersonRandomizerService {

    private AssetManager assetManager;
    private Parameters parameters;
    private Random random;

    public PersonRandomizerServiceImpl(AssetManager assetManager) {
        this.assetManager = assetManager;
        this.parameters = new Parameters();
        this.random = new Random();
    }

    @Override
    public Person readRandomInhabitant(Country country) {
        Person person = null;

        try {
            TranslatorService translatorService =
                    new TranslatorServiceImpl(assetManager.open(parameters.getNamingFile()));
            String countryDir = translatorService.translateToUUID(country.getName());
            String countryDirPath = parameters.getPhotosDir() + countryDir;
            String [] photos = assetManager.list(countryDirPath);

            if(photos.length <= 0) {
                return null;
            }

            int randomIndex = random.nextInt(photos.length);
            translatorService =
                    new TranslatorServiceImpl(assetManager.open(parameters.getNamingFile()));
            String personFile = countryDirPath + "/" + photos[randomIndex];
            String personName = translatorService.translateToName(photos[randomIndex]);
            person = new Person(personName, personFile);
        } catch (IOException e) {
        }

        return person;
    }
}
