package com.example.mysupermarket;

import java.io.Serializable;

public class Item implements Serializable {

    private String type, url;
    private Integer price;

    public Item(String type, String url, Integer price) {
        this.type = type;
        this.url = url;
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPrice() {
        return String.valueOf(price);
    }

    public void setPrice(Integer price) {
        this.price = price;
    }
}
