package com.jarviz.webstore.tools;

import lombok.ToString;

public enum OrderStatus {
    checking("checking"), inProcessing("in_processing"), processed("processed");
    private String text;

    OrderStatus(String value) {
        this.text = value;
    }

    @Override
    public String toString() {
        return text;
    }
}
