package com.mycompany.myapp.domain.enumeration;

/**
 * The PolicyType enumeration.
 */
public enum PolicyType {
    LIFE("Life"),
    HEATH("Health"),
    MOTAR("Motar"),
    OTHER("Other");

    private final String value;

    PolicyType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
