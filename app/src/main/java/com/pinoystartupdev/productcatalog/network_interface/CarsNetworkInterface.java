package com.pinoystartupdev.productcatalog.network_interface;

import com.pinoystartupdev.productcatalog.model.cars.Cars;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CarsNetworkInterface {
    @GET("/api/cars/page:1/maxitems:10/")
    Call<Cars> getCars();
}
