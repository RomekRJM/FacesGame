package rjm.romek.facegame.service;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.Charset;

public class TranslatorServiceImpl implements TranslatorService {
    private final String encoding;

    public TranslatorServiceImpl() {
        this("UTF-8");
    }

    TranslatorServiceImpl(String encoding) {
        this.encoding = encoding;
    }

    @Override
    public String translateToName(String fileName) {
        try {
            return URLDecoder.decode(fileName, encoding);
        } catch (UnsupportedEncodingException e) {
            return fileName;
        }
    }

    @Override
    public String translateToFileName(String name) {
        try {
            return URLEncoder.encode(name, encoding);
        } catch (UnsupportedEncodingException e) {
            return name;
        }
    }
}
