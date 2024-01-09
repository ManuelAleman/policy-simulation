package com.example.proyectoappmovilfinal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.proyectoappmovilfinal.actividades.ActividadCuatro;
import com.example.proyectoappmovilfinal.actividades.ActividadDos;
import com.example.proyectoappmovilfinal.actividades.ActividadUno;
import com.example.proyectoappmovilfinal.extras.ConexionBaseDatos;

public class MainActivity extends AppCompatActivity {
    private static ConexionBaseDatos conexion;
    private static SQLiteDatabase baseDatos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        conexion = ConexionBaseDatos.getInstanciaBaseDatos(this);
        baseDatos = conexion.getWritableDatabase();

        Toolbar toolbar = findViewById(R.id.toolbarPrincipal);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_opciones, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.actividadUno){
            Intent intent = new Intent(this, ActividadUno.class);
            Toast.makeText(this, "Actividad uno", Toast.LENGTH_SHORT).show();
            startActivity(intent);
            return true;
        }

        if(id == R.id.actividadDos){
            Intent intent = new Intent(this, ActividadDos.class);
            Toast.makeText(this, "Actividad dos", Toast.LENGTH_SHORT).show();
            startActivity(intent);
            return true;
        }
        if(id == R.id.actividadTres){
            Intent intent = new Intent(this, ActividadCuatro.class);
            Toast.makeText(this, "Actividad Cuatro", Toast.LENGTH_SHORT).show();
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public static ConexionBaseDatos getConexion() {
        return conexion;
    }

    public static SQLiteDatabase getBaseDatos() {
        return baseDatos;
    }
}