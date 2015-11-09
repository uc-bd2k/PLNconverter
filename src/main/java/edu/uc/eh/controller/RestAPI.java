package edu.uc.eh.controller;

import edu.uc.eh.service.PrositeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by chojnasm on 11/9/15.
 */

@Controller
public class RestAPI {

    private static final Logger log = LoggerFactory.getLogger(PrositeService.class);

    private final PrositeService prositeService;


    @Autowired
    public RestAPI(PrositeService prositeService) {
        this.prositeService = prositeService;
    }

    @RequestMapping(value = "api/convert/{peptide}", method = RequestMethod.GET)
    public
    @ResponseBody
    List<String> convertToPLN(@PathVariable String peptide) {
        log.info(String.format("Run convertToPLN with argument: %s", peptide));

        return prositeService.getTable(peptide);
    }

}
