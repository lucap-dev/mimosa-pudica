package com.lucapruneti.mimosapudica.repository;

public interface MimosaPudicaRepository {

    byte[] findOriginalImage(String resourceId);

    byte[] findOptimizedImage(String resourceId);

    boolean deleteOriginalImage(String resourceId);

    boolean deleteOptimizedImage(String resourceId);

}
