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
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class ShoppingListAdapter extends RecyclerView.Adapter<ShoppingListAdapter.ViewHolder> {
    LayoutInflater inflater;
    List<Ingredient> shoppingListIngredients;
    Context context;
    FirebaseDatabase db;

    public ShoppingListAdapter(Context context, List<Ingredient> shoppingListIngredients){
        this.inflater = LayoutInflater.from(context);
        this.context = context;
        this.shoppingListIngredients = shoppingListIngredients;

    }

    @NonNull
    @Override
    public ShoppingListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.shopping_list_card_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShoppingListAdapter.ViewHolder holder, int position) {
        holder.shopping_ingredient.setText(shoppingListIngredients.get(position).getIngredientName()+"");
        holder.shopping_category.setText(shoppingListIngredients.get(position).getIngredientCategory()+"");
        holder.shopping_trash_icon.setVisibility(View.VISIBLE);
        holder.shopping_trash_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Delete", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return shoppingListIngredients.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView shopping_ingredient;
        TextView shopping_category;
        ImageView shopping_trash_icon;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            shopping_ingredient = itemView.findViewById(R.id.shopping_ingredient);
            shopping_category = itemView.findViewById(R.id.shopping_category);
            shopping_trash_icon = itemView.findViewById(R.id.shopping_trash_icon);
        }
    }
}
