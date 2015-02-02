package rjm.romek.facegame.service;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import org.apache.commons.io.IOUtils;
import rjm.romek.facegame.data.Parameters;
import rjm.romek.source.model.Country;

import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

public class PhotoServiceImpl implements PhotoService {

    private AssetProvider assetProvider;
    private Random random;

    public PhotoServiceImpl(AssetProvider assetProvider) {
        this.assetProvider = assetProvider;
        this.random = new Random();
    }

    @Override
    public Bitmap readRandomInhabitantBitmap(Country country) {

        InputStream istr = null;
        try {
            String countryDirPath = assetProvider.getCountryDirPath() + country.getCountryDir();
            String [] photos = assetProvider.list(countryDirPath);
            int randomIndex = random.nextInt(photos.length);
            istr = assetProvider.open(photos[randomIndex]);
        } catch (IOException e) {
        }

        return BitmapFactory.decodeStream(istr);
    }
}
