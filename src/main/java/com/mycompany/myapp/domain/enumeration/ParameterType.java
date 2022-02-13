package com.mycompany.myapp.domain.enumeration;

/**
 * The ParameterType enumeration.
 */
public enum ParameterType {
    MAKE("Make"),
    MODEL("Model"),
    VARIENT("Varient"),
    CC("CubicCapacity"),
    VEHICAL("VehicalType"),
    HEATH("HeathInsurance"),
    MOTAR("MotarInsurance");

    private final String value;

    ParameterType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
