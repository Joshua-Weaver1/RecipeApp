package com.example.recipeapp.Listeners;

import com.example.recipeapp.Models.DishInformationResponse;

public interface DishInformationListener {
    void didError(String message);
    void didFetch(DishInformationResponse response, String message);
}
