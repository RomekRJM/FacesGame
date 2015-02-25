package rjm.romek.facegame.service;

import java.util.Map;

public interface TranslatorService {
    public static final String EMPTY_PREFIX = "empty_";

    Map<String, String> translateInBatch(Map<String, String> map);

    public String translateToName(String uuid);
    public String translateToUUID(String name);
}
