package edu.uc.eh.utils;

import edu.uc.eh.structures.CharacterDouble;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by chojnasm on 11/25/15.
 */
public class UtilsFormatTest {

    @Test
    public void testModificationToCharDouble() throws Exception {

        assertEquals(new CharacterDouble('K',80d),UtilsFormat.getInstance().modificationToCharDouble("K[+80]"));
        assertEquals(new CharacterDouble('K',80.1),UtilsFormat.getInstance().modificationToCharDouble("K[+80.1]"));
        assertEquals(new CharacterDouble('K',-80.0),UtilsFormat.getInstance().modificationToCharDouble("K[-80]"));
    }
}