package com.lucapruneti.mimosapudica.web;

import com.google.common.io.ByteStreams;
import com.lucapruneti.mimosapudica.service.MimosaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.support.ServletContextResource;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@RestController
public class MimosaPudicaController {

    @Autowired
    private MimosaService mimosaService;

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
                                       @RequestParam("reference") String reference) throws IOException {

        byte[] bytes = mimosaService.show(predefinedTypeName, reference);

        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);
        headers.setCacheControl(CacheControl.noCache().getHeaderValue());

        return new ResponseEntity<byte[]>(bytes, headers, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/image/show/{predefinedTypeName}",
            params = "reference",
            method = RequestMethod.DELETE)
    public ResponseEntity show(@PathVariable String predefinedTypeName,
                               @RequestParam("reference") String reference) throws IOException {

        return new ResponseEntity(HttpStatus.OK);
    }

}
