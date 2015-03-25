package rjm.romek.facegame.utils;

import android.test.AndroidTestCase;

import java.io.IOException;
import java.io.InputStream;

public class EncodingTest extends AndroidTestCase {

    public void testEncodingFileNames() throws IOException {
        InputStream open = getContext().getAssets().open("photos/S%C3%A3o+Tom%C3%A9+and+Pr%C3%ADncipe");
        assertNotNull(open);
        open.close();
    }
}
