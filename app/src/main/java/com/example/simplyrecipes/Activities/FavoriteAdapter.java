package com.example.simplyrecipes.Activities;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.res.Resources;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.simplyrecipes.R;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.List;


public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder> {
    LayoutInflater inflater;
    List<Recipe> recipes;
    Context context;
    FirebaseDatabase db;

    public FavoriteAdapter (Context context, List<Recipe> recipes) {
        this.inflater = LayoutInflater.from(context);
        this.recipes = recipes;
        this.context = context;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.favorite_recipe_card_layout, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteAdapter.ViewHolder holder, final int position) {
        if(recipes.get(position).getTitle().equals("")) {
            holder.favorite_recipe_tv.setText("");
        }else {
            holder.favorite_recipe_tv.setText(recipes.get(position).getTitle());
        }
        if(!(recipes.get(position).getImage().equals(""))) {
            Picasso.get().load(recipes.get(position).getImage()).into(holder.favorite_recipe_image);
        }
        if(!(recipes.get(position).getRecipeTime() == -1)) {
            holder.favorite_recipe_category.setText(recipes.get(position).getRecipeTime()+" min");
        }

        holder.favorite_trash_icon.setClickable(true);
        holder.favorite_recipe_image.setClickable(true);

        holder.favorite_recipe_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, RecipeDetailActivity.class);
                intent.putExtra("recipeID", recipes.get(position).getRecipeID());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return this.recipes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView favorite_recipe_tv;
        TextView favorite_recipe_category;
        ImageView favorite_trash_icon;
        ImageView favorite_recipe_image;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            favorite_recipe_tv = itemView.findViewById(R.id.favorite_recipe_tv);
            favorite_recipe_category = itemView.findViewById(R.id.favorite_category_tv);
            favorite_trash_icon = itemView.findViewById(R.id.favorite_trash_icon);
            favorite_recipe_image = itemView.findViewById(R.id.favorite_recipe_image);

        }
    }
}
