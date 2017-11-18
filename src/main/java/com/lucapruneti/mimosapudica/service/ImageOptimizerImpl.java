package com.lucapruneti.mimosapudica.service;

import org.springframework.stereotype.Component;

@Component
public class ImageOptimizerImpl implements ImageOptimizer {

    @Override
    public byte[] optimize(byte[] bytes) {
        return bytes;
    }
}
