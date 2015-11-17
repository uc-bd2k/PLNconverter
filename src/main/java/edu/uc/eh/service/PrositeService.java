package edu.uc.eh.service;

import edu.uc.eh.utils.NetworkUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chojnasm on 11/9/15.
 */

@Service
public class PrositeService {

    private static final Logger log = LoggerFactory.getLogger(PrositeService.class);

    public String getTable(String peptide) {

        String response = "Prosite API did not respond.";
        String prositeUrl = String.format("http://prosite.expasy.org/cgi-bin/prosite/PSScan.cgi?sig=%s&lineage=9606&db=sp&output=json",peptide);

        log.info("Querying: " + prositeUrl);

        try {
            response = NetworkUtils.readUrl(prositeUrl);
        } catch (Exception e) {
            e.printStackTrace();
        }


        log.info("Response: " + response);
        return response;
    }
}
