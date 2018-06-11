package com.pinoystartupdev.productcatalog.model.cars;

import com.google.gson.annotations.SerializedName;

public class Cars {
    @SerializedName("success") boolean success;

    public boolean isSuccess() {
        return success;
    }
}
