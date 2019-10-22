package com.jarviz.webstore.Enums;

public enum PayType {
    Cash("Cash"), Paypal("Paypal");
    private String text;

    PayType(String val) {
        this.text = val;
    }

    @Override
    public String toString() {
        return text;
    }
}
