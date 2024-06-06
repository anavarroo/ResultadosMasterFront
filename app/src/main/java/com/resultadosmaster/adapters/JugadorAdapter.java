package com.resultadosmaster.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.resultadosmaster.R;
import com.resultadosmaster.model.Jugador;
import com.squareup.picasso.Picasso;

import java.util.List;

public class JugadorAdapter extends BaseAdapter {

    List<Jugador> jugadores;
    Context context;

    TextView nombreText;
    TextView apellidosText;
    TextView numeroText;

    TextView puntosText;




    public JugadorAdapter(List<Jugador> jugadores, Context context) {
        this.jugadores = jugadores;
        this.context = context;
    }

    @Override
    public int getCount() {
        return jugadores.size();
    }

    @Override
    public Object getItem(int i) {
        return jugadores.get(i);
    }

    @Override
    public long getItemId(int i) {
        return jugadores.get(i).getId();
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        if (view == null) {
            // Inflar la vista si no está disponible
            view = LayoutInflater.from(context).inflate(R.layout.player_list, viewGroup, false);
        }

        nombreText = view.findViewById(R.id.nombreText);
        apellidosText = view.findViewById(R.id.apellidosText);
        numeroText = view.findViewById(R.id.numeroText);
        puntosText = view.findViewById(R.id.puntosText);
        ImageView imgView = view.findViewById(R.id.imgView);
        ImageView banderaView = view.findViewById(R.id.banderaView);



        nombreText.setText(jugadores.get(position).getNombre());
        apellidosText.setText(jugadores.get(position).getApellidos());
        numeroText.setText(jugadores.get(position).getNumero_ranking()+ "");
        puntosText.setText(jugadores.get(position).getPuntos());

        // Cargar la imagen con Picasso si la URL de la imagen está disponible
        String imgUrl = jugadores.get(position).getImagen_url();
        if (imgUrl != null && !imgUrl.isEmpty()) {
            float borderRadius = 25f; // Ajusta el radio según tus preferencias
            Picasso.get().load(imgUrl)
                    .placeholder(R.drawable.baseline_360_24)
                    .error(R.drawable.baseline_360_24)
                    .transform(new RoundRectTransformation(borderRadius)) // Aplicar la transformación de bordes redondos
                    .into(imgView);
        } else {
            // Si no hay URL de imagen disponible, mostrar una imagen predeterminada
            imgView.setImageResource(R.drawable.ic_delete);
        }

        // Cargar la imagen con Picasso si la URL de la imagen está disponible
        String banderaUrl = jugadores.get(position).getBandera_url();
        if (banderaUrl != null && !banderaUrl.isEmpty()) {
            Picasso.get().load(banderaUrl)
                    .placeholder(R.drawable.baseline_360_24)
                    .error(R.drawable.baseline_360_24)
                    .into(banderaView);
        } else {
            // Si no hay URL de imagen disponible, mostrar una imagen predeterminada
            banderaView.setImageResource(R.drawable.ic_delete);
        }

        return view;


    }
}
