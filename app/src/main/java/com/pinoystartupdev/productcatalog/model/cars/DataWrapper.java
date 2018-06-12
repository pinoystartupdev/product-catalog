package com.pinoystartupdev.productcatalog.model.cars;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DataWrapper {
    @SerializedName("data") Data data;
    @SerializedName("id") String id;
    @SerializedName("images") List<Images> imagesList;

    public Data getData() {
        return data;
    }

    public String getId() {
        return id;
    }

    public List<Images> getImagesList() {
        return imagesList;
    }
}
