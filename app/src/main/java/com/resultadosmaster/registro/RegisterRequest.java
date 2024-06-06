package com.resultadosmaster.registro;

public class RegisterRequest {
    /** El nombre proporcionado en la solicitud de registro. */
    private String nombre;

    /** Los apellidos proporcionados en la solicitud de registro. */
    private String apellidos;


    /** El correo electrónico proporcionado en la solicitud de registro. */
    private String correo;


    /** La contraseña proporcionada en la solicitud de registro. */
    private String contrasena;

    public RegisterRequest(String nombre, String apellidos, String correo, String contrasena) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.correo = correo;
        this.contrasena = contrasena;
    }
}
