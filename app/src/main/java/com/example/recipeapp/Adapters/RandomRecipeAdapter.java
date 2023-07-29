package com.example.recipeapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recipeapp.Listeners.SelectRecipeListener;
import com.example.recipeapp.Models.Recipe;
import com.example.recipeapp.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RandomRecipeAdapter extends RecyclerView.Adapter<RandomRecipeViewHolder> {
    // Instance variables
    private final Context mContext;
    private final List<Recipe> mListOfRecipes;
    private final SelectRecipeListener mRecipeListener;

    // Constructor
    public RandomRecipeAdapter(Context context, List<Recipe> listOfRecipes, SelectRecipeListener recipeListener) {
        mContext = context;
        mListOfRecipes = listOfRecipes;
        mRecipeListener = recipeListener;
    }

    // Inflates the ViewHolder for the item layout
    @NonNull
    @Override
    public RandomRecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.random_recipe_list, parent, false);
        return new RandomRecipeViewHolder(view);
    }

    // Binds data to the ViewHolder
    @Override
    public void onBindViewHolder(@NonNull RandomRecipeViewHolder holder, int position) {
        Recipe recipe = mListOfRecipes.get(position);

        // Set the title, servings, time and image of the recipe
        holder.textView_title.setText(recipe.title);
        holder.textView_title.setSelected(true);
        holder.textView_servings.setText(recipe.servings + " Servings");
        holder.textView_time.setText(recipe.readyInMinutes + " Minutes");
        Picasso.get().load(recipe.image).into(holder.imageView_food);

        // Set a click listener on the container view for the recipe item
        holder.random_list_container.setOnClickListener(view -> mRecipeListener.onRecipeSelected(String.valueOf(recipe.id)));
    }

    // Returns the number of items in the data set
    @Override
    public int getItemCount() {
        return mListOfRecipes.size();
    }
}

// ViewHolder class for the item layout
class RandomRecipeViewHolder extends RecyclerView.ViewHolder {
    // Instance variables for the views in the layout
    CardView random_list_container;
    TextView textView_title, textView_servings, textView_time;
    ImageView imageView_food;

    // Constructor
    public RandomRecipeViewHolder(@NonNull View itemView) {
        super(itemView);
        random_list_container = itemView.findViewById(R.id.random_list_container);
        textView_title = itemView.findViewById(R.id.textView_title);
        textView_servings = itemView.findViewById(R.id.textView_servings);
        textView_time = itemView.findViewById(R.id.textView_time);
        imageView_food = itemView.findViewById(R.id.imageView_food);
    }
}
