package com.example.proyectoappmovilfinal.actividades;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.proyectoappmovilfinal.extras.ConexionBaseDatos;
import com.example.proyectoappmovilfinal.MainActivity;
import com.example.proyectoappmovilfinal.R;
import com.example.proyectoappmovilfinal.extras.Rutinas;
import com.example.proyectoappmovilfinal.viewers.CuentaViewer;

public class ActividadCuatro extends AppCompatActivity implements View.OnClickListener {

    private EditText editTextTextIDAct4;
    private Button botonCuentaSelec, botonPolizaSelec;
    private static ConexionBaseDatos conexion;
    private static SQLiteDatabase baseDatos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividad_cuatro);


        editTextTextIDAct4 = findViewById(R.id.editTextTextIDAct4);
        botonCuentaSelec = findViewById(R.id.botonCuentaSelec);
        botonPolizaSelec = findViewById(R.id.botonPolizaSelec);

        conexion = MainActivity.getConexion();
        baseDatos = conexion.getWritableDatabase();

        botonCuentaSelec.setOnClickListener(this);
        botonPolizaSelec.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        if(v == botonCuentaSelec) {
            String id = editTextTextIDAct4.getText().toString();
            if(id.isEmpty()) {
                Toast.makeText(this, "Ingrese un ID", Toast.LENGTH_SHORT).show();
                return;
            }
            if(id.length() != 6) {
                Toast.makeText(this, "Ingrese un ID válido", Toast.LENGTH_SHORT).show();
                return;
            }



            String existe = "SELECT * FROM catalogo WHERE cuenta = '" + id + "'";
            Cursor cursorExiste = baseDatos.rawQuery(existe, null);
            if(!cursorExiste.moveToNext()) {
                Rutinas.mensajeDialog("NO EXISTE LA CUENTA", this);
                return;
            }

            Intent intent = new Intent(this, CuentaViewer.class);
            intent.putExtra("id", id);
            startActivity(intent);
        }

        if(v == botonPolizaSelec) {
            String id = editTextTextIDAct4.getText().toString();
            if(id.isEmpty()) {
                Toast.makeText(this, "Ingrese un ID", Toast.LENGTH_SHORT).show();
                return;
            }
            String existe = "SELECT * FROM polizas WHERE poliza = '" + id + "'";
            Cursor cursorExiste = baseDatos.rawQuery(existe, null);
            if(!cursorExiste.moveToNext()) {
                Rutinas.mensajeDialog("NO EXISTE LA POLIZA", this);
                return;
            }

            Intent intent = new Intent(this, ActividadPoliza.class);
            intent.putExtra("id", id);
            startActivity(intent);
            return;
        }
    }
}