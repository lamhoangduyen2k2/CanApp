package com.example.canapp.api;

import com.example.canapp.model.UserLogin;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiService {
    @FormUrlEncoded
    @POST("login")
    Call<UserLogin> login(@Field("email") String email,
                          @Field("password") String password);
}
