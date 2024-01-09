package com.example.proyectoappmovilfinal.actividades;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.proyectoappmovilfinal.extras.ConexionBaseDatos;
import com.example.proyectoappmovilfinal.MainActivity;
import com.example.proyectoappmovilfinal.objetosAux.PolizaObjeto;
import com.example.proyectoappmovilfinal.R;
import com.example.proyectoappmovilfinal.extras.Rutinas;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ActividadDos extends AppCompatActivity implements View.OnClickListener {
    private static ConexionBaseDatos conexion;
    private static SQLiteDatabase baseDatos;
    private EditText poliza;
    private EditText cuenta1, cuenta2, cuenta3, cuenta4, cuenta5;
    private EditText cargo1, cargo2, cargo3, cargo4, cargo5;
    private EditText abono1, abono2, abono3, abono4, abono5;
    private Button botonValidar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividad_dos);

        conexion = MainActivity.getConexion();
        baseDatos = conexion.getWritableDatabase();

        poliza = findViewById(R.id.editTextNumberNumeroPoliza);
        cuenta1 = findViewById(R.id.editTextCuenta1);
        cuenta2 = findViewById(R.id.editTextCuenta2);
        cuenta3 = findViewById(R.id.editTextCuenta3);
        cuenta4 = findViewById(R.id.editTextCuenta4);
        cuenta5 = findViewById(R.id.editTextCuenta5);
        cargo1 = findViewById(R.id.editTextCargo1);
        cargo2 = findViewById(R.id.editTextCargo2);
        cargo3 = findViewById(R.id.editTextCargo3);
        cargo4 = findViewById(R.id.editTextCargo4);
        cargo5 = findViewById(R.id.editTextCargo5);
        abono1 = findViewById(R.id.editTextAbono1);
        abono2 = findViewById(R.id.editTextAbono2);
        abono3 = findViewById(R.id.editTextAbono3);
        abono4 = findViewById(R.id.editTextAbono4);
        abono5 = findViewById(R.id.editTextAbono5);
        botonValidar = findViewById(R.id.botonAplicarPoliza);

        botonValidar.setOnClickListener(this);
    }

    public int tipoMovimiento( EditText cargo, EditText abono) {
        if(cargo.getText().toString().isEmpty())
            return 2;
        else if(abono.getText().toString().isEmpty())
            return  1;

        return 0;
    }

    public boolean validarNivelTres(String cuentaString){
        if(cuentaString.substring(4,6).equals("00"))
            return false;

        return true;
    }

    public boolean existe(String[] cuentas, String cuenta){
        for(int i = 0 ; i<cuentas.length ; i++){
            if(cuentas[i] != null){
                if(cuentas[i].equals(cuenta))
                    return true;
            }
        }
        return false;
    }

    public void aplicarCargos(String query){
        try {
            baseDatos.execSQL(query);
        }catch (Exception e){}
    }

    @Override
    public void onClick(View v) {
        if (v == botonValidar) {
            int camposLlenos = 0;
            int cargo=0, abono=0;
            String numeroPoliza = poliza.getText().toString();

            if (numeroPoliza.isEmpty()) {
                Rutinas.mensajeDialog("No se ingreso un numero de Poliza", this);
                return;
            }

            String existePoliza = "SELECT * FROM polizas WHERE poliza = " + numeroPoliza;
            try {
                Cursor cursor = baseDatos.rawQuery(existePoliza, null);
                if (cursor.moveToNext()) {
                    Rutinas.mensajeDialog("El numero de poliza ya existe", this);
                    return;
                }
            } catch (Exception e) {
            }

            String[] cuentas = new String[5];
            String[] cargos = new String[5];
            int[] tipoMovimiento = new int[5];


            //poliza numero 1
            String cuenta1String = cuenta1.getText().toString();
            String cargo1String = cargo1.getText().toString();
            String abono1String = abono1.getText().toString();

            if (!cuenta1String.isEmpty() && (!cargo1String.isEmpty() || !abono1String.isEmpty())) {
                if (validarNivelTres(cuenta1String)) {
                    if(!existe(cuentas, cuenta1String)){
                        System.out.println("entro 1");
                        String existeCuenta1 = "SELECT * FROM catalogo WHERE cuenta = " + cuenta1String;
                        try {
                            Cursor cursor = baseDatos.rawQuery(existeCuenta1, null);
                            if (cursor.moveToNext()) {
                                int n = tipoMovimiento(cargo1, abono1);
                                System.out.println("n: " + n);
                                if (n != 0) {
                                    tipoMovimiento[0] = n;
                                    cargos[0] = n == 1 ? cargo1.getText().toString() : abono1.getText().toString();
                                    cuentas[0] = cuenta1String;
                                    camposLlenos++;
                                    if (n == 1)
                                        cargo += Integer.parseInt(cargos[0]);
                                    else
                                        abono += Integer.parseInt(cargos[0]);
                                }
                            }
                        } catch (Exception e) {
                        }
                    }
                }

            }

            //poliza numero 2
            String cuenta2String = cuenta2.getText().toString();
            String cargo2String = cargo2.getText().toString();
            String abono2String = abono2.getText().toString();

            if (!cuenta2String.isEmpty() && (!cargo2String.isEmpty() || !abono2String.isEmpty())) {
                if (validarNivelTres(cuenta2String)) {
                    if(!existe(cuentas, cuenta2String)){
                        System.out.println("entro 2");
                        String existeCuenta1 = "SELECT * FROM catalogo WHERE cuenta = " + cuenta2String;
                        try {
                            Cursor cursor = baseDatos.rawQuery(existeCuenta1, null);
                            if (cursor.moveToNext()) {
                                int n = tipoMovimiento(cargo2, abono2);
                                System.out.println("n: " + n);
                                if (n != 0) {
                                    tipoMovimiento[1] = n;
                                    cargos[1] = n == 1 ? cargo2.getText().toString() : abono2.getText().toString();
                                    cuentas[1] = cuenta2String;
                                    camposLlenos++;
                                    if (n == 1)
                                        cargo += Integer.parseInt(cargos[1]);
                                    else
                                        abono += Integer.parseInt(cargos[1]);
                                }
                            }
                        } catch (Exception e) {
                        }

                    }
                }
            }

            //poliza numero 3
            String cuenta3String = cuenta3.getText().toString();
            String cargo3String = cargo3.getText().toString();
            String abono3String = abono3.getText().toString();

            if (!cuenta3String.isEmpty() && (!cargo3String.isEmpty() || !abono3String.isEmpty())) {
                if(validarNivelTres(cuenta3String)) {
                    if(!existe(cuentas, cuenta3String)) {
                        System.out.println("entro 3");
                        String existeCuenta1 = "SELECT * FROM catalogo WHERE cuenta = " + cuenta3String;
                        try {
                            Cursor cursor = baseDatos.rawQuery(existeCuenta1, null);
                            if (cursor.moveToNext()) {
                                int n = tipoMovimiento(cargo3, abono3);
                                System.out.println("n: en la cuenta 3  " + n);
                                if (n != 0) {
                                    tipoMovimiento[2] = n;
                                    System.out.println("n: cuenta 3  " + n);
                                    cargos[2] = n == 1 ? cargo3.getText().toString() : abono3.getText().toString();
                                    cuentas[2] = cuenta3String;
                                    camposLlenos++;
                                    if (n == 1)
                                        cargo += Integer.parseInt(cargos[2]);
                                    else
                                        abono += Integer.parseInt(cargos[2]);
                                }
                            }
                        } catch (Exception e) {
                        }
                    }
                }
            }

            //poliza numero 4
            String cuenta4String = cuenta4.getText().toString();
            String cargo4String = cargo4.getText().toString();
            String abono4String = abono4.getText().toString();

            if (!cuenta4String.isEmpty() && (!cargo4String.isEmpty() || !abono4String.isEmpty())) {
                if(validarNivelTres(cuenta4String)) {
                    if(!existe(cuentas, cuenta4String)) {
                        System.out.println("entro 4");
                        String existeCuenta1 = "SELECT * FROM catalogo WHERE cuenta = " + cuenta4String;
                        try {
                            Cursor cursor = baseDatos.rawQuery(existeCuenta1, null);
                            if (cursor.moveToNext()) {
                                int n = tipoMovimiento(cargo4, abono4);
                                if (n != 0) {
                                    tipoMovimiento[3] = n;
                                    cargos[3] = n == 1 ? cargo4.getText().toString() : abono4.getText().toString();
                                    cuentas[3] = cuenta4String;
                                    camposLlenos++;
                                    if (n == 1)
                                        cargo += Integer.parseInt(cargos[3]);
                                    else
                                        abono += Integer.parseInt(cargos[3]);
                                }
                            }
                        } catch (Exception e) {
                        }
                    }
                }
            }

            //poliza numero 5
            String cuenta5String = cuenta5.getText().toString();
            String cargo5String = cargo5.getText().toString();
            String abono5String = abono5.getText().toString();

            if (!cuenta5String.isEmpty() && (!cargo5String.isEmpty() || !abono5String.isEmpty())) {
                if(validarNivelTres(cuenta5String)) {
                    if(!existe(cuentas, cuenta5String)) {
                        System.out.println("entro 5");
                        String existeCuenta1 = "SELECT * FROM catalogo WHERE cuenta = " + cuenta5String;
                        try {
                            Cursor cursor = baseDatos.rawQuery(existeCuenta1, null);
                            if (cursor.moveToNext()) {
                                int n = tipoMovimiento(cargo5, abono5);
                                if (n != 0) {
                                    tipoMovimiento[4] = n;
                                    cargos[4] = n == 1 ? cargo5.getText().toString() : abono5.getText().toString();
                                    cuentas[4] = cuenta5String;
                                    camposLlenos++;
                                    if (n == 1)
                                        cargo += Integer.parseInt(cargos[4]);
                                    else
                                        abono += Integer.parseInt(cargos[4]);
                                }
                            }
                        } catch (Exception e) {
                        }
                    }
                }
            }

            if (camposLlenos < 2) {
                Rutinas.mensajeDialog("No a cumplido con el requisito de 2 movimientos", this);
                return;
            }
            if(cargo != abono){
                System.out.println("cargo: " + cargo + " abono: " + abono);
                Rutinas.mensajeDialog("El cargo y el abono no son iguales", this);
                return;
            }

            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            Date fecha = new Date();

            for( int i =0; i<cuentas.length; i++){
                if(cuentas[i] != null){
                    PolizaObjeto polizaObjeto = new PolizaObjeto(numeroPoliza,  df.format(fecha), cuentas[i], tipoMovimiento[i], cargos[i]);
                    String queryPoliza = "INSERT INTO polizas VALUES " +
                            "(" + polizaObjeto.getId() + ", '" + polizaObjeto.getFecha() + "', "
                            + polizaObjeto.getCuenta() + ", " + polizaObjeto.getTipoMonto() + ", "
                            + polizaObjeto.getImporte() + ")";
                    try {
                        baseDatos.execSQL(queryPoliza);
                        Rutinas.mensajeToast("Se ha aplicado la poliza", this);
                    }catch (Exception e){}
                }
            }

            for(int i = 0 ; i<tipoMovimiento.length ; i++){
                if(tipoMovimiento[i] == 0){
                    continue;
                }
                if(tipoMovimiento[i] == 1){
                    String queryCargo = "UPDATE catalogo SET cargo = cargo + " + cargos[i] + " WHERE cuenta = " + cuentas[i];
                    String queryCargoPadre = "UPDATE catalogo SET cargo = cargo + " + cargos[i] + " WHERE cuenta = " + cuentas[i].substring(0,2) + "0000";
                    String queryCargoPadre2 = "UPDATE catalogo SET cargo = cargo + " + cargos[i] + " WHERE cuenta = " + cuentas[i].substring(0,4) + "00";
                    aplicarCargos(queryCargoPadre2);
                    aplicarCargos(queryCargoPadre);
                    aplicarCargos(queryCargo);
                }else{
                    String queryAbono = "UPDATE catalogo SET abono = abono + " + cargos[i] + " WHERE cuenta = " + cuentas[i];
                    String queryAbonoPadre = "UPDATE catalogo SET abono = abono + " + cargos[i] + " WHERE cuenta = " + cuentas[i].substring(0,2) + "0000";
                    String queryAbonoPadre2 = "UPDATE catalogo SET abono = abono + " + cargos[i] + " WHERE cuenta = " + cuentas[i].substring(0,4) + "00";
                    aplicarCargos(queryAbonoPadre2);
                    aplicarCargos(queryAbonoPadre);
                    aplicarCargos(queryAbono);
                }

            }

        }
    }

}