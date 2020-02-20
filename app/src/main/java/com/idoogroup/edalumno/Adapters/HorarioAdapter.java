package com.idoogroup.edalumno.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.idoogroup.edalumno.IdooGroupApplication;
import com.idoogroup.edalumno.Models.CalendarizacionModel;
import com.idoogroup.edalumno.R;

import java.util.List;

public class HorarioAdapter extends RecyclerView.Adapter<HorarioAdapter.ViewHolder> {

    private List<CalendarizacionModel> horarioModels;
    private int layout;


    public HorarioAdapter(List<CalendarizacionModel> horarioModels, int layout) {
        this.horarioModels = horarioModels;
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
        holder.bind(horarioModels.get(position), (horarioModels.size() - 1 == position));
    }


    @Override
    public int getItemCount() {
        return horarioModels.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvHorarioCalendario;
        public TextView tvAsignaturaCalendario;
        public TextView tvProfesorCalendario;
        public View vSeparadorHorario;
        public IdooGroupApplication globalAPP;


        public ViewHolder(View itemView) {
            super(itemView);

            // ASIGNACION DE VARAIBLES
            tvHorarioCalendario = (TextView) itemView.findViewById(R.id.tvHorarioCalendario);
            tvAsignaturaCalendario = (TextView) itemView.findViewById(R.id.tvAsignaturaCalendario);
            tvProfesorCalendario = (TextView) itemView.findViewById(R.id.tvProfesorCalendario);
            vSeparadorHorario = (View) itemView.findViewById(R.id.vSeparadorHorario);
            globalAPP = (IdooGroupApplication) itemView.getContext().getApplicationContext();

            // ASIGNACION DE FUENTES
            tvHorarioCalendario.setTypeface(globalAPP.getFuenteAvenidLight(itemView.getContext().getAssets()));
            tvAsignaturaCalendario.setTypeface(globalAPP.getFuenteAvenidLight(itemView.getContext().getAssets()));
            tvProfesorCalendario.setTypeface(globalAPP.getFuenteAvenidLight(itemView.getContext().getAssets()));
        }


        public void bind(CalendarizacionModel horarioModel, final Boolean chequeable) {
            this.tvHorarioCalendario.setText(horarioModel.getNomTurno().getHoraInicial() + " - " + horarioModel.getNomTurno().getHoraFinal());
            this.tvAsignaturaCalendario.setText(horarioModel.getSubject().getName());
            this.tvProfesorCalendario.setText(horarioModel.getTeacher().getName() + " " + horarioModel.getTeacher().getLastName());

            if (chequeable) {
                this.vSeparadorHorario.setVisibility(View.GONE);
            }
        }
    }
}
