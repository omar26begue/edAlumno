package com.idoogroup.edalumno.Fragments.FragmentNotificaciones;


import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.idoogroup.edalumno.Adapters.NotificacionesAdapter;
import com.idoogroup.edalumno.Helpers.Constants;
import com.idoogroup.edalumno.IdooGroupApplication;
import com.idoogroup.edalumno.Models.NotificacionesAppModel;
import com.idoogroup.edalumno.Models.NotificacionesModel;
import com.idoogroup.edalumno.R;
import com.idoogroup.edalumno.WebServicesIdooGroup;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;


public class NotificacionesTareasFragment extends Fragment {

    // VARIABLES DE LA CLASE
    private RecyclerView rvNotificacionesTareas;
    private RecyclerView.Adapter mAdapterNotificacionesTareas;
    private RecyclerView.LayoutManager mLayoutManagerNotificaciones;
    private TextView tvNotificacionesTareas, tvNoDatos;

    private IdooGroupApplication globalAPP;
    private WebServicesIdooGroup servicesEDUKO;
    private Dialog dialogLoading = null;


    public NotificacionesTareasFragment() {
        // Required empty public constructor
    }


    public static NotificacionesTareasFragment newInstance() {
        return new NotificacionesTareasFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_notificaciones_tareas, container, false);

        try {
            // VARIABLE GLOBAL
            globalAPP = (IdooGroupApplication) getActivity().getApplication();
            servicesEDUKO = globalAPP.getService();

            // ASIGNADO LAS VARIABLES
            rvNotificacionesTareas = (RecyclerView) v.findViewById(R.id.rvNotificacionesTareas);
            tvNotificacionesTareas = (TextView) v.findViewById(R.id.tvNotificacionesTareas);
            tvNoDatos = (TextView) v.findViewById(R.id.tvNoDatos);

            // ASIGNACION DE FUENTES
            tvNotificacionesTareas.setTypeface(globalAPP.getFuenteAvenidHeavy(getActivity().getAssets()));
            tvNoDatos.setTypeface(globalAPP.getFuenteAvenidRoman(getActivity().getAssets()));

            updateNotificaciones();
        } catch (Exception e) {
            Toasty.error(getContext(), getString(R.string.error_app), Toast.LENGTH_SHORT).show();
            Log.e(Constants.TAG, e.getMessage());
        }

        return v;
    }


    public void updateNotificaciones() {
        try {
            List<NotificacionesAppModel> notif = new ArrayList<>();
            List<NotificacionesModel> notificaciones = new ArrayList<>();

            for (int i = 0; i < globalAPP.notificacionesModels.size(); i++) {
                if (globalAPP.notificacionesModels.get(i).getTipo().equals("homework")) {
                    notificaciones.add(globalAPP.notificacionesModels.get(i));
                }
            }

            for (int i = 0; i < notificaciones.size(); i++) {
                Boolean agregar = true;

                for (int j = 0; j < notif.size(); j++) {
                    if (notif.get(j).getFecha().substring(0, 10).equals(notificaciones.get(i).getCreated().substring(0, 10))) {
                        agregar = false;

                        notif.get(j).getNotificaciones().add(notificaciones.get(i));
                    }
                }

                if (agregar == true) {
                    NotificacionesAppModel appModel = new NotificacionesAppModel();
                    appModel.setFecha(notificaciones.get(i).getCreated());
                    List<NotificacionesModel> models = new ArrayList<>();
                    models.add(notificaciones.get(i));
                    appModel.setNotificaciones(models);

                    notif.add(appModel);
                }
            }

            if (notif != null && notif.size() > 0) {
                rvNotificacionesTareas.setVisibility(View.VISIBLE);
                tvNoDatos.setVisibility(View.GONE);

                mAdapterNotificacionesTareas = new NotificacionesAdapter(notif, R.layout.items_notificaciones_tareas);
                mLayoutManagerNotificaciones = new LinearLayoutManager(getContext());

                rvNotificacionesTareas.setLayoutManager(mLayoutManagerNotificaciones);
                rvNotificacionesTareas.setAdapter(mAdapterNotificacionesTareas);
            } else {
                rvNotificacionesTareas.setVisibility(View.GONE);
                tvNoDatos.setVisibility(View.VISIBLE);
            }
        } catch (Exception e) {
            Toasty.error(getContext(), getString(R.string.error_app), Toast.LENGTH_SHORT).show();
            Log.e(Constants.TAG, e.getMessage());
        }
    }

}
