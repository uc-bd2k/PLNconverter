package edu.uc.eh.service;

import edu.uc.eh.structures.DiffIdentifier;
import edu.uc.eh.structures.CharacterDouble;
import edu.uc.eh.structures.StringDouble;
import edu.uc.eh.utils.UtilsFormat;
import edu.uc.eh.utils.UtilsIO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by chojnasm on 11/25/15.
 * Manage access to PSI-MOD ontology.
 */

@Service
public class PsiModService {
    private final Map<Character, List<DiffIdentifier>> mapping;

    public PsiModService() {
        mapping = UtilsIO.getInstance().readResource("/psi-mod/mapping.csv");
    }


    /**
     * Get ontology identifier for given peptide modification (e.g. K[+80])
     *
     * @param modification
     * @return
     */
    public StringDouble getIdentifier(String modification) {
        CharacterDouble cd = UtilsFormat.getInstance().modificationToCharDouble(modification);
        List<DiffIdentifier> list = mapping.get(cd.getCharacter());
        String currentIdentifier = "";

        Double minDiff = Double.MAX_VALUE;

        for (DiffIdentifier di : list) {
            if (Math.abs(di.getDiff() - cd.getaDouble()) < minDiff) {
                minDiff = Math.abs(di.getDiff() - cd.getaDouble());
                currentIdentifier = di.getIdentifier();
            }
        }
        return new StringDouble(currentIdentifier, minDiff);
    }
}
