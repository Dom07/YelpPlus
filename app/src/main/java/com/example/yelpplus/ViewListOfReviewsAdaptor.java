package com.example.yelpplus;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ViewListOfReviewsAdaptor extends RecyclerView.Adapter<ViewListOfReviewsAdaptor.MyViewHolder> {
    private List<Reviews> dataList;
    private Context context;

    public ViewListOfReviewsAdaptor(List<Reviews> dataList, Context context){
        this.dataList = dataList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewListOfReviewsAdaptor.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.single_review_view, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewListOfReviewsAdaptor.MyViewHolder holder, int position) {

        holder.username.setText(dataList.get(position).getUsername());
        holder.business_name.setText(dataList.get(position).getBusiness_name());
        holder.product.setRating(dataList.get(position).getProduct());
        holder.service.setRating(dataList.get(position).getService());
        holder.ambience.setRating(dataList.get(position).getAmbience());
        holder.reviews.setText(dataList.get(position).getReviews());
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView username;
        TextView business_name;
        RatingBar product;
        RatingBar service;
        RatingBar ambience;
        TextView reviews;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.username);
            service = itemView.findViewById(R.id.ratingBarServiceReview);
            product = itemView.findViewById(R.id.ratingBarProductReview);
            ambience = itemView.findViewById(R.id.ratingBarAmbienceReview);
            ambience = itemView.findViewById(R.id.reviewDescription);
        }
    }
}