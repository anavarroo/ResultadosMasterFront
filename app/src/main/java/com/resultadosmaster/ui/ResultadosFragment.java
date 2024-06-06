package com.resultadosmaster.ui;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.google.firebase.database.annotations.NotNull;
import com.resultadosmaster.R;
import com.resultadosmaster.adapters.PartidoAdapter;
import com.resultadosmaster.adapters.TorneoAdapter;
import com.resultadosmaster.interfaces.ResultadosAPI;
import com.resultadosmaster.model.Partido;
import com.resultadosmaster.model.Torneo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ResultadosFragment extends Fragment {

    Spinner spinnerYear;
    ListView listViewTorneos;
    ListView listViewPartidos;
    ResultadosAPI api;

    List<Torneo> listaTorneos;
    TorneoAdapter torneoAdapter;

    // Dentro de tu Fragment
    private PartidoAdapter partidoAdapter;


    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_resultados, container, false);

        spinnerYear = view.findViewById(R.id.spinnerYear);
        listViewTorneos = view.findViewById(R.id.listViewTorneos);
        listViewPartidos = view.findViewById(R.id.listViewPartidos);

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
        api = retrofit.create(ResultadosAPI.class);

        // Inicializar el adaptador de torneos
        listaTorneos = new ArrayList<>();
        torneoAdapter = new TorneoAdapter(getContext(), listaTorneos);

        listViewTorneos.setAdapter(torneoAdapter);

        // Inicializar el adaptador de partidos
        partidoAdapter = new PartidoAdapter(getContext(), new ArrayList<>());
        listViewPartidos.setAdapter(partidoAdapter);

        // Cargar años disponibles en el Spinner
        cargarAnios();

        // Escuchar eventos de selección en el Spinner
        spinnerYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                int selectedYear = Integer.parseInt(spinnerYear.getSelectedItem().toString());
                cargarTorneosPorAnio(selectedYear);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // No hacer nada
            }
        });

        // Escuchar eventos de clic en la lista de torneos
        listViewTorneos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
              long torneoId = listaTorneos.get(i).getId();
              cargarPartidosPorTorneo(torneoId);

            }
        });

        return view;
    }

    private void cargarAnios() {
        // Hacer una solicitud para obtener todos los torneos
        Call<List<Torneo>> call = api.getAllTorneos();
        call.enqueue(new Callback<List<Torneo>>() {
            @Override
            public void onResponse(Call<List<Torneo>> call, Response<List<Torneo>> response) {
                if (response.isSuccessful()) {
                    List<Torneo> torneos = response.body();
                    Set<Integer> yearsSet = new HashSet<>();
                    for (Torneo torneo : torneos) {
                        // Extraer el año del torneo y agregarlo al conjunto
                        int year = obtenerAnioDesdeFecha(torneo.getFechatorneo()); // Suponiendo que hay un método para obtener el año de la fecha
                        yearsSet.add(year);
                    }
                    // Convertir el conjunto de años a una lista ordenada
                    List<Integer> yearsList = new ArrayList<>(yearsSet);
                    Collections.sort(yearsList);

                    // Configurar el adaptador del Spinner con los años disponibles
                    ArrayAdapter<Integer> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, yearsList);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerYear.setAdapter(adapter);
                } else {
                    // Manejar el error de respuesta
                }
            }

            @Override
            public void onFailure(Call<List<Torneo>> call, Throwable t) {
                // Manejar el error de la solicitud
            }
        });
    }

    // Método para extraer el año de una fecha (esto es un ejemplo, deberías usar tu propia lógica)
    private int obtenerAnioDesdeFecha(String fecha) {
        // Dividir la cadena de fecha en partes usando el espacio como delimitador
        String[] partes = fecha.split(" ");

        // La última parte debería ser el año
        String posibleAnio = partes[partes.length - 1];

        try {
            // Intentar analizar la cadena del año a un entero
            return Integer.parseInt(posibleAnio);
        } catch (NumberFormatException e) {
            // Manejar el caso en que la cadena no se pueda analizar a un entero
            e.printStackTrace();
            return 0; // O un valor predeterminado que indique un error
        }
    }

    private void cargarTorneosPorAnio(int year) {
        // Hacer una solicitud HTTP para obtener los torneos por año
        Call<List<Torneo>> call = api.getTorneosByYear(year);
        call.enqueue(new Callback<List<Torneo>>() {
            @Override
            public void onResponse(Call<List<Torneo>> call, Response<List<Torneo>> response) {
                if (response.isSuccessful()) {
                    // La respuesta es exitosa, obtenemos la lista de torneos
                    List<Torneo> torneos = response.body();

                    // Ahora actualizamos la lista de torneos en la interfaz de usuario
                    actualizarListaTorneos(torneos);
                } else {
                    // La solicitud no fue exitosa, manejar el error
                    Log.e("Error", "Error al cargar torneos por año: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<Torneo>> call, Throwable t) {
                // La solicitud falló, manejar el error
                Log.e("Error", "Fallo al cargar torneos por año: " + t.getMessage());
            }
        });
    }

    private void actualizarListaTorneos(List<Torneo> torneos) {
        // Limpiar los datos actuales del adaptador
        listaTorneos.clear();

        // Agregar los nuevos torneos al adaptador
        listaTorneos.addAll(torneos);

        // Notificar al adaptador que los datos han cambiado
        torneoAdapter.notifyDataSetChanged();
    }

    private void cargarPartidosPorTorneo(long torneoId) {
        // Hacer una solicitud HTTP para obtener los partidos por torneo
        Call<List<Partido>> call = api.getPartidosByTorneo(torneoId);
        call.enqueue(new Callback<List<Partido>>() {
            @Override
            public void onResponse(Call<List<Partido>> call, Response<List<Partido>> response) {
                if (response.isSuccessful()) {
                    // La respuesta es exitosa, obtenemos la lista de partidos
                    List<Partido> partidos = response.body();

                    // Ahora actualizamos la lista de partidos en la interfaz de usuario
                    actualizarListaPartidos(partidos);
                } else {
                    // La solicitud no fue exitosa, manejar el error
                    Log.e("Error", "Error al cargar partidos por torneo: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<Partido>> call, Throwable t) {
                // La solicitud falló, manejar el error
                Log.e("Error", "Fallo al cargar partidos por torneo: " + t.getMessage());
            }
        });
    }

    private void actualizarListaPartidos(List<Partido> partidos) {
        // Aquí debes tener un adaptador para la lista de partidos, así que asegúrate de tenerlo declarado y configurado
        // Supongamos que tienes un adaptador llamado partidoAdapter
        partidoAdapter.clear();

        // Agregar los nuevos partidos al adaptador
        partidoAdapter.addAll(partidos);

        // Notificar al adaptador que los datos han cambiado
        partidoAdapter.notifyDataSetChanged();
    }
}
