package edu.uc.eh.service;

import edu.uc.eh.structures.DiffIdentifier;
import edu.uc.eh.structures.CharacterDouble;
import edu.uc.eh.structures.StringDoubleString;
import edu.uc.eh.utils.UtilsFormat;
import edu.uc.eh.utils.UtilsIO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by chojnasm on 11/25/15.
 * Manage access to PSI-MOD ontology.
 */

@Service
public class PsiModService {

    private static final Logger log = LoggerFactory.getLogger(PsiModService.class);
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
    public StringDoubleString getIdentifier(String modification) {
        CharacterDouble cd = UtilsFormat.getInstance().modificationToCharDouble(modification);
        List<DiffIdentifier> list = mapping.get(cd.getCharacter());
        String currentIdentifier = "";

        Double minDiff = Double.MAX_VALUE;
        Double originalDiff = null;
        String description = null;

        if(list == null) {
            String msg =  String.format("Modification %s not found", modification);
            log.warn(msg);
            throw new RuntimeException(msg);
        }

        for (DiffIdentifier di : list) {
            if (Math.abs(di.getDiff() - cd.getaDouble()) < minDiff) {
                minDiff = Math.abs(di.getDiff() - cd.getaDouble());
                originalDiff = di.getDiff();
                currentIdentifier = di.getIdentifier();
                description = di.getDescription();
            }
        }
        return new StringDoubleString(currentIdentifier, originalDiff, description);
    }
}
