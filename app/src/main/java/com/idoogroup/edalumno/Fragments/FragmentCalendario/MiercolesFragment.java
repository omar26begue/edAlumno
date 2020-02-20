package com.idoogroup.edalumno.Fragments.FragmentCalendario;


import android.app.Dialog;
import android.os.Build;
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

import com.idoogroup.edalumno.Adapters.HorarioAdapter;
import com.idoogroup.edalumno.Helpers.Constants;
import com.idoogroup.edalumno.IdooGroupApplication;
import com.idoogroup.edalumno.Models.CalendarizacionModel;
import com.idoogroup.edalumno.R;
import com.idoogroup.edalumno.WebServicesIdooGroup;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;

/**
 * A simple {@link Fragment} subclass.
 */
public class MiercolesFragment extends Fragment {

    public static final String TITLE = "M";
    private TextView tvNoDatos;
    private RecyclerView rvHorario;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private IdooGroupApplication globalAPP;
    private WebServicesIdooGroup servicesEDUKO;
    private Dialog dialogLoading = null;


    public MiercolesFragment() {
        // Required empty public constructor
    }


    public static MiercolesFragment newInstance() {
        return new MiercolesFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_miercoles, container, false);

        try {
            // VARIABLE GLOBAL
            globalAPP = (IdooGroupApplication) getActivity().getApplication();
            servicesEDUKO = globalAPP.getService();

            // ASIGNADO LAS VARIABLES
            rvHorario = (RecyclerView) v.findViewById(R.id.rvHorarioMiercoles);
            tvNoDatos = (TextView) v.findViewById(R.id.tvNoDatos);

            // ASIGNACION DE FUENTES
            tvNoDatos.setTypeface(globalAPP.getFuenteAvenidRoman(getActivity().getAssets()));
        } catch (Exception e) {
            Toasty.error(getContext(), getString(R.string.error_app), Toast.LENGTH_SHORT).show();
            Log.e(Constants.TAG, e.getMessage());
        }

        return v;
    }


    public void updateHorario() {
        if (globalAPP == null)
            globalAPP = (IdooGroupApplication) getActivity().getApplication();

        List<CalendarizacionModel> miercoles = new ArrayList<>();

        for (int i = 0; i < globalAPP.calendarizacionModels.size(); i++) {
            if (globalAPP.calendarizacionModels.get(i).getDiaSemana().getNombre().toLowerCase().equals("miercoles")) {
                miercoles.add(globalAPP.calendarizacionModels.get(i));
            }
        }

        if (miercoles != null && miercoles.size() > 0) {
            rvHorario.setVisibility(View.VISIBLE);
            tvNoDatos.setVisibility(View.GONE);

            mAdapter = new HorarioAdapter(miercoles, R.layout.items_horarios_calendario);
            mLayoutManager = new LinearLayoutManager(getContext());

            rvHorario.setLayoutManager(mLayoutManager);
            /*SlideInBottomAnimatorAdapter animatorAdapter = new SlideInBottomAnimatorAdapter(mAdapter, rvHorario);
            ScaleInAnimatorAdapter animatorAdapter1 = new ScaleInAnimatorAdapter(animatorAdapter, rvHorario);*/
            rvHorario.setAdapter(mAdapter);
        } else {
            rvHorario.setVisibility(View.GONE);
            tvNoDatos.setVisibility(View.VISIBLE);
        }
    }

}
