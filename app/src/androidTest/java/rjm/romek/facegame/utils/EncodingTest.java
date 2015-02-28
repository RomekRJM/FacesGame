package rjm.romek.facegame.utils;

import android.test.AndroidTestCase;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import rjm.romek.facegame.model.Question;
import rjm.romek.source.model.Country;

public class EncodingTest extends AndroidTestCase {

    public void testEncodingFileNames() throws IOException {
        InputStream open = getContext().getAssets().open("photos/S%C3%A3o+Tom%C3%A9+and+Pr%C3%ADncipe");
        assertNotNull(open);
        open.close();
    }
}
