package com.example.proyectoappmovilfinal.actividades;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.example.proyectoappmovilfinal.extras.ConexionBaseDatos;
import com.example.proyectoappmovilfinal.objetosAux.CuentaObjeto;
import com.example.proyectoappmovilfinal.adaptadores.CustomAdapter;
import com.example.proyectoappmovilfinal.MainActivity;
import com.example.proyectoappmovilfinal.R;

import java.util.ArrayList;
import java.util.Comparator;

public class ActividadTres extends AppCompatActivity {
    private static ConexionBaseDatos conexion;
    private static SQLiteDatabase baseDatos;
    private ArrayList<CuentaObjeto> cuentas;
    private StringBuilder formato;
    private String[] datos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividad_tres);

        conexion = MainActivity.getConexion();
        baseDatos = conexion.getWritableDatabase();

        cuentas = new ArrayList<>();
        formato = new StringBuilder();
        recuperarCuentas();

        RecyclerView rv = findViewById(R.id.RecyclerViewActividad3);
        rv.setLayoutManager(new GridLayoutManager(this,1));


        datos = formato.toString().split("\n");
        CustomAdapter adapter = new CustomAdapter(  datos );
        rv.setAdapter(adapter);

    }

    public void recuperarCuentas(){
        ArrayList<CuentaObjeto> subCuentas = new ArrayList<>();
        ArrayList<CuentaObjeto> subsubCuentas = new ArrayList<>();
        String cuenta, nombre, cargo, abono, nivel;
        try {
            String query = "SELECT * FROM CATALOGO";
            Cursor cursor = baseDatos.rawQuery(query, null);
            while (cursor.moveToNext()) {
                cuenta = cursor.getString(0);
                nombre = cursor.getString(1);
                cargo = cursor.getString(2);
                abono = cursor.getString(3);
                nivel = cursor.getString(4);
                if (Integer.parseInt(nivel) == 1) {
                    cuentas.add(new CuentaObjeto(cuenta, nombre, cargo, abono, nivel));
                } else if (Integer.parseInt(nivel) == 2) {
                    subCuentas.add(new CuentaObjeto(cuenta, nombre, cargo, abono, nivel));
                } else {
                    subsubCuentas.add(new CuentaObjeto(cuenta, nombre, cargo, abono, nivel));
                }
            }
        }catch (Exception e){
            System.out.println("Error al recuperar las cuentas");
        }

        cuentas.sort(Comparator.comparing(CuentaObjeto::getId));

        for(int i = 0; i < subCuentas.size(); i++){
            for(int j = 0; j < subsubCuentas.size(); j++){
                if(subCuentas.get(i).getId().equals(subsubCuentas.get(j).getId().substring(0,4)+"00")){
                    subCuentas.get(i).addSubCuenta(subsubCuentas.get(j));
                }
            }
        }

        for(int i = 0; i < cuentas.size(); i++){
            for(int j = 0; j < subCuentas.size(); j++){
                if(cuentas.get(i).getId().equals(subCuentas.get(j).getId().substring(0,2)+"0000")){
                    cuentas.get(i).addSubCuenta(subCuentas.get(j));
                }
            }
        }



        System.out.println("Total de cuentas: "+cuentas.size());
        System.out.println("Total de subcuentas: "+subCuentas.size());
        System.out.println("Total de subsubcuentas: "+subsubCuentas.size());
        cuentas.forEach(c -> metodoRecursivo(c, Integer.parseInt(c.getNivel())));

        System.out.println(formato.toString());

    }


    public void metodoRecursivo(CuentaObjeto c, int nivel){
        String tabs = "";
        for (int i = 0; i < nivel-1; i++) {
            tabs += "\t\t\t\t";
        }
        formato.append(tabs+ " " + c.getId() + " " +c.getNombre()+"\n");
        System.out.println(tabs+c.getNombre());
        if (!c.subCuentasVacio()) {
            for (CuentaObjeto hijo : c.getSubCuentas()) {
                metodoRecursivo(hijo, Integer.parseInt(hijo.getNivel()));
            }
        }
    }
}