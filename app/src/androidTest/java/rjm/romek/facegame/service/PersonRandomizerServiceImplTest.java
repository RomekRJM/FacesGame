package rjm.romek.facegame.service;

import android.content.res.AssetManager;
import android.test.AndroidTestCase;

import junit.framework.Assert;

import rjm.romek.source.model.Country;

public class PersonRandomizerServiceImplTest extends AndroidTestCase {

    private PersonRandomizerService personService;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        AssetManager assetManager = getContext().getAssets();
        personService = new PersonRandomizerServiceImpl(assetManager);
    }

    public void testOpensSingleFileFolder() {
        Country country = new Country();
        country.setName("Poland");

        Assert.assertNotNull(personService.readRandomInhabitant(country));
    }

    public void testReturnsNullForEmptyFileFolder() {
        Country country = new Country();
        country.setName("test");

        Assert.assertNull(personService.readRandomInhabitant(country));
    }
}