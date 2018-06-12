package com.pinoystartupdev.productcatalog.model.cars;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Metadata {
    @SerializedName("product_count") int productCount;
    @SerializedName("results") List<DataWrapper> dataWrapperList;

    public int getProductCount() {
        return productCount;
    }

    public List<DataWrapper> getDataWrapperList() {
        return dataWrapperList;
    }
}
