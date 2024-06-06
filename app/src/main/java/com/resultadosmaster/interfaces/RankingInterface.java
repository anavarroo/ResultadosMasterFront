package com.resultadosmaster.interfaces;

import com.resultadosmaster.model.Jugador;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface RankingInterface {

    @GET("/api/v1/ranking")
    Call<List<Jugador>> getAll();

    @GET("/api/v1/ranking/{genero}")
    Call<List<Jugador>> getAllByGenero(@Path("genero") String genero);

}
