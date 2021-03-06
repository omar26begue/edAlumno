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


public class LunesFragment extends Fragment {

    public static final String TITLE = "L";
    private TextView tvNoDatos;
    private RecyclerView rvHorario;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private IdooGroupApplication globalAPP;
    private WebServicesIdooGroup servicesEDUKO;
    private Dialog dialogLoading = null;


    public LunesFragment() {
        // Required empty public constructor
    }


    public static LunesFragment newInstance() {
        return new LunesFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_lunes, container, false);

        try {
            // VARIABLE GLOBAL
            globalAPP = (IdooGroupApplication) getActivity().getApplication();
            servicesEDUKO = globalAPP.getService();

            // ASIGNADO LAS VARIABLES
            rvHorario = (RecyclerView) v.findViewById(R.id.rvHorarioLunes);
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
        try {
            if (globalAPP == null)
                globalAPP = (IdooGroupApplication) getActivity().getApplication();

            List<CalendarizacionModel> lunes = new ArrayList<>();

            for (int i = 0; i < globalAPP.calendarizacionModels.size(); i++) {
                if (globalAPP.calendarizacionModels.get(i).getDiaSemana().getNombre().toLowerCase().equals("lunes")) {
                    lunes.add(globalAPP.calendarizacionModels.get(i));
                }
            }

            if (lunes != null && lunes.size() > 0) {
                rvHorario.setVisibility(View.VISIBLE);
                tvNoDatos.setVisibility(View.GONE);

                mAdapter = new HorarioAdapter(lunes, R.layout.items_horarios_calendario);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    mLayoutManager = new LinearLayoutManager(getContext());
                }

                rvHorario.setLayoutManager(mLayoutManager);
                rvHorario.setAdapter(mAdapter);
            } else {
                rvHorario.setVisibility(View.GONE);
                tvNoDatos.setVisibility(View.VISIBLE);
            }
        } catch (Exception e) {
            Toasty.error(getContext(), getString(R.string.error_app), Toast.LENGTH_SHORT).show();
            Log.e(Constants.TAG, e.getMessage());
        }
    }

}
