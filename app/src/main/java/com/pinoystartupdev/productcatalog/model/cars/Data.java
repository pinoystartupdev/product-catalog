package com.pinoystartupdev.productcatalog.model.cars;

import com.google.gson.annotations.SerializedName;

public class Data {
    @SerializedName("name") String name;
    @SerializedName("price") String price;
    @SerializedName("brand") String brand;
    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public String getBrand() {
        return brand;
    }
}
