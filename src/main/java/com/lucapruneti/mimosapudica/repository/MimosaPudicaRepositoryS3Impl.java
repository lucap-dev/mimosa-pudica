package com.lucapruneti.mimosapudica.repository;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.google.common.base.Splitter;
import com.google.common.io.ByteStreams;
import com.lucapruneti.mimosapudica.service.PredifinedTypeName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.io.*;

@Repository
public class MimosaPudicaRepositoryS3Impl implements MimosaPudicaRepository {

    private Logger LOG = LoggerFactory.getLogger(MimosaPudicaRepositoryS3Impl.class);

    //endpoint to the S3 bucket to store the images in.
    @Value("${aws-s3-endpoint}")
    private String awsS3Endpoint;

    //the access key for the S3 bucket
    @Value("${aws-accesskey}")
    private String awsAccesskey;

    //the secret key for the S3 bucket
    @Value("${aws-secretkey}")
    private String awsSecretkey;

    private AmazonS3Client amazonS3Client;

    @PostConstruct
    public void init() {

        AWSCredentials credentials = null;

        try {
            credentials = new BasicAWSCredentials(awsAccesskey, awsSecretkey);
        } catch (IllegalArgumentException ex) {
            String message = ex.getMessage();
            if (LOG.isErrorEnabled()) {
                LOG.error(message);
            }
            throw new RuntimeException(message);
        }

        AmazonS3 s3client = AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(Regions.DEFAULT_REGION)
                .build();

        if (!StringUtils.hasText(awsS3Endpoint)) {
            String message = "awsS3Endpoint PARAM cannot be NULL";
            if (LOG.isErrorEnabled()) {
                LOG.error(message);
            }
            throw new RuntimeException(message);
        }
    }

    //AWS S3 directory strategy
    //~/<predefined-type-name>/<first-4-chars>/<second-4-chars>/<unique-original-image-file-name>
    private String getAwsS3FilePath(String resourceName,
                                    PredifinedTypeName predifinedTypeName) {

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(predifinedTypeName.getName());
        stringBuilder.append("/");

        File file = new File(resourceName);
        Iterable<String> tokens = Splitter.fixedLength(4).split(file.getName());
        int counter = 0;
        for (String token : tokens) {
            counter++;
            if (counter <= 2 && token.length() >= 4) {
                stringBuilder.append(token);
                stringBuilder.append("/");
            } else {
                break;
            }
        }

        stringBuilder.append(resourceName);

        return stringBuilder.toString();
    }

    @Override
    public byte[] findImage(String resourceName,
                            PredifinedTypeName predifinedTypeName) {

        //<predefined-type-name>/<first-4-chars>/<second-4-chars>/<unique-original-image-file-name>


        return mock();
    }

    @Override
    public byte[] saveImage(byte[] image,
                            String resourceName,
                            PredifinedTypeName predifinedTypeName) {

        InputStream inputStream = new ByteArrayInputStream(image);
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(Long.valueOf(image.length));

        try {
            PutObjectResult putObjectResult = amazonS3Client.putObject(awsS3Endpoint,
                    getAwsS3FilePath(resourceName, predifinedTypeName),
                    inputStream,
                    objectMetadata);
        } catch (AmazonServiceException ase) {
        } catch (AmazonClientException ece) {
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException ex) {
            }

        }


        return image;
    }

    @Override
    public boolean deleteImage(String resourceName,
                               PredifinedTypeName predifinedTypeName) {

        return true;
    }

    private byte[] mock() {
        String resourceId = "src/test/resources/images/test.jpg";

        byte[] image = null;
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
                image = ByteStreams.toByteArray(inputStream);
            } catch (IOException ex) {
                if (LOG.isErrorEnabled()) {
                    LOG.error("Loading resourceId '{}'", resourceId);
                }
            }
        }

        return image;
    }

}
