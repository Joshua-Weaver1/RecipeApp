package com.example.recipeapp;

import android.content.Context;

import com.example.recipeapp.Listeners.DishInformationListener;
import com.example.recipeapp.Listeners.DishStepsListener;
import com.example.recipeapp.Listeners.RandomRecipeResponseListener;
import com.example.recipeapp.Models.DishInformationResponse;
import com.example.recipeapp.Models.DishStepsResponse;
import com.example.recipeapp.Models.RandomRecipeApiResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
public class RequestManager {

    private Retrofit retrofit;
    private Context context;

    public RequestManager(Context context) {
        this.context = context;

        // Create Retrofit instance
        retrofit = new Retrofit.Builder()
                .baseUrl("https://api.spoonacular.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    //Get random recipes.
    public void getRandomRecipes(RandomRecipeResponseListener listener, List<String> tags) {
        // Create instance of CallRandomRecipes interface
        CallRandomRecipes callRandomRecipes = retrofit.create(CallRandomRecipes.class);

        // Create call
        Call<RandomRecipeApiResponse> call = callRandomRecipes.callRandomRecipe(context.getString(R.string.api_key), "10", tags);

        // Enqueue call
        call.enqueue(new Callback<RandomRecipeApiResponse>() {
            @Override
            public void onResponse(Call<RandomRecipeApiResponse> call, Response<RandomRecipeApiResponse> response) {
                if (!response.isSuccessful()) {
                    // Notify listener of error
                    listener.didError(response.message());
                    return;
                }
                // Notify listener of successful response
                listener.didFetch(response.body(), response.message());
            }

            @Override
            public void onFailure(Call<RandomRecipeApiResponse> call, Throwable t) {
                // Notify listener of failure
                listener.didError(t.getMessage());
            }
        });
    }

    // Get information for a specific dish.
    public void getDishInformation(DishInformationListener dishInformationListener, int id) {
        // Create instance of CallDishInformation interface
        CallDishInformation callDishInformation = retrofit.create(CallDishInformation.class);

        // Create call
        Call<DishInformationResponse> dishInformationResponseCall = callDishInformation.callDishInformation(id, context.getString(R.string.api_key));

        // Enqueue call
        dishInformationResponseCall.enqueue(new Callback<DishInformationResponse>() {
            @Override
            public void onResponse(Call<DishInformationResponse> call, Response<DishInformationResponse> response) {
                if (!response.isSuccessful()) {
                    // Notify listener of error
                    dishInformationListener.didError(response.message());
                    return;
                }
                // Notify listener of successful response
                dishInformationListener.didFetch(response.body(), response.message());
            }

            @Override
            public void onFailure(Call<DishInformationResponse> call, Throwable t) {
                // Notify listener of failure
                dishInformationListener.didError(t.getMessage());
            }
        });
    }

    //Get steps for a specific dish.
    public void getDishSteps(DishStepsListener stepsListener, int id) {
        // Create a Retrofit instance for making HTTP calls
        CallDishSteps callDishSteps = retrofit.create(CallDishSteps.class);

        // Create a HTTP GET request to retrieve dish steps from the API endpoint
        Call<List<DishStepsResponse>> call = callDishSteps.callDishSteps(id, context.getString(R.string.api_key));

        // Enqueue the request asynchronously
        call.enqueue(new Callback<List<DishStepsResponse>>() {
            @Override
            public void onResponse(Call<List<DishStepsResponse>> call, Response<List<DishStepsResponse>> response) {
                if (!response.isSuccessful()) {
                    // Notify the listener that an error occurred while fetching data
                    stepsListener.didError(response.message());
                    return;
                }
                // Notify the listener that data was successfully fetched
                stepsListener.didFetch(response.body(), response.message());
            }

            @Override
            public void onFailure(Call<List<DishStepsResponse>> call, Throwable t) {
                // Notify the listener that an error occurred while fetching data
                stepsListener.didError(t.getMessage());
            }
        });
    }

    // Interface for making a HTTP GET request to retrieve random recipes from the API endpoint
    private interface CallRandomRecipes {
        @GET("recipes/random")
        Call<RandomRecipeApiResponse> callRandomRecipe(
                @Query("apiKey") String apiKey,
                @Query("number") String number,
                @Query("tags") List<String> tags
        );
    }

    // Interface for making a HTTP GET request to retrieve dish information from the API endpoint
    private interface CallDishInformation {
        @GET("recipes/{id}/information")
        Call<DishInformationResponse> callDishInformation(
                @Path("id") int id,
                @Query("apiKey") String apiKey
        );
    }

    // Interface for making a HTTP GET request to retrieve dish steps from the API endpoint
    private interface CallDishSteps {
        @GET("recipes/{id}/analyzedInstructions")
        Call<List<DishStepsResponse>> callDishSteps(
                @Path("id") int id,
                @Query("apiKey") String apiKey
        );
    }
}
