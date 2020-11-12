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

import java.util.List;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.ViewHolder> {
    LayoutInflater inflater;
    List<Ingredient> ingredients;
    private Context context;

    public IngredientAdapter(Context context, List<Ingredient> ingredients) {
        this.inflater = LayoutInflater.from(context);
        this.ingredients = ingredients;
        this.context = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.ingredient_detail_card_layout, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientAdapter.ViewHolder holder, int position) {
        holder.ingredient.setText(ingredients.get(position).getIngredientAmount()+ " " + ingredients.get(position).getIngredientName());
        if(ingredients.get(position).getIngredientCategory() != null) {
            holder.category_tv.setText(ingredients.get(position).getIngredientCategory());
        }
        holder.add_shopping_list_icon.setClickable(false); // for now, clicking on it would crash it since there is no viable function for it
        holder.trash_icon.setVisibility(View.INVISIBLE);
    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView ingredient;
        TextView category_tv;
        ImageView add_shopping_list_icon;
        ImageView trash_icon;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ingredient = itemView.findViewById(R.id.ingredient);
            category_tv = itemView.findViewById(R.id.category_tv);
            add_shopping_list_icon = itemView.findViewById(R.id.add_shopping_list_icon);
            trash_icon = itemView.findViewById(R.id.trash_icon);
        }
    }


}
