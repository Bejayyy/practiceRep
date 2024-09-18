package com.example.finalpactice;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {
    @POST("register.php")
    Call<RegisterResponse> registerUser(@Body User user);

    @POST("login.php")
    Call<LoginResponse>  loginUser(@Body LoginRequest loginRequest);
}
