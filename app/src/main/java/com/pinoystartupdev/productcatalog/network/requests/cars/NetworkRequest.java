package com.pinoystartupdev.productcatalog.network.requests.cars;

import com.pinoystartupdev.productcatalog.model.cars.Cars;
import com.pinoystartupdev.productcatalog.network.APIClient;
import com.pinoystartupdev.productcatalog.network.requests.CarsNetworkInterface;

import retrofit2.Call;
import retrofit2.Callback;

public class NetworkRequest {
    public void requestCarsAPI(int currentPage, Callback<Cars> carsCallback) {
        int maxItem = 5;

        CarsNetworkInterface carsNetworkInterface = APIClient.getClient().create(CarsNetworkInterface.class);

        Call<Cars> carsCall = carsNetworkInterface.getCars(currentPage, maxItem);

        carsCall.enqueue(carsCallback);
    }
}
