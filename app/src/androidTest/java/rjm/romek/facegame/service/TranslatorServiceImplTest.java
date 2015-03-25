package rjm.romek.facegame.service;

import junit.framework.TestCase;

import java.nio.charset.Charset;

public class TranslatorServiceImplTest extends TestCase {
    private final TranslatorService translatorService = new TranslatorServiceImpl(Charset.defaultCharset().displayName());

    public void testShouldTranslateEncodedDirName() {
        assertEquals("Côte d’Ivoire", translatorService.translateToName("C%C3%B4te+d%27Ivoire"));
    }

    public void testShouldTranslateDecodedDirName() {
        assertEquals("C%C3%B4te+d%27Ivoire", translatorService.translateToFileName("Côte d’Ivoire"));
    }
}
