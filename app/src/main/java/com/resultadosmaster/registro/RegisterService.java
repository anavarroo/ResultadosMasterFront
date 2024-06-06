package com.resultadosmaster.registro;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RegisterService {

    @POST("/auth/register")
    Call<UserDtoRegister> register(@Body RegisterRequest request);
    @POST("/auth/login")
    Call<AuthResponse> login(@Body LoginRequest request);


}
