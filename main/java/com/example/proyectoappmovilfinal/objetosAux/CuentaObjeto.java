package com.example.proyectoappmovilfinal.objetosAux;

import java.util.ArrayList;

public class CuentaObjeto {
    private String id;
    private String nombre;
    private String cargo;
    private String abono;
    private String nivel;
    private ArrayList<CuentaObjeto> subCuentas;



    public CuentaObjeto(String id, String nombre, String cargo, String abono, String nivel) {
        this.id = id;
        this.nombre = nombre;
        this.cargo = cargo;
        this.abono = abono;
        this.nivel = nivel;
        this.subCuentas = new ArrayList<>();
    }

    public void addSubCuenta(CuentaObjeto subCuenta) {
        this.subCuentas.add(subCuenta);
    }

    public boolean subCuentasVacio() {
        return this.subCuentas.isEmpty();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public ArrayList<CuentaObjeto> getSubCuentas() {
        return subCuentas;
    }

    public String getNivel() {
        return nivel;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getAbono() {
        return abono;
    }

    public void setAbono(String abono) {
        this.abono = abono;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    public void setSubCuentas(ArrayList<CuentaObjeto> subCuentas) {
        this.subCuentas = subCuentas;
    }
}
