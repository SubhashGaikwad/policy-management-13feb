package com.mycompany.myapp.domain.enumeration;

/**
 * The PremiumMode enumeration.
 */
public enum PremiumMode {
    YEARLY("Yearly"),
    HLY("Halfyear"),
    QLY("Qly"),
    MONTHLY("Monthly"),
    SSS("sss"),
    SINGLE("Single");

    private final String value;

    PremiumMode(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
