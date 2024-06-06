package com.resultadosmaster.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.resultadosmaster.R;
import com.resultadosmaster.model.Torneo;

import java.util.List;

public class TorneoAdapter extends ArrayAdapter<Torneo> {
    private Context context;
    private List<Torneo> torneos;

    public TorneoAdapter(Context context, List<Torneo> torneos) {
        super(context, 0, torneos);
        this.context = context;
        this.torneos = torneos;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_torneo, parent, false);
        }

        // Obtener el torneo actual
        Torneo torneo = torneos.get(position);

        // Obtener referencias a las vistas del diseño de la celda
        TextView nombreTorneoTextView = convertView.findViewById(R.id.textViewNombreTorneo);
        TextView fechaTorneoTextView = convertView.findViewById(R.id.textViewFechaTorneo);
        TextView ciudadTorneoTextView = convertView.findViewById(R.id.textViewCiudadTorneo);

        // Establecer los datos del torneo en las vistas correspondientes
        nombreTorneoTextView.setText(torneo.getTipo());
        fechaTorneoTextView.setText(torneo.getFechatorneo());
        ciudadTorneoTextView.setText(torneo.getCiudad());

        return convertView;
    }
}
