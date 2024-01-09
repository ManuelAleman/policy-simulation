package com.example.proyectoappmovilfinal.actividades;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.proyectoappmovilfinal.extras.ConexionBaseDatos;
import com.example.proyectoappmovilfinal.MainActivity;
import com.example.proyectoappmovilfinal.R;
import com.example.proyectoappmovilfinal.extras.Rutinas;

public class ActividadUno extends AppCompatActivity  implements View.OnClickListener{

    private static ConexionBaseDatos conexion;
    private static SQLiteDatabase baseDatos;
    private Button botonRecuperar, botonLimpiar, botonGrabar, botonBorrar, botonModificar, botonConsultar;
    private EditText campoCuenta, campoNombre, campoCargo, campoAbono;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividad_uno);

        botonRecuperar = findViewById(R.id.botonRecuperar);
        botonLimpiar = findViewById(R.id.botonLimpiar);
        botonGrabar = findViewById(R.id.botonGrabar);
        botonBorrar = findViewById(R.id.botonBorrar);
        botonModificar = findViewById(R.id.botonModificar);
        botonConsultar = findViewById(R.id.botonConsultar);

        campoCuenta = findViewById(R.id.escribirCuentaActividadUno);
        campoNombre = findViewById(R.id.escribirNombreActividadUno);
        campoCargo = findViewById(R.id.escribirCargoActividadUno);
        campoAbono = findViewById(R.id.escribirAbonoActividadUno);

        campoCuenta.requestFocus();
        campoCargo.setText("0");
        campoAbono.setText("0");

        botonRecuperar.setOnClickListener(this);
        botonLimpiar.setOnClickListener(this);
        botonGrabar.setOnClickListener(this);
        botonBorrar.setOnClickListener(this);
        botonModificar.setOnClickListener(this);
        botonConsultar.setOnClickListener(this);

        conexion = MainActivity.getConexion();
        baseDatos = conexion.getWritableDatabase();


        String query = "SELECT * FROM CATALOGO";
        Cursor c = baseDatos.rawQuery(query, null);
        while(c.moveToNext()){
            String cuenta = c.getString(0);
            String nombre = c.getString(1);
            int cargo = c.getInt(2);
            int abono = c.getInt(3);
            int nivel = c.getInt(4);
            System.out.println(cuenta + " " + nombre + " " + cargo + " " + abono + " " + nivel);
        }

        if(conexion == null){
            Rutinas.mensajeDialog("No se a creado la base datos", this);
        }
    }

    public void limpiar(){
        campoCuenta.setText("");
        campoNombre.setText("");
        campoCargo.setText("0");
        campoAbono.setText("0");
    }

    public boolean tieneSubcuentas(String query){
        Cursor c = baseDatos.rawQuery(query, null);
        if(c.getCount() > 0){
            return true;
        }
        return false;
    }

    public boolean validarDinero(String query){
        Cursor c = baseDatos.rawQuery(query, null);
        if (c.moveToFirst()) {
            int cargo = c.getInt(0);
            int abono = c.getInt(1);
            if (cargo != 0 || abono != 0) {
                limpiar();
                return true;
            }
        }
        return false;
    }


    @Override
    public void onClick(View v) {
        if(v == botonRecuperar){
            String id = campoCuenta.getText().toString();

            String query = "SELECT * FROM CATALOGO WHERE cuenta = " + id;
            if(id.isEmpty()){
                Rutinas.mensajeDialog("Debe escribir un id", this);
                limpiar();
                return;
            }

            if(id.length() != 6){
                Rutinas.mensajeDialog("El id debe tener 6 caracteres", this);
                limpiar();
                return;
            }

            Cursor c = baseDatos.rawQuery(query, null);
            c.moveToFirst();

            if(c.getCount() == 0){
                Rutinas.mensajeDialog("No se encontro el id", this);
                limpiar();
                return;
            }
            campoNombre.setText(c.getString(1));
            campoCargo.setText(c.getString(2));
            campoAbono.setText(c.getString(3));

            return;
        }
        if(v == botonLimpiar){
            limpiar();
            return;
        }
        if(v == botonGrabar){
            int nivel = 1;

            String cuenta = campoCuenta.getText().toString();
            String cuentaPadre = "";

            if(cuenta.isEmpty()){
                Rutinas.mensajeDialog("Debe escribir un id", this);
                limpiar();
                return;
            }

            if(cuenta.length() != 6){
                Rutinas.mensajeDialog("El id debe tener 6 caracteres", this);
                limpiar();
                return;
            }

            if(campoNombre.getText().toString().isEmpty()){
                Rutinas.mensajeDialog("Debe escribir un nombre", this);
                limpiar();
                return;
            }

            if(!cuenta.substring(2, 4).equals("00")){
                cuentaPadre = cuenta.substring(0, 2) + "0000";
                nivel = 2;
            }

            if(!cuenta.substring(4, 6).equals("00")){
                cuentaPadre = cuenta.substring(0, 4) + "00";
                nivel = 3;
            }


            if(!cuentaPadre.isEmpty()){
                String query = "SELECT * FROM CATALOGO WHERE cuenta = '" + cuentaPadre + "'";
                Cursor c = baseDatos.rawQuery(query, null);
                c.moveToFirst();

                if(c.getCount() == 0){
                    Rutinas.mensajeDialog("No se encontro el id padre", this);
                    limpiar();
                    return;
                }
            }

            String query = "INSERT INTO CATALOGO VALUES('" + cuenta + "', '" + campoNombre.getText().toString() + "', " + campoCargo.getText().toString() + ", " + campoAbono.getText().toString() + ", " + nivel + ")";
            try {
                baseDatos.execSQL(query);
            }catch ( Exception e ){
                Rutinas.mensajeDialog("No se pudo grabar", this);
                limpiar();
                return;
            }

            if(nivel==3) {
                String queryCargo1 = "UPDATE CATALOGO SET cargo = cargo+'" + Integer.parseInt(campoCargo.getText().toString())
                        + "' WHERE cuenta = '" + cuenta.substring(0, 2) + "0000" + "'";
                String queryAbono1 = "UPDATE CATALOGO SET abono = abono+'" + Integer.parseInt(campoAbono.getText().toString())
                        + "' WHERE cuenta = '" + cuenta.substring(0, 2) + "0000" + "'";
                String queryCargo2 = "UPDATE CATALOGO SET cargo = cargo+'" + Integer.parseInt(campoCargo.getText().toString())
                        + "' WHERE cuenta = '" + cuenta.substring(0, 4) + "00" + "'";
                String queryAbono2 = "UPDATE CATALOGO SET abono = abono+'" + Integer.parseInt(campoAbono.getText().toString())
                        + "' WHERE cuenta = '" + cuenta.substring(0, 4) + "00" + "'";
                try {
                    baseDatos.execSQL(queryCargo1);
                    baseDatos.execSQL(queryAbono1);
                    baseDatos.execSQL(queryCargo2);
                    baseDatos.execSQL(queryAbono2);
                } catch (SQLiteException e) {
                    return;
                }
            }
            if(nivel==2) {
                String queryCargo1 = "UPDATE CATALOGO SET cargo = cargo+'" + Integer.parseInt(campoCargo.getText().toString())
                        + "' WHERE cuenta = '" + cuenta.substring(0, 2) + "0000" + "'";
                String queryAbono1 = "UPDATE CATALOGO SET abono = abono+'" + Integer.parseInt(campoAbono.getText().toString())
                        + "' WHERE cuenta = '" + cuenta.substring(0, 2) + "0000" + "'";
                try {
                    baseDatos.execSQL(queryCargo1);
                    baseDatos.execSQL(queryAbono1);
                } catch (SQLiteException e) {
                    return;
                }
            }
            Rutinas.mensajeToast("Se grabo correctamente", this);
            limpiar();
            return;

        }
        if(v == botonBorrar){
            String id = campoCuenta.getText().toString();

            if(id.isEmpty()){
                Rutinas.mensajeDialog("Debe escribir un id", this);
                limpiar();
                return;
            }

            if(id.length() != 6){
                Rutinas.mensajeDialog("El id debe tener 6 caracteres", this);
                limpiar();
                return;
            }

            if(!id.substring(4, 6).equals("00")){
                boolean tieneDinero = validarDinero("SELECT cargo, abono FROM CATALOGO WHERE cuenta = '" + id + "'");
                if(tieneDinero){
                    return;
                }
            }else if(!id.substring(2, 4).equals("00")){
                String query = "SELECT * FROM CATALOGO WHERE cuenta LIKE '" + id.substring(0, 4) + "%' AND cuenta <> '" + id +"'";
                boolean tieneSub = tieneSubcuentas(query);
                boolean tieneDinero = validarDinero("SELECT cargo, abono FROM CATALOGO WHERE cuenta = '" + id + "'");
                if(tieneSub){
                    if(tieneDinero){
                        Rutinas.mensajeDialog("No se puede borrar cuenta de nivel 2 porque tiene dinero", this);
                        return;
                    }
                }

                if(tieneDinero){
                    Rutinas.mensajeDialog("No se puede borrar cuenta de nivel 2 porque tiene dinero", this);
                    return;
                }
            } else {
                System.out.println("entro");
                String query = "SELECT * FROM CATALOGO WHERE cuenta LIKE '" + id.substring(0, 2) + "%' AND cuenta <> '" + id +"'";
                boolean tieneSub = tieneSubcuentas(query);
                boolean tieneDinero = validarDinero("SELECT cargo, abono FROM CATALOGO WHERE cuenta = '" + id + "'");
                if(tieneSub){
                    if(tieneDinero){
                        Rutinas.mensajeDialog("No se puede borrar porque tiene dinero", this);
                        return;
                    }
                }
            }

            String existe = "SELECT * FROM CATALOGO WHERE cuenta = '" + id + "'";
            Cursor c = baseDatos.rawQuery(existe, null);
            c.moveToFirst();
            if(c.getCount() == 0){
                Rutinas.mensajeDialog("No se encontro el id", this);
                limpiar();
                return;
            }

            String queryFinal = "DELETE FROM CATALOGO WHERE cuenta = '" + id + "'";
            try {
                baseDatos.execSQL(queryFinal);
            }catch ( SQLiteException e ){
                Rutinas.mensajeDialog("No se pudo borrar", this);
                limpiar();
                return;
            }
            Rutinas.mensajeToast("Se borro correctamente", this);
            limpiar();
            return;
        }
        if(v == botonModificar){
            String id = campoCuenta.getText().toString();
            String existe = "SELECT * FROM CATALOGO WHERE cuenta = '" + id + "'";
            Cursor c = baseDatos.rawQuery(existe, null);
            c.moveToFirst();
            if(c.getCount() == 0){
                Rutinas.mensajeDialog("No se encontro el id", this);
                limpiar();
                return;
            }

            String nuevoNombre = campoNombre.getText().toString();

            if(nuevoNombre.isEmpty()){
                Rutinas.mensajeDialog("Debe escribir un nombre", this);
                limpiar();
                return;
            }

            String query = "UPDATE CATALOGO SET nombre = '" + nuevoNombre + "' WHERE cuenta = '" + id + "'";
            try {
                baseDatos.execSQL(query);
            }catch ( SQLiteException e ){
                Rutinas.mensajeDialog("No se pudo modificar", this);
                limpiar();
                return;
            }
            Rutinas.mensajeToast("Se modifico correctamente", this);
            limpiar();
            return;
        }
        if(v == botonConsultar){
            Intent intent = new Intent(this, ActividadTres.class);
            Toast.makeText(this, "Actividad tres", Toast.LENGTH_SHORT).show();
            startActivity(intent);
            return;
        }
    }


}