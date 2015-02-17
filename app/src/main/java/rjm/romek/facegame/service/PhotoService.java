package rjm.romek.facegame.service;

import android.graphics.Bitmap;
import rjm.romek.source.model.Country;

public interface PhotoService {
    public Bitmap readFromAssets(String file);
}
