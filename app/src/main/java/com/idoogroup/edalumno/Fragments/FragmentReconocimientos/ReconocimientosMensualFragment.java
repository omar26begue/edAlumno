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


public class ReconocimientosMensualFragment extends Fragment {

    // VARIABLES DE LA CLASE
    private TextView tvReconocimientosMensual, tvNoDatos, tvFecha;
    private RecyclerView rvReconocimientosMensual;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private IdooGroupApplication globalAPP;
    private WebServicesIdooGroup servicesEDUKO;
    private Dialog dialogLoading = null;

    private Calendar calendar = Calendar.getInstance();
    private Calendar calendar1 = Calendar.getInstance();
    private Calendar calendar2 = Calendar.getInstance();
    private SimpleDateFormat sdfBD = new SimpleDateFormat("yyyy-MM-dd");
    private SimpleDateFormat sdfAPP = new SimpleDateFormat("dd/MM/yyyy");


    public ReconocimientosMensualFragment() {
        // Required empty public constructor
    }


    public static ReconocimientosMensualFragment newInstance() {
        return new ReconocimientosMensualFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_reconocimientos_mensual, container, false);

        try {
            // VARIABLE GLOBAL
            globalAPP = (IdooGroupApplication) getActivity().getApplication();
            servicesEDUKO = globalAPP.getService();

            // ASIGNADO LAS VARIABLES
            tvReconocimientosMensual = (TextView) v.findViewById(R.id.tvReconocimientosMensual);
            tvNoDatos = (TextView) v.findViewById(R.id.tvNoDatos);
            tvFecha = (TextView) v.findViewById(R.id.tvFecha);
            rvReconocimientosMensual = (RecyclerView) v.findViewById(R.id.rvReconocimientosMensual);

            // ASIGNACION DE FUENTES
            tvReconocimientosMensual.setTypeface(globalAPP.getFuenteAvenidHeavy(getActivity().getAssets()));
            tvNoDatos.setTypeface(globalAPP.getFuenteAvenidRoman(getActivity().getAssets()));
        } catch (Exception e) {
            Toasty.error(getContext(), getString(R.string.error_app), Toast.LENGTH_SHORT).show();
            Log.e(Constants.TAG, e.getMessage());
        }

        return v;
    }


    public void updateReconocimiento() {
        List<StudentsRecognitionModel> reconocimientos = new ArrayList<>();
        String fechaHoy = calendar.get(Calendar.DAY_OF_MONTH) + "/" + (Integer.valueOf(calendar.get(Calendar.MONTH)) + 1) + "/" + calendar.get(Calendar.YEAR);

        try {
            for (int i = 0; i < globalAPP.reconocimientoModels.size(); i++) {
                if (globalAPP.reconocimientoModels.get(i).getFrecuencia().getTipo_frecuencia().toLowerCase().equals("mensuales")) {
                    reconocimientos.add(globalAPP.reconocimientoModels.get(i));
                }
            }
        } catch (Exception e) {
            Toast.makeText(getContext(), getContext().getString(R.string.error_fecha), Toast.LENGTH_SHORT).show();
        }

        if (reconocimientos != null && reconocimientos.size() > 0) {
            rvReconocimientosMensual.setVisibility(View.VISIBLE);
            tvNoDatos.setVisibility(View.GONE);

            mAdapter = new ReconocimientosAdapter(reconocimientos, R.layout.items_reconocimientos);
            mLayoutManager = new LinearLayoutManager(getContext());

            rvReconocimientosMensual.setLayoutManager(mLayoutManager);
            rvReconocimientosMensual.setAdapter(mAdapter);
        } else {
            rvReconocimientosMensual.setVisibility(View.GONE);
            tvNoDatos.setVisibility(View.VISIBLE);
        }
    }

}
