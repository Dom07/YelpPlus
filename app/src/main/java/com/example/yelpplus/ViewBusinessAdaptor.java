package com.example.yelpplus;

import android.content.Context;
import android.media.Rating;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

public class ViewBusinessAdaptor extends RecyclerView.Adapter<ViewBusinessAdaptor.MyViewHolder> {
    private List<Reviews_profile> dataList;
    private Context context;

    public ViewBusinessAdaptor(List<Reviews_profile> dataList, Context context){
        this.dataList = dataList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewBusinessAdaptor.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.single_review_view_profile, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewBusinessAdaptor.MyViewHolder holder, int position) {
        holder.name.setText(dataList.get(position).getAuthor());
        holder.serviceRating.setRating(dataList.get(position).getService());
        holder.productRating.setRating(dataList.get(position).getProduct());
        holder.ambienceRating.setRating(dataList.get(position).getAmbience());
        holder.reviewTitle.setText(dataList.get(position).getTitle());
        holder.reviewDescription.setText(dataList.get(position).getDescription());

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView name;
        RatingBar serviceRating;
        RatingBar productRating;
        RatingBar ambienceRating;
        TextView reviewTitle;
        TextView reviewDescription;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tv_business_name);
            serviceRating = itemView.findViewById(R.id.ratingBarServiceReview);
            productRating = itemView.findViewById(R.id.ratingBarProductReview);
            ambienceRating = itemView.findViewById(R.id.ratingBarAmbienceReview);
            reviewTitle = itemView.findViewById(R.id.tv_review_title);
            reviewDescription = itemView.findViewById(R.id.tv_review_description);
        }
    }
}