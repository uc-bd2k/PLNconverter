package edu.uc.eh.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * Created by chojnasm on 11/17/15.
 */
public class UtilsNetwork {

    private static UtilsNetwork instance;

    private UtilsNetwork() {
    }

    static {
        instance = new UtilsNetwork();
    }

    public static UtilsNetwork getInstance(){return instance;}

    public String readUrl(String urlString) throws Exception {
        BufferedReader reader = null;
        try {
            URL url = new URL(urlString);
            reader = new BufferedReader(new InputStreamReader(url.openStream()));
            StringBuffer buffer = new StringBuffer();
            int read;
            char[] chars = new char[1024];
            while ((read = reader.read(chars)) != -1)
                buffer.append(chars, 0, read);

            return buffer.toString();
        } finally {
            if (reader != null)
                reader.close();
        }
    }
}
