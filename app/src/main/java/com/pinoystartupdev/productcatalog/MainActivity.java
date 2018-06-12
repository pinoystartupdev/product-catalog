package com.pinoystartupdev.productcatalog;

import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.pinoystartupdev.productcatalog.adapter.FeedRecyclerViewAdapter;
import com.pinoystartupdev.productcatalog.model.cars.Cars;
import com.pinoystartupdev.productcatalog.model.cars.DataWrapper;
import com.pinoystartupdev.productcatalog.network.APIClient;
import com.pinoystartupdev.productcatalog.network.requests.CarsNetworkInterface;
import com.pinoystartupdev.productcatalog.network.InfiniteScrollInterface;
import com.pinoystartupdev.productcatalog.network.requests.cars.NetworkRequest;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements InfiniteScrollInterface<Cars>{
    @BindView(R.id.swiptRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.recyclerViewFeed)
    RecyclerView recyclerViewFeed;

    MyCarsCallback myCarsCallback;

    List<DataWrapper> dataWrapperList;

    int currentPage = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        dataWrapperList = new ArrayList<>();
        myCarsCallback = new MyCarsCallback();

        FeedRecyclerViewAdapter feedRecyclerViewAdapter = new FeedRecyclerViewAdapter(getApplicationContext(), dataWrapperList);
        recyclerViewFeed.setAdapter(feedRecyclerViewAdapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerViewFeed.setLayoutManager(linearLayoutManager);

        recyclerViewFeed.addOnScrollListener(new MyInfiniteScrollListener (this));

        new NetworkRequest().requestCarsAPI(currentPage, new MyCarsCallback());

        swipeRefreshLayout.setOnRefreshListener(new MySwipeRefressListener());
    }

    @Override
    public void beforeLoadingNextItems() {
        currentPage ++;
    }

    @Override
    public void successLoadingNextItems(Cars response) {
        dataWrapperList.addAll(response.getMetadata().getDataWrapperList());

        recyclerViewFeed.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void failLoadingNextItems() {
        //TODO handle failed response here
    }

    class MySwipeRefressListener implements SwipeRefreshLayout.OnRefreshListener {

        @Override
        public void onRefresh() {
            currentPage = 1;

            new NetworkRequest().requestCarsAPI(currentPage, new Callback<Cars>() {
                @Override
                public void onResponse(Call<Cars> call, Response<Cars> response) {
                    swipeRefreshLayout.setRefreshing(false);

                    dataWrapperList.addAll(response.body().getMetadata().getDataWrapperList());

                    recyclerViewFeed.getAdapter().notifyDataSetChanged();
                }

                @Override
                public void onFailure(Call<Cars> call, Throwable t) {
                    //TODO handle failed response here
                }
            });
        }
    }

    class MyCarsCallback implements Callback<Cars> {
        @Override
        public void onResponse(Call<Cars> call, Response<Cars> response) {
            dataWrapperList.addAll(response.body().getMetadata().getDataWrapperList());

            recyclerViewFeed.getAdapter().notifyDataSetChanged();
        }

        @Override
        public void onFailure(Call<Cars> call, Throwable t) {
            //TODO handle failed response here
        }
    }

    class MyInfiniteScrollListener extends RecyclerView.OnScrollListener {
        InfiniteScrollInterface<Cars> infiniteScrollInterface;

        private boolean loading = true;
        int pastVisiblesItems, visibleItemCount, totalItemCount;

        public MyInfiniteScrollListener(InfiniteScrollInterface<Cars> infiniteScrollInterface) {
            this.infiniteScrollInterface = infiniteScrollInterface;
        }

        @Override
        public void onScrolled(final RecyclerView recyclerView, int dx, int dy)
        {
            if(dy > 0) //check for scroll down
            {
                visibleItemCount = recyclerView.getLayoutManager().getChildCount();
                totalItemCount = recyclerView.getLayoutManager().getItemCount();
                pastVisiblesItems = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();

                if (loading)
                {
                    if ( (visibleItemCount + pastVisiblesItems) >= totalItemCount)
                    {
                        loading = false;

                        infiniteScrollInterface.beforeLoadingNextItems();

                        new NetworkRequest().requestCarsAPI(
                                currentPage,
                                new Callback<Cars>() {
                                    @Override
                                    public void onResponse(Call<Cars> call, Response<Cars> response) {
                                        infiniteScrollInterface.successLoadingNextItems(response.body());

                                        loading = true;
                                    }

                                    @Override
                                    public void onFailure(Call<Cars> call, Throwable t) {
                                        infiniteScrollInterface.failLoadingNextItems();
                                    }
                                }
                        );
                    }
                }
            }
        }
    }
}
