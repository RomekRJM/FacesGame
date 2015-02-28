package rjm.romek.facegame.service;

import android.test.AndroidTestCase;

import java.nio.charset.Charset;

public class TranslatorServiceImplTest extends AndroidTestCase {
    private final TranslatorService translatorService = new TranslatorServiceImpl(Charset.defaultCharset().displayName());

    public void testShouldTranslateEncodedDirName() {
        assertEquals("Côte d’Ivoire", translatorService.translateToName("C%C3%B4te+d%27Ivoire"));
    }

    public void testShouldTranslateDecodedDirName() {
        assertEquals("C%C3%B4te+d%27Ivoire", translatorService.translateToFileName("Côte d’Ivoire"));
    }
}
