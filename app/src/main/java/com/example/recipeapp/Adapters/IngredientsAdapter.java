package com.example.recipeapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recipeapp.Models.ExtendedIngredient;
import com.example.recipeapp.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsViewHolder> {
    // Instance variables
    private final Context mContext;
    private final List<ExtendedIngredient> mExtendedIngredientList;

    // Constructor
    public IngredientsAdapter(Context context, List<ExtendedIngredient> extendedIngredientList) {
        mContext = context;
        mExtendedIngredientList = extendedIngredientList;
    }

    // Inflates the ViewHolder for the item layout
    @NonNull
    @Override
    public IngredientsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.list_dish_ingredients, parent, false);
        return new IngredientsViewHolder(view);
    }

    // Binds data to the ViewHolder
    @Override
    public void onBindViewHolder(@NonNull IngredientsViewHolder holder, int position) {
        ExtendedIngredient ingredient = mExtendedIngredientList.get(position);

        // Set the name and amount of the ingredient
        holder.textView_ingredients_name.setText(ingredient.name);
        holder.textView_ingredients_name.setSelected(true);
        holder.textView_ingredients_amount.setText(ingredient.original);
        holder.textView_ingredients_amount.setSelected(true);

        // Load the image of the ingredient using Picasso library
        Picasso.get()
                .load("https://spoonacular.com/cdn/ingredients_100x100/" + ingredient.image)
                .into(holder.imageView_ingredients);
    }

    // Returns the number of items in the data set
    @Override
    public int getItemCount() {
        return mExtendedIngredientList.size();
    }
}

// ViewHolder class for the item layout
class IngredientsViewHolder extends RecyclerView.ViewHolder {
    // Instance variables for the views in the layout
    TextView textView_ingredients_name, textView_ingredients_amount;
    ImageView imageView_ingredients;

    // Constructor
    public IngredientsViewHolder(@NonNull View itemView) {
        super(itemView);
        textView_ingredients_name = itemView.findViewById(R.id.textView_ingredients_name);
        textView_ingredients_amount = itemView.findViewById(R.id.textView_ingredients_amount);
        imageView_ingredients = itemView.findViewById(R.id.imageView_ingredients);
    }
}