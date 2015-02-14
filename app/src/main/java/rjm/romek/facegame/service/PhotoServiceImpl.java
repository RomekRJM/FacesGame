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

    private AssetManager assetManager;
    private Parameters parameters;
    private Random random;

    public PhotoServiceImpl(AssetManager assetManager) {
        this.assetManager = assetManager;
        this.random = new Random();
        this.parameters = new Parameters();
    }

    @Override
    public Bitmap readRandomInhabitantBitmap(Country country) {
        InputStream istr = null;
        Bitmap bitmap = null;

        try {
            String countryDirPath = parameters.getPhotosDir() + country.getCountryDir();
            String [] photos = assetManager.list(countryDirPath);

            if(photos.length <= 0) {
                return null;
            }

            int randomIndex = random.nextInt(photos.length);
            istr = assetManager.open(countryDirPath + "/" + photos[randomIndex]);
            bitmap = BitmapFactory.decodeStream(istr);
        } catch (IOException e) {
        } finally {
            IOUtils.closeQuietly(istr);
        }

        return bitmap;
    }
}
