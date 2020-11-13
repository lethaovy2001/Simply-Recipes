package com.example.simplyrecipes.Activities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.example.simplyrecipes.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FilterAdapter extends RecyclerView.Adapter<FilterAdapter.ViewHolder> {
    LayoutInflater inflater;
    Context context;
    List<String> filters;

    public FilterAdapter (Context context, List<String> filters) {
        this.inflater = LayoutInflater.from(context);
        this.filters = filters;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.filter_card_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FilterAdapter.ViewHolder holder, final int position) {
        holder.checkBox.setText(filters.get(position));
    }

    @Override
    public int getItemCount() {
        return this.filters.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CheckBox checkBox;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.filter_checkbox);
        }
    }
}
