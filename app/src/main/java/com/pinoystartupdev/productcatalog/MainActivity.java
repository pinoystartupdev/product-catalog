package com.pinoystartupdev.productcatalog;

import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.pinoystartupdev.productcatalog.adapter.FeedRecyclerViewAdapter;
import com.pinoystartupdev.productcatalog.model.cars.Cars;
import com.pinoystartupdev.productcatalog.model.cars.DataWrapper;
import com.pinoystartupdev.productcatalog.network.APIClient;
import com.pinoystartupdev.productcatalog.network.requests.CarsNetworkInterface;
import com.pinoystartupdev.productcatalog.network.InfiniteScrollInterface;
import com.pinoystartupdev.productcatalog.network.requests.cars.NetworkRequest;
import com.pinoystartupdev.productcatalog.shared_prefrence.MySharedPreferenceHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements InfiniteScrollInterface<Cars>{
    public static final String CURRENT_DATA_WRAPPER_LIST_KEY = "com.pinoystartupdev.MainActivity.CURRENT_DATA_WRAPPER_LIST_KEY";
    public static final String CURRENT_PAGE_KEY = "com.pinoystartupdev.MainActivity.CURRENT_PAGE_KEY";

    @BindView(R.id.toolbarMainActivity)
    Toolbar toolbarMainActivity;

    @BindView(R.id.swiptRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.recyclerViewFeed)
    RecyclerView recyclerViewFeed;

    MyCarsCallback myCarsCallback;

    List<DataWrapper> dataWrapperList;

    int currentPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        // initialize fields here
        dataWrapperList = new ArrayList<>();
        myCarsCallback = new MyCarsCallback();
        currentPage = 1;

        // set up tool bar here
        setSupportActionBar(toolbarMainActivity);
        getSupportActionBar().setTitle("Product Catalog");
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        // set up adapter for recycleview here
        FeedRecyclerViewAdapter feedRecyclerViewAdapter = new FeedRecyclerViewAdapter(getApplicationContext(), dataWrapperList);

        // set up recycler view here
        recyclerViewFeed.setAdapter(feedRecyclerViewAdapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerViewFeed.setLayoutManager(linearLayoutManager);

        // add infinite scroll listener
        recyclerViewFeed.addOnScrollListener(new MyInfiniteScrollListener (this));

        // set up pull to refresh here
        swipeRefreshLayout.setOnRefreshListener(new MySwipeRefressListener());

        // check if has savedInstance state, then use it if it's available
        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(CURRENT_DATA_WRAPPER_LIST_KEY)) {
                String serializedResponseFromBundle = savedInstanceState.getString(CURRENT_DATA_WRAPPER_LIST_KEY);
                this.currentPage = savedInstanceState.getInt(CURRENT_PAGE_KEY);

                DataWrapper[] dataWrappers = new Gson().fromJson(serializedResponseFromBundle, DataWrapper[].class);

                List<DataWrapper> dataWrapperListFromBundle = Arrays.asList(dataWrappers);

                dataWrapperList.addAll(dataWrapperListFromBundle);

                recyclerViewFeed.getAdapter().notifyDataSetChanged();
            } else {
                // perform network request, then update dataWrapperList field and recyclerViewFeed
                new NetworkRequest().requestCarsAPI(currentPage, new MyCarsCallback());
            }
        } else {
            // perform network request, then update dataWrapperList field and recyclerViewFeed
            new NetworkRequest().requestCarsAPI(currentPage, new MyCarsCallback());
        }
    }

    @Override
    protected void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);

        // Save current dataWrapperList
        outState.putString(CURRENT_DATA_WRAPPER_LIST_KEY, String.valueOf(new Gson().toJson(dataWrapperList)));

        // Save currentPage
        outState.putInt(CURRENT_PAGE_KEY, currentPage);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_toolbar_main_activity, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        int id = item.getItemId();

        switch (id) {
            case R.id.actionFilter:
                Toast.makeText(MainActivity.this, "goto Filter Screen", Toast.LENGTH_SHORT).show();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /*
    * there seems duplicity at class MyInfiniteScrollListener..., class MyCarsCallback..., class MySwipeRefressListener..., re handling of network request.
    * May I suggest having a middle man that handles the incoming/outgoing data and an interface for it so that the UI can respond to these events.
    * */

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

                    // serialize the response, then save via SharedPreference for offline functionality
                    MySharedPreferenceHandler.saveSharedSetting(getApplicationContext(), MySharedPreferenceHandler.MainFeedSharedPreference.SHARED_PREFERENCE_KEY_MAIN_FEED, String.valueOf(new Gson().toJson(response.body())));

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
            // serialize the response, then save via SharedPreference for offline functionality

            MySharedPreferenceHandler.saveSharedSetting(getApplicationContext(), MySharedPreferenceHandler.MainFeedSharedPreference.SHARED_PREFERENCE_KEY_MAIN_FEED, String.valueOf(new Gson().toJson(response.body())));

            dataWrapperList.addAll(response.body().getMetadata().getDataWrapperList());

            recyclerViewFeed.getAdapter().notifyDataSetChanged();
        }

        @Override
        public void onFailure(Call<Cars> call, Throwable t) {
            // check if SharedPreference has data, then use that data if it's available

            if (MySharedPreferenceHandler.hasPreference(getApplicationContext(), MySharedPreferenceHandler.MainFeedSharedPreference.SHARED_PREFERENCE_KEY_MAIN_FEED)) {
                String serializedResponseFromSharedPreference = MySharedPreferenceHandler.readSharedSetting(getApplicationContext(), MySharedPreferenceHandler.MainFeedSharedPreference.SHARED_PREFERENCE_KEY_MAIN_FEED, null);

                Cars cars = new Gson().fromJson(serializedResponseFromSharedPreference, Cars.class);

                dataWrapperList.addAll(cars.getMetadata().getDataWrapperList());

                recyclerViewFeed.getAdapter().notifyDataSetChanged();
            } else {
                //TODO handle failed response here
            }
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

                        // last item reached, fire beforeLoadingNextItems()

                        infiniteScrollInterface.beforeLoadingNextItems();

                        // perform operation (network request)

                        new NetworkRequest().requestCarsAPI(
                                currentPage,
                                new Callback<Cars>() {
                                    @Override
                                    public void onResponse(Call<Cars> call, Response<Cars> response) {
                                        //fire successLoadingNextItems(T) if request was a success

                                        infiniteScrollInterface.successLoadingNextItems(response.body());

                                        loading = true;
                                    }

                                    @Override
                                    public void onFailure(Call<Cars> call, Throwable t) {
                                        //fire failLoadingNextItems() if request was a failure

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
