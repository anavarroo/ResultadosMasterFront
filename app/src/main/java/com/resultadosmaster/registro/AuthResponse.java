package com.resultadosmaster.registro;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Representa una respuesta de autenticación.
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class AuthResponse {

    /** El token de autenticación generado. */
    private String token;

    // Constructor sin argumentos
    public AuthResponse() {
    }

    // Constructor con todos los argumentos
    public AuthResponse(String token) {
        this.token = token;
    }

    // Getters y setters

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
