package com.example.mountanguy;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class DiaryEntryAdapter extends RecyclerView.Adapter<DiaryEntryAdapter.ViewHolder> {

    private List<DataBaseHelper.DiaryEntry> diaryEntries;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView foodNameTextView;
        public TextView foodAmountTextView;

        public ViewHolder(View view) {
            super(view);
            foodNameTextView = view.findViewById(R.id.food_name);
            foodAmountTextView = view.findViewById(R.id.food_amount);
        }
    }

    public DiaryEntryAdapter(List<DataBaseHelper.DiaryEntry> diaryEntries) {
        this.diaryEntries = diaryEntries;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_diary_entry, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        DataBaseHelper.DiaryEntry entry = diaryEntries.get(position);
        holder.foodNameTextView.setText(entry.getFoodName());
        holder.foodAmountTextView.setText(String.format("%.2f", entry.getFoodAmount()));
    }

    @Override
    public int getItemCount() {
        return diaryEntries.size();
    }
}
