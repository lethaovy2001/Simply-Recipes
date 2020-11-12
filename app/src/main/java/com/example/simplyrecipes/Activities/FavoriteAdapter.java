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
        View view = inflater.inflate(R.layout.shopping_list_card_layout, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteAdapter.ViewHolder holder, final int position) {
        holder.recipeName.setText(recipes.get(position).getTitle());
        holder.recipeName.setClickable(true);
        holder.trash_can_icon.setClickable(true);

        holder.recipeName.setOnClickListener(new View.OnClickListener() {
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
        TextView recipeName;
        TextView subName;
        ImageView trash_can_icon;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            recipeName = itemView.findViewById(R.id.ingredient);
            subName = itemView.findViewById(R.id.category_tv);

            trash_can_icon = itemView.findViewById(R.id.trash_icon);
        }
    }
}
