package com.example.proyectoappmovilfinal.extras;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class ConexionBaseDatos extends SQLiteOpenHelper {

    static public int version = 1;
    private static ConexionBaseDatos instancia = null;
    private String createSqltable1 = "CREATE TABLE CATALOGO (cuenta TEXT primary key CHECK (length(cuenta) = 6)" +
            ", nombre TEXT, cargo INTEGER, abono INTEGER, nivel INTEGER)";
    private String createSqlTable2 = "CREATE TABLE POLIZAS (poliza TEXT, fecha TEXT, cuenta TEXT, tipoMovto INTEGER, importe INTEGER" +
            ", primary key(poliza, cuenta), FOREIGN KEY(cuenta) REFERENCES CATALOGO(cuenta))";

    private ConexionBaseDatos(@Nullable Context context) {
        super(context, "Contabilidad_Proyecto", null, version);
    }

    public static ConexionBaseDatos getInstanciaBaseDatos(Context context){
        if(instancia == null){
            instancia = new ConexionBaseDatos(context);
        }
        return instancia;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createSqltable1);
        db.execSQL(createSqlTable2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS CATALOGO");
        db.execSQL("DROP TABLE IF EXISTS POLIZAS");

        db.execSQL(createSqltable1);
        db.execSQL(createSqlTable2);
    }
}
