package rjm.romek.facegame.service;

import android.test.AndroidTestCase;
import android.test.InstrumentationTestCase;

import junit.framework.TestCase;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;


public class TranslatorServiceImplTest extends AndroidTestCase {
	
	private InputStream dictionaryStream;
	private TranslatorService translatorService;
	
	@Override
	public void setUp() {
		String naming = "{" +
				"    \"68555f7b-2f81-4a4b-893a-4c618f40ddba.JPEG\": \"Quett Masire.JPEG\"," +
				"    \"7003eb4f-c5c3-4c42-9ee3-da50d326a33d.jpg\": \"Luther Wenge.jpg\"," +
				"    \"26900166-bd4e-4b79-b6f4-bd81a8864f18\": \"Niue\"" +
				"}";
		
		dictionaryStream = new ByteArrayInputStream(naming.getBytes());
		translatorService = new TranslatorServiceImpl(dictionaryStream);
	}

	public void testShouldTranslateCountryCorrectly() {
		assertEquals("Niue", translatorService.translate("26900166-bd4e-4b79-b6f4-bd81a8864f18"));
	}
	
	public void testShouldTranslatePersonCorrectly() {
		assertEquals("Quett Masire.JPEG", translatorService.translate("68555f7b-2f81-4a4b-893a-4c618f40ddba.JPEG"));
	}
	
	public void testShouldReturnNullOnNotExistingPerson() {
		assertNull(translatorService.translate("654.JPEG"));
	}
	
	public void testShouldReturnNullOnNotExistingCountry() {
		assertNull(translatorService.translate("654"));
	}
	
	public void testShouldReturnNullOnInvalidStream() {
		TranslatorServiceImpl invalid = new TranslatorServiceImpl(null);
		assertNull(translatorService.translate("smth"));
	}
	
	public void testShouldReturnNullOnWStream() {
		TranslatorServiceImpl invalid = new TranslatorServiceImpl(new ByteArrayInputStream("{".getBytes()));
		assertNull(translatorService.translate("smth"));
	}

	public void testShouldReturnNullOnIOException() {
		TranslatorServiceImpl invalid = new TranslatorServiceImpl(new InputStream() {
			@Override
			public int read() throws IOException {
				throw new IOException();
			}
		});
		
		assertNull(translatorService.translate("smth"));
	}
}
