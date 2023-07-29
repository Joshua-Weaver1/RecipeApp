
package com.example.recipeapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.recipeapp.Adapters.RandomRecipeAdapter;
import com.example.recipeapp.Listeners.RandomRecipeResponseListener;
import com.example.recipeapp.Listeners.SelectRecipeListener;
import com.example.recipeapp.Models.RandomRecipeApiResponse;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    // Declare variables
    private ProgressDialog progressDialog;
    private RequestManager requestManager;
    private RandomRecipeAdapter randomRecipeAdapter;
    private RecyclerView recyclerView;
    private Spinner spinner;
    private final List<String> tags = new ArrayList<>();
    private SearchView searchView;
    private Button btnBlueTheme, btnRedTheme;
    private View homePage;
    private TextView recipeAppTitle;
    private final String MYPREFS = "MyPreferences_001";
    private SharedPreferences mySharedPreferences;
    private SharedPreferences.Editor myEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize variables
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Processing...");
        homePage = findViewById(R.id.home_page);
        recipeAppTitle = findViewById(R.id.recipe_app_title);
        mySharedPreferences = getSharedPreferences(MYPREFS, 0);
        myEditor = mySharedPreferences.edit();

        // Check if preferences have been created
        if (mySharedPreferences != null && mySharedPreferences.contains("backColor")) {
            applySavedPreferences();
        } else {
            Toast.makeText(getApplicationContext(), "No Preferences found", Toast.LENGTH_LONG).show();
        }

        // Set up button listeners
        btnBlueTheme = findViewById(R.id.btnBlueTheme);
        btnBlueTheme.setOnClickListener(this);
        btnRedTheme = findViewById(R.id.btnRedTheme);
        btnRedTheme.setOnClickListener(this);

        // Set up search view
        searchView = findViewById(R.id.recipeSearchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                tags.clear();
                tags.add(query);
                requestManager.getRandomRecipes(randomRecipeResponseListener, tags);
                progressDialog.show();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        // Set up spinner
        spinner = findViewById(R.id.spinner_tags);
        ArrayAdapter arrayAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.tags,
                R.layout.spinner_text
        );
        arrayAdapter.setDropDownViewResource(R.layout.spinner_inner_text);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(spinnerSelectedListener);

        // Initialize RequestManager
        requestManager = new RequestManager(this);
    }

    @Override
    public void onClick(View v) {
        // Clear all previous selections
        myEditor.clear();

        // Set preferences based on button click
        if (v.getId() == btnBlueTheme.getId()) {
            myEditor.putInt("backColor", Color.GRAY);
            myEditor.putInt("layoutColor", Color.rgb(21, 29, 35));
            myEditor.putInt("textColor", Color.rgb(79, 89, 79));
        } else {
            myEditor.putInt("backColor", Color.BLUE);
            myEditor.putInt("layoutColor", Color.rgb(27, 7, 13));
            myEditor.putInt("textColor", Color.rgb(87, 82, 73));
        }
        myEditor.apply();
        applySavedPreferences();
    }

    private void applySavedPreferences() {
        // Extract the <key-value> pairs, use default param for missing data
        int layoutColor = mySharedPreferences.getInt("layoutColor", Color.WHITE);
        int textColor = mySharedPreferences.getInt("textColor", Color.BLACK);

        // Apply saved preferences to UI elements
        homePage.setBackgroundColor(layoutColor);
        recipeAppTitle.setTextColor(textColor);
    }

    private final RandomRecipeResponseListener randomRecipeResponseListener =
            new RandomRecipeResponseListener() {
                @Override
                public void didFetch(RandomRecipeApiResponse response, String message) {
                    // Dismiss the progress dialog when recipe data is received
                    progressDialog.dismiss();

                    // Set up the recycler view
                    recyclerView = findViewById(R.id.recycler_random);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this, 1));

                    // Set up the adapter for the recycler view
                    randomRecipeAdapter = new RandomRecipeAdapter(MainActivity.this, response.recipes, selectRecipeListener);
                    recyclerView.setAdapter(randomRecipeAdapter);
                }

                @Override
                public void didError(String message) {
                    // Show an error message if recipe data cannot be retrieved
                    Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                }
            };

    private final AdapterView.OnItemSelectedListener spinnerSelectedListener =
            new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    // Clear the list of tags and add the selected tag
                    tags.clear();
                    tags.add(adapterView.getSelectedItem().toString());

                    // Request random recipes using the selected tag
                    requestManager.getRandomRecipes(randomRecipeResponseListener, tags);

                    // Show the progress dialog while waiting for the response
                    progressDialog.show();
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                    // Do nothing when no item is selected
                }
            };

    private final SelectRecipeListener selectRecipeListener = id -> {
        // Start the DishInformationActivity when a recipe is selected
        startActivity(new Intent(MainActivity.this, DishInformationActivity.class)
                .putExtra("id", id));
    };

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }

    public void apiBtn(View v){
        String url = "https://spoonacular.com/food-api";
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }
}