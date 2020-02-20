package com.idoogroup.edalumno.Adapters;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.idoogroup.edalumno.IdooGroupApplication;
import com.idoogroup.edalumno.Models.EventosModel;
import com.idoogroup.edalumno.R;

import java.util.List;

public class EventosAdapter extends RecyclerView.Adapter<EventosAdapter.ViewHolder> {

    private List<EventosModel> horarios;
    private int layout;


    public EventosAdapter(List<EventosModel> horarios, int layout) {
        this.horarios = horarios;
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
        holder.bind(horarios.get(position));
    }


    @Override
    public int getItemCount() {
        return horarios.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        // VARIABLES DE LA CLASE
        public TextView tvAsistenciaVerificadaHorario, tvAsistenciaEventos, tvAsistenciaDescripcion, tvAsistenciaSalon, tvAsistenciaLugar, tvAsistenciaDireccion;
        public ImageView ivAsistenciaVerificada, ivAsistenciaNOVerificada;
        public IdooGroupApplication globalAPP;


        public ViewHolder(View itemView) {
            super(itemView);

            // ASIGNACION DE VARIABLES
            tvAsistenciaVerificadaHorario = (TextView) itemView.findViewById(R.id.tvAsistenciaVerificadaHorario);
            tvAsistenciaEventos = (TextView) itemView.findViewById(R.id.tvAsistenciaEventos);
            tvAsistenciaDescripcion = (TextView) itemView.findViewById(R.id.tvAsistenciaDescripcion);
            tvAsistenciaSalon = (TextView) itemView.findViewById(R.id.tvAsistenciaSalon);
            tvAsistenciaLugar = (TextView) itemView.findViewById(R.id.tvAsistenciaLugar);
            tvAsistenciaDireccion = (TextView) itemView.findViewById(R.id.tvAsistenciaDireccion);
            ivAsistenciaVerificada = (ImageView) itemView.findViewById(R.id.ivAsistenciaVerificada);
            ivAsistenciaNOVerificada = (ImageView) itemView.findViewById(R.id.ivAsistenciaNOVerificada);
            globalAPP = (IdooGroupApplication) itemView.getContext().getApplicationContext();

            // ASIGNACION DE FUENTES
            tvAsistenciaVerificadaHorario.setTypeface(globalAPP.getFuenteAvenidBook(itemView.getContext().getAssets()));
            tvAsistenciaEventos.setTypeface(globalAPP.getFuenteAvenidHeavy(itemView.getContext().getAssets()));
            tvAsistenciaDescripcion.setTypeface(globalAPP.getFuenteAvenidBook(itemView.getContext().getAssets()));
            tvAsistenciaSalon.setTypeface(globalAPP.getFuenteAvenidBook(itemView.getContext().getAssets()));
            tvAsistenciaLugar.setTypeface(globalAPP.getFuenteAvenidBook(itemView.getContext().getAssets()));
            tvAsistenciaDireccion.setTypeface(globalAPP.getFuenteAvenidBook(itemView.getContext().getAssets()));
        }


        public void bind(final EventosModel eventosModel) {
            this.tvAsistenciaVerificadaHorario.setText(eventosModel.getHorarios());
            this.tvAsistenciaEventos.setText(eventosModel.getNombreEvento());
            this.tvAsistenciaDescripcion.setText(eventosModel.getDescripcionEvento());

            this.tvAsistenciaSalon.setText(eventosModel.getLugar());
            this.tvAsistenciaLugar.setText(eventosModel.getPlaceAddress());

            if (eventosModel.getPickUpPoint().size() > 0) {
                String puntosRecoguida = eventosModel.getPickUpPoint().get(0).getPlace();

                for (int i = 1; i < eventosModel.getPickUpPoint().size(); i++) {
                    puntosRecoguida += "; ";
                    puntosRecoguida += eventosModel.getPickUpPoint().get(i).getPlace();
                }

                this.tvAsistenciaDireccion.setText(puntosRecoguida);
            } else this.tvAsistenciaDireccion.setText("- N/A -");

            if (eventosModel.getInfo().isConfirmed() == true) {
                this.ivAsistenciaVerificada.setVisibility(View.VISIBLE);
            } else {
                this.ivAsistenciaNOVerificada.setVisibility(View.VISIBLE);
            }
        }

    }
}
