package com.resultadosmaster.model;

public class Partido {


    private Long id;
    private Long torneoId;
    private String ronda;
    private String fecha_hora;
    private String pareja_1;
    private String pareja_2;
    private String resultado;


    public Partido() {
    }

    public Partido(Long id, Long torneoId, String ronda, String fecha_hora, String pareja_1, String pareja_2, String resultado) {
        this.id = id;
        this.torneoId = torneoId;
        this.ronda = ronda;
        this.fecha_hora = fecha_hora;
        this.pareja_1 = pareja_1;
        this.pareja_2 = pareja_2;
        this.resultado = resultado;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTorneoId() {
        return torneoId;
    }

    public void setTorneoId(Long torneoId) {
        this.torneoId = torneoId;
    }

    public String getRonda() {
        return ronda;
    }

    public void setRonda(String ronda) {
        this.ronda = ronda;
    }

    public String getFecha_hora() {
        return fecha_hora;
    }

    public void setFecha_hora(String fecha_hora) {
        this.fecha_hora = fecha_hora;
    }

    public String getPareja_1() {
        return pareja_1;
    }

    public void setPareja_1(String pareja_1) {
        this.pareja_1 = pareja_1;
    }

    public String getPareja_2() {
        return pareja_2;
    }

    public void setPareja_2(String pareja_2) {
        this.pareja_2 = pareja_2;
    }

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }
}
