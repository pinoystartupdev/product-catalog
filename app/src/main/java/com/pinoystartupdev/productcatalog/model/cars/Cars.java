package com.pinoystartupdev.productcatalog.model.cars;

import com.google.gson.annotations.SerializedName;

public class Cars {
    @SerializedName("success") boolean success;
    @SerializedName("metadata") Metadata metadata;

    public boolean isSuccess() {
        return success;
    }

    public Metadata getMetadata() {
        return metadata;
    }

//    class Metadata {
//        @SerializedName("product_count") int productCount;
//
//        public int getProductCount() {
//            return productCount;
//        }
//    }
}
