package com.example.yelpplus;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ListOfEventsAdaptor extends RecyclerView.Adapter<ListOfEventsAdaptor.MyViewHolder> {
    private List<EventBooking> dataList;
    private Context context;

    public ListOfEventsAdaptor(List<EventBooking> dataList, Context context){
        this.dataList = dataList;
        this.context = context;
    }

    @NonNull
    @Override
    public ListOfEventsAdaptor.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.single_event_view, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListOfEventsAdaptor.MyViewHolder holder, int position) {
        holder.businessName.setText(dataList.get(position).getBusiness().getName());
        String[] date = dataList.get(position).getDate().split("T");
        holder.eventDate.setText(date[0]);
        holder.eventTime.setText(dataList.get(position).getTime());
        holder.guestCount.setText(dataList.get(position).getGuestCount());
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView businessName;
        TextView eventDate;
        TextView eventTime;
        TextView guestCount;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            businessName = itemView.findViewById(R.id.event_business_name);
            eventDate = itemView.findViewById(R.id.event_date);
            eventTime = itemView.findViewById(R.id.event_time);
            guestCount = itemView.findViewById(R.id.event_guest_count);
        }
    }
}
