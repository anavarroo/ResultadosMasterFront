package com.resultadosmaster.model;

public class Jugador {


    private Long id;
    private String nombre;
    private String apellidos;
    private Long numero_ranking;
    private String puntos;
    private String imagen_url;

    private String bandera_url;

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    private String genero;

    public Jugador() {

    }

    public Jugador(Long id, String nombre, String apellidos, Long numero_ranking, String puntos, String imagen_url, String bandera_url, String genero) {
        this.id = id;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.numero_ranking = numero_ranking;
        this.puntos = puntos;
        this.imagen_url = imagen_url;
        this.bandera_url = bandera_url;
        this.genero = genero;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public Long getNumero_ranking() {
        return numero_ranking;
    }

    public void setNumero_ranking(Long numero_ranking) {
        this.numero_ranking = numero_ranking;
    }

    public String getPuntos() {
        return puntos;
    }

    public void setPuntos(String puntos) {
        this.puntos = puntos;
    }

    public String getImagen_url() {
        return imagen_url;
    }

    public void setImagen_url(String imagen_url) {
        this.imagen_url = imagen_url;
    }


    public String getBandera_url() {
        return bandera_url;
    }

    public void setBandera_url(String bandera_url) {
        this.bandera_url = bandera_url;
    }

    @Override
    public String toString() {
        return "Jugador{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", numero_ranking=" + numero_ranking +
                ", puntos='" + puntos + '\'' +
                ", imagen_url='" + imagen_url + '\'' +
                ", bandera_url='" + bandera_url + '\'' +
                '}';
    }
}
