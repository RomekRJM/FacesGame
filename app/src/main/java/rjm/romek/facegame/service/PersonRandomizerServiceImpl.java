package rjm.romek.facegame.service;

import android.content.res.AssetManager;

import java.io.IOException;
import java.util.Random;

import rjm.romek.facegame.common.Parameters;
import rjm.romek.source.model.Country;
import rjm.romek.source.model.Person;

public class PersonRandomizerServiceImpl implements PersonRandomizerService {

    private AssetManager assetManager;
    private Parameters parameters;
    private Random random;
    private TranslatorService translatorService;

    public PersonRandomizerServiceImpl(AssetManager assetManager) {
        this.assetManager = assetManager;
        this.parameters = new Parameters();
        this.random = new Random();
        this.translatorService = new TranslatorServiceImpl();
    }

    @Override
    public Person readRandomInhabitant(Country country) {
        Person person = null;

        try {
            String countryDir = translatorService.translateToFileName(country.getName());
            String countryDirPath = parameters.getPhotosDir() + countryDir;
            String[] photos = assetManager.list(countryDirPath);

            if (photos.length <= 0) {
                return null;
            }

            int randomIndex = random.nextInt(photos.length);
            String personName = countryDirPath + "/" + photos[randomIndex];
            person = new Person(personName);
        } catch (IOException e) {
        }

        return person;
    }
}
