package com.example.recipeapp.Listeners;

import com.example.recipeapp.Models.DishStepsResponse;

import java.util.List;

public interface DishStepsListener {
    void didFetch(List<DishStepsResponse> stepsResponse, String message);
    void didError(String message);
}
