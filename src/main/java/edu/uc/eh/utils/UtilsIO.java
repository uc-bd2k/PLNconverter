package edu.uc.eh.utils;

import edu.uc.eh.structures.DiffIdentifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.*;

/**
 * Created by chojnasm on 11/24/15.
 * IO Utils delivered as Singleton
 */
public class UtilsIO {

    private static final Logger log = LoggerFactory.getLogger(UtilsIO.class);
    private static UtilsIO instance;

    private UtilsIO() {
    }

    static {
        instance = new UtilsIO();
    }

    public static UtilsIO getInstance() {
        return instance;
    }

    /**
     * Read resource file from absolute path, which is relative to src/main/resources.
     * Store data in a structure enabling quick search for identifiers of modifications
     * @param fileName
     * @return Map
     */
    public Map<Character, List<DiffIdentifier>> readResource(String fileName) {
        Class<?> cl = getClass();
        InputStream is = cl.getResourceAsStream(fileName);
        Map<Character, List<DiffIdentifier>> map = new HashMap<>();

        try(BufferedReader br =
                    new BufferedReader(new InputStreamReader(is))) {

            String inputLine;

            while ((inputLine = br.readLine()) != null) {

                String[] fields = inputLine.split("\t");
                if(fields[0].equals("identifier")) continue;

                String identifier = fields[0];
                Character c = fields[1].charAt(0);
                Double d = Double.parseDouble(fields[2]);
                String description = fields[3];

                DiffIdentifier di = new DiffIdentifier(d,identifier,description);

                if(!map.containsKey(c)){
                    map.put(c,new ArrayList<>());
                }

                List<DiffIdentifier> list = map.get(c);
                list.add(di);
                Collections.sort(list);
            }

            br.close();
        } catch (IOException e) {
            log.error("IOException: " + e);
        }

        return map;
    }


}
