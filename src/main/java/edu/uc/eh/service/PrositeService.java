package edu.uc.eh.service;

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

    public List<String> getTable(String peptide) {
        ArrayList<String> output = new ArrayList<>();
        output.add("Output1");
        output.add("Output2");
        output.add("Output3");

        return output;
    }
}
