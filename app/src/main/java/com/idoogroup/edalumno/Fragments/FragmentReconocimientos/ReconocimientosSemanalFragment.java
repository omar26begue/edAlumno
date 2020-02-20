package com.idoogroup.edalumno.Fragments.FragmentReconocimientos;


import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
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


public class ReconocimientosSemanalFragment extends Fragment {

    // VARIABLES DE LA CLASE
    private TextView tvReconocimientosSemanal, tvNoDatos;
    private RecyclerView rvReconocimientosSemanal;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private IdooGroupApplication globalAPP;
    private WebServicesIdooGroup servicesEDUKO;
    private Dialog dialogLoading = null;

    private Calendar calendar = Calendar.getInstance();
    private Calendar calendar1 = Calendar.getInstance();
    private SimpleDateFormat sdfBD = new SimpleDateFormat("yyyy-MM-dd");
    private SimpleDateFormat sdfAPP = new SimpleDateFormat("dd/MM/yyyy");
    private String idFrecuencia;

    public List<StudentsRecognitionModel> reconocimientos = new ArrayList<>();


    public ReconocimientosSemanalFragment() {
        // Required empty public constructor
    }


    public static ReconocimientosSemanalFragment newInstance() {
        return new ReconocimientosSemanalFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_reconocimientos_semanal, container, false);

        try {
            // VARIABLE GLOBAL
            globalAPP = (IdooGroupApplication) getActivity().getApplication();
            servicesEDUKO = globalAPP.getService();

            // ASIGNADO LAS VARIABLES
            tvReconocimientosSemanal = (TextView) v.findViewById(R.id.tvReconocimientosSemanal);
            tvNoDatos = (TextView) v.findViewById(R.id.tvNoDatos);
            rvReconocimientosSemanal = (RecyclerView) v.findViewById(R.id.rvReconocimientosSemanal);

            // ASIGNACION DE FUENTES
            tvReconocimientosSemanal.setTypeface(globalAPP.getFuenteAvenidHeavy(getActivity().getAssets()));
            tvNoDatos.setTypeface(globalAPP.getFuenteAvenidRoman(getActivity().getAssets()));

            reconocimientos = globalAPP.reconocimientoModels;

            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            LayoutInflater inflaterLoading = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflaterLoading.inflate(R.layout.dialog_loading, null);

            builder.setView(view);
            dialogLoading = builder.create();
            dialogLoading.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialogLoading.setCancelable(false);
        } catch (Exception e) {
            Toasty.error(getContext(), getString(R.string.error_app), Toast.LENGTH_SHORT).show();
            Log.e(Constants.TAG, e.getMessage());
        }

        return v;
    }


    public void updateReconocimiento() {
        List<StudentsRecognitionModel> semanal = new ArrayList<>();

        try {
            for (int i = 0; i < globalAPP.reconocimientoModels.size(); i++) {
                if (globalAPP.reconocimientoModels.get(i).getFrecuencia().getTipo_frecuencia().toLowerCase().equals("semanales")) {
                    semanal.add(globalAPP.reconocimientoModels.get(i));
                }
            }
        } catch (Exception e) {
            Toast.makeText(getContext(), getContext().getString(R.string.error_fecha), Toast.LENGTH_SHORT).show();
        }

        if (semanal != null && semanal.size() > 0) {
            rvReconocimientosSemanal.setVisibility(View.VISIBLE);
            tvNoDatos.setVisibility(View.GONE);

            mAdapter = new ReconocimientosAdapter(semanal, R.layout.items_reconocimientos);
            mLayoutManager = new LinearLayoutManager(getContext());

            rvReconocimientosSemanal.setLayoutManager(mLayoutManager);
            rvReconocimientosSemanal.setAdapter(mAdapter);
        } else {
            rvReconocimientosSemanal.setVisibility(View.GONE);
            tvNoDatos.setVisibility(View.VISIBLE);
        }
    }

}
