package rjm.romek.facegame.service;

import android.content.res.AssetManager;
import android.test.AndroidTestCase;

import junit.framework.Assert;

import rjm.romek.facegame.service.PhotoService;
import rjm.romek.facegame.service.PhotoServiceImpl;
import rjm.romek.source.model.Country;

public class PhotoServiceImplTest extends AndroidTestCase {

    private PhotoService photoService;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        AssetManager assetManager = getContext().getAssets();
        photoService = new PhotoServiceImpl(assetManager);
    }

    public void testOpensSingleFileFolder() {
        Country country = new Country();
        country.setName("Poland");

        Assert.assertNotNull(photoService.readRandomInhabitantBitmap(country));
    }

    public void testReturnsNullForEmptyFileFolder() {
        Country country = new Country();
        country.setName("test");

        Assert.assertNull(photoService.readRandomInhabitantBitmap(country));
    }
}