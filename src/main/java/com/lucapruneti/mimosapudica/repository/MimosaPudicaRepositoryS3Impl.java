package com.lucapruneti.mimosapudica.repository;

import com.google.common.io.ByteStreams;
import com.lucapruneti.mimosapudica.service.PredifinedTypeName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.*;

@Repository
public class MimosaPudicaRepositoryS3Impl implements MimosaPudicaRepository {

    //endpoint to the S3 bucket to store the images in.
    @Value("${aws-s3-endpoint}")
    private String awsS3Endpoint;

    //the access key for the S3 bucket
    @Value("${aws-accesskey}")
    private String awsAccesskey;

    //the secret key for the S3 bucket
    @Value("${aws-secretkey}")
    private String awsSecretkey;

    private Logger LOG = LoggerFactory.getLogger(MimosaPudicaRepositoryS3Impl.class);

    @Override
    public byte[] findImage(String resourceId,
                            PredifinedTypeName predifinedTypeName) {

        //<predefined-type-name>/<first-4-chars>/<second-4-chars>/<unique-original-image-file-name>

        resourceId = "src/test/resources/images/test.jpg";

        byte[] bytes = null;
        InputStream inputStream = null;

        try {
            inputStream = new FileInputStream(new File(resourceId));
        } catch (FileNotFoundException ex) {
            if (LOG.isErrorEnabled()) {
                LOG.error("Not found: resourceId '{}'", resourceId);
            }
        }

        if (inputStream != null) {
            try {
                bytes = ByteStreams.toByteArray(inputStream);
            } catch (IOException ex) {
                if (LOG.isErrorEnabled()) {
                    LOG.error("Loading resourceId '{}'", resourceId);
                }
            }
        }

        return bytes;
    }

    @Override
    public byte[] saveImage(byte[] image, PredifinedTypeName predifinedTypeName) {
        return image;
    }

    @Override
    public boolean deleteImage(String resourceId,
                               PredifinedTypeName predifinedTypeName) {

        return true;
    }
}
