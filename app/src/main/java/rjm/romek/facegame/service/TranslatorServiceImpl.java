package rjm.romek.facegame.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

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
    public Map<String, String> translateInBatch(Map<String, String> map) {
        Map<String, String> completed = new HashMap<String, String>();

        try {
            reader.beginObject();

            while (reader.hasNext()) {
                String currentUUID = reader.nextName();
                String currentName = reader.nextString();

                for(Map.Entry<String, String> entry : map.entrySet()) {
                    if((StringUtils.startsWith(entry.getValue(), EMPTY_PREFIX)
                            && StringUtils.equals(currentUUID, entry.getKey()))) {
                        completed.put(currentUUID, currentName);
                    }

                    if(StringUtils.startsWith(entry.getKey(), EMPTY_PREFIX)
                            && StringUtils.equals(currentName, entry.getValue()))
                    {
                        completed.put(currentUUID, currentName);
                    }
                }
            }

        } catch (IOException exc) {
        } finally {
            IOUtils.closeQuietly(reader);
        }

        map.putAll(completed);

        return map;
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
