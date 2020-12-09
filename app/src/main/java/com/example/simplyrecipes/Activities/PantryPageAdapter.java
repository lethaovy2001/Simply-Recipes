package com.example.simplyrecipes.Activities;

import android.app.Activity;
import android.content.Context;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Parcelable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.simplyrecipes.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.lang.reflect.Method;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class PantryPageAdapter extends RecyclerView.Adapter<PantryPageAdapter.ViewHolder> {

    LayoutInflater inflater;
    List<Ingredient> ingredients;
    Context context;
    DatabaseReference reference;
    FirebaseAuth auth;

    public PantryPageAdapter(Context context, List<Ingredient> ingredients) {
        this.inflater = LayoutInflater.from(context);
        this.ingredients = ingredients;
        this.context = context;
    }

    @NonNull
    @Override
    public PantryPageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                           int viewType) {
        View view = inflater.inflate(R.layout.ingredient_pantry_card_layout, parent, false);
        return new ViewHolder(view);
    }

    /**
     * Holder handler for each card layout
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final int currentPosition = position; // for On click listener
        holder.ingredient.setText(ingredients.get(position).getIngredientName());
        holder.add_to_cart.setClickable(false);
        holder.add_to_cart.setVisibility(View.INVISIBLE);
        holder.remove_from_pantry.setClickable(true);
        holder.remove_from_pantry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "remove", Toast.LENGTH_SHORT).show();
                Ingredient deleteIngredient = ingredients.get(currentPosition);
                removeIngredient(deleteIngredient);
                ingredients.clear();

            }
        });


    }

    /**
     * Add ingredient from pantry list to shopping list and update shopping list's recycler view
     *
     * @param addIngredient
     */
    private void addIngredientToShoppingCart(Ingredient addIngredient, final int position) {
        auth = FirebaseAuth.getInstance();
        reference =
                FirebaseDatabase.getInstance().getReference("users/" + auth.getCurrentUser().getUid() + "/Shopping List");
        reference.equalTo(ingredients.get(position).getIngredientID())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.child(ingredients.get(position).getIngredientID()+"").exists()) {
                            ingredients.get(position).setInShoppingCart(true);
                            Toast.makeText(context, "This ingredient already exist in the cart", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
        if(ingredients.get(position).isInShoppingCart() == false) {
            // set up the ingredient id and ingredient name
            reference.child(ingredients.get(position).getIngredientID()+"").child("Ingredient Name").setValue(ingredients.get(position).getIngredientName())
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()) {

                            }
                        }
                    });

            // set up the ingredient category
            reference = FirebaseDatabase.getInstance()
                    .getReference("users/"+auth.getCurrentUser().getUid()+"/Shopping List/"+ingredients.get(position).getIngredientID());
            reference.child("Ingredient Category").setValue(ingredients.get(position)
                    .getIngredientCategory()).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()) {
                        Toast.makeText(context, "Successfully added ingredient to shopping cart!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
    /**
     * Delete ingredient from pantry list on database and update recycler view
     *
     * @param deleteIngredient
     */
    private void removeIngredient(Ingredient deleteIngredient) {
        // remove ingredient from Firebase Database
        auth = FirebaseAuth.getInstance();

        reference =
                FirebaseDatabase.getInstance().getReference("users/" + auth.getCurrentUser().getUid() + "/Pantry");
        reference.child(deleteIngredient.getIngredientID() + "").removeValue();



        // remove ingredient from recycler view
        int ingredientPosition = ingredients.indexOf(deleteIngredient);
        ingredients.remove(ingredientPosition);
        notifyItemRemoved(ingredientPosition);
        notifyItemRangeChanged(0, ingredients.size());
        notifyItemRangeChanged(ingredientPosition, ingredients.size() - ingredientPosition);

    }

    public void clear() {
        int size = ingredients.size();
        ingredients.clear();
        notifyItemRangeRemoved(0,size);
    }

    /**
     * Return ingredients list size
     *
     * @return
     */
    @Override
    public int getItemCount() {
        return ingredients.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView ingredient, category;
        ImageView add_to_cart, remove_from_pantry;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ingredient = itemView.findViewById(R.id.ingredient_name);
            category = itemView.findViewById(R.id.category_tv);
            add_to_cart = itemView.findViewById(R.id.add_shopping_list_icon);
            remove_from_pantry = itemView.findViewById(R.id.trash_icon);
        }

    }
}
