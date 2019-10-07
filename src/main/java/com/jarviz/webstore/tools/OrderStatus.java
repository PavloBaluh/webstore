package com.jarviz.webstore.tools;

import lombok.ToString;

public enum OrderStatus {
    Value1("checking"), Value2("in_processing"), Value3("processed");
    private String text;

    OrderStatus(String value) {
        this.text = value;
    }

    @Override
    public String toString() {
        return text;
    }
}
