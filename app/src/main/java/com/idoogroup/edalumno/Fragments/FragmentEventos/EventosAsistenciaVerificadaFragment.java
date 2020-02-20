package com.idoogroup.edalumno.Fragments.FragmentEventos;


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

import com.idoogroup.edalumno.Adapters.EventosAdapter;
import com.idoogroup.edalumno.Helpers.Constants;
import com.idoogroup.edalumno.IdooGroupApplication;
import com.idoogroup.edalumno.Models.EventosModel;
import com.idoogroup.edalumno.R;
import com.idoogroup.edalumno.WebServicesIdooGroup;

import java.util.List;

import es.dmoral.toasty.Toasty;


public class EventosAsistenciaVerificadaFragment extends Fragment {

    // VARIABLES DE LA CLASE
    public TextView tvAsistenciaVerificada, tvNoDatos;
    public RecyclerView rvAsistenciaVerificada;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private IdooGroupApplication globalAPP;
    private WebServicesIdooGroup servicesEDUKO;
    private Dialog dialogLoading = null;


    public EventosAsistenciaVerificadaFragment() {
        // Required empty public constructor
    }


    public static EventosAsistenciaVerificadaFragment newInstance() {
        return new EventosAsistenciaVerificadaFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_eventos_asistencia_verificada, container, false);

        try {
            // VARIABLE GLOBAL
            globalAPP = (IdooGroupApplication) getActivity().getApplication();
            servicesEDUKO = globalAPP.getService();

            // ASIGNADO LAS VARIABLES
            tvAsistenciaVerificada = (TextView) v.findViewById(R.id.tvAsistenciaVerificada);
            tvNoDatos = (TextView) v.findViewById(R.id.tvNoDatos);
            rvAsistenciaVerificada = (RecyclerView) v.findViewById(R.id.rvAsistenciaVerificada);

            // ASIGNACION DE FUENTES
            tvAsistenciaVerificada.setTypeface(globalAPP.getFuenteAvenidHeavy(getActivity().getAssets()));
            tvNoDatos.setTypeface(globalAPP.getFuenteAvenidRoman(getActivity().getAssets()));
        } catch (Exception e) {
            Toasty.error(getContext(), getString(R.string.error_app), Toast.LENGTH_SHORT).show();
            Log.e(Constants.TAG, e.getMessage());
        }

        return v;
    }


    public void updateEventos(List<EventosModel> eventosModels) {
        if (eventosModels != null && eventosModels.size() > 0) {
            rvAsistenciaVerificada.setVisibility(View.VISIBLE);
            tvNoDatos.setVisibility(View.GONE);

            mAdapter = new EventosAdapter(eventosModels, R.layout.items_eventos);
            mLayoutManager = new LinearLayoutManager(getContext());

            rvAsistenciaVerificada.setLayoutManager(mLayoutManager);
            rvAsistenciaVerificada.setAdapter(mAdapter);
        } else {
            rvAsistenciaVerificada.setVisibility(View.GONE);
            tvNoDatos.setVisibility(View.VISIBLE);
        }

        rvAsistenciaVerificada.setAlpha(1);
    }

}
