package com.example.proyectoappmovilfinal.actividades;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;

import com.example.proyectoappmovilfinal.extras.ConexionBaseDatos;
import com.example.proyectoappmovilfinal.MainActivity;
import com.example.proyectoappmovilfinal.R;
import com.example.proyectoappmovilfinal.objetosAux.PolizaObjeto;

import java.util.ArrayList;
import java.util.List;

public class ActividadPoliza extends AppCompatActivity {

    private static ConexionBaseDatos conexion;
    private static SQLiteDatabase baseDatos;
    List<PolizaObjeto> listaPolizas;
    private TextView opcionCuenta1, opcionCuenta2, opcionCuenta3, opcionCuenta4, opcionCuenta5;
    private TextView opcionFecha1, opcionFecha2, opcionFecha3, opcionFecha4, opcionFecha5;
    private TextView opcionCargo1, opcionCargo2, opcionCargo3, opcionCargo4, opcionCargo5;
    private TextView opcionAbono1, opcionAbono2, opcionAbono3, opcionAbono4, opcionAbono5;
    private TextView polizaInfoBotID;
    private String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividad_poliza);


        conexion = MainActivity.getConexion();
        baseDatos = conexion.getWritableDatabase();


        opcionCuenta1 = findViewById(R.id.opcionCuenta1);
        opcionCuenta2 = findViewById(R.id.opcionCuenta2);
        opcionCuenta3 = findViewById(R.id.opcionCuenta3);
        opcionCuenta4 = findViewById(R.id.opcionCuenta4);
        opcionCuenta5 = findViewById(R.id.opcionCuenta5);

        opcionFecha1 = findViewById(R.id.opcionFecha1);
        opcionFecha2 = findViewById(R.id.opcionFecha2);
        opcionFecha3 = findViewById(R.id.opcionFecha3);
        opcionFecha4 = findViewById(R.id.opcionFecha4);
        opcionFecha5 = findViewById(R.id.opcionFecha5);

        opcionCargo1 = findViewById(R.id.opcionCargo1);
        opcionCargo2 = findViewById(R.id.opcionCargo2);
        opcionCargo3 = findViewById(R.id.opcionCargo3);
        opcionCargo4 = findViewById(R.id.opcionCargo4);
        opcionCargo5 = findViewById(R.id.opcionCargo5);

        opcionAbono1 = findViewById(R.id.opcionAbono1);
        opcionAbono2 = findViewById(R.id.opcionAbono2);
        opcionAbono3 = findViewById(R.id.opcionAbono3);
        opcionAbono4 = findViewById(R.id.opcionAbono4);
        opcionAbono5 = findViewById(R.id.opcionAbono5);

        polizaInfoBotID = findViewById(R.id.polizaInfoBotID);


        mostrar();
    }

    public void mostrar(){
        id = getIntent().getStringExtra("id");
        listaPolizas = new ArrayList<>();
        int cont=0;

        String poliza, fecha, cuenta, cargo, abono;
        try {
            String query = "SELECT * FROM polizas WHERE poliza = '" + id + "'";
            Cursor cursor = baseDatos.rawQuery(query, null);
            while (cursor.moveToNext()) {
                poliza = cursor.getString(0);
                fecha = cursor.getString(1);
                cuenta = cursor.getString(2);
                cargo = cursor.getString(3);
                abono = cursor.getString(4);
                listaPolizas.add(new PolizaObjeto(poliza,fecha, cuenta, Integer.parseInt(cargo), abono));
                cont++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        for(int i=cont; i<5; i++){
            listaPolizas.add(new PolizaObjeto("","","",0,""));
        }
        for(int i=0; i<listaPolizas.size(); i++){
            if (i == 0) {
                valores(opcionCuenta1, opcionFecha1, opcionCargo1, opcionAbono1, listaPolizas.get(i));
            } else if (i == 1) {
                valores(opcionCuenta2, opcionFecha2, opcionCargo2, opcionAbono2, listaPolizas.get(i));
            } else if (i == 2) {
                valores(opcionCuenta3, opcionFecha3, opcionCargo3, opcionAbono3, listaPolizas.get(i));
            } else if (i == 3) {
                valores(opcionCuenta4, opcionFecha4, opcionCargo4, opcionAbono4, listaPolizas.get(i));
            } else if (i == 4) {
                valores(opcionCuenta5, opcionFecha5, opcionCargo5, opcionAbono5, listaPolizas.get(i));
            }
        }

        polizaInfoBotID.setText("Poliza: " + id);
    }
    private void valores(TextView cuenta, TextView fecha, TextView cargo, TextView abono, PolizaObjeto poliza) {
        if (poliza.getCuenta().isEmpty()) {
            cuenta.setText("");
            fecha.setText("");
            cargo.setText("");
            abono.setText("");
        } else {
            cuenta.setText(poliza.getCuenta());
            fecha.setText(String.valueOf(poliza.getFecha()));
            cargo.setText(String.valueOf(poliza.getTipoMonto()));
            abono.setText(poliza.getImporte());
        }
    }
}