package com.example.patanjali;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
public class stock_adapter extends RecyclerView.Adapter<stock_adapter.MyViewHolder> {
    private List<stats> statsList;
    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView name,price,quantity;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            this.name = (TextView) itemView.findViewById(R.id.name);
            this.price = (TextView) itemView.findViewById(R.id.price);
            this.quantity = (TextView) itemView.findViewById(R.id.quantity);
        }
    }
    public stock_adapter(List<stats> statsList) {
        this.statsList = statsList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.stats,parent,false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        stats stats = statsList.get(position);
        holder.name.setText(stats.getName());
        holder.price.setText(stats.getPrice());
        holder.quantity.setText(stats.getQuantity());
    }

    @Override
    public int getItemCount() {
        return statsList.size();
    }
}
