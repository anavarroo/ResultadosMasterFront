package com.resultadosmaster.ui;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.annotations.NotNull;
import com.resultadosmaster.R;
import com.resultadosmaster.adapters.JugadorAdapter;
import com.resultadosmaster.interfaces.RankingInterface;
import com.resultadosmaster.model.Jugador;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RankingFragment extends Fragment {

    List<Jugador> jugadores;
    RankingInterface rankingInterface;
    ListView listView;
    Spinner spinnerGenres;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ranking, container, false);

        spinnerGenres = view.findViewById(R.id.spinnerGenero);
        listView = view.findViewById(R.id.listView);

        cargarGeneros();
        return view;
    }

    private void cargarGeneros() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, Arrays.asList("MASCULINO", "FEMENINO"));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGenres.setAdapter(adapter);

        // Agregar un listener para escuchar los eventos de selección en el Spinner
        spinnerGenres.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                String generoSeleccionado = (String) adapterView.getItemAtPosition(position);
                getAllByGenero(generoSeleccionado);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // No hacer nada
            }
        });
    }

    private void getAllByGenero(String genero) {

        // Obtener el token JWT de las preferencias compartidas
        SharedPreferences preferences = getActivity().getSharedPreferences("AUTH", MODE_PRIVATE);
        String token = preferences.getString("TOKEN", "");

// Agregar el token JWT al encabezado de autorización de las solicitudes HTTP
        Interceptor interceptor = new Interceptor() {
            @NotNull
            @Override
            public okhttp3.Response intercept(@NotNull Chain chain) throws IOException {
                Request request = chain.request().newBuilder()
                        .addHeader("Authorization", "Bearer " + token)
                        .build();
                return chain.proceed(request);
            }
        };

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();

// Configurar Retrofit con el cliente OkHttpClient personalizado
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://98.66.187.58:8084/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client) // Agregar el cliente personalizado aquí
                .build();


        rankingInterface = retrofit.create(RankingInterface.class);

        Call<List<Jugador>> call = rankingInterface.getAllByGenero(genero);
        call.enqueue(new Callback<List<Jugador>>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<List<Jugador>> call, Response<List<Jugador>> response) {
                if (response.isSuccessful()) {
                    jugadores = response.body();
                    JugadorAdapter jugadorAdapter= new JugadorAdapter(jugadores, getActivity().getApplicationContext());
                    listView.setAdapter(jugadorAdapter);
                    jugadores.forEach(p -> Log.i("Jugadores: ",p.toString()));
                } else {
                    Log.e("Response err: ",response.message());
                }
            }

            @Override
            public void onFailure(Call<List<Jugador>> call, Throwable t) {
                Log.e("Throw err: ",t.getMessage());
            }
        });
    }
}
