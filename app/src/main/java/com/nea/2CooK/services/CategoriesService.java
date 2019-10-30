package com.techspaceke.cookit.services;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.techspaceke.cookit.Constants;
import com.techspaceke.cookit.models.Categories;

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

public class CategoriesService {
    public static void listCategories( Callback callback) {
        OkHttpClient client = new OkHttpClient.Builder().build();

        HttpUrl.Builder urlBuilder = HttpUrl.parse(Constants.MEALDB_CATEGORIES_URL).newBuilder();
//        urlBuilder.addQueryParameter(Constants.MEALDB_NAME_QUERY_PARAMETER, meal);
        String url = urlBuilder.build().toString();

        Request request = new Request.Builder()
                .url(url)
                .build();

        Call call = client.newCall(request);
        call.enqueue(callback);
    }

    public static List<Categories> processResults(Response response) {
        List<Categories> categories = new ArrayList<>();
        Gson gson = new Gson();

        try{
            String data = response.body().string();
            JSONObject dataJson = new JSONObject(data);
            JSONArray recipeArray = dataJson.getJSONArray("categories");
            if (response.isSuccessful()){
                Type listType = new TypeToken<List<Categories>>() {}.getType();
                categories = gson.fromJson(recipeArray.toString(),listType);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return categories;
    }

}
