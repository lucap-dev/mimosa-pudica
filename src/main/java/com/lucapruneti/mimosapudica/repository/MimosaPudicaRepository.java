package com.lucapruneti.mimosapudica.repository;

import com.lucapruneti.mimosapudica.service.PredifinedTypeName;

public interface MimosaPudicaRepository {

    byte[] findImage(String resourceName, PredifinedTypeName predifinedTypeName);

    byte[] saveImage(byte[] image, String resourceName, PredifinedTypeName predifinedTypeName);

    boolean deleteImage(String resourceName, PredifinedTypeName predifinedTypeName);

}
