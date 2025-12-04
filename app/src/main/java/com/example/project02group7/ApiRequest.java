package com.example.project02group7;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class ApiRequest {
    OkHttpClient client = new OkHttpClient();
    public void getRecipes(Callback callback) {
        String url = "https://10.0.2.2:3000/recipes";

        Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(callback);
    }
}
