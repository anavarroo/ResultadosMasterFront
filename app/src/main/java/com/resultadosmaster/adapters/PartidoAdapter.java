package com.resultadosmaster.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.resultadosmaster.model.Partido;
import com.resultadosmaster.R;

import java.util.List;

public class PartidoAdapter extends ArrayAdapter<Partido> {

    public PartidoAdapter(Context context, List<Partido> partidos) {
        super(context, 0, partidos);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Partido partido = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_partido, parent, false);
        }

        TextView tvRonda = convertView.findViewById(R.id.tvRonda);
        TextView tvFechaHora = convertView.findViewById(R.id.tvFechaHora);
        TextView tvPareja1 = convertView.findViewById(R.id.tvPareja1);
        TextView tvPareja2 = convertView.findViewById(R.id.tvPareja2);
        TextView tvResultado = convertView.findViewById(R.id.tvResultado);

        tvRonda.setText(partido.getRonda());
        tvFechaHora.setText(partido.getFecha_hora());
        tvPareja1.setText(partido.getPareja_1());
        tvPareja2.setText(partido.getPareja_2());
        tvResultado.setText(partido.getResultado());

        return convertView;
    }
}
