package com.trianacodes.script.domesticon.Entidades;

public class ClaseBancos {

    private String Identificador;
    private String Descripcion;
    private Double Saldo;

    public ClaseBancos(String identificador, String descripcion, Double saldo) {
        Identificador = identificador;
        Descripcion = descripcion;
        Saldo = saldo;
    }

    public ClaseBancos() {
        Identificador = "";
        Descripcion = "";
        Saldo = Double.valueOf(0);
    }


    public String getIdentificador() {
        return Identificador;
    }

    public void setIdentificador(String identificador) {
        Identificador = identificador;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    public Double getSaldo() {
        return Saldo;
    }

    public void setSaldo(Double saldo) {
        Saldo = saldo;
    }

}

