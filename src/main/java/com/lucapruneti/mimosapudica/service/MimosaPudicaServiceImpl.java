package com.lucapruneti.mimosapudica.service;

import com.lucapruneti.mimosapudica.repository.MimosaPudicaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class MimosaPudicaServiceImpl implements MimosaPudicaService {

    private Logger LOG = LoggerFactory.getLogger(MimosaPudicaServiceImpl.class);

    @Autowired
    private MimosaPudicaRepository mimosaPudicaRepository;

    @Autowired
    private ImageOptimizer imageOptimizer;

    @Override
    public byte[] show(String predefinedTypeName,
                       String reference) {

        if (LOG.isDebugEnabled()) {
            LOG.debug("predefinedTypeName = '{}' - reference = '{}'", predefinedTypeName, reference);
        }

        //sanity checks
        assert StringUtils.hasText(predefinedTypeName);
        assert StringUtils.hasText(reference);

        PredifinedTypeName predifinedType = PredifinedTypeName.valueOf(predefinedTypeName.toUpperCase());
        assert predifinedType != null;

        byte[] image = null;

        //Look for an already optimized images
        image = mimosaPudicaRepository.findImage(reference, PredifinedTypeName.THUMBNAIL);
        if (image != null) {
            //we found what we look for
            return image;
        }

        image = mimosaPudicaRepository.findImage(reference, PredifinedTypeName.ORIGINAL);
        if (image == null) {
            throw new RuntimeException("Unable to find predefinedTypeName = '"
                    + predefinedTypeName
                    + "' - reference = '"
                    + reference
                    + "'");
        }

        image = imageOptimizer.optimize(image);
        if (image == null) {
            if (LOG.isErrorEnabled()) {
                LOG.error("TODO - Add message here");
            }
            throw new RuntimeException();
        }

        image = mimosaPudicaRepository.saveImage(image, PredifinedTypeName.THUMBNAIL);
        if (image == null) {
            if (LOG.isErrorEnabled()) {
                LOG.error("TODO - Add message here");
            }
            throw new RuntimeException();
        }

        return image;
    }

    @Override
    public boolean flush(String predefinedTypeName,
                         String reference) {
        //sanity checks
        assert StringUtils.hasText(predefinedTypeName);
        assert StringUtils.hasText(reference);

        PredifinedTypeName predifinedType = PredifinedTypeName.valueOf(predefinedTypeName.toUpperCase());
        assert predifinedType != null;

        if (!mimosaPudicaRepository.deleteImage(reference, PredifinedTypeName.THUMBNAIL)) {
            if (LOG.isErrorEnabled()) {
                LOG.error("TODO - Add message here");
            }
            throw new RuntimeException();
        }

        if (predifinedType.equals(PredifinedTypeName.ORIGINAL)) {
            if (!mimosaPudicaRepository.deleteImage(reference, PredifinedTypeName.ORIGINAL)) {
                if (LOG.isErrorEnabled()) {
                    LOG.error("TODO - Add message here");
                }
                throw new RuntimeException();
            }
        }

        return true;
    }
}

