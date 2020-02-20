package com.idoogroup.edalumno.Adapters;


import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.idoogroup.edalumno.IdooGroupApplication;
import com.idoogroup.edalumno.Models.AssistancesModel;
import com.idoogroup.edalumno.R;

import java.util.List;

public class CalificacionesAdapter extends RecyclerView.Adapter<CalificacionesAdapter.ViewHolder> {

    private List<AssistancesModel> evaluacionesModel;
    private int layout;


    public CalificacionesAdapter(List<AssistancesModel> evaluacionesModel, int layout) {
        this.evaluacionesModel = evaluacionesModel;
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
        holder.bind(evaluacionesModel.get(position));
    }


    @Override
    public int getItemCount() {
        return evaluacionesModel.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvEvaluacionesAsignaturas, tvEvaluacionesParticipacion, tvEvaluacionesParticipacionEstrella;
        public TextView tvEvaluacionesCalificacionDiaria, tvEvaluacionesEmocional, tvEvaluacionesCalificacionDiariaReal;
        public TextView tvEvaluacionesCalificacionMateria, tvEvaluacionesCalificacionMateriaReal;
        public ImageView ivEvaluacionesCerrar, ivEvaluacionesAbrir;
        public ImageView ivStar1Activo, ivStar2Activo, ivStar3Activo, ivStar4Activo, ivStar5Activo;
        public ImageView ivStar1Inactivo, ivStar2Inactivo, ivStar3Inactivo, ivStar4Inactivo, ivStar5Inactivo;
        public ImageView ivEvaluacionesEmocionalExcelente, ivEvaluacionesEmocionalBien, ivEvaluacionesEmocionalTriste, ivEvaluacionesEmocionalEnfermo;
        public LinearLayout llEvaluacionesContenido;
        public IdooGroupApplication globalAPP;


        public ViewHolder(final View itemView) {
            super(itemView);

            tvEvaluacionesAsignaturas = (TextView) itemView.findViewById(R.id.tvEvaluacionesAsignaturas);
            ivEvaluacionesCerrar = (ImageView) itemView.findViewById(R.id.ivEvaluacionesCerrar);
            ivEvaluacionesAbrir = (ImageView) itemView.findViewById(R.id.ivEvaluacionesAbrir);
            tvEvaluacionesParticipacion = (TextView) itemView.findViewById(R.id.tvEvaluacionesParticipacion);
            tvEvaluacionesParticipacionEstrella = (TextView) itemView.findViewById(R.id.tvEvaluacionesParticipacionEstrella);
            tvEvaluacionesCalificacionDiaria = (TextView) itemView.findViewById(R.id.tvEvaluacionesCalificacionDiaria);
            tvEvaluacionesEmocional = (TextView) itemView.findViewById(R.id.tvEvaluacionesEmocional);
            tvEvaluacionesCalificacionDiariaReal = (TextView) itemView.findViewById(R.id.tvEvaluacionesCalificacionDiariaReal);
            tvEvaluacionesCalificacionMateria = (TextView) itemView.findViewById(R.id.tvEvaluacionesCalificacionMateria);
            tvEvaluacionesCalificacionMateriaReal = (TextView) itemView.findViewById(R.id.tvEvaluacionesCalificacionMateriaReal);
            llEvaluacionesContenido = (LinearLayout) itemView.findViewById(R.id.llEvaluacionesContenido);
            ivStar1Activo = (ImageView) itemView.findViewById(R.id.ivStar1Activo);
            ivStar2Activo = (ImageView) itemView.findViewById(R.id.ivStar2Activo);
            ivStar3Activo = (ImageView) itemView.findViewById(R.id.ivStar3Activo);
            ivStar4Activo = (ImageView) itemView.findViewById(R.id.ivStar4Activo);
            ivStar5Activo = (ImageView) itemView.findViewById(R.id.ivStar5Activo);
            ivStar1Inactivo = (ImageView) itemView.findViewById(R.id.ivStar1Inactivo);
            ivStar2Inactivo = (ImageView) itemView.findViewById(R.id.ivStar2Inactivo);
            ivStar3Inactivo = (ImageView) itemView.findViewById(R.id.ivStar3Inactivo);
            ivStar4Inactivo = (ImageView) itemView.findViewById(R.id.ivStar4Inactivo);
            ivStar5Inactivo = (ImageView) itemView.findViewById(R.id.ivStar5Inactivo);
            ivEvaluacionesEmocionalExcelente = (ImageView) itemView.findViewById(R.id.ivEvaluacionesEmocionalExcelente);
            ivEvaluacionesEmocionalBien = (ImageView) itemView.findViewById(R.id.ivEvaluacionesEmocionalBien);
            ivEvaluacionesEmocionalEnfermo = (ImageView) itemView.findViewById(R.id.ivEvaluacionesEmocionalEnfermo);
            ivEvaluacionesEmocionalTriste = (ImageView) itemView.findViewById(R.id.ivEvaluacionesEmocionalTriste);
            globalAPP = (IdooGroupApplication) itemView.getContext().getApplicationContext();

            // ASIGNACION DE FUENTES
            tvEvaluacionesAsignaturas.setTypeface(globalAPP.getFuenteAvenidHeavy(itemView.getContext().getAssets()));
            tvEvaluacionesCalificacionDiariaReal.setTypeface(globalAPP.getFuenteAvenidHeavy(itemView.getContext().getAssets()));
            tvEvaluacionesCalificacionMateriaReal.setTypeface(globalAPP.getFuenteAvenidHeavy(itemView.getContext().getAssets()));
            tvEvaluacionesParticipacion.setTypeface(globalAPP.getFuenteAvenidBook(itemView.getContext().getAssets()));
            tvEvaluacionesParticipacionEstrella.setTypeface(globalAPP.getFuenteAvenidBook(itemView.getContext().getAssets()));
            tvEvaluacionesCalificacionDiaria.setTypeface(globalAPP.getFuenteAvenidBook(itemView.getContext().getAssets()));
            tvEvaluacionesEmocional.setTypeface(globalAPP.getFuenteAvenidBook(itemView.getContext().getAssets()));
            tvEvaluacionesCalificacionMateria.setTypeface(globalAPP.getFuenteAvenidBook(itemView.getContext().getAssets()));


            ivEvaluacionesCerrar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CardView cardView = (CardView) itemView.findViewById(R.id.cvEvaluaciones);
                    cardView.setCardBackgroundColor(Color.parseColor("#A2E62E"));
                    cardView.setAlpha((float) 0.5);

                    tvEvaluacionesAsignaturas.setTextColor(Color.parseColor("#FFFFFF"));

                    ivEvaluacionesCerrar.setVisibility(View.GONE);
                    ivEvaluacionesAbrir.setVisibility(View.VISIBLE);
                    tvEvaluacionesParticipacion.setVisibility(View.GONE);
                    tvEvaluacionesParticipacionEstrella.setVisibility(View.GONE);
                    tvEvaluacionesCalificacionDiaria.setVisibility(View.GONE);
                    tvEvaluacionesEmocional.setVisibility(View.GONE);
                    tvEvaluacionesCalificacionDiariaReal.setVisibility(View.GONE);
                    tvEvaluacionesCalificacionMateria.setVisibility(View.GONE);
                    tvEvaluacionesCalificacionMateriaReal.setVisibility(View.GONE);
                    llEvaluacionesContenido.setVisibility(View.GONE);
                }
            });

            ivEvaluacionesAbrir.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CardView cardView = (CardView) itemView.findViewById(R.id.cvEvaluaciones);
                    cardView.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
                    tvEvaluacionesAsignaturas.setTextColor(Color.parseColor("#A2E62E"));
                    cardView.setAlpha((float) 1);

                    ivEvaluacionesCerrar.setVisibility(View.VISIBLE);
                    ivEvaluacionesAbrir.setVisibility(View.GONE);
                    tvEvaluacionesParticipacion.setVisibility(View.VISIBLE);
                    tvEvaluacionesParticipacionEstrella.setVisibility(View.VISIBLE);
                    tvEvaluacionesCalificacionDiaria.setVisibility(View.VISIBLE);
                    tvEvaluacionesEmocional.setVisibility(View.VISIBLE);
                    tvEvaluacionesCalificacionDiariaReal.setVisibility(View.VISIBLE);
                    tvEvaluacionesCalificacionMateria.setVisibility(View.VISIBLE);
                    tvEvaluacionesCalificacionMateriaReal.setVisibility(View.VISIBLE);
                    llEvaluacionesContenido.setVisibility(View.VISIBLE);
                }
            });
        }


        public void bind(AssistancesModel evaluacionesModel) {
            this.tvEvaluacionesAsignaturas.setText(evaluacionesModel.getCalendarizacion().getSubject().getName());
            this.tvEvaluacionesParticipacionEstrella.setText(String.valueOf(evaluacionesModel.getCalification()));
            tvEvaluacionesParticipacionEstrella.setText(evaluacionesModel.getDayEval().getDescription());
            tvEvaluacionesCalificacionDiariaReal.setText(String.valueOf(evaluacionesModel.getDayEval().getQuantitative()));
            tvEvaluacionesCalificacionMateriaReal.setText(String.valueOf(evaluacionesModel.getCalification()));

            switch (evaluacionesModel.getEmotionalEvaluation()) {
                case "E": {
                    ivStar1Inactivo.setVisibility(View.GONE);
                    ivStar1Activo.setVisibility(View.VISIBLE);
                    ivStar2Inactivo.setVisibility(View.GONE);
                    ivStar2Activo.setVisibility(View.VISIBLE);
                    ivStar3Inactivo.setVisibility(View.GONE);
                    ivStar3Activo.setVisibility(View.VISIBLE);
                    ivStar4Inactivo.setVisibility(View.GONE);
                    ivStar4Activo.setVisibility(View.VISIBLE);
                    ivStar5Inactivo.setVisibility(View.GONE);
                    ivStar5Activo.setVisibility(View.VISIBLE);

                    ivEvaluacionesEmocionalExcelente.setVisibility(View.VISIBLE);
                }
                break;

                case "B": {
                    ivStar1Inactivo.setVisibility(View.GONE);
                    ivStar1Activo.setVisibility(View.VISIBLE);
                    ivStar2Inactivo.setVisibility(View.GONE);
                    ivStar2Activo.setVisibility(View.VISIBLE);
                    ivStar3Inactivo.setVisibility(View.GONE);
                    ivStar3Activo.setVisibility(View.VISIBLE);
                    ivStar4Inactivo.setVisibility(View.GONE);
                    ivStar4Activo.setVisibility(View.VISIBLE);

                    ivEvaluacionesEmocionalBien.setVisibility(View.VISIBLE);
                }
                break;

                case "T": {
                    ivStar1Inactivo.setVisibility(View.GONE);
                    ivStar1Activo.setVisibility(View.VISIBLE);
                    ivStar2Inactivo.setVisibility(View.GONE);
                    ivStar2Activo.setVisibility(View.VISIBLE);
                    ivStar3Inactivo.setVisibility(View.GONE);
                    ivStar3Activo.setVisibility(View.VISIBLE);

                    ivEvaluacionesEmocionalTriste.setVisibility(View.VISIBLE);
                }
                break;

                case "X": {
                    ivStar1Inactivo.setVisibility(View.GONE);
                    ivStar1Activo.setVisibility(View.VISIBLE);
                    ivStar2Inactivo.setVisibility(View.GONE);
                    ivStar2Activo.setVisibility(View.VISIBLE);

                    ivEvaluacionesEmocionalEnfermo.setVisibility(View.VISIBLE);
                }
                break;
            }
        }
    }
}
