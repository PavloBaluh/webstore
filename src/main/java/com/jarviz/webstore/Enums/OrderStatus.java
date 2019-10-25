package com.jarviz.webstore.Enums;

import lombok.ToString;

public enum OrderStatus {
    checking("checking"), in_processing("in_processing"), processed("processed");
    private String text;

    OrderStatus(String value) {
        this.text = value;
    }

    @Override
    public String toString() {
        return text;
    }
}
