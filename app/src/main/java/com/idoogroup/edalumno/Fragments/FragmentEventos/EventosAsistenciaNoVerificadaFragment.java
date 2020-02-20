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


public class EventosAsistenciaNoVerificadaFragment extends Fragment {

    // VARIABLES DE LA CLASE
    public TextView tvAsistenciaNoVerificada, tvNoDatos;
    public RecyclerView rvAsistenciaNOVerificada;
    private EventosAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private IdooGroupApplication globalAPP;
    private WebServicesIdooGroup servicesEDUKO;
    private Dialog dialogLoading = null;


    public EventosAsistenciaNoVerificadaFragment() {
        // Required empty public constructor
    }


    public static EventosAsistenciaNoVerificadaFragment newInstance() {
        return new EventosAsistenciaNoVerificadaFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_eventos_asistencia_no_verificada, container, false);

        try {
            // VARIABLE GLOBAL
            globalAPP = (IdooGroupApplication) getActivity().getApplication();
            servicesEDUKO = globalAPP.getService();

            // ASIGNADO LAS VARIABLES
            tvAsistenciaNoVerificada = (TextView) v.findViewById(R.id.tvAsistenciaNoVerificada);
            tvNoDatos = (TextView) v.findViewById(R.id.tvNoDatos);
            rvAsistenciaNOVerificada = (RecyclerView) v.findViewById(R.id.rvAsistenciaNOVerificada);

            // ASIGNACION DE FUENTES
            tvAsistenciaNoVerificada.setTypeface(globalAPP.getFuenteAvenidHeavy(getActivity().getAssets()));
            tvNoDatos.setTypeface(globalAPP.getFuenteAvenidRoman(getActivity().getAssets()));
        } catch (Exception e) {
            Toasty.error(getContext(), getString(R.string.error_app), Toast.LENGTH_SHORT).show();
            Log.e(Constants.TAG, e.getMessage());
        }

        return v;
    }


    public void updateEventos(List<EventosModel> eventosModels) {
        try {
            if (eventosModels != null && eventosModels.size() > 0) {
                rvAsistenciaNOVerificada.setVisibility(View.VISIBLE);
                tvNoDatos.setVisibility(View.GONE);

                mAdapter = new EventosAdapter(eventosModels, R.layout.items_eventos);
                mLayoutManager = new LinearLayoutManager(getContext());

                rvAsistenciaNOVerificada.setLayoutManager(mLayoutManager);
                rvAsistenciaNOVerificada.setAdapter(mAdapter);

            } else {
                rvAsistenciaNOVerificada.setVisibility(View.GONE);
                tvNoDatos.setVisibility(View.VISIBLE);
            }

            rvAsistenciaNOVerificada.setAlpha(1);
        } catch (Exception e) {
            Toasty.error(getContext(), getString(R.string.error_app), Toast.LENGTH_SHORT).show();
            Log.e(Constants.TAG, e.getMessage());
        }
    }

}
