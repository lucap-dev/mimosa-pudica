package com.lucapruneti.mimosapudica.repository;

import com.lucapruneti.mimosapudica.service.PredifinedTypeName;

public interface MimosaPudicaRepository {

    byte[] findImage(String resourceId, PredifinedTypeName predifinedTypeName);

    byte[] saveImage(byte[] image, PredifinedTypeName predifinedTypeName);

    boolean deleteImage(String resourceId, PredifinedTypeName predifinedTypeName);

}
