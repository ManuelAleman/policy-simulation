package com.example.proyectoappmovilfinal.viewers;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectoappmovilfinal.adaptadores.AdaptadorOpcionInfo;
import com.example.proyectoappmovilfinal.extras.ConexionBaseDatos;
import com.example.proyectoappmovilfinal.MainActivity;
import com.example.proyectoappmovilfinal.R;
import com.example.proyectoappmovilfinal.objetosAux.CuentaObjeto;

import java.util.ArrayList;
import java.util.List;

public class CuentaViewer extends AppCompatActivity {

    List<CuentaObjeto> listaCuentas;
    private static ConexionBaseDatos conexion;
    private static SQLiteDatabase baseDatos;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuenta_viewer);


        conexion = MainActivity.getConexion();
        baseDatos = conexion.getWritableDatabase();

        mostrar();
    }

    public void mostrar() {
        id = getIntent().getStringExtra("id");
        listaCuentas = new ArrayList<>();
        String query = "SELECT * FROM catalogo WHERE cuenta LIKE '" + id.substring(0, 2) + "%'";

        if (!id.substring(2, 4).equals("00")) {
            query = "SELECT * FROM catalogo WHERE cuenta LIKE '" + id.substring(0, 4) + "%'";
        }

        if(!id.substring(4, 6).equals("00")) {
            query = "SELECT * FROM catalogo WHERE cuenta LIKE '" + id.substring(0, 6) + "%'";
        }

        String cuenta, nombre, cargo, abono, nivel;
        try {
            Cursor cursor = baseDatos.rawQuery(query, null);
            while (cursor.moveToNext()) {
                cuenta = cursor.getString(0);
                nombre = cursor.getString(1);
                cargo = cursor.getString(2);
                abono = cursor.getString(3);
                nivel = cursor.getString(4);
                listaCuentas.add(new CuentaObjeto(cuenta, nombre, cargo, abono, nivel));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        AdaptadorOpcionInfo adaptador = new AdaptadorOpcionInfo(listaCuentas, this);
        RecyclerView recyclerView = findViewById(R.id.recyclerViewViewer);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adaptador);
    }
}
