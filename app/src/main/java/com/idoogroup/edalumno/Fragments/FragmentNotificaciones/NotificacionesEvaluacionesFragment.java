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


public class NotificacionesEvaluacionesFragment extends Fragment {

    // VARIABLES DE LA CLASE
    private TextView tvNotificacionesEvaluacionnes, tvNoDatos;
    private RecyclerView rvNotificacionesEvaluaciones;
    private RecyclerView.Adapter mAdapterNotificacionesEvaluaciones;
    private RecyclerView.LayoutManager mLayoutManagerNotificaciones;

    private IdooGroupApplication globalAPP;
    private WebServicesIdooGroup servicesEDUKO;
    private Dialog dialogLoading = null;


    public NotificacionesEvaluacionesFragment() {
        // Required empty public constructor
    }


    public static NotificacionesEvaluacionesFragment newInstance() {
        return new NotificacionesEvaluacionesFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_notificaciones_evaluaciones, container, false);

        try {
            // VARIABLE GLOBAL
            globalAPP = (IdooGroupApplication) getActivity().getApplication();
            servicesEDUKO = globalAPP.getService();

            // ASIGNADO LAS VARIABLES
            rvNotificacionesEvaluaciones = (RecyclerView) v.findViewById(R.id.rvNotificacionesEvaluaciones);
            tvNotificacionesEvaluacionnes = (TextView) v.findViewById(R.id.tvNotificacionesEvaluacionnes);
            tvNoDatos = (TextView) v.findViewById(R.id.tvNoDatos);

            // ASIGNACION DE FUENTES
            tvNotificacionesEvaluacionnes.setTypeface(globalAPP.getFuenteAvenidHeavy(getActivity().getAssets()));
            tvNoDatos.setTypeface(globalAPP.getFuenteAvenidRoman(getContext().getAssets()));
        } catch (Exception e) {

        }

        return v;
    }


    public void updateNotificaciones() {
        try {
            List<NotificacionesAppModel> notif = new ArrayList<>();
            List<NotificacionesModel> notificaciones = new ArrayList<>();

            for (int i = 0; i < globalAPP.notificacionesModels.size(); i++) {
                if (globalAPP.notificacionesModels.get(i).getTipo().equals("evaluation")) {
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
                rvNotificacionesEvaluaciones.setVisibility(View.VISIBLE);
                tvNoDatos.setVisibility(View.GONE);

                mAdapterNotificacionesEvaluaciones = new NotificacionesAdapter(notif, R.layout.items_notificaciones_tareas);
                mLayoutManagerNotificaciones = new LinearLayoutManager(getContext());

                rvNotificacionesEvaluaciones.setLayoutManager(mLayoutManagerNotificaciones);
                rvNotificacionesEvaluaciones.setAdapter(mAdapterNotificacionesEvaluaciones);
            } else {
                rvNotificacionesEvaluaciones.setVisibility(View.GONE);
                tvNoDatos.setVisibility(View.VISIBLE);
            }
        } catch (Exception e) {
            Toasty.error(getContext(), getString(R.string.error_app), Toast.LENGTH_SHORT).show();
            Log.e(Constants.TAG, e.getMessage());
        }
    }

}
