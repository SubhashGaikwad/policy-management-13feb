package com.mycompany.myapp.domain.enumeration;

/**
 * The Zone enumeration.
 */
public enum Zone {
    A,
    B,
    C;

    private final String value;

    Zone(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
