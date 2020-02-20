package com.idoogroup.edalumno.Adapters;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.idoogroup.edalumno.IdooGroupApplication;
import com.idoogroup.edalumno.Interfaces.TareasCallback;
import com.idoogroup.edalumno.Models.HomeworksHomeworksModel;
import com.idoogroup.edalumno.Models.SubjectModelHomeworks;
import com.idoogroup.edalumno.R;

import java.util.List;

public class TareasXRealizarAdaper extends RecyclerView.Adapter<TareasXRealizarAdaper.ViewHolder> {

    // VARIABLES DE LA CLASE
    private List<SubjectModelHomeworks> asignaturas;
    private int layout;
    private TareasCallback tareasCallback;


    public TareasXRealizarAdaper(List<SubjectModelHomeworks> asignaturas, int layout, TareasCallback tareasCallback) {
        this.asignaturas = asignaturas;
        this.layout = layout;
        this.tareasCallback = tareasCallback;
    }


    @Override
    public TareasXRealizarAdaper.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(asignaturas.get(position));

        holder.updateTareas(asignaturas.get(position).getHomeworksModels(), tareasCallback);
    }


    @Override
    public int getItemCount() {
        return asignaturas.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvTareasAsignaturas;
        public RecyclerView rvAsignauras;
        private RecyclerView.Adapter mAdapter;
        private RecyclerView.LayoutManager mLayoutManager;
        public IdooGroupApplication globalAPP;


        public ViewHolder(View itemView) {
            super(itemView);

            // ASIGNACION DE VARAIBLES
            tvTareasAsignaturas = (TextView) itemView.findViewById(R.id.tvTareasAsignaturas);
            rvAsignauras = (RecyclerView) itemView.findViewById(R.id.rvAsignauras);
            globalAPP = (IdooGroupApplication) itemView.getContext().getApplicationContext();

            // ASIGNACION DE FUENTES
            tvTareasAsignaturas.setTypeface(globalAPP.getFuenteAvenidHeavy(itemView.getContext().getAssets()));
        }


        public void bind(final SubjectModelHomeworks tareas) {
            this.tvTareasAsignaturas.setText(tareas.getName());
        }


        private void updateTareas(List<HomeworksHomeworksModel> tarea, TareasCallback tareasCallback) {
            if (tarea != null && tarea.size() > 0) {
                mAdapter = new TareasAdapter(tarea, R.layout.items_tareas_asignaturas, tareasCallback);
                mLayoutManager = new LinearLayoutManager(itemView.getContext());

                rvAsignauras.setLayoutManager(mLayoutManager);
                rvAsignauras.setAdapter(mAdapter);
            }
        }

    }
}
