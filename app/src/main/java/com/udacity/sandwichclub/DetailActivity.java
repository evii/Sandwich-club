package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        //get all the TextViews that needs to be populated
        TextView alsoKnownTv = findViewById(R.id.also_known_tv);
        TextView originTv = findViewById(R.id.origin_tv);
        TextView ingredientsTv = findViewById(R.id.ingredients_tv);
        TextView descriptionTv = findViewById(R.id.description_tv);

        // get the Also known as info and populate the TextView
        StringBuilder stringBuilder = new StringBuilder();
        List<String> alsoKnownAs = sandwich.getAlsoKnownAs();
        for (String alsoKnownAsItem : alsoKnownAs) {

            int alsoKnownAsLength = alsoKnownAs.size();
            int alsoKnownAsIndex = alsoKnownAs.indexOf(alsoKnownAsItem);

            if ((alsoKnownAsLength - 1) == alsoKnownAsIndex) {
                stringBuilder.append(alsoKnownAsItem);
            } else {
                stringBuilder.append(alsoKnownAsItem + "\n");
            }
        }
        alsoKnownTv.setText(stringBuilder);
        stringBuilder.setLength(0);

        // get the Origin info and populate the TextView
        String origin = sandwich.getPlaceOfOrigin();
        originTv.setText(origin);

        // get the Ingredients info and populate the TextView
        List<String> ingredients = sandwich.getIngredients();
        for (String ingredientsItem : ingredients) {

            int ingredientsLentgh = ingredients.size();
            int ingredientIndex = ingredients.indexOf(ingredientsItem);

            if ((ingredientsLentgh - 1) == ingredientIndex) {
                stringBuilder.append(ingredientsItem);
            } else {
                stringBuilder.append(ingredientsItem + "\n");
            }
        }
        ingredientsTv.setText(stringBuilder);
        stringBuilder.setLength(0);

        // get the Description info and populate the TextView
        String description = sandwich.getDescription();
        descriptionTv.setText(description);
    }
}
