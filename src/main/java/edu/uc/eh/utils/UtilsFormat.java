package edu.uc.eh.utils;

import edu.uc.eh.structures.CharacterDouble;

/**
 * Created by chojnasm on 11/25/15.
 * Utils related to regular expressions and text formatting
 */
public class UtilsFormat {

    private static UtilsFormat instance;

    private UtilsFormat() {
    }

    static {
        instance = new UtilsFormat();
    }

    public static UtilsFormat getInstance(){return instance;}

    /**
     * Parse compound letter and modification mass from string e.g. K[+80]
     * @param input
     * @return
     */
    public CharacterDouble modificationToCharDouble(String input){
        Character c = input.substring(0,1).charAt(0);
        Double d = Double.parseDouble(input.substring(1).replaceAll("[+\\]\\[]",""));

        return new CharacterDouble(c,d);
    }
}
