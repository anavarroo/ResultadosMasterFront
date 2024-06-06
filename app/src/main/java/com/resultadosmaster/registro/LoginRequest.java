package com.resultadosmaster.registro;


public class LoginRequest {

    /** El nombre de usuario proporcionado en la solicitud de inicio de sesión. */
    private String correo;

    /** La contraseña proporcionada en la solicitud de inicio de sesión. */
    private String contrasena;


    public LoginRequest(String correo, String contrasena) {
        this.correo = correo;
        this.contrasena = contrasena;
    }
}