package com.pinoystartupdev.productcatalog.network.requests;

import com.pinoystartupdev.productcatalog.model.cars.Cars;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface CarsNetworkInterface {
    @GET("/api/cars/page:{page}/maxitems:{maxitems}/")
    Call<Cars> getCars(@Path("page") int page, @Path("maxitems") int maxItems);
}
