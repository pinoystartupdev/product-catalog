package com.pinoystartupdev.productcatalog.adapter;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.pinoystartupdev.productcatalog.R;
import com.pinoystartupdev.productcatalog.model.cars.Data;
import com.pinoystartupdev.productcatalog.model.cars.DataWrapper;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FeedRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<DataWrapper> dataWrapperList;
    private OnRowClickListener onRowClickListener;

    public FeedRecyclerViewAdapter(Context context, List<DataWrapper> dataWrapperList, OnRowClickListener onRowClickListener) {
        // use context of Application to avoid leaking of activity context
        if (context instanceof Application) {
            this.context = context;
        } else {
            throw new IllegalArgumentException("context instanceof Application is false");
        }

        this.dataWrapperList = dataWrapperList;
        this.onRowClickListener = onRowClickListener;
    }

    public class FeedRecyclerViewViewHolder extends RecyclerView.ViewHolder {
        int position;
        DataWrapper dataWrapper;

        @BindView(R.id.imageViewCoverPhoto)
        ImageView imageViewCoverPhoto;

        @BindView(R.id.textViewProductName)
        TextView textViewProductName;

        @BindView(R.id.textViewProductBrand)
        TextView textViewProductBrand;

        @BindView(R.id.textViewProductPrice)
        TextView textViewProductPrice;


        public FeedRecyclerViewViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }

        public void setDataWrapper(int position, DataWrapper dataWrapper) {
            this.position = position;
            this.dataWrapper = dataWrapper;
        }

        public void display() {
            textViewProductName.setText(dataWrapper.getData().getName());
            textViewProductBrand.setText(dataWrapper.getData().getBrand());
            textViewProductPrice.setText(dataWrapper.getData().getPrice());

            Picasso.get()
                    .load(dataWrapper.getImagesList().get(0).getUrl())
                    .resize(400, 300)
                    .centerCrop()
                    .into(imageViewCoverPhoto);

            // todo, it might be good to display all images in a thumbnail just below the big image
        }

        public void setOnRowClickListener(final OnRowClickListener onRowClickListener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onRowClickListener.OnClickListner(v, position);
                }
            });
        }
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        final View view = layoutInflater.inflate(R.layout.view_holder_feed, parent, false);
        return new FeedRecyclerViewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        FeedRecyclerViewViewHolder feedRecyclerViewViewHolder = (FeedRecyclerViewViewHolder) holder;
        DataWrapper dataWrapper = dataWrapperList.get(position);

        feedRecyclerViewViewHolder.setDataWrapper(position, dataWrapper);

        feedRecyclerViewViewHolder.display();

        feedRecyclerViewViewHolder.setOnRowClickListener(onRowClickListener);
    }

    @Override
    public int getItemCount() {
        return dataWrapperList.size();
    }

    public interface OnRowClickListener {
        void OnClickListner(View view, int position);
    }
}