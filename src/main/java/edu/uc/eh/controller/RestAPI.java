package edu.uc.eh.controller;

import edu.uc.eh.service.PrositeService;
import edu.uc.eh.service.PsiModService;
import edu.uc.eh.structures.StringDoubleString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by chojnasm on 11/9/15.
 */

@Controller
public class RestAPI {

    private static final Logger log = LoggerFactory.getLogger(PrositeService.class);

    private final PrositeService prositeService;
    private final PsiModService psiModService;


    @Autowired
    public RestAPI(PrositeService prositeService, PsiModService psiModService) {

        this.prositeService = prositeService;
        this.psiModService = psiModService;
    }

    @RequestMapping(value = "api/prosite/{peptide}", method = RequestMethod.GET)
    public
    @ResponseBody
    String getFromProsite(@PathVariable String peptide) {
        log.info(String.format("Run convertToPLN with argument: %s", peptide));

        return prositeService.getTable(peptide);
    }

    @RequestMapping(value = "api/psimod/{modification}", method = RequestMethod.GET)
    public
    @ResponseBody
    StringDoubleString getFromPsiMod(@PathVariable String modification) {
        log.info(String.format("Get modification identifier: %s", modification));

        return psiModService.getIdentifier(modification);
    }

}
