package com.example.simplyrecipes.Activities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.simplyrecipes.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class HomePageAdapter extends RecyclerView.Adapter<HomePageAdapter.ViewHolder> {

    LayoutInflater inflater;
    List<Recipe> recipes;

    public HomePageAdapter(Context context, List<Recipe> recipes) {
        this.inflater = LayoutInflater.from(context);
        this.recipes = recipes;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_recipe_view_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //binding the data
        holder.recipe_name.setText(recipes.get(position).getTitle());
        Picasso.get().load(recipes.get(position).getImage()).into(holder.recipe_image);
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView recipe_image;
        TextView recipe_name, rating_val;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            recipe_name = itemView.findViewById(R.id.recipe_name);
            rating_val = itemView.findViewById(R.id.rating_val);
            recipe_image = itemView.findViewById(R.id.recipe_image);
        }

    }
}
