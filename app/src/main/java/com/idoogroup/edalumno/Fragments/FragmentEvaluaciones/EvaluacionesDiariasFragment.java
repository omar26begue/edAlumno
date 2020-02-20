package com.idoogroup.edalumno.Fragments.FragmentEvaluaciones;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.idoogroup.edalumno.Adapters.EvaluacionesAdapter;
import com.idoogroup.edalumno.Helpers.Constants;
import com.idoogroup.edalumno.Helpers.DatePickerFragment;
import com.idoogroup.edalumno.Helpers.Moment.Moment;
import com.idoogroup.edalumno.IdooGroupApplication;
import com.idoogroup.edalumno.Models.AssistancesModel;
import com.idoogroup.edalumno.Models.NomCalificacionModel;
import com.idoogroup.edalumno.Models.StudentsEvaluationModel;
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


public class EvaluacionesDiariasFragment extends Fragment {

    // VARIABLES DE LA CLASE
    private TextView tvEvaluacionesDiarias, tvNoDatos, tvFecha, tvAsignatura, tvPuntosInicial, tvPuntosFinal;
    private LinearLayout llAsignaturas, llPuntosInicial, llPuntosFinal;
    private RecyclerView rvEvaluacionesDiarias;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private IdooGroupApplication globalAPP;
    private WebServicesIdooGroup servicesEDUKO;
    private Dialog dialogLoading = null;

    private Calendar calendar = Calendar.getInstance();
    private SimpleDateFormat sdfBD = new SimpleDateFormat("yyyy-MM-dd");
    private SimpleDateFormat sdfAPP = new SimpleDateFormat("dd/MM/yyyy");

    private String fecha, idSubject = "-1", idFrecuencia;
    private int puntosInicial = 1, puntosFinal = 10;
    private List<AssistancesModel> assistancesModels = new ArrayList<>();


    public EvaluacionesDiariasFragment() {
        // Required empty public constructor
    }


    public static EvaluacionesDiariasFragment newInstance() {
        return new EvaluacionesDiariasFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_evaluaciones_diarias, container, false);

        try {
            // VARIABLE GLOBAL
            globalAPP = (IdooGroupApplication) getActivity().getApplication();
            servicesEDUKO = globalAPP.getService();

            // ASIGNADO LAS VARIABLES
            tvEvaluacionesDiarias = (TextView) v.findViewById(R.id.tvEvaluacionesDiarias);
            tvNoDatos = (TextView) v.findViewById(R.id.tvNoDatos);
            tvFecha = (TextView) v.findViewById(R.id.tvFecha);
            tvAsignatura = (TextView) v.findViewById(R.id.tvAsignatura);
            tvPuntosInicial = (TextView) v.findViewById(R.id.tvPuntosInicial);
            tvPuntosFinal = (TextView) v.findViewById(R.id.tvPuntosFinal);
            tvFecha = (TextView) v.findViewById(R.id.tvFecha);
            llAsignaturas = (LinearLayout) v.findViewById(R.id.llAsignaturas);
            llPuntosInicial = (LinearLayout) v.findViewById(R.id.llPuntosInicial);
            llPuntosFinal = (LinearLayout) v.findViewById(R.id.llPuntosFinal);
            rvEvaluacionesDiarias = (RecyclerView) v.findViewById(R.id.rvEvaluacionesDiarias);

            // ASIGNACION DE FUENTES
            tvEvaluacionesDiarias.setTypeface(globalAPP.getFuenteAvenidHeavy(getActivity().getAssets()));
            tvNoDatos.setTypeface(globalAPP.getFuenteAvenidRoman(getActivity().getAssets()));
            tvAsignatura.setTypeface(globalAPP.getFuenteAvenidRoman(getActivity().getAssets()));
            tvPuntosInicial.setTypeface(globalAPP.getFuenteAvenidRoman(getActivity().getAssets()));
            tvPuntosFinal.setTypeface(globalAPP.getFuenteAvenidRoman(getActivity().getAssets()));
            tvFecha.setTypeface(globalAPP.getFuenteAvenidRoman(getContext().getAssets()));

            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            LayoutInflater inflaterLoading = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflaterLoading.inflate(R.layout.dialog_loading, null);

            builder.setView(view);
            dialogLoading = builder.create();
            dialogLoading.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialogLoading.setCancelable(false);

            tvFecha.setText(String.valueOf(calendar.get(Calendar.DAY_OF_MONTH) + "/" + (Integer.valueOf(calendar.get(Calendar.MONTH)) + 1) + "/" + calendar.get(Calendar.YEAR)));

            llAsignaturas.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showAsignaturas(v);
                }
            });

            llPuntosInicial.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showPuntosInicial(v);
                }
            });

            llPuntosFinal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showPuntosFinal(v);
                }
            });

            tvFecha.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DatePickerFragment datePickerFragment = new DatePickerFragment();
                    datePickerFragment.show(getActivity().getFragmentManager(), "Date picker");

                    datePickerFragment.setListener(new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            tvFecha.setText(dayOfMonth + "/" + (month + 1) + "/" + year);

                            updadeEvaluaciones();
                        }
                    });
                }
            });


            for (int i = 0; i < globalAPP.frecuenciaModels.size(); i++) {
                if (globalAPP.frecuenciaModels.get(i).getTipo_frecuencia().toLowerCase().equals("diarias")) {
                    idFrecuencia = globalAPP.frecuenciaModels.get(i).getId();
                }
            }

            updadeEvaluaciones();
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


    private void showAsignaturas(View view) {
        try {
            PopupMenu popupMenu = new PopupMenu(view.getContext(), view);
            popupMenu.getMenu().add(Menu.NONE, 0, 0, "Todas");

            for (int i = 0; i < globalAPP.subjectsModels.size(); i++) {
                popupMenu.getMenu().add(Menu.NONE, i + 1, i + 1, globalAPP.subjectsModels.get(i).getName());
            }

            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    if (item.getItemId() == 0) {
                        tvAsignatura.setText("Todas");
                        idSubject = "-1";
                    } else {
                        tvAsignatura.setText(globalAPP.subjectsModels.get(item.getItemId() - 1).getName());
                        idSubject = globalAPP.subjectsModels.get(item.getItemId() - 1).getId();
                    }
                    return true;
                }
            });

            popupMenu.show();
        } catch (Exception e) {
            Toasty.error(getContext(), getString(R.string.error_app), Toast.LENGTH_SHORT).show();
            Log.e(Constants.TAG, e.getMessage());
        }
    }


    private void showPuntosInicial(View view) {
        try {
            PopupMenu popupMenu = new PopupMenu(view.getContext(), view);
            popupMenu.getMenu().add(Menu.NONE, 1, 1, "1 ptos");
            popupMenu.getMenu().add(Menu.NONE, 2, 2, "2 ptos");
            popupMenu.getMenu().add(Menu.NONE, 3, 3, "3 ptos");
            popupMenu.getMenu().add(Menu.NONE, 4, 4, "4 ptos");
            popupMenu.getMenu().add(Menu.NONE, 5, 5, "5 ptos");
            popupMenu.getMenu().add(Menu.NONE, 6, 6, "6 ptos");
            popupMenu.getMenu().add(Menu.NONE, 7, 7, "7 ptos");
            popupMenu.getMenu().add(Menu.NONE, 8, 8, "8 ptos");
            popupMenu.getMenu().add(Menu.NONE, 9, 9, "9 ptos");
            popupMenu.getMenu().add(Menu.NONE, 10, 10, "10 ptos");

            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    tvPuntosInicial.setText(item.getTitle());
                    puntosInicial = item.getItemId();

                    updadeEvaluaciones();
                    return true;
                }
            });

            popupMenu.show();
        } catch (Exception e) {
            Toasty.error(getContext(), getString(R.string.error_app), Toast.LENGTH_SHORT).show();
            Log.e(Constants.TAG, e.getMessage());
        }
    }


    private void showPuntosFinal(View view) {
        try {
            PopupMenu popupMenu = new PopupMenu(view.getContext(), view);
            popupMenu.getMenu().add(Menu.NONE, 1, 1, "1 ptos");
            popupMenu.getMenu().add(Menu.NONE, 2, 2, "2 ptos");
            popupMenu.getMenu().add(Menu.NONE, 3, 3, "3 ptos");
            popupMenu.getMenu().add(Menu.NONE, 4, 4, "4 ptos");
            popupMenu.getMenu().add(Menu.NONE, 5, 5, "5 ptos");
            popupMenu.getMenu().add(Menu.NONE, 6, 6, "6 ptos");
            popupMenu.getMenu().add(Menu.NONE, 7, 7, "7 ptos");
            popupMenu.getMenu().add(Menu.NONE, 8, 8, "8 ptos");
            popupMenu.getMenu().add(Menu.NONE, 9, 9, "9 ptos");
            popupMenu.getMenu().add(Menu.NONE, 10, 10, "10 ptos");

            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    tvPuntosFinal.setText(item.getTitle());
                    puntosFinal = item.getItemId();

                    updadeEvaluaciones();
                    return true;
                }
            });

            popupMenu.show();
        } catch (Exception e) {
            Toasty.error(getContext(), getString(R.string.error_app), Toast.LENGTH_SHORT).show();
            Log.e(Constants.TAG, e.getMessage());
        }
    }


    public void obtenerEvaluaciones() {
        try {
            mostrarCargando();

            final String token = globalAPP.sessionEDUAPP.getAccessToken(getContext());
            final String idStudent = globalAPP.sessionEDUAPP.getID(getContext());
            String filtro = "{\"include\":[{\"subject\":\"teachers\"},\"frecuencia\",\"nomCalificacion\"],\"where\":{\"studentId\":\"" + idStudent + "\"},\"order\":\"fecha desc\"}";

            Call<List<AssistancesModel>> call = servicesEDUKO.getAsistencia(token, filtro);
            call.enqueue(new Callback<List<AssistancesModel>>() {
                @Override
                public void onResponse(Call<List<AssistancesModel>> call, Response<List<AssistancesModel>> response) {
                    ocultarCargando(true);

                    switch (response.code()) {
                        case 200: {
                            if (response.isSuccessful()) {
                                globalAPP.assistancesModels = response.body();
                                assistancesModels = response.body();

                                updadeEvaluaciones();
                            } else {
                                assistancesModels.clear();
                            }
                        }
                        break;

                        case 401: {
                            assistancesModels.clear();
                            Toasty.error(getContext(), getString(R.string.error_login_app), Toast.LENGTH_SHORT).show();
                        }
                        break;

                        default: {
                            Log.i("EDUKO-ALUMNO-CALIF", "WS Listar calificaciones -> default");
                            Toasty.error(getContext(), getString(R.string.error_ealuaciones), Toast.LENGTH_SHORT).show();
                            assistancesModels.clear();
                        }
                        break;
                    }
                }

                @Override
                public void onFailure(Call<List<AssistancesModel>> call, Throwable throwable) {
                    ocultarCargando(true);
                    Log.i("EDUKO-ALUMNO-CALIF", "WS Listar calificaciones -> failure");
                    Toasty.error(getContext(), getString(R.string.error_ealuaciones), Toast.LENGTH_SHORT).show();
                    assistancesModels.clear();
                }
            });
        } catch (Exception e) {
            Toasty.error(getContext(), getString(R.string.error_app), Toast.LENGTH_SHORT).show();
            Log.e(Constants.TAG, e.getMessage());
        }
    }


    public void updadeEvaluaciones() {
        try {
            List<StudentsEvaluationModel> calificaciones = new ArrayList<>();

            if (puntosInicial > puntosFinal) {
                Toast.makeText(getContext(), getString(R.string.error_rango_puntuacion), Toast.LENGTH_SHORT).show();
                puntosInicial = 1;
                puntosFinal = 10;
                tvPuntosInicial.setText("1 ptos");
                tvPuntosFinal.setText("10 ptos");
            }

            for (int i = 0; i < assistancesModels.size(); i++) {
                if (assistancesModels.get(i).getAssistance() == true) {
                    if (puntosInicial <= assistancesModels.get(i).getDayEval().getQuantitative()) {
                        if (puntosFinal >= assistancesModels.get(i).getDayEval().getQuantitative()) {
                            Moment moment = new Moment(assistancesModels.get(i).getFecha(), "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault());
                            moment.getCalendar().setTimeZone(TimeZone.getTimeZone("GMT-07:00"));
                            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm aaa");
                            sdf.setTimeZone(TimeZone.getTimeZone("GMT-07:00"));
                            Date fechaBD = moment.getDate();

                            Date fechaAPP = sdfAPP.parse(tvFecha.getText().toString());

                            if (fechaAPP.getYear() == fechaBD.getYear() && fechaAPP.getMonth() == fechaBD.getMonth() && fechaAPP.getDay() == fechaBD.getDay()) {
                                if (idSubject.equals("-1")) {
                                    StudentsEvaluationModel evaluationModel = new StudentsEvaluationModel();
                                    evaluationModel.setFecha(assistancesModels.get(i).getFecha());
                                    evaluationModel.setStudentId(globalAPP.sessionEDUAPP.getID(getContext()));
                                    evaluationModel.setSubjectId(assistancesModels.get(i).getCalendarizacion().getSubjectId());
                                    evaluationModel.setFrecuenciaId(idFrecuencia);
                                    evaluationModel.setNomCalificacionId(assistancesModels.get(i).getDayEvalId());
                                    evaluationModel.getSubject().setName(assistancesModels.get(i).getCalendarizacion().getSubject().getName());

                                    NomCalificacionModel calificacionModel = new NomCalificacionModel();
                                    calificacionModel.setActive(true);
                                    calificacionModel.setQualitative(assistancesModels.get(i).getDayEval().getQualitative());
                                    calificacionModel.setQuantitative(assistancesModels.get(i).getDayEval().getQuantitative());
                                    calificacionModel.setDescription(assistancesModels.get(i).getDayEval().getDescription());
                                    calificacionModel.setId(assistancesModels.get(i).getDayEval().getId());

                                    evaluationModel.setNomCalificacion(calificacionModel);
                                    calificaciones.add(evaluationModel);
                                } else if (assistancesModels.get(i).getCalendarizacion().getSubjectId().equals(idSubject)) {
                                    StudentsEvaluationModel evaluationModel = new StudentsEvaluationModel();
                                    evaluationModel.setFecha(assistancesModels.get(i).getFecha());
                                    evaluationModel.setStudentId(globalAPP.sessionEDUAPP.getID(getContext()));
                                    evaluationModel.setSubjectId(assistancesModels.get(i).getCalendarizacion().getSubjectId());
                                    evaluationModel.setFrecuenciaId(idFrecuencia);
                                    evaluationModel.setNomCalificacionId(assistancesModels.get(i).getDayEvalId());
                                    evaluationModel.getSubject().setName(assistancesModels.get(i).getCalendarizacion().getSubject().getName());

                                    NomCalificacionModel calificacionModel = new NomCalificacionModel();
                                    calificacionModel.setActive(true);
                                    calificacionModel.setQualitative(assistancesModels.get(i).getDayEval().getQualitative());
                                    calificacionModel.setQuantitative(assistancesModels.get(i).getDayEval().getQuantitative());
                                    calificacionModel.setDescription(assistancesModels.get(i).getDayEval().getDescription());
                                    calificacionModel.setId(assistancesModels.get(i).getDayEval().getId());

                                    evaluationModel.setNomCalificacion(calificacionModel);
                                    calificaciones.add(evaluationModel);
                                }
                            }
                        }
                    }
                }
            }

            if (calificaciones != null && calificaciones.size() > 0) {
                rvEvaluacionesDiarias.setVisibility(View.VISIBLE);
                tvNoDatos.setVisibility(View.GONE);

                mAdapter = new EvaluacionesAdapter(calificaciones, R.layout.items_calificaciones);
                mLayoutManager = new LinearLayoutManager(getContext());

                rvEvaluacionesDiarias.setLayoutManager(mLayoutManager);
                rvEvaluacionesDiarias.setAdapter(mAdapter);
            } else {
                rvEvaluacionesDiarias.setVisibility(View.GONE);
                tvNoDatos.setVisibility(View.VISIBLE);
            }
        } catch (Exception e) {
            Toasty.error(getContext(), getString(R.string.error_app), Toast.LENGTH_SHORT).show();
            Log.e(Constants.TAG, e.getMessage());
        }
    }

}
