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
    private List<Reviews_profile> dataList;
    private Context context;

    public ViewListOfReviewsAdaptor(List<Reviews_profile> dataList, Context context){
        this.dataList = dataList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewListOfReviewsAdaptor.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.single_review_view_profile, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewListOfReviewsAdaptor.MyViewHolder holder, int position) {
        holder.tv_review_title.setText(dataList.get(position).getTitle());
        holder.tv_description.setText(dataList.get(position).getDescription());
        holder.product_rating.setRating(dataList.get(position).getProduct());
        holder.service_rating.setRating(dataList.get(position).getService());
        holder.ambience_rating.setRating(dataList.get(position).getAmbience());
//        holder.tv_business_name.setText(dataList.get(position).getBusiness().getName());
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tv_review_title;
        TextView business_name;
        RatingBar product_rating;
        RatingBar service_rating;
        RatingBar ambience_rating;
        TextView tv_description;
        TextView tv_business_name;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_review_title = itemView.findViewById(R.id.tv_review_title);
            tv_description = itemView.findViewById(R.id.tv_review_description);
            product_rating = itemView.findViewById(R.id.ratingBarProductReview);
            service_rating = itemView.findViewById(R.id.ratingBarServiceReview);
            ambience_rating = itemView.findViewById(R.id.ratingBarAmbienceReview);
            tv_business_name = itemView.findViewById(R.id.tv_business_name);
        }
    }
}