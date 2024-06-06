    package com.resultadosmaster.ui;

    import android.content.Intent;
    import android.content.SharedPreferences;
    import android.os.Bundle;
    import android.text.SpannableString;
    import android.text.Spanned;
    import android.text.TextUtils;
    import android.text.method.PasswordTransformationMethod;
    import android.text.style.UnderlineSpan;
    import android.util.Log;
    import android.view.MotionEvent;
    import android.view.View;
    import android.widget.Button;
    import android.widget.EditText;
    import android.widget.ImageButton;
    import android.widget.TextView;
    import android.widget.Toast;

    import androidx.annotation.NonNull;
    import androidx.annotation.Nullable;
    import androidx.appcompat.app.AppCompatActivity;

    import com.google.android.gms.auth.api.signin.GoogleSignIn;
    import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
    import com.google.android.gms.auth.api.signin.GoogleSignInClient;
    import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
    import com.google.android.gms.common.api.ApiException;
    import com.google.android.gms.tasks.OnCompleteListener;
    import com.google.android.gms.tasks.Task;
    import com.google.firebase.auth.AuthCredential;
    import com.google.firebase.auth.AuthResult;
    import com.google.firebase.auth.FirebaseAuth;
    import com.google.firebase.auth.FirebaseUser;
    import com.google.firebase.auth.GoogleAuthProvider;
    import com.resultadosmaster.R;
    import com.resultadosmaster.registro.AuthResponse;
    import com.resultadosmaster.registro.LoginRequest;
    import com.resultadosmaster.registro.RegisterRequest;
    import com.resultadosmaster.registro.RegisterService;
    import com.resultadosmaster.registro.UserDtoRegister;

    import java.security.SecureRandom;

    import retrofit2.Call;
    import retrofit2.Callback;
    import retrofit2.Response;
    import retrofit2.Retrofit;
    import retrofit2.converter.gson.GsonConverterFactory;


    /**
     * Actividad para iniciar sesión.
     */
    public class Login extends AppCompatActivity {

        private TextView textView;
        private GoogleSignInClient client;

        private TextView mensaje;
        private EditText user;
        private EditText password;
        private Retrofit retrofit;
        private RegisterService registerService;

        private EditText passwordEditText;
        private boolean passwordVisible = false;


        private Button button;



        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.login);

            // Inicialización de elementos de la interfaz de usuario
            passwordEditText = findViewById(R.id.inputPassword);

            button = findViewById(R.id.buttonLoging);
            underlineText(button);

            // Inicialización de elementos de la interfaz de usuario y configuración de Google Sign-In
            textView = findViewById(R.id.signInWithGoogle);
            GoogleSignInOptions options = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(getString(R.string.default_web_client_id))
                    .requestEmail()
                    .build();
            client = GoogleSignIn.getClient(this,options);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = client.getSignInIntent();
                    startActivityForResult(i,1234);

                }
            });

            // Agregar OnClickListener al DrawableEnd del EditText
            passwordEditText.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    final int DRAWABLE_RIGHT = 2;
                    if(event.getAction() == MotionEvent.ACTION_UP) {
                        if(event.getRawX() >= (passwordEditText.getRight() - passwordEditText.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                            // Si se hizo clic en el ícono del DrawableEnd
                            togglePasswordVisibility();
                            return true;
                        }
                    }
                    return false;
                }
            });


            // Inicialización de campos de texto y botones
            user = findViewById(R.id.inputUser);
            password = findViewById(R.id.inputPassword);

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
                passwordEditText.setTransformationMethod(PasswordTransformationMethod.getInstance());
                passwordVisible = false;
            } else {
                // Si la contraseña está oculta, mostrarla
                passwordEditText.setTransformationMethod(null);
                passwordVisible = true;
            }
            // Mover el cursor al final del texto
            passwordEditText.setSelection(passwordEditText.getText().length());
        }

        public void onRegisterButtonClick(View view) {
            Intent intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);
            finish();
        }


        @Override
        protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            if(requestCode == 1234){
                Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                try {
                    GoogleSignInAccount account = task.getResult(ApiException.class);
                    String idToken = account.getIdToken();

                    Log.e("Google ID Token", idToken);
                    AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(),null);
                    FirebaseAuth.getInstance().signInWithCredential(credential)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()){
                                        // Autenticación exitosa, obtener detalles del usuario
                                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                        if (user != null) {

                                            String idToken = account.getIdToken();

                                            Log.e("Google ID Token", idToken);

                                            Log.d("Google User Info", "User ID: " + user.getUid());
                                            Log.d("Google User Info", "Email: " + user.getEmail());
                                            Log.d("Google User Info", "Display Name: " + user.getDisplayName());
                                            Log.d("Google User Info", "Photo URL: " + user.getPhotoUrl());


                                            // Obtener los detalles del usuario
                                            String nombre = user.getDisplayName();
                                            String correo = user.getEmail();

                                            // Separar el nombre completo en nombre y apellidos
                                            String[] partesNombre = nombre.split(" ");
                                            String nombreUsuario = partesNombre[0];
                                            String apellidosUsuario = "";

                                            if (partesNombre.length > 1) {
                                                for (int i = 1; i < partesNombre.length; i++) {
                                                    apellidosUsuario += partesNombre[i] + " ";
                                                }
                                                // Eliminar el espacio adicional al final
                                                apellidosUsuario = apellidosUsuario.trim();
                                            }

                                            String contrasena = "1234";

                                            RegisterRequest registerRequest = new RegisterRequest(nombreUsuario, apellidosUsuario, correo, contrasena);

                                            // Inicializar Retrofit
                                            retrofit = new Retrofit.Builder()
                                                    .baseUrl("http://98.66.187.58:8084/") // Reemplaza "URL_BASE_DE_TU_API" con la URL base de tu API
                                                    .addConverterFactory(GsonConverterFactory.create())
                                                    .build();
                                            // Inicializar ApiService
                                            registerService = retrofit.create(RegisterService.class);
                                            Call<UserDtoRegister> registro = registerService.register(registerRequest);
                                            registro.enqueue(new Callback<UserDtoRegister>() {
                                                @Override
                                                public void onResponse(Call<UserDtoRegister> call, Response<UserDtoRegister> response) {
                                                    if (response.isSuccessful()) {
                                                        // Registro exitoso
                                                        Log.e("Estado Registro: ", "EXITO");
                                                    } else {
                                                        Log.e("Estado Registro: ", "FALLO");
                                                    }
                                                }

                                                @Override
                                                public void onFailure(Call<UserDtoRegister> call, Throwable t) {
                                                    Log.e("Estado Registro: ", "FALLO GORDO");
                                                }

                                            });

                                            // Crear un objeto de solicitud de inicio de sesión con las credenciales del usuario
                                            LoginRequest loginRequest = new LoginRequest(correo, contrasena);

                                            // Realizar una solicitud de inicio de sesión al servidor
                                            Call<AuthResponse> login = registerService.login(loginRequest);
                                            login.enqueue(new Callback<AuthResponse>() {
                                                @Override
                                                public void onResponse(Call<AuthResponse> call, Response<AuthResponse> response) {
                                                    if (response.isSuccessful()) {
                                                        // Obtener el objeto de respuesta
                                                        AuthResponse authResponse = response.body();

                                                        // Obtener el token de autenticación
                                                        String token = authResponse.getToken();

                                                        // Guardar el token JWT en las preferencias compartidas para su uso posterior
                                                        SharedPreferences preferences = getSharedPreferences("AUTH", MODE_PRIVATE);
                                                        SharedPreferences.Editor editor = preferences.edit();
                                                        editor.putString("TOKEN", token);
                                                        editor.apply();
                                                        Log.e("token", token);

                                                        // Redirigir a la actividad principal
                                                        Intent intent = new Intent(Login.this, ContenedorFragmentActivity.class);
                                                        startActivity(intent);
                                                    } else {
                                                        // Manejar errores de autenticación
                                                        Toast.makeText(Login.this, "Pulse en el icono de Google", Toast.LENGTH_SHORT).show();
                                                    }
                                                }

                                                @Override
                                                public void onFailure(Call<AuthResponse> call, Throwable t) {
                                                    // Manejar errores de conexión
                                                    mensaje.setText("Error de conexión");
                                                }


                                            });

                                        }
                                        else {
                                            Log.e("Google User Info", "FirebaseUser is null");
                                        }


                                    }
                                    else {
                                        // Si hay un error, muestra un mensaje de error
                                        Toast.makeText(Login.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }

                                }
                            });

                } catch (ApiException e) {
                    e.printStackTrace();
                }

            }

        }


        public void onClick(View view) {

            // Inicializar Retrofit
            retrofit = new Retrofit.Builder()

                    .baseUrl("http://98.66.187.58:8084/") // Reemplaza "URL_BASE_DE_TU_API" con la URL base de tu API
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            // Inicializar ApiService
            registerService = retrofit.create(RegisterService.class);

            String username= user.getText().toString();
            String userpassword= password.getText().toString();


            // Verificar que los campos no estén vacíos
            if (TextUtils.isEmpty(username) || TextUtils.isEmpty(userpassword)) {
                Toast.makeText(Login.this, "Introduce todos los datos", Toast.LENGTH_SHORT).show();
                return;
            }

            // Crear un objeto de solicitud de inicio de sesión con las credenciales del usuario
            LoginRequest loginRequest = new LoginRequest(username, userpassword);

            // Realizar una solicitud de inicio de sesión al servidor
            Call<AuthResponse> call = registerService.login(loginRequest);
            call.enqueue(new Callback<AuthResponse>() {
                @Override
                public void onResponse(Call<AuthResponse> call, Response<AuthResponse> response) {
                    if (response.isSuccessful()) {
                        // Obtener el objeto de respuesta
                        AuthResponse authResponse = response.body();

                        // Obtener el token de autenticación
                        String token = authResponse.getToken();

                        // Guardar el token JWT en las preferencias compartidas para su uso posterior
                        SharedPreferences preferences = getSharedPreferences("AUTH", MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString("TOKEN", token);
                        editor.apply();

                        Log.e("token", token);

                        // Redirigir a la actividad principal
                        Intent intent = new Intent(Login.this, ContenedorFragmentActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        // Manejar errores de autenticación
                        Toast.makeText(Login.this, "Usuario o Contraseña incorrectos", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<AuthResponse> call, Throwable t) {
                    // Manejar errores de conexión
                    mensaje.setText("Error de conexión");
                }


            });


        }

    }