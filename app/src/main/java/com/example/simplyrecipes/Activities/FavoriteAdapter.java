package com.example.simplyrecipes.Activities;

import android.content.Context;
import android.content.Intent;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.res.Resources;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.simplyrecipes.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.List;


public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder> {
    LayoutInflater inflater;
    List<Recipe> recipes;
    Context context;
    FirebaseDatabase db;
    FirebaseAuth auth;
    DatabaseReference reference;

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

        final int currPosition = position;
        holder.favorite_trash_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Removing...", Toast.LENGTH_SHORT).show();
                Recipe deleteRecipe = recipes.get(currPosition);
                removeRecipe(deleteRecipe);
            }
        });

    }

    private void removeRecipe(Recipe deleteRecipe) {
        // remove recipe from the Firebase Database
        auth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference("users/"
                +auth.getCurrentUser().getUid()+"/Favorite");
        reference.child(deleteRecipe.getRecipeID()+"").removeValue();
        Toast.makeText(context, "Removing From Database...", Toast.LENGTH_SHORT).show();

        int recipePosition = recipes.indexOf(deleteRecipe);
        recipes.remove(recipePosition);
        notifyItemRemoved(recipePosition);
        notifyItemRangeChanged(0, recipes.size());
        Toast.makeText(context, deleteRecipe.getTitle() + " successfuly removed!", Toast.LENGTH_SHORT).show();

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
