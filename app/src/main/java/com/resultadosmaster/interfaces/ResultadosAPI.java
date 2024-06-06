package com.resultadosmaster.interfaces;

import com.resultadosmaster.model.Partido;
import com.resultadosmaster.model.Torneo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ResultadosAPI {

    @GET("/api/v1/torneos")
    Call<List<Torneo>> getAllTorneos();
    @GET("/api/v1/torneos/year/{year}")
    Call<List<Torneo>> getTorneosByYear(@Path("year") int year);

    @GET("/api/v1/partidos/torneo/{torneoId}/partidos")
    Call<List<Partido>> getPartidosByTorneo(@Path("torneoId") long torneoId);

    // Agrega otros métodos de la API según sea necesario
}
