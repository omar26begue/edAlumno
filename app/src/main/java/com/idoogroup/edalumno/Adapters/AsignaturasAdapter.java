package com.idoogroup.edalumno.Adapters;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.idoogroup.edalumno.IdooGroupApplication;
import com.idoogroup.edalumno.Models.HomeworksHomeworksModel;
import com.idoogroup.edalumno.Models.SubjectModelHomeworks;
import com.idoogroup.edalumno.R;

import java.util.List;

public class AsignaturasAdapter extends RecyclerView.Adapter<AsignaturasAdapter.ViewHolder> {

    // VARIABLES DE LA CLASE
    private List<SubjectModelHomeworks> tareas;
    private int layout;


    public AsignaturasAdapter(List<SubjectModelHomeworks> tareas, int layout) {
        this.tareas = tareas;
        this.layout = layout;
    }


    @Override
    public AsignaturasAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.bind(tareas.get(position));

        holder.updateTareas(tareas.get(position).getHomeworksModels());
    }


    @Override
    public int getItemCount() {
        return tareas.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvTareasAsignaturas, tvNombreTarea, tvPuntosTarea;
        public ImageView ivTareasDetalles;
        public RecyclerView rvAsignauras;
        private RecyclerView.Adapter mAdapter;
        private RecyclerView.LayoutManager mLayoutManager;
        public IdooGroupApplication globalAPP;


        public ViewHolder(View itemView) {
            super(itemView);

            // ASIGNACION DE VARAIBLES
            tvTareasAsignaturas = (TextView) itemView.findViewById(R.id.tvTareasAsignaturas);
            tvNombreTarea = (TextView) itemView.findViewById(R.id.tvNombreTarea);
            tvPuntosTarea = (TextView) itemView.findViewById(R.id.tvPuntosTarea);
            ivTareasDetalles = (ImageView) itemView.findViewById(R.id.ivTareasDetalles);
            rvAsignauras = (RecyclerView) itemView.findViewById(R.id.rvAsignauras);
            globalAPP = (IdooGroupApplication) itemView.getContext().getApplicationContext();

            // ASIGNACION DE FUENTES
            tvTareasAsignaturas.setTypeface(globalAPP.getFuenteAvenidHeavy(itemView.getContext().getAssets()));
        }


        public void bind(final SubjectModelHomeworks tarea) {
            this.tvTareasAsignaturas.setText(tarea.getName());
        }


        private void updateTareas(List<HomeworksHomeworksModel> tarea) {
            if (tarea != null && tarea.size() > 0) {
                mAdapter = new TareasAdapter(tarea, R.layout.items_tareas_asignaturas, null);
                mLayoutManager = new LinearLayoutManager(itemView.getContext());

                rvAsignauras.setLayoutManager(mLayoutManager);
                rvAsignauras.setAdapter(mAdapter);
            }
        }

    }

}
