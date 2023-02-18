package com.example.postdatatoapiretrofit;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RetrofitAPI {
    @POST("users")
    Call<DataModal> createPost(@Body JsonObject dataModal);
}
