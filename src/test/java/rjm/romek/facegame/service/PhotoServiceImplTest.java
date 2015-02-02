package rjm.romek.facegame.service;

import android.test.AndroidTestCase;
import junit.framework.TestCase;
import org.mockito.Mockito;
import rjm.romek.source.model.Country;

import java.io.FileInputStream;
import java.io.IOException;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

public class PhotoServiceImplTest extends AndroidTestCase {

    private PhotoService photoService;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        AssetProvider assetProvider = Mockito.mock(AssetProvider.class);

        when(assetProvider.list("resources/photos/a")).thenReturn(new String[]{
                "resources/photos/a/a1.png"
        });
        when(assetProvider.list("resources/photos/z")).thenReturn(new String[]{
        });
        when(assetProvider.list("resources/photos/b")).thenReturn(new String[]{
                "resources/photos/b/b1.jpg",
                "resources/photos/b/b2.jpg"
        });

        when(assetProvider.open("resources/photos/a/a1.png")).thenReturn(new FileInputStream("resources/photos/a/a1.png"));
        when(assetProvider.open("resources/photos/b/b1.jpg")).thenReturn(new FileInputStream("resources/photos/b/b1.jpg"));
        when(assetProvider.open("resources/photos/b/b2.jpg")).thenReturn(new FileInputStream("resources/photos/b/b2.jpg"));

        when(assetProvider.getCountryDirPath()).thenReturn("");

        photoService = new PhotoServiceImpl(assetProvider);
    }

    public void testOpensSingleFileFolder() {
        Country country = new Country();
        country.setCountryDir("resources/photos/a");

        assertNotNull(photoService.readRandomInhabitantBitmap(country));
    }

    public void testReturnsNullForEmptyFileFolder() {
        Country country = new Country();
        country.setCountryDir("resources/photos/z");

        assertNull(photoService.readRandomInhabitantBitmap(country));
    }

    public void testReturnsDifferentImagesIn10Tries() {
        Country country = new Country();
        country.setCountryDir("resources/photos/z");

        assertNull(photoService.readRandomInhabitantBitmap(country));
    }
}