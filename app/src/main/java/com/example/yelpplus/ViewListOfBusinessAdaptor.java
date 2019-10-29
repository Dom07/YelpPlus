package com.example.yelpplus;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ViewListOfBusinessAdaptor extends RecyclerView.Adapter<ViewListOfBusinessAdaptor.MyViewHolder> {
    private List<Business> dataList;
    private Context context;

    public ViewListOfBusinessAdaptor(List<Business> dataList, Context context){
        this.dataList = dataList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewListOfBusinessAdaptor.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.single_business_view, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewListOfBusinessAdaptor.MyViewHolder holder, int position) {

        holder.name.setText(dataList.get(position).getName());
        holder.address.setText(dataList.get(position).getAddress());
        String[] images = dataList.get(position).getPhoto();

        // TODO: make this code better, its hardcoded
        if(images.length >0) {
            Picasso.Builder builder = new Picasso.Builder(context);
            builder.downloader(new OkHttp3Downloader(context));
            builder.build().load(images[0])
                    .placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.ic_launcher_background)
                    .into(holder.image_view_one);

            builder.build().load(images[1])
                    .placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.ic_launcher_background)
                    .into(holder.image_view_two);

            builder.build().load(images[2])
                    .placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.ic_launcher_background)
                    .into(holder.image_view_three);

            builder.build().load(images[3])
                    .placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.ic_launcher_background)
                    .into(holder.image_view_four);
        }

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView name;
        TextView phone_number;
        TextView address;
        ImageView image_view_one;
        ImageView image_view_two;
        ImageView image_view_three;
        ImageView image_view_four;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.business_title);
            address = itemView.findViewById(R.id.business_address);
            image_view_one = itemView.findViewById(R.id.business_image_one);
            image_view_two = itemView.findViewById(R.id.business_image_two);
            image_view_three = itemView.findViewById(R.id.business_image_three);
            image_view_four = itemView.findViewById(R.id.business_image_four);

        }
    }
}