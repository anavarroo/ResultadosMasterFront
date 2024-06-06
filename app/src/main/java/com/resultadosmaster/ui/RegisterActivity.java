package com.resultadosmaster.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.text.style.UnderlineSpan;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.resultadosmaster.R;

import com.resultadosmaster.registro.RegisterRequest;
import com.resultadosmaster.registro.RegisterService;
import com.resultadosmaster.registro.UserDtoRegister;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterActivity extends AppCompatActivity {

    private TextView mensaje;

    private Retrofit retrofit;
    private RegisterService registerService;

    private EditText inputNombre, inputApellidos, inputCorreo, password;
    private boolean passwordVisible = false;

    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Registro
        inputNombre = findViewById(R.id.inputNombre);
        inputApellidos = findViewById(R.id.inputApellidos);
        inputCorreo = findViewById(R.id.inputCorreo);
        password = findViewById(R.id.inputContrasena);

        button = findViewById(R.id.btnInicioSesion);
        underlineText(button);


        // Agregar OnClickListener al DrawableEnd del EditText de la contraseña
        password.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_RIGHT = 2;
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (password.getRight() - password.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        // Si se hizo clic en el ícono del DrawableEnd
                        togglePasswordVisibility();
                        return true;
                    }
                }
                return false;
            }
        });
    }

    // Método onClick para el botón "Iniciar Sesión"
    public void onLoginButtonClick(View view) {
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
        finish();
    }

    // Método para subrayar el texto del botón
    private void underlineText(Button button) {
        String buttonText = button.getText().toString();
        SpannableString content = new SpannableString(buttonText);
        content.setSpan(new UnderlineSpan(), 0, content.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        button.setText(content);
    }

    private void togglePasswordVisibility() {
        if (passwordVisible) {
            // Si la contraseña es visible, ocultarla
            password.setTransformationMethod(new PasswordTransformationMethod());
            passwordVisible = false;
        } else {
            // Si la contraseña está oculta, mostrarla
            password.setTransformationMethod(null);
            passwordVisible = true;
        }
        // Mover el cursor al final del texto
        password.setSelection(password.getText().length());
    }

    public void onClickRegistro(View view) {

        String nombre = inputNombre.getText().toString();
        String apellidos = inputApellidos.getText().toString();
        String correo = inputCorreo.getText().toString();
        String contrasena = password.getText().toString();

        // Verificar si algún campo está vacío
        if (TextUtils.isEmpty(nombre) || TextUtils.isEmpty(apellidos) || TextUtils.isEmpty(correo) || TextUtils.isEmpty(contrasena)) {
            // Mostrar un Toast indicando que todos los campos deben estar rellenos
            Toast.makeText(this, "Todos los campos deben estar rellenos", Toast.LENGTH_SHORT).show();
            return;
        }

        RegisterRequest registerRequest = new RegisterRequest(nombre, apellidos, correo, contrasena);

        // Inicializar Retrofit
        retrofit = new Retrofit.Builder()
                .baseUrl("http://98.66.187.58:8084/") // Reemplaza "URL_BASE_DE_TU_API" con la URL base de tu API
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        // Inicializar ApiService
        registerService = retrofit.create(RegisterService.class);

        Call<UserDtoRegister> call = registerService.register(registerRequest);
        call.enqueue(new Callback<UserDtoRegister>() {
            @Override
            public void onResponse(Call<UserDtoRegister> call, Response<UserDtoRegister> response) {
                if (response.isSuccessful()) {
                    // Registro exitoso
                    Toast.makeText(RegisterActivity.this, "Registro exitoso", Toast.LENGTH_SHORT).show();

                    // Redirigir al usuario a la actividad de inicio de sesión (Login)
                    Intent intent = new Intent(RegisterActivity.this, Login.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(RegisterActivity.this, "Fallo en el registro", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserDtoRegister> call, Throwable t) {
            }

        });
    }
}
