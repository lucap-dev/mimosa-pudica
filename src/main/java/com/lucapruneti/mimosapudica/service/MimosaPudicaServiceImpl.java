package com.lucapruneti.mimosapudica.service;

import com.lucapruneti.mimosapudica.repository.MimosaPudicaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MimosaPudicaServiceImpl implements MimosaPudicaService {

    private Logger LOG = LoggerFactory.getLogger(MimosaPudicaServiceImpl.class);

    @Autowired
    private MimosaPudicaRepository mimosaPudicaRepository;

    @Override
    public byte[] show(String predefinedTypeName,
                       String reference) {

        byte[] bytes = mimosaPudicaRepository.findOriginalImage("src/test/resources/images/test.jpg");

        if (bytes == null) {
            throw new RuntimeException("Unable to find predefinedTypeName = '"
                    + predefinedTypeName
                    + "' - reference = '"
                    + reference
                    + "'");
        }

        return bytes;
    }

    @Override
    public boolean flush(String predefinedTypeName,
                         String reference) {
        return false;
    }
}
