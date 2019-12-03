package com.example.yelpplus;

import android.content.Context;
import android.media.Rating;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

public class ViewBusinessAdaptor extends RecyclerView.Adapter<ViewBusinessAdaptor.MyViewHolder> {
    private List<Reviews_profile> dataList;
    private Context context;
    private String businessID;

    public ViewBusinessAdaptor(List<Reviews_profile> dataList, Context context, String business_id){
        this.dataList = dataList;
        this.context = context;
        this.businessID = business_id;
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
        final String name = dataList.get(position).getAuthor();
        final String userID = dataList.get(position).getUserID();

        holder.reviewCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle args = new Bundle();
                args.putBoolean("claimed", true);
                args.putBoolean("registered", true);
                args.putBoolean("follow_reviewer", true);
                args.putString("business_id", businessID);
                args.putString("name",name);
                args.putString("reviewer_id", userID);

                AppCompatActivity activity = (AppCompatActivity)view.getContext();

                FragmentConfirmAction fragment = new FragmentConfirmAction();
                fragment.setArguments(args);
                activity.getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frame_layout_home, fragment)
                        .commit();
            }
        });
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
        CardView reviewCard;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tv_business_name);
            serviceRating = itemView.findViewById(R.id.ratingBarServiceReview);
            productRating = itemView.findViewById(R.id.ratingBarProductReview);
            ambienceRating = itemView.findViewById(R.id.ratingBarAmbienceReview);
            reviewTitle = itemView.findViewById(R.id.tv_review_title);
            reviewDescription = itemView.findViewById(R.id.tv_review_description);
            reviewCard = itemView.findViewById(R.id.reviewCard);
        }
    }
}
