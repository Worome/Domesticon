package com.trianacodes.script.domesticon.Entidades;

import com.trianacodes.script.domesticon.sqlite.DatosTablas;

public class ClaseGastos {

    private String Id, Descripcion, Meses, Estado;
    private Double Importe;

    public ClaseGastos(String id, String descripcion, String meses, String estado, Double importe) {
        Id = id;
        Descripcion = descripcion;
        Meses = meses;
        Estado = estado;
        Importe = importe;
    }

    public ClaseGastos(){

        Id = "";
        Descripcion = "";
        Meses = "";
        Estado = "";
        Importe = Double.valueOf(0);

    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    public String getMeses() {
        return Meses;
    }

    public void setMeses(String meses) {
        Meses = meses;
    }

    public String getEstado() {
        return Estado;
    }

    public void setEstado(String estado) {
        Estado = estado;
    }

    public Double getImporte() {
        return Importe;
    }

    public void setImporte(Double importe) {
        Importe = importe;
    }

}
