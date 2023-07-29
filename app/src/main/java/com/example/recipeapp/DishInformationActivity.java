package com.example.recipeapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.recipeapp.Adapters.DishStepsAdapter;
import com.example.recipeapp.Adapters.IngredientsAdapter;
import com.example.recipeapp.Listeners.DishInformationListener;
import com.example.recipeapp.Listeners.DishStepsListener;
import com.example.recipeapp.Models.DishInformationResponse;
import com.example.recipeapp.Models.DishStepsResponse;
import com.squareup.picasso.Picasso;

import java.util.List;
/**
 * This activity displays information about a dish, including its name, source, summary, image,
 * ingredients, and steps.
 */
public class DishInformationActivity extends AppCompatActivity {
    // Define member variables
    int id;
    TextView textView_dish_name, textView_dish_source;
    ImageView imageView_dish_image;
    RecyclerView recycler_dish_ingredients, recycler_dish_steps;
    RequestManager requestManager;
    ProgressDialog progressDialog;
    IngredientsAdapter ingredientsAdapter;
    DishStepsAdapter dishStepsAdapter;
    LinearLayout activity_dish_information;

    // Define SharedPreferences-related member variables
    final String MYPREFS = "MyPreferences_001";
    SharedPreferences mySharedPreferences;
    SharedPreferences.Editor myEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dish_information);

        // Initialize the activity_dish_information LinearLayout
        activity_dish_information = (LinearLayout) findViewById(R.id.activity_dish_information);

        // Initialize the shared preferences object and editor
        mySharedPreferences = getSharedPreferences(MYPREFS, 0);
        myEditor = mySharedPreferences.edit();

        // Check if a preferences file has been created and apply saved preferences if it exists
        if (mySharedPreferences != null && mySharedPreferences.contains("backColor")) {
            applySavedPreferences();
        } else {
            Toast.makeText(getApplicationContext(),
                    "No Preferences found", Toast.LENGTH_LONG).show();
        }

        // Initialize view components
        findViews();

        // Get dish ID from intent
        id = Integer.parseInt(getIntent().getStringExtra("id"));

        // Initialize RequestManager and fetch dish information and steps
        requestManager = new RequestManager(this);
        requestManager.getDishInformation(dishInformationListener, id);
        requestManager.getDishSteps(dishStepsListener, id);

        // Initialize and show progress dialog
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Processing Information...");
        progressDialog.show();
    }

    // Method to apply saved preferences to activity layout
    public void applySavedPreferences() {
        // Extract the key-value pairs, use default param for missing data
        int layoutColor = mySharedPreferences.getInt("layoutColor", Color.WHITE);

        // Set the background color of activity_dish_information LinearLayout
        activity_dish_information.setBackgroundColor(layoutColor);
    }

    // Method to initialize view components
    private void findViews() {
        textView_dish_name = findViewById(R.id.textView_dish_name);
        textView_dish_source = findViewById(R.id.textView_dish_source);
        imageView_dish_image = findViewById(R.id.imageView_dish_image);
        recycler_dish_ingredients = findViewById(R.id.recycler_dish_ingredients);
        recycler_dish_steps = findViewById(R.id.recycler_dish_steps);
    }

    // Listener for dish information request
    private final DishInformationListener dishInformationListener = new DishInformationListener() {
        @Override
        public void didError(String message) {
            Toast.makeText(DishInformationActivity.this, message, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void didFetch(DishInformationResponse response, String message) {
            // Dismiss progress dialog
            progressDialog.dismiss();

            // Set dish information text views and image view using response object
            textView_dish_name.setText(response.title);
            textView_dish_source.setText(response.sourceName);
            Picasso.get().load(response.image).into(imageView_dish_image);

            // Set up recycler_dish_ingredients RecyclerView and its adapter
            recycler_dish_ingredients.setHasFixedSize(true);
            recycler_dish_ingredients.setLayoutManager(new LinearLayoutManager(DishInformationActivity.this, LinearLayoutManager.HORIZONTAL, false));
            ingredientsAdapter = new IngredientsAdapter(DishInformationActivity.this, response.extendedIngredients);
            recycler_dish_ingredients.setAdapter(ingredientsAdapter);

        }
    };

    private final DishStepsListener dishStepsListener = new DishStepsListener() {
        @Override
        public void didFetch(List<DishStepsResponse> stepsResponse, String message) {
            recycler_dish_steps.setHasFixedSize(true);
            recycler_dish_steps.setLayoutManager(new LinearLayoutManager(DishInformationActivity.this, LinearLayoutManager.VERTICAL, false));
            dishStepsAdapter = new DishStepsAdapter(DishInformationActivity.this, stepsResponse);
            recycler_dish_steps.setAdapter(dishStepsAdapter);
        }

        @Override
        public void didError(String message) {

        }
    };
}