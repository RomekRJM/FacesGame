package rjm.romek.facegame.data;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.commons.io.IOUtils;

import com.google.gson.stream.JsonReader;

public class TranslatorServiceImpl implements TranslatorService {
	
	private final InputStream dictionaryStream;
	
	public TranslatorServiceImpl(InputStream dictionaryStream) {
		this.dictionaryStream = dictionaryStream;
	}

	@Override
	public String translate(String uuid) {
		String translatedName = null;
		JsonReader reader = null;
		
		try {
			reader = new JsonReader(new InputStreamReader(dictionaryStream));
			
			reader.beginObject();
			
			while (reader.hasNext()) {
				if(reader.nextName().equals(uuid)) {
					translatedName = reader.nextString();
					break;
				} else {
					reader.nextString();
				}
			}
			
		} catch (IOException exc) {
		} finally {
			IOUtils.closeQuietly(reader);
		}
		
		return translatedName;
	}

}
