package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {
    private static Sandwich sandwich;
    private static String LOG_TAG = "JsonUtils";

    public static Sandwich parseSandwichJson(String json) {
        String name;
        List<String> alsoKnownAs = new ArrayList<>();
        String placeOfOrigin;
        String description;
        String imageUrl;
        List<String> ingredients = new ArrayList<>();

        try {
            JSONObject jsonObject = new JSONObject(json);
            name = jsonObject.getJSONObject("name").getString("mainName").toString();
            Log.v(LOG_TAG, "name: " + name);


            JSONArray knownAsArray = jsonObject.getJSONObject("name").getJSONArray("alsoKnownAs");
            for (int i = 0; i < knownAsArray.length(); i++) {
                String knownAsItem = knownAsArray.getString(i);
                Log.v(LOG_TAG, "knownAs: " + knownAsItem);
                alsoKnownAs.add(knownAsItem);
            }

            placeOfOrigin = jsonObject.getString("placeOfOrigin");
            Log.v(LOG_TAG, "Origin: " + placeOfOrigin);
            description = jsonObject.getString("description");
            Log.v(LOG_TAG, "Description: " + description);
            imageUrl = jsonObject.getString("image");
            Log.v(LOG_TAG, "Image url: " + imageUrl);

            JSONArray ingredientsArray = jsonObject.getJSONArray("ingredients");
            for (int i = 0; i < ingredientsArray.length(); i++) {
                String ingredientsItem = ingredientsArray.getString(i);
                Log.v(LOG_TAG, "ingredients: " + ingredientsItem);
                ingredients.add(ingredientsItem + ", ");

                sandwich = new Sandwich(name, alsoKnownAs, placeOfOrigin, description, imageUrl, ingredients);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return sandwich;
    }
}
