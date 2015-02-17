package rjm.romek.facegame.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.commons.io.IOUtils;

import com.google.gson.stream.JsonReader;

public class TranslatorServiceImpl implements TranslatorService {
	
	private final JsonReader reader;
	
	public TranslatorServiceImpl(InputStream dictionaryStream) {
        if(dictionaryStream == null) {
            throw new IllegalArgumentException("Dictionary stream should not be null");
        }

        reader = new JsonReader(new InputStreamReader(dictionaryStream));
	}

	@Override
	public String translateToName(String uuid) {
		String translatedName = null;
		
		try {
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

    @Override
    public String translateToUUID(String name) {
        String translatedUUID = null;

        try {
            reader.beginObject();

            while (reader.hasNext()) {
                String currentUUID = reader.nextName();
                String currentName = reader.nextString();
                if(currentName.equals(name)) {
                    translatedUUID = currentUUID;
                    break;
                }
            }

        } catch (IOException exc) {
        } finally {
            IOUtils.closeQuietly(reader);
        }

        return translatedUUID;
    }
}
