package rjm.romek.facegame;

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

public class PhotoServiceImplTest extends InstrumentationTestCase {

    private PhotoService photoService;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        AssetProvider assetProvider = Mockito.mock(AssetProvider.class);

        Mockito.when(assetProvider.list("resources/photos/a")).thenReturn(new String[]{
                "resources/photos/a/a1.png"
        });
        Mockito.when(assetProvider.list("resources/photos/z")).thenReturn(new String[]{
        });
        Mockito.when(assetProvider.list("resources/photos/b")).thenReturn(new String[]{
                "resources/photos/b/b1.jpg",
                "resources/photos/b/b2.jpg"
        });

        Mockito.when(assetProvider.open("resources/photos/a/a1.png")).thenReturn(new FileInputStream("resources/photos/a/a1.png"));
        Mockito.when(assetProvider.open("resources/photos/b/b1.jpg")).thenReturn(new FileInputStream("resources/photos/b/b1.jpg"));
        Mockito.when(assetProvider.open("resources/photos/b/b2.jpg")).thenReturn(new FileInputStream("resources/photos/b/b2.jpg"));

        Mockito.when(assetProvider.getCountryDirPath()).thenReturn("");

        photoService = new PhotoServiceImpl(new AssetProviderImpl(getInstrumentation().getContext().getAssets(), new Parameters()));
    }

    public void testOpensSingleFileFolder() {
        Country country = new Country();
        country.setCountryDir("resources/photos/a");

        Assert.assertNotNull(photoService.readRandomInhabitantBitmap(country));
    }

    public void testReturnsNullForEmptyFileFolder() {
        Country country = new Country();
        country.setCountryDir("resources/photos/z");

        Assert.assertNull(photoService.readRandomInhabitantBitmap(country));
    }

    public void testReturnsDifferentImagesIn10Tries() {
        Country country = new Country();
        country.setCountryDir("resources/photos/z");

        Assert.assertNull(photoService.readRandomInhabitantBitmap(country));
    }
}