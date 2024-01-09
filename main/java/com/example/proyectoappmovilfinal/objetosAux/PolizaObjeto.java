package com.example.proyectoappmovilfinal.objetosAux;

import java.text.SimpleDateFormat;
import java.util.Date;

public class PolizaObjeto {
    private String id;
    private String fecha;
    private String cuenta;
    private int tipoMonto;
    private String importe;


    public PolizaObjeto(String id, String fecha, String cuenta, int tipoMonto, String importe) {
        this.id = id;
        this.fecha = fecha;
        this.cuenta = cuenta;
        this.tipoMonto = tipoMonto;
        this.importe = importe;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getCuenta() {
        return cuenta;
    }

    public void setCuenta(String cuenta) {
        this.cuenta = cuenta;
    }

    public int getTipoMonto() {
        return tipoMonto;
    }

    public void setTipoMonto(int tipoMonto) {
        this.tipoMonto = tipoMonto;
    }

    public String getImporte() {
        return importe;
    }

    public void setImporte(String importe) {
        this.importe = importe;
    }
}
