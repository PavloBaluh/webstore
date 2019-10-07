package com.jarviz.webstore.tools;

public enum PayType {
    Value1("Cash"), Value2("Paypal");
    private String text;

    PayType(String val) {
        this.text = val;
    }

    @Override
    public String toString() {
        return text;
    }
}
