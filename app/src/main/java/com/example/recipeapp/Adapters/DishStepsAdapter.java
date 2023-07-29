package com.example.recipeapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recipeapp.Models.DishStepsResponse;
import com.example.recipeapp.R;

import java.util.List;

public class DishStepsAdapter extends RecyclerView.Adapter<DishStepsViewHolder> {
    private final Context context;
    private final List<DishStepsResponse> stepsResponseList;

    public DishStepsAdapter(Context context, List<DishStepsResponse> stepsResponseList) {
        this.context = context;
        this.stepsResponseList = stepsResponseList;
    }

    @NonNull
    @Override
    public DishStepsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the layout for each item in the RecyclerView
        View view = LayoutInflater.from(context).inflate(R.layout.list_dish_steps, parent, false);
        return new DishStepsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DishStepsViewHolder holder, int position) {
        // Bind the data to the views in the ViewHolder
        holder.textView_steps_name.setText(stepsResponseList.get(position).name);
        holder.recycler_dish_steps.setHasFixedSize(true);
        holder.recycler_dish_steps.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        DishStepsPartsAdapter dishStepsPartsAdapter = new DishStepsPartsAdapter(context, stepsResponseList.get(position).steps);
        holder.recycler_dish_steps.setAdapter(dishStepsPartsAdapter);
    }

    @Override
    public int getItemCount() {
        // Return the number of items in the list
        return stepsResponseList.size();
    }
}

// ViewHolder class for each item in the RecyclerView
class DishStepsViewHolder extends RecyclerView.ViewHolder {
    TextView textView_steps_name;
    RecyclerView recycler_dish_steps;

    public DishStepsViewHolder(@NonNull View itemView) {
        super(itemView);
        textView_steps_name = itemView.findViewById(R.id.textView_steps_name);
        recycler_dish_steps = itemView.findViewById(R.id.recycler_dish_steps);
    }
}
