package com.resultadosmaster.model;

public class Torneo {


    private Long id;
    private String ciudad;
    private String tipo;
    private String fechatorneo;


    public Torneo() {
    }

    public Torneo(Long id, String ciudad, String tipo, String fechatorneo) {
        this.id = id;
        this.ciudad = ciudad;
        this.tipo = tipo;
        this.fechatorneo = fechatorneo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getFechatorneo() {
        return fechatorneo;
    }

    public void setFechatorneo(String fechatorneo) {
        this.fechatorneo = fechatorneo;
    }
}
