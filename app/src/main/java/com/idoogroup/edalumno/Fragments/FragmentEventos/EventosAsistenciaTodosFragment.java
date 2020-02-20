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


public class EventosAsistenciaTodosFragment extends Fragment {

    // VARIABLES
    public TextView tvAsistenciaTodos, tvNoDatos;
    public RecyclerView rvAsistenciaTodos;
    private RecyclerView.Adapter mAdapter;
    private LinearLayoutManager mLayoutManager;

    private IdooGroupApplication globalAPP;
    private WebServicesIdooGroup servicesEDUKO;
    private Dialog dialogLoading = null;


    public EventosAsistenciaTodosFragment() {
        // Required empty public constructor
    }


    public static EventosAsistenciaTodosFragment newInstance() {
        return new EventosAsistenciaTodosFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_eventos_asistencia_todos, container, false);

        try {
            // VARIABLE GLOBAL
            globalAPP = (IdooGroupApplication) getActivity().getApplication();
            servicesEDUKO = globalAPP.getService();

            // ASIGNADO LAS VARIABLES
            tvAsistenciaTodos = (TextView) v.findViewById(R.id.tvAsistenciaTodos);
            tvNoDatos = (TextView) v.findViewById(R.id.tvNoDatos);
            rvAsistenciaTodos = (RecyclerView) v.findViewById(R.id.rvAsistenciaTodos);

            // ASIGNACION DE FUENTES
            tvAsistenciaTodos.setTypeface(globalAPP.getFuenteAvenidHeavy(getActivity().getAssets()));
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
                rvAsistenciaTodos.setVisibility(View.VISIBLE);
                tvNoDatos.setVisibility(View.GONE);

                mAdapter = new EventosAdapter(eventosModels, R.layout.items_eventos);
                mLayoutManager = new LinearLayoutManager(getContext());

                rvAsistenciaTodos.setLayoutManager(mLayoutManager);
                rvAsistenciaTodos.setAdapter(mAdapter);
            } else {
                rvAsistenciaTodos.setVisibility(View.GONE);
                tvNoDatos.setVisibility(View.VISIBLE);
            }

            rvAsistenciaTodos.setAlpha(1);
        } catch (Exception e) {
            Toasty.error(getContext(), getString(R.string.error_app), Toast.LENGTH_SHORT).show();
            Log.e(Constants.TAG, e.getMessage());
        }
    }

}
