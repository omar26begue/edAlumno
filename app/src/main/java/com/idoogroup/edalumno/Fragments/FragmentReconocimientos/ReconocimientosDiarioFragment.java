package com.idoogroup.edalumno.Fragments.FragmentReconocimientos;


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

import com.idoogroup.edalumno.Adapters.ReconocimientosAdapter;
import com.idoogroup.edalumno.Helpers.Constants;
import com.idoogroup.edalumno.IdooGroupApplication;
import com.idoogroup.edalumno.Models.StudentsRecognitionModel;
import com.idoogroup.edalumno.R;
import com.idoogroup.edalumno.WebServicesIdooGroup;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import es.dmoral.toasty.Toasty;


public class ReconocimientosDiarioFragment extends Fragment {

    // VARIABLES DE LA CLASE
    private TextView tvReconocimientosDiaria, tvNoDatos;
    private RecyclerView rvReconocimientosDiarias;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private IdooGroupApplication globalAPP;
    private WebServicesIdooGroup servicesEDUKO;
    private Dialog dialogLoading = null;

    private Calendar calendar = Calendar.getInstance();
    private Calendar calendar1 = Calendar.getInstance();
    private SimpleDateFormat sdfBD = new SimpleDateFormat("yyyy-MM-dd");
    private SimpleDateFormat sdfAPP = new SimpleDateFormat("dd/MM/yyyy");

    public List<StudentsRecognitionModel> reconocimientos = new ArrayList<>();


    public ReconocimientosDiarioFragment() {
        // Required empty public constructor
    }


    public static ReconocimientosDiarioFragment newInstance() {
        return new ReconocimientosDiarioFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_reconocimientos_diario, container, false);

        try {
            // VARIABLE GLOBAL
            globalAPP = (IdooGroupApplication) getActivity().getApplication();
            servicesEDUKO = globalAPP.getService();

            // ASIGNADO LAS VARIABLES
            tvReconocimientosDiaria = (TextView) v.findViewById(R.id.tvReconocimientosDiaria);
            tvNoDatos = (TextView) v.findViewById(R.id.tvNoDatos);
            rvReconocimientosDiarias = (RecyclerView) v.findViewById(R.id.rvReconocimientosDiarias);

            // ASIGNACION DE FUENTES
            tvReconocimientosDiaria.setTypeface(globalAPP.getFuenteAvenidHeavy(getActivity().getAssets()));
            tvNoDatos.setTypeface(globalAPP.getFuenteAvenidRoman(getActivity().getAssets()));

            updateReconocimiento();
        } catch (Exception e) {
            Toasty.error(getContext(), getString(R.string.error_app), Toast.LENGTH_SHORT).show();
            Log.e(Constants.TAG, e.getMessage());
        }

        return v;
    }


    public void updateReconocimiento() {
        try {
            List<StudentsRecognitionModel> diarios = new ArrayList<>();

            for (int i = 0; i < globalAPP.reconocimientoModels.size(); i++) {
                if (globalAPP.reconocimientoModels.get(i).getFrecuencia().getTipo_frecuencia().toLowerCase().equals("diarias")) {
                    diarios.add(globalAPP.reconocimientoModels.get(i));
                }
            }

            if (diarios != null && diarios.size() > 0) {
                rvReconocimientosDiarias.setVisibility(View.VISIBLE);
                tvNoDatos.setVisibility(View.GONE);

                mAdapter = new ReconocimientosAdapter(diarios, R.layout.items_reconocimientos);
                mLayoutManager = new LinearLayoutManager(getContext());

                rvReconocimientosDiarias.setLayoutManager(mLayoutManager);
                rvReconocimientosDiarias.setAdapter(mAdapter);
            } else {
                rvReconocimientosDiarias.setVisibility(View.GONE);
                tvNoDatos.setVisibility(View.VISIBLE);
            }
        } catch (Exception e) {
            Toasty.error(getContext(), getString(R.string.error_app), Toast.LENGTH_SHORT).show();
            Log.e(Constants.TAG, e.getMessage());
        }
    }

}
