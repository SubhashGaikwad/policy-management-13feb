package com.mycompany.myapp.domain.enumeration;

/**
 * The PolicyStatus enumeration.
 */
public enum PolicyStatus {
    OPEN("Open"),
    INFORCE("inforce"),
    CANCELLED("Cancelled"),
    CLOSED("Closed"),
    MATURED("Matured"),
    SUSPENDED("Suspended");

    private final String value;

    PolicyStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
