package edu.uc.eh.service;

import edu.uc.eh.utils.UtilsIO;
import edu.uc.eh.utils.UtilsNetwork;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Created by chojnasm on 11/9/15.
 */

@Service
public class PrositeService {

    private static final Logger log = LoggerFactory.getLogger(PrositeService.class);

    @Value("${urls.prosite}")
    String prositeTemplate;

    @Value("${resources.mapping}")
    String psiModUrl;

    public String getTable(String peptide) {

        String response = "Prosite API did not respond.";
        String prositeUrl = String.format(prositeTemplate, peptide);

        log.info("Querying: " + prositeUrl);
        log.info("PsiMod: " + UtilsIO.getInstance().readResource(psiModUrl));

        try {
            response = UtilsNetwork.getInstance().readUrl(prositeUrl);
        } catch (Exception e) {
            e.printStackTrace();
        }

        log.info("Response: " + response);
        return response;
    }
}
