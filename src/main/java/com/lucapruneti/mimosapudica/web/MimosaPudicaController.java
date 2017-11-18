package com.lucapruneti.mimosapudica.web;

import com.lucapruneti.mimosapudica.service.MimosaPudicaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
public class MimosaPudicaController {

    @Autowired
    private MimosaPudicaService mimosaPudicaService;

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(value = Exception.class)
    public String handleException(Exception e) {
        return e.getMessage();
    }

    @RequestMapping(value = "/image/show/{predefinedTypeName}/{dummySeoName}",
            params = "reference",
            method = RequestMethod.GET)
    public ResponseEntity<byte[]> show(@PathVariable String predefinedTypeName,
                                       @PathVariable String dummySeoName,
                                       @RequestParam("reference") String reference) {

        byte[] bytes = mimosaPudicaService.show(predefinedTypeName, reference);

        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);
        headers.setCacheControl(CacheControl.noCache().getHeaderValue());

        return new ResponseEntity<byte[]>(bytes, headers, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/image/show/{predefinedTypeName}",
            params = "reference",
            method = RequestMethod.DELETE)
    public ResponseEntity show(@PathVariable String predefinedTypeName,
                               @RequestParam("reference") String reference) {

        mimosaPudicaService.flush(predefinedTypeName, reference);

        return new ResponseEntity(HttpStatus.OK);
    }

}
