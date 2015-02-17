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

    public PhotoServiceImpl(AssetManager assetManager) throws IOException {
        this.assetManager = assetManager;
    }

    @Override
    public Bitmap readFromAssets(String file) {
        InputStream inStr = null;
        Bitmap bitmap = null;

        try {
            inStr = assetManager.open(file);
            bitmap = BitmapFactory.decodeStream(inStr);
        } catch (IOException e) {
        }

        return bitmap;
    }
}
