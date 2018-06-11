package com.pinoystartupdev.productcatalog;

import android.graphics.Movie;
import android.os.TokenWatcher;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.pinoystartupdev.productcatalog.model.cars.Cars;
import com.pinoystartupdev.productcatalog.network_interface.APIClient;
import com.pinoystartupdev.productcatalog.network_interface.CarsNetworkInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CarsNetworkInterface carsNetworkInterface = APIClient.getClient().create(CarsNetworkInterface.class);

        Call<Cars> carsCall = carsNetworkInterface.getCars();

        carsCall.enqueue(new Callback<Cars>() {
            @Override
            public void onResponse(Call<Cars> call, Response<Cars> response) {
                Toast.makeText(MainActivity.this, "HELLO WORLD" + response.body().isSuccess(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Cars> call, Throwable t) {
                Toast.makeText(MainActivity.this, "HELLO FAIL WORLD", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
