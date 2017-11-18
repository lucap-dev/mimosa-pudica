package com.lucapruneti.mimosapudica.service;

import java.io.IOException;

public interface MimosaService {

    byte[] show(String predefinedTypeName,
                String reference) throws IOException;

    boolean flush(String predefinedTypeName,
                  String reference);

}
