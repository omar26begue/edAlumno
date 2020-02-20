package com.idoogroup.edalumno.Adapters;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.idoogroup.edalumno.Helpers.Moment.Moment;
import com.idoogroup.edalumno.IdooGroupApplication;
import com.idoogroup.edalumno.Models.NotificacionesAppModel;
import com.idoogroup.edalumno.R;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import es.dmoral.toasty.Toasty;

public class NotificacionesAdapter extends RecyclerView.Adapter<NotificacionesAdapter.ViewHolder> {

    private List<NotificacionesAppModel> notificacionesModels;
    private int layout;


    public NotificacionesAdapter(List<NotificacionesAppModel> notificacionesModels, int layout) {
        this.notificacionesModels = notificacionesModels;
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
        holder.bind(notificacionesModels.get(position));
    }


    @Override
    public int getItemCount() {
        return notificacionesModels.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvNotificacionesFecha;
        public RecyclerView rvNotififMsg;
        private RecyclerView.Adapter mAdapter;
        private RecyclerView.LayoutManager mLayoutManager;
        public IdooGroupApplication globalAPP;


        public ViewHolder(View itemView) {
            super(itemView);

            // ASIGNACION DE VARAIBLES
            tvNotificacionesFecha = (TextView) itemView.findViewById(R.id.tvNotificacionesFecha);
            rvNotififMsg = (RecyclerView) itemView.findViewById(R.id.rvNotififMsg);
            globalAPP = (IdooGroupApplication) itemView.getContext().getApplicationContext();

            // ASIGNACION DE FUENTES
            tvNotificacionesFecha.setTypeface(globalAPP.getFuenteAvenidHeavy(itemView.getContext().getAssets()));
        }


        public void bind(NotificacionesAppModel notificacionesModels) {
            //this.tvNotificacionesFecha.setText(notificacionesModels.getFecha());
            try {
                Moment moment = new Moment(notificacionesModels.getFecha(), "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault());
                moment.getCalendar().setTimeZone(TimeZone.getTimeZone("GMT-07:00"));
                final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm aaa");
                sdf.setTimeZone(TimeZone.getTimeZone("GMT-07:00"));
                this.tvNotificacionesFecha.setText(sdf.format(moment.getDate()).substring(0, 10));

                mAdapter = new NotificacionesTareasAdapter(notificacionesModels.getNotificaciones(), R.layout.items_notificaciones_tareas_noti);
                mLayoutManager = new LinearLayoutManager(itemView.getContext());

                rvNotififMsg.setLayoutManager(mLayoutManager);
                rvNotififMsg.setAdapter(mAdapter);
            } catch (Exception e) {
                Toasty.error(itemView.getContext(), itemView.getContext().getString(R.string.error_fecha), Toast.LENGTH_SHORT).show();
            }

        }
    }
}
