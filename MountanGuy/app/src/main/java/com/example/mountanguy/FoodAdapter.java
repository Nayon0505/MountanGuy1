package com.example.mountanguy;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodViewHolder> {

    private List<String> foodList;
    private OnItemClickListener listener;

    public FoodAdapter(List<String> foodList, OnItemClickListener listener) {
        this.foodList = foodList;
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(String foodItem);
    }

    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        return new FoodViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodViewHolder holder, int position) {
        String foodItem = foodList.get(position);
        holder.foodTitle.setText(foodItem);
        holder.itemView.setOnClickListener(v -> listener.onItemClick(foodItem));
    }

    @Override
    public int getItemCount() {
        return foodList.size();
    }

    public static class FoodViewHolder extends RecyclerView.ViewHolder {
        public TextView foodTitle;

        public FoodViewHolder(View view) {
            super(view);
            foodTitle = view.findViewById(android.R.id.text1);
        }
    }
}