package com.example.simplyrecipes.Activities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.example.simplyrecipes.R;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FilterAdapter extends RecyclerView.Adapter<FilterAdapter.ViewHolder> {
    LayoutInflater inflater;
    Context context;
    List<String> filters;
    Set<String> selectedFilters;
    Set<String> savedFilters;

    public FilterAdapter (Context context, List<String> filters, Set<String> savedFilters) {
        this.inflater = LayoutInflater.from(context);
        this.filters = filters;
        this.context = context;
        this.selectedFilters = new HashSet<String>();
        this.savedFilters = savedFilters;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.filter_card_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FilterAdapter.ViewHolder holder, final int position) {
        for (String option: savedFilters) {
            if (filters.get(position) == option) {
                holder.checkBox.setChecked(true);
            }
        }
        holder.checkBox.setText(filters.get(position));
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isChecked()) {
                    selectedFilters.add(filters.get(position));
                } else {
                    selectedFilters.remove(filters.get(position));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.filters.size();
    }

    public Set<String> getSelectedFilters() {
        return selectedFilters;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CheckBox checkBox;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.filter_checkbox);
        }
    }
}
