package com.pinoystartupdev.productcatalog.model.cars;

import com.google.gson.annotations.SerializedName;

public class Images {
    @SerializedName("url") String url;

    public String getUrl() {
        return url;
    }
}
