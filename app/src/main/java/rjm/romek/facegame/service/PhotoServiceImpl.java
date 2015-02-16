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

    public PhotoServiceImpl(AssetManager assetManager) throws IOException {
        this.assetManager = assetManager;
        this.random = new Random();
        this.parameters = new Parameters();
    }

    @Override
    public Bitmap readRandomInhabitantBitmap(Country country) {
        InputStream inStr = null;
        Bitmap bitmap = null;

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
            inStr = assetManager.open(countryDirPath + "/" + photos[randomIndex]);
            bitmap = BitmapFactory.decodeStream(inStr);
        } catch (IOException e) {
        }

        return bitmap;
    }
}
