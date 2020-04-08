package com.trianacodes.script.domesticon.Entidades;

public class ClaseEstados {

    private String Identificador, Descripcion;

    public ClaseEstados(String identificador, String descripcion) {
        Identificador = identificador;
        Descripcion = descripcion;
    }

    public ClaseEstados(){
        Identificador="";
        Descripcion="";
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

}
