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
            name = jsonObject.optJSONObject("name").optString("mainName").toString();
            Log.v(LOG_TAG, "name: " + name);


            JSONArray knownAsArray = jsonObject.optJSONObject("name").optJSONArray("alsoKnownAs");
            for (int i = 0; i < knownAsArray.length(); i++) {
                String knownAsItem = knownAsArray.optString(i);
                Log.v(LOG_TAG, "knownAs: " + knownAsItem);
                alsoKnownAs.add(knownAsItem);
            }

            placeOfOrigin = jsonObject.optString("placeOfOrigin");
            Log.v(LOG_TAG, "Origin: " + placeOfOrigin);
            description = jsonObject.optString("description");
            Log.v(LOG_TAG, "Description: " + description);
            imageUrl = jsonObject.optString("image");
            Log.v(LOG_TAG, "Image url: " + imageUrl);

            JSONArray ingredientsArray = jsonObject.optJSONArray("ingredients");
            for (int i = 0; i < ingredientsArray.length(); i++) {
                String ingredientsItem = ingredientsArray.optString(i);
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
