package com.example.simplyrecipes.Activities;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.simplyrecipes.R;
import com.squareup.picasso.Picasso;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.List;

public class HomePageAdapter extends RecyclerView.Adapter<HomePageAdapter.ViewHolder> {

    LayoutInflater inflater;
    List<Recipe> recipes;
    private Context context;

    public HomePageAdapter(Context context, List<Recipe> recipes) {
        this.inflater = LayoutInflater.from(context);
        this.recipes = recipes;
        this.context = context;
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
        Drawable bg_drawable;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            recipe_name = itemView.findViewById(R.id.recipe_name);
            rating_val = itemView.findViewById(R.id.rating_val);
            recipe_image = itemView.findViewById(R.id.recipe_image);
            Resources bg_res = context.getResources();
            try {
                bg_drawable = Drawable.createFromXml(bg_res, bg_res.getXml(R.xml.layout_bg));
            } catch (IOException e) {
                e.printStackTrace();
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            }
            recipe_image.setBackground(bg_drawable);
        }

    }
}
