package com.example.yelpplus;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CategoryListAdaptor extends RecyclerView.Adapter<CategoryListAdaptor.MyViewHolder> {
    private List<Category> dataList;
    private Context context;

    public CategoryListAdaptor(List<Category> dataList, Context context){
        this.dataList = dataList;
        this.context = context;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.single_category_view, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.category_name.setText(dataList.get(position).getName());
        Picasso.Builder builder= new Picasso.Builder(context);
        builder.downloader(new OkHttp3Downloader(context));
        builder.build().load(dataList.get(position).getImage())
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)
                .into(holder.category_image);

        // TODO: Replace this with code to click on the whole card instead of just the button
        holder.category_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity activity = (AppCompatActivity)view.getContext();
                Fragment fragment = new Fragment_Business_List();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout_home, fragment).addToBackStack(null).commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class MyViewHolder extends  RecyclerView.ViewHolder{
        ImageView category_image;
        TextView category_name;
        public MyViewHolder(@NonNull View itemView){
            super(itemView);
            category_image = itemView.findViewById(R.id.category_image);
            category_name = itemView.findViewById(R.id.category_name);
        }
    }
}
