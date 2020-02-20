package com.idoogroup.edalumno.Fragments.FragmentCalificaciones;


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

import com.idoogroup.edalumno.Adapters.CalificacionesAdapter;
import com.idoogroup.edalumno.Helpers.Constants;
import com.idoogroup.edalumno.Helpers.Moment.Moment;
import com.idoogroup.edalumno.IdooGroupApplication;
import com.idoogroup.edalumno.Models.AssistancesModel;
import com.idoogroup.edalumno.R;
import com.idoogroup.edalumno.WebServicesIdooGroup;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import es.dmoral.toasty.Toasty;


public class CalificacionesAnualFragment extends Fragment {

    private TextView tvCalificacionesAnuales, tvNoDatos;
    private RecyclerView rvCalificacionesAnuales;
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

    public String idSubject = "-1", fecha;


    public CalificacionesAnualFragment() {
        // Required empty public constructor
    }


    public static CalificacionesAnualFragment newInstance() {
        return new CalificacionesAnualFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_calificaciones_anual, container, false);

        try {
            // VARIABLE GLOBAL
            globalAPP = (IdooGroupApplication) getActivity().getApplication();
            servicesEDUKO = globalAPP.getService();

            // ASIGNADO LAS VARIABLES
            tvCalificacionesAnuales = (TextView) v.findViewById(R.id.tvCalificacionesAnuales);
            tvNoDatos = (TextView) v.findViewById(R.id.tvNoDatos);
            rvCalificacionesAnuales = (RecyclerView) v.findViewById(R.id.rvCalificacionesAnuales);

            // ASIGNACION DE FUENTES
            tvCalificacionesAnuales.setTypeface(globalAPP.getFuenteAvenidHeavy(getActivity().getAssets()));
            tvNoDatos.setTypeface(globalAPP.getFuenteAvenidRoman(getActivity().getAssets()));
        } catch (Exception e) {
            Toasty.error(getContext(), getString(R.string.error_app), Toast.LENGTH_SHORT).show();
            Log.e(Constants.TAG, e.getMessage());
        }

        return v;
    }


    public void updateCalificaciones() {
        try {
            List<AssistancesModel> asistencia = new ArrayList<>();
            String fechaHoy = calendar.get(Calendar.DAY_OF_MONTH) + "/" + (Integer.valueOf(calendar.get(Calendar.MONTH)) + 1) + "/" + calendar.get(Calendar.YEAR);

            for (int i = 0; i < globalAPP.assistancesModels.size(); i++) {
                if (globalAPP.assistancesModels.get(i).getAssistance() == true) {
                    Moment moment = new Moment(globalAPP.assistancesModels.get(i).getFecha(), "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault());
                    moment.getCalendar().setTimeZone(TimeZone.getTimeZone("GMT-07:00"));
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm aaa");
                    sdf.setTimeZone(TimeZone.getTimeZone("GMT-07:00"));

                    calendar1.setTime(sdf.parse(sdf.format(moment.getDate())));
                    Date fechaAPP = sdfAPP.parse(fechaHoy);
                    calendar2.setTime(fechaAPP);

                    if (calendar1.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR)) {
                        if (idSubject.equals("-1")) {
                            asistencia.add(globalAPP.assistancesModels.get(i));
                        } else if (globalAPP.assistancesModels.get(i).getCalendarizacion().getSubject().getId().equals(idSubject)) {
                            asistencia.add(globalAPP.assistancesModels.get(i));
                        }
                    }
                }
            }

            if (asistencia != null && asistencia.size() > 0) {
                rvCalificacionesAnuales.setVisibility(View.VISIBLE);
                tvNoDatos.setVisibility(View.GONE);

                mAdapter = new CalificacionesAdapter(asistencia, R.layout.items_evaluaciones);
                mLayoutManager = new LinearLayoutManager(getContext());

                rvCalificacionesAnuales.setLayoutManager(mLayoutManager);
                rvCalificacionesAnuales.setAdapter(mAdapter);
            } else {
                rvCalificacionesAnuales.setVisibility(View.GONE);
                tvNoDatos.setVisibility(View.VISIBLE);
            }
        } catch (Exception e) {
            Toasty.error(getContext(), getString(R.string.error_app), Toast.LENGTH_SHORT).show();
            Log.e(Constants.TAG, e.getMessage());
        }
    }

}
