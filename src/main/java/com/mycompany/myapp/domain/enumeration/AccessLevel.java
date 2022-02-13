package com.mycompany.myapp.domain.enumeration;

/**
 * The AccessLevel enumeration.
 */
public enum AccessLevel {
    ADMIN("Admin"),
    AGENT("Agent"),
    CUSTOMER("Customer");

    private final String value;

    AccessLevel(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
