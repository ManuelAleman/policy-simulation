package com.example.proyectoappmovilfinal.adaptadores;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectoappmovilfinal.R;
import com.example.proyectoappmovilfinal.objetosAux.CuentaObjeto;

import java.util.List;

public class AdaptadorOpcionInfo extends RecyclerView.Adapter<AdaptadorOpcionInfo.ViewHolder> {
    private List<CuentaObjeto> listaCuentas;
    private LayoutInflater mInflater;
    private Context context;

    public AdaptadorOpcionInfo(List<CuentaObjeto> listaCuentas, Context context) {
        this.mInflater = LayoutInflater.from(context);
        this.listaCuentas = listaCuentas;
        this.context = context;
    }

    @Override
    public AdaptadorOpcionInfo.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.cuenta_viewer_patron, null);
        return new AdaptadorOpcionInfo.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorOpcionInfo.ViewHolder holder, int position) {
        holder.bindData(listaCuentas.get(position));
    }

    @Override
    public int getItemCount() {
        return listaCuentas.size();
    }


    public void setItems(List<CuentaObjeto> items) {
        listaCuentas = items;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView opcionID, opcionNombre, cargoOpcion, abonoOpcion;
        ViewHolder(View itemView) {
            super(itemView);
            opcionID = itemView.findViewById(R.id.opcionID);
            opcionNombre = itemView.findViewById(R.id.opcionNombre);
            cargoOpcion = itemView.findViewById(R.id.cargoOpcion);
            abonoOpcion = itemView.findViewById(R.id.abonoOpcion);
        }

        void bindData(final CuentaObjeto item) {
            opcionID.setText(item.getId());
            opcionNombre.setText(item.getNombre());
            cargoOpcion.setText("Cargo: " + item.getCargo());
            abonoOpcion.setText("Abono: " + item.getAbono());

            itemView.findViewById(R.id.relativeLayout).setBackground(new ColorDrawable(Color.parseColor("#66b05d")));
            int n = Integer.parseInt(item.getNivel());
            if(n == 3) {
                itemView.findViewById(R.id.relativeLayout).setBackground(new ColorDrawable(Color.parseColor("#a3d977")));
            } else if (n==2) {
                itemView.findViewById(R.id.relativeLayout).setBackground(new ColorDrawable(Color.parseColor("#7cd772")));
            }
        }
    }
}
