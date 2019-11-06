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
    private List<Business> dataList;
    private Context context;

    public ViewBusinessAdaptor(List<Business> dataList, Context context){
        this.dataList = dataList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewBusinessAdaptor.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.single_business_view, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewBusinessAdaptor.MyViewHolder holder, int position) {

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
        TextInputEditText reviewTitle;
        TextInputEditText reviewDescription;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
