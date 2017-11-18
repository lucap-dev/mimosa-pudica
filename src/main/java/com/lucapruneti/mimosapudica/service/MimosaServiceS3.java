package com.lucapruneti.mimosapudica.service;

import com.google.common.io.ByteStreams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.*;

@Service
public class MimosaServiceS3 implements MimosaService {

    private Logger LOG = LoggerFactory.getLogger(MimosaServiceS3.class);

    @Override
    public byte[] show(String predefinedTypeName,
                       String reference) {

        byte[] bytes = null;
        InputStream inputStream = null;

        try {
            inputStream = new FileInputStream(new File("src/test/resources/images/test.jpg"));
        } catch (FileNotFoundException ex) {
            if (LOG.isErrorEnabled()) {
                LOG.error("Not found: resource '{}' reference = '{}'", predefinedTypeName, reference);
            }
        }

        if (inputStream != null) {
            try {
                bytes = ByteStreams.toByteArray(inputStream);
            } catch (IOException ex) {
                if (LOG.isErrorEnabled()) {
                    LOG.error("Loading resource '{}' reference = '{}'", predefinedTypeName, reference);
                }
            }
        }

        return bytes;
    }

    @Override
    public boolean flush(String predefinedTypeName,
                         String reference) {
        return false;
    }
}
