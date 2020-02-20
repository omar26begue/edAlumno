package com.idoogroup.edalumno.Fragments.FragmentCalificaciones;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.idoogroup.edalumno.Adapters.CalificacionesAdapter;
import com.idoogroup.edalumno.Helpers.Constants;
import com.idoogroup.edalumno.Helpers.DatePickerFragment;
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
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CalificacionesDiariasFragment extends Fragment {

    private TextView tvCalificacionesDiarias, tvNoDatos, tvFecha;
    private RecyclerView rvCalificacionesDiarias;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private IdooGroupApplication globalAPP;
    private WebServicesIdooGroup servicesEDUKO;
    private Dialog dialogLoading = null;

    private Calendar calendar = Calendar.getInstance();
    private SimpleDateFormat sdfBD = new SimpleDateFormat("yyyy-MM-dd");
    private SimpleDateFormat sdfAPP = new SimpleDateFormat("dd/MM/yyyy");

    public String idSubject = "-1", fecha;


    public CalificacionesDiariasFragment() {
        // Required empty public constructor
    }


    public static CalificacionesDiariasFragment newInstance() {
        return new CalificacionesDiariasFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_calificaciones_diarias, container, false);

        try {
            // VARIABLE GLOBAL
            globalAPP = (IdooGroupApplication) getActivity().getApplication();
            servicesEDUKO = globalAPP.getService();

            // ASIGNADO LAS VARIABLES
            tvCalificacionesDiarias = (TextView) v.findViewById(R.id.tvCalificacionesDiarias);
            tvNoDatos = (TextView) v.findViewById(R.id.tvNoDatos);
            tvFecha = (TextView) v.findViewById(R.id.tvFecha);
            rvCalificacionesDiarias = (RecyclerView) v.findViewById(R.id.rvCalificacionesDiarias);

            // ASIGNACION DE FUENTES
            tvCalificacionesDiarias.setTypeface(globalAPP.getFuenteAvenidHeavy(getActivity().getAssets()));
            tvNoDatos.setTypeface(globalAPP.getFuenteAvenidRoman(getActivity().getAssets()));
            tvFecha.setTypeface(globalAPP.getFuenteAvenidRoman(getContext().getAssets()));

            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            LayoutInflater inflaterLoading = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflaterLoading.inflate(R.layout.dialog_loading, null);

            builder.setView(view);
            dialogLoading = builder.create();
            dialogLoading.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialogLoading.setCancelable(false);

            tvFecha.setText(String.valueOf(calendar.get(Calendar.DAY_OF_MONTH) + "/" + (Integer.valueOf(calendar.get(Calendar.MONTH)) + 1) + "/" + calendar.get(Calendar.YEAR)));

            tvFecha.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DatePickerFragment datePickerFragment = new DatePickerFragment();
                    datePickerFragment.show(getActivity().getFragmentManager(), "Date picker");

                    datePickerFragment.setListener(new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            tvFecha.setText(dayOfMonth + "/" + (month + 1) + "/" + year);

                            actualizarCalificacionesDiarias();
                        }
                    });
                }
            });
        } catch (Exception e) {
            Toasty.error(getContext(), getString(R.string.error_app), Toast.LENGTH_SHORT).show();
            Log.e(Constants.TAG, e.getMessage());
        }

        return v;
    }


    /**
     * Muestra el icono para la espera de una transición
     */
    private void mostrarCargando() {
        try {
            dialogLoading.show();
        } catch (Exception e) {
            Toasty.error(getContext(), getString(R.string.error_app), Toast.LENGTH_SHORT).show();
            Log.e(Constants.TAG, e.getMessage());
        }
    }


    /**
     * Oculta el icono para de la transcición
     *
     * @param tiempo
     */
    private void ocultarCargando(boolean tiempo) {
        try {
            if (tiempo) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dialogLoading.dismiss();
                    }
                }, 1000);
            } else
                dialogLoading.dismiss();
        } catch (Exception e) {
            Toasty.error(getContext(), getString(R.string.error_app), Toast.LENGTH_SHORT).show();
            Log.e(Constants.TAG, e.getMessage());
        }
    }


    public void actualizarCalificacionesDiarias() {
        try {
            mostrarCargando();

            final String token = globalAPP.sessionEDUAPP.getAccessToken(getContext());
            final String idStudent = globalAPP.sessionEDUAPP.getID(getContext());
            String filtro = "{\"include\":[{\"calendarizacion\":\"subject\"},\"dayEval\"],\"where\":{\"studentId\":\"" + idStudent + "\"},\"order\":\"fecha desc\"}";

            Call<List<AssistancesModel>> call = servicesEDUKO.getAsistencia(token, filtro);
            call.enqueue(new Callback<List<AssistancesModel>>() {
                @Override
                public void onResponse(Call<List<AssistancesModel>> call, Response<List<AssistancesModel>> response) {
                    ocultarCargando(true);

                    switch (response.code()) {
                        case 200: {
                            if (response.isSuccessful()) {
                                Log.i("EDUKO-ALUMNO-CALIF", "WS Listar calificaciones -> " + response.body().size());
                                globalAPP.assistancesModels = response.body();
                                updateCalificaciones();
                            } else {
                                globalAPP.assistancesModels.clear();
                                updateCalificaciones();
                            }
                        }
                        break;

                        case 401: {
                            globalAPP.assistancesModels.clear();
                            updateCalificaciones();
                            Toasty.error(getContext(), getString(R.string.error_login_app), Toast.LENGTH_SHORT).show();
                        }
                        break;

                        default: {
                            Log.i("EDUKO-ALUMNO-CALIF", "WS Listar calificaciones -> default");
                            Toasty.error(getContext(), getString(R.string.error_calificaciones), Toast.LENGTH_SHORT).show();
                            globalAPP.assistancesModels.clear();
                            updateCalificaciones();
                        }
                        break;
                    }
                }

                @Override
                public void onFailure(Call<List<AssistancesModel>> call, Throwable throwable) {
                    ocultarCargando(true);
                    Log.i("EDUKO-ALUMNO-CALIF", "WS Listar calificaciones -> failure");
                    Toasty.error(getContext(), getString(R.string.error_calificaciones), Toast.LENGTH_SHORT).show();
                    globalAPP.assistancesModels.clear();
                    updateCalificaciones();
                }
            });
        } catch (Exception e) {
            Toasty.error(getContext(), getString(R.string.error_app), Toast.LENGTH_SHORT).show();
            Log.e(Constants.TAG, e.getMessage());
        }
    }


    private void updateCalificaciones() {
        try {
            List<AssistancesModel> asistencia = new ArrayList<>();
            Calendar calendar1 = Calendar.getInstance();
            Calendar calendar2 = Calendar.getInstance();

            for (int i = 0; i < globalAPP.assistancesModels.size(); i++) {
                Moment moment = new Moment(globalAPP.assistancesModels.get(i).getFecha(), "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault());
                moment.getCalendar().setTimeZone(TimeZone.getTimeZone("GMT-07:00"));
                SimpleDateFormat sdfOrdenar = new SimpleDateFormat("dd/MM/yyyy hh:mm aaa");
                sdfOrdenar.setTimeZone(TimeZone.getTimeZone("GMT-07:00"));
                Date fechaBD = moment.getDate();

                Date fechaAPP = sdfAPP.parse(tvFecha.getText().toString());
                calendar2.setTime(fechaAPP);

                if (fechaAPP.getYear() == fechaBD.getYear() && fechaAPP.getMonth() == fechaBD.getMonth() && fechaAPP.getDay() == fechaBD.getDay()) {//(globalAPP.assistancesModels.get(i).getAssistance() == true) {
                    if (idSubject.equals("-1")) {
                        asistencia.add(globalAPP.assistancesModels.get(i));
                    } else if (globalAPP.assistancesModels.get(i).getCalendarizacion().getSubject().getId().equals(idSubject)) {
                        asistencia.add(globalAPP.assistancesModels.get(i));
                    }
                }
            }

            if (asistencia != null && asistencia.size() > 0) {
                rvCalificacionesDiarias.setVisibility(View.VISIBLE);
                tvNoDatos.setVisibility(View.GONE);

                mAdapter = new CalificacionesAdapter(asistencia, R.layout.items_evaluaciones);
                mLayoutManager = new LinearLayoutManager(getContext());

                rvCalificacionesDiarias.setLayoutManager(mLayoutManager);
                rvCalificacionesDiarias.setAdapter(mAdapter);
            } else {
                rvCalificacionesDiarias.setVisibility(View.GONE);
                tvNoDatos.setVisibility(View.VISIBLE);
            }
        } catch (Exception e) {
            Toasty.error(getContext(), getString(R.string.error_app), Toast.LENGTH_SHORT).show();
            Log.e(Constants.TAG, e.getMessage());
        }
    }

}
