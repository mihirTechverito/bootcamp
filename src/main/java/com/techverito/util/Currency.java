package com.techverito.util;

public enum Currency {

    INR(1),
    USD(70),
    GBP(140);

    public final double conversionValue;

    Currency(double conversionValue) {
        this.conversionValue = conversionValue;
    }
}
