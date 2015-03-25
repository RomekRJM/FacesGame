package rjm.romek.facegame.service;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.io.InputStream;

public class PhotoServiceImpl implements PhotoService {

    private AssetManager assetManager;

    public PhotoServiceImpl(AssetManager assetManager) throws IOException {
        this.assetManager = assetManager;
    }

    @Override
    public Bitmap readFromAssets(String file) {
        InputStream inStr;
        Bitmap bitmap = null;

        try {
            inStr = assetManager.open(file);
            bitmap = BitmapFactory.decodeStream(inStr);
        } catch (IOException e) {
        }

        return bitmap;
    }
}
