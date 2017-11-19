package com.lucapruneti.mimosapudica.service;

public enum PredifinedTypeName {

    ORIGINAL("original"),
    THUMBNAIL("thumbnail");

    private final String name;

    PredifinedTypeName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
