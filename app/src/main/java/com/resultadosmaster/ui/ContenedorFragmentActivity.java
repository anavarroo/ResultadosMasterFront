package com.resultadosmaster.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.resultadosmaster.R;

public class ContenedorFragmentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contenedorfragmentactivity);

        // Referencia al BottomNavigationView y seleccionar la opción de inicio
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.navigation_resultados);

        // Obtener el NavController del NavHostFragment para gestionar la navegación entre fragmentos
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        NavController navController = navHostFragment.getNavController();

        // Configurar un listener para el BottomNavigationView para navegar entre fragmentos
        bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.navigation_resultados){
                navController.navigate(R.id.resultadosFragment);
            } else if (item.getItemId() == R.id.navigation_ranking){
                navController.navigate(R.id.rankingFragment);
            } else if (item.getItemId() == R.id.navigation_historia){
                navController.navigate(R.id.historiaFragment);
            }
            return true;
        });

        // Después de que el usuario ha iniciado sesión correctamente, muestra el Toast
        showToast("Logueado Correctamente");

        // Obtener referencia al headerTextView
        TextView headerTextView = findViewById(R.id.headerTextView);
        // Agregar un listener de clic al headerTextView
        headerTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navegar al fragmento Home cuando se hace clic en headerTextView
                navController.navigate(R.id.homeFragment);
            }
        });

        // Obtener referencia al textViewDetalles
        TextView textViewDetalles = findViewById(R.id.textViewDetalles);
        // Agregar un listener de clic al textViewDetalles
        textViewDetalles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Abrir el enlace en el navegador web cuando se hace clic en textViewDetalles
                Uri uri = Uri.parse("https://www.a1padelglobal.com/");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
    }

    /**
     * Método para mostrar un Toast con un mensaje específico.
     *
     * @param message El mensaje a mostrar en el Toast.
     */
    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
