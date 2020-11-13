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
    FirebaseAuth auth;
    DatabaseReference reference;

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
        final int currentPosition = position; // for On click listener
        holder.shopping_trash_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Removing...", Toast.LENGTH_SHORT).show();
                Ingredient deleteIngredient = shoppingListIngredients.get(currentPosition);
                removeIngredient(deleteIngredient);
            }
        });

    }

    // removes the selected ingredient and updates recyclerview
    private void removeIngredient(Ingredient deleteIngredient) {
        // remove ingredient from Firebase Database
        auth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference("users/"+auth.getCurrentUser().getUid()+"/Shopping List");
        reference.child(deleteIngredient.getIngredientID()+"").removeValue();
        Toast.makeText(context, deleteIngredient.getIngredientName() +" has been removed from database", Toast.LENGTH_SHORT).show();


        // remove ingredient from recycler view
        int ingredientPosition = shoppingListIngredients.indexOf(deleteIngredient);
        shoppingListIngredients.remove(ingredientPosition);
        notifyItemRemoved(ingredientPosition);
        notifyItemRangeChanged(ingredientPosition, shoppingListIngredients.size() - ingredientPosition);
        Toast.makeText(context, deleteIngredient.getIngredientName() + " removed from shopping list", Toast.LENGTH_SHORT).show();
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
