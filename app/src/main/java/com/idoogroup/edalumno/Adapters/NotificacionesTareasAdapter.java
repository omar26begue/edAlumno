package com.idoogroup.edalumno.Adapters;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.idoogroup.edalumno.IdooGroupApplication;
import com.idoogroup.edalumno.Models.NotificacionesModel;
import com.idoogroup.edalumno.R;

import java.util.List;

public class NotificacionesTareasAdapter extends RecyclerView.Adapter<NotificacionesTareasAdapter.ViewHolder> {

    // ASIGNACION DE VARIABLES
    private List<NotificacionesModel> notificaciones;
    private int layout;


    public NotificacionesTareasAdapter(List<NotificacionesModel> notificaciones, int layout) {
        this.notificaciones = notificaciones;
        this.layout = layout;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(notificaciones.get(position));
    }


    @Override
    public int getItemCount() {
        return notificaciones.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        // ASIGNACION DE VARIABLES
        public TextView tvNotificacionesMsg;
        public IdooGroupApplication globalAPP;


        public ViewHolder(View itemView) {
            super(itemView);

            // ASIGNACION DE VARIABLES
            tvNotificacionesMsg = (TextView) itemView.findViewById(R.id.tvNotificacionesMsg);
            globalAPP = (IdooGroupApplication) itemView.getContext().getApplicationContext();

            // ASIGNACION DE FUENTES
            tvNotificacionesMsg.setTypeface(globalAPP.getFuenteAvenidBook(itemView.getContext().getAssets()));
        }


        public void bind(NotificacionesModel descripcion) {
            this.tvNotificacionesMsg.setText(descripcion.getMessage());
        }
    }
}
