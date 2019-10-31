package com.nea.a2cook.services;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.nea.a2cook.Constants;
import com.nea.a2cook.models.Recipes;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class RecipeService {

    public static void findRecipes(String meal  , Callback callback) {
        OkHttpClient client = new OkHttpClient.Builder().build();

        HttpUrl.Builder urlBuilder = HttpUrl.parse( Constants.MEALDB_BASE_URL).newBuilder();
        urlBuilder.addQueryParameter(Constants.MEALDB_NAME_QUERY_PARAMETER, meal);
        String url = urlBuilder.build().toString();
        Request request = new Request.Builder()
                .url(url)
                .build();

        Call call = client.newCall(request);
        call.enqueue(callback);
    }

    public static List<Recipes> processResults(Response response) {
        List<Recipes> recipes = new ArrayList<>();
        Gson gson = new Gson();

        try{
            String data = response.body().string();
            JSONObject dataJson = new JSONObject(data);
            JSONArray recipeArray = dataJson.getJSONArray("meals");
            if (response.isSuccessful()){
                Type listType = new TypeToken<List<Recipes>> () {}.getType();
                recipes = gson.fromJson(recipeArray.toString(),listType);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return recipes;
    }

}
