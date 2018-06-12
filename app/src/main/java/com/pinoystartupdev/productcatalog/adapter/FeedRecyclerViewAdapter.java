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
    Context context;
    List<DataWrapper> dataWrapperList;

    public FeedRecyclerViewAdapter(Context context,  List<DataWrapper> dataWrapperList) {
        // use context of Application to avoid leaking of activity context
        if (context instanceof Application) {
            this.context = context;
        } else {
            throw new IllegalArgumentException("context instanceof Application is false");
        }

        this.dataWrapperList = dataWrapperList;
    }

    public class FeedRecyclerViewViewHolder extends RecyclerView.ViewHolder {
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

        public void setDataWrapper(DataWrapper dataWrapper) {
            this.dataWrapper = dataWrapper;
        }

        public void display() {
            textViewProductName.setText(dataWrapper.getData().getName());
            textViewProductBrand.setText(dataWrapper.getData().getBrand());
            textViewProductPrice.setText(dataWrapper.getData().getPrice());

//            if (onTimelineClickListener != null) {
//                textViewProductName.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        onProductClick(view, userPOJO, productPOJO);
//                    }
//                });
//            }
//
            Picasso.get()
                    .load(dataWrapper.getImagesList().get(0).getUrl())
                    .resize(400, 300)
                    .centerCrop()
                    .into(imageViewCoverPhoto);
//
//            if (onTimelineClickListener != null) {
//                imageViewCoverPhoto.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        onProductClick(view, userPOJO, productPOJO);
//                    }
//                });
//            }
//
//            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
//            ThumbnailRecyclerViewAdapter thumbnailRecyclerViewAdapter = new ThumbnailRecyclerViewAdapter(context, productPOJO.getImageResIdList());
//
//            if (onTimelineClickListener != null) {
//                thumbnailRecyclerViewAdapter.setThumbnailCallback(this);
//            }
//
//            recyclerViewThumbnails.setLayoutManager(linearLayoutManager);
//            recyclerViewThumbnails.setAdapter(thumbnailRecyclerViewAdapter);

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

        feedRecyclerViewViewHolder.setDataWrapper(dataWrapper);

        feedRecyclerViewViewHolder.display();
    }

    @Override
    public int getItemCount() {
        return dataWrapperList.size();
    }
}