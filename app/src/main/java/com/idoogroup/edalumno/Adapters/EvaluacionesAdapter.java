package com.idoogroup.edalumno.Adapters;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.idoogroup.edalumno.Helpers.Moment.Moment;
import com.idoogroup.edalumno.IdooGroupApplication;
import com.idoogroup.edalumno.Models.StudentsEvaluationModel;
import com.idoogroup.edalumno.R;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import es.dmoral.toasty.Toasty;

public class EvaluacionesAdapter extends RecyclerView.Adapter<EvaluacionesAdapter.ViewHolder> {

    // VARIABLES DE LA CLASE
    private List<StudentsEvaluationModel> calificaciones;
    private int layout;


    public EvaluacionesAdapter(List<StudentsEvaluationModel> calificaciones, int layout) {
        this.calificaciones = calificaciones;
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
        holder.bind(calificaciones.get(position));
    }

    @Override
    public int getItemCount() {
        return calificaciones.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        // VARIABLES DE LA CLASE
        public TextView tvAsignaturaCalificaciones, tvCalificaciones, tvProfesorCalificaciones, tvFecha;
        public IdooGroupApplication globalAPP;


        public ViewHolder(View itemView) {
            super(itemView);

            // ASIGNACION DE VARAIBLES
            tvAsignaturaCalificaciones = (TextView) itemView.findViewById(R.id.tvAsignaturaCalificaciones);
            tvCalificaciones = (TextView) itemView.findViewById(R.id.tvCalificaciones);
            tvProfesorCalificaciones = (TextView) itemView.findViewById(R.id.tvProfesorCalificaciones);
            tvFecha = (TextView) itemView.findViewById(R.id.tvFecha);
            globalAPP = (IdooGroupApplication) itemView.getContext().getApplicationContext();

            // ASIGNACION DE FUENTES
            tvAsignaturaCalificaciones.setTypeface(globalAPP.getFuenteAvenidBook(itemView.getContext().getAssets()));
            tvCalificaciones.setTypeface(globalAPP.getFuenteAvenidBook(itemView.getContext().getAssets()));
            tvProfesorCalificaciones.setTypeface(globalAPP.getFuenteAvenidBook(itemView.getContext().getAssets()));
            tvFecha.setTypeface(globalAPP.getFuenteAvenidBook(itemView.getContext().getAssets()));
        }


        public void bind(final StudentsEvaluationModel calificacionesModel) {
            this.tvAsignaturaCalificaciones.setText(calificacionesModel.getSubject().getName());
            this.tvCalificaciones.setText(String.valueOf(calificacionesModel.getNomCalificacion().getQualitative() + " - " + calificacionesModel.getNomCalificacion().getQuantitative() + " ptos"));

            try {
                Moment moment = new Moment(calificacionesModel.getFecha(), "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault());
                moment.getCalendar().setTimeZone(TimeZone.getTimeZone("GMT-07:00"));
                final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm aaa");
                sdf.setTimeZone(TimeZone.getTimeZone("GMT-07:00"));
                this.tvFecha.setText(sdf.format(moment.getDate()));
            } catch (Exception e) {
                Toasty.error(itemView.getContext(), itemView.getContext().getString(R.string.error_fecha), Toast.LENGTH_SHORT).show();
            }

            /*if (calificacionesModel.getSubject().getTeacher().size() > 0)
                this.tvProfesorCalificaciones.setText("Profesor: " + calificacionesModel.getSubject().getTeacher().get(0).getName() + " " + calificacionesModel.getSubject().getTeacher().get(0).getLastName());*/
        }
    }
}
