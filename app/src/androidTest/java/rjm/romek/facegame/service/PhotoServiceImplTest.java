package rjm.romek.facegame.service;

import android.content.res.AssetManager;
import android.test.AndroidTestCase;
import android.test.InstrumentationTestCase;
import junit.framework.Assert;
import org.mockito.Mockito;
import rjm.romek.facegame.data.Parameters;
import rjm.romek.facegame.service.AssetProvider;
import rjm.romek.facegame.service.AssetProviderImpl;
import rjm.romek.facegame.service.PhotoService;
import rjm.romek.facegame.service.PhotoServiceImpl;
import rjm.romek.source.model.Country;

import java.io.FileInputStream;
import java.util.Properties;

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
        country.setCountryDir("0a0349f1-ad9e-466c-94fc-cb122d2a441e");

        Assert.assertNotNull(photoService.readRandomInhabitantBitmap(country));
    }

    public void testReturnsNullForEmptyFileFolder() {
        Country country = new Country();
        country.setCountryDir("test");

        Assert.assertNull(photoService.readRandomInhabitantBitmap(country));
    }
}