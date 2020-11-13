package com.example.simplyrecipes.Activities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.simplyrecipes.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import java.util.List;

public class PantryIngredientAdapter extends RecyclerView.Adapter<PantryIngredientAdapter.ViewHolder> {
    LayoutInflater inflater;
    List<Ingredient> pantryIngredients;
    DatabaseReference reference;
    FirebaseAuth auth;
    private Context context;


    public PantryIngredientAdapter(Context context, List<Ingredient> ingredients) {
        this.inflater = LayoutInflater.from(context);
        this.pantryIngredients = ingredients;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.ingredient_pantry_card_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.ingredient.setText(pantryIngredients.get(position).getIngredientName() + "");
        holder.category.setText(pantryIngredients.get(position).getIngredientCategory() + "");
        holder.add_ingredient_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Add", Toast.LENGTH_SHORT).show();
            }
        });
        holder.pantry_trash_icon.setVisibility(View.VISIBLE);
        holder.pantry_trash_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Delete", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView ingredient, category;
        ImageView add_ingredient_icon, pantry_trash_icon;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ingredient = itemView.findViewById(R.id.ingredient_recipe_card);
            category = itemView.findViewById(R.id.category_tv);
            add_ingredient_icon = itemView.findViewById(R.id.add_shopping_list_icon);
            pantry_trash_icon = itemView.findViewById(R.id.trash_icon);
        }
    }

}
