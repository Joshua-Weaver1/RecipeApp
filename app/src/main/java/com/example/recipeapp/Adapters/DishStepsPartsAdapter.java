package com.example.recipeapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recipeapp.Models.Step;
import com.example.recipeapp.R;

import java.util.List;

public class DishStepsPartsAdapter extends RecyclerView.Adapter<DishStepsPartsViewHolder>{

    // Declare class variables
    Context context;
    List<Step> stepList;

    // Constructor
    public DishStepsPartsAdapter(Context context, List<Step> stepList) {
        this.context = context;
        this.stepList = stepList;
    }

    // Create ViewHolder for adapter
    @NonNull
    @Override
    public DishStepsPartsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_dish_steps_parts, parent, false);
        return new DishStepsPartsViewHolder(view);
    }

    // Bind data to ViewHolder
    @Override
    public void onBindViewHolder(@NonNull DishStepsPartsViewHolder holder, int position) {
        // Set text for step number and step title
        holder.textView_dish_steps_number.setText(String.valueOf(stepList.get(position).number));
        holder.textView_dish_steps_title.setText(stepList.get(position).step);
    }

    // Get number of items in adapter
    @Override
    public int getItemCount() {
        return stepList.size();
    }
}

// ViewHolder for DishStepsPartsAdapter
class DishStepsPartsViewHolder extends RecyclerView.ViewHolder {

    // Declare view variables
    TextView textView_dish_steps_number, textView_dish_steps_title;

    // Constructor
    public DishStepsPartsViewHolder(@NonNull View itemView) {
        super(itemView);
        // Get references to view elements
        textView_dish_steps_number = itemView.findViewById(R.id.textView_dish_steps_number);
        textView_dish_steps_title = itemView.findViewById(R.id.textView_dish_steps_title);
    }
}
