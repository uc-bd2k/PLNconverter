package edu.uc.eh.service;

import edu.uc.eh.structures.DiffIdentifier;
import edu.uc.eh.structures.CharacterDouble;
import edu.uc.eh.structures.StringDoubleStringList;
import edu.uc.eh.utils.UtilsFormat;
import edu.uc.eh.utils.UtilsIO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    public StringDoubleStringList getIdentifier(String modification, double epsilon) {
        CharacterDouble cd = UtilsFormat.getInstance().modificationToCharDouble(modification);
        List<DiffIdentifier> list = mapping.get(cd.getCharacter());
        String currentIdentifier = "";

        Double minDiff = Double.MAX_VALUE;
        Double originalDiff = null;
        String description = null;

        List<StringDoubleStringList> similar = null;

        if(list == null) {
            String msg =  String.format("Modification %s not found", modification);
            log.warn(msg);
            throw new RuntimeException(msg);
        }

        for (DiffIdentifier di : list) {

            double delta = Math.abs(di.getDiff() - cd.getaDouble());
            if (delta <= minDiff && delta < epsilon) {

                if(delta == minDiff){
                    if(similar == null)similar = new ArrayList<>();

                    similar.add(new StringDoubleStringList(di.getIdentifier(),di.getDiff(),di.getDescription(),null));
                }else{
                    minDiff = delta;
                    originalDiff = di.getDiff();
                    currentIdentifier = di.getIdentifier();
                    description = di.getDescription();
                }

            }
        }
        return new StringDoubleStringList(currentIdentifier, originalDiff, description,similar);
    }
}
