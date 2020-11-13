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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.ViewHolder> {
    LayoutInflater inflater;
    List<Ingredient> ingredients;
    private Context context;
    DatabaseReference reference;
    FirebaseAuth auth;



    public IngredientAdapter(Context context, List<Ingredient> ingredients) {
        this.inflater = LayoutInflater.from(context);
        this.ingredients = ingredients;
        this.context = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.ingredient_card_recipe_detail_layout, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientAdapter.ViewHolder holder, final int position) {

        holder.ingredient_recipe_card.setText(ingredients.get(position).getIngredientAmount()+
                " " + ingredients.get(position).getIngredientName());
        holder.add_ingredient_icon.setVisibility(View.VISIBLE);
        holder.add_ingredient_icon.setClickable(true);
        holder.add_ingredient_icon.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                auth = FirebaseAuth.getInstance();
                reference = FirebaseDatabase.getInstance()
                        .getReference("users/"+auth.getCurrentUser().getUid()+"/Shopping List");

                // check if the ingredient exist in the shopping cart already
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


        });
    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView ingredient_recipe_card;
        ImageView add_ingredient_icon;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ingredient_recipe_card = itemView.findViewById(R.id.ingredient_recipe_card);
            add_ingredient_icon = itemView.findViewById(R.id.add_ingredient_icon);
        }
    }


}
