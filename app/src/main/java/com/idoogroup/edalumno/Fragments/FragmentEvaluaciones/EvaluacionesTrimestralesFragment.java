package com.idoogroup.edalumno.Fragments.FragmentEvaluaciones;


import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.idoogroup.edalumno.Adapters.EvaluacionesAdapter;
import com.idoogroup.edalumno.Helpers.Constants;
import com.idoogroup.edalumno.Helpers.Moment.Moment;
import com.idoogroup.edalumno.IdooGroupApplication;
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


public class EvaluacionesTrimestralesFragment extends Fragment {

    // VARIABLES DE LA CLASE
    private TextView tvEvaluacionesTrimestrales, tvNoDatos, tvAsignatura, tvPuntosInicial, tvPuntosFinal;
    private LinearLayout llAsignaturas, llPuntosInicial, llPuntosFinal;
    private RecyclerView rvEvaluacionesTrimestrales;
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

    private String idSubject = "-1";
    private int puntosInicial = 1, puntosFinal = 10;


    public EvaluacionesTrimestralesFragment() {
        // Required empty public constructor
    }


    public static EvaluacionesTrimestralesFragment newInstance() {
        return new EvaluacionesTrimestralesFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_evaluaciones_trimestrales, container, false);

        try {
            // VARIABLE GLOBAL
            globalAPP = (IdooGroupApplication) getActivity().getApplication();
            servicesEDUKO = globalAPP.getService();

            // ASIGNADO LAS VARIABLES
            tvEvaluacionesTrimestrales = (TextView) v.findViewById(R.id.tvEvaluacionesTrimestrales);
            tvNoDatos = (TextView) v.findViewById(R.id.tvNoDatos);
            tvAsignatura = (TextView) v.findViewById(R.id.tvAsignatura);
            tvPuntosInicial = (TextView) v.findViewById(R.id.tvPuntosInicial);
            tvPuntosFinal = (TextView) v.findViewById(R.id.tvPuntosFinal);
            llAsignaturas = (LinearLayout) v.findViewById(R.id.llAsignaturas);
            llPuntosInicial = (LinearLayout) v.findViewById(R.id.llPuntosInicial);
            llPuntosFinal = (LinearLayout) v.findViewById(R.id.llPuntosFinal);
            rvEvaluacionesTrimestrales = (RecyclerView) v.findViewById(R.id.rvEvaluacionesTrimestrales);


            // ASIGNACION DE FUENTES
            tvEvaluacionesTrimestrales.setTypeface(globalAPP.getFuenteAvenidHeavy(getActivity().getAssets()));
            tvNoDatos.setTypeface(globalAPP.getFuenteAvenidRoman(getActivity().getAssets()));
            tvAsignatura.setTypeface(globalAPP.getFuenteAvenidRoman(getActivity().getAssets()));
            tvPuntosInicial.setTypeface(globalAPP.getFuenteAvenidRoman(getActivity().getAssets()));
            tvPuntosFinal.setTypeface(globalAPP.getFuenteAvenidRoman(getActivity().getAssets()));


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
        } catch (Exception e) {
            Toasty.error(getContext(), getString(R.string.error_app), Toast.LENGTH_SHORT).show();
            Log.e(Constants.TAG, e.getMessage());
        }

        return v;
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

                        actualizarEvaluaciones();
                    } else {
                        tvAsignatura.setText(globalAPP.subjectsModels.get(item.getItemId() - 1).getName());
                        idSubject = globalAPP.subjectsModels.get(item.getItemId() - 1).getId();

                        actualizarEvaluaciones();
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

                    actualizarEvaluaciones();
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

                    actualizarEvaluaciones();
                    return true;
                }
            });

            popupMenu.show();
        } catch (Exception e) {
            Toasty.error(getContext(), getString(R.string.error_app), Toast.LENGTH_SHORT).show();
            Log.e(Constants.TAG, e.getMessage());
        }
    }


    public void actualizarEvaluaciones() {
        try {
            List<StudentsEvaluationModel> evaluaciones = new ArrayList<>();
            String fechaHoy = calendar.get(Calendar.DAY_OF_MONTH) + "/" + (Integer.valueOf(calendar.get(Calendar.MONTH)) + 1) + "/" + calendar.get(Calendar.YEAR);

            if (puntosInicial > puntosFinal) {
                Toast.makeText(getContext(), getString(R.string.error_rango_puntuacion), Toast.LENGTH_SHORT).show();
                puntosInicial = 1;
                puntosFinal = 10;
                tvPuntosInicial.setText("5 ptos");
                tvPuntosFinal.setText("10 ptos");
            }

            for (int i = 0; i < globalAPP.evaluacionesModels.size(); i++) {
                if (globalAPP.evaluacionesModels.get(i).getFrecuencia().getTipo_frecuencia().toLowerCase().equals("trimestrales")) {
                    Moment moment = new Moment(globalAPP.evaluacionesModels.get(i).getFecha(), "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault());
                    moment.getCalendar().setTimeZone(TimeZone.getTimeZone("GMT-07:00"));
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm aaa");
                    sdf.setTimeZone(TimeZone.getTimeZone("GMT-07:00"));

                    Date fechaAPP = sdfAPP.parse(fechaHoy);
                    calendar2.setTime(fechaAPP);

                    calendar1.setTime(sdf.parse(sdf.format(moment.getDate())));
                    calendar2.set(Calendar.MONTH, -3);

                    if (idSubject.equals("-1")) {
                        if (globalAPP.evaluacionesModels.get(i).getNomCalificacion().getQuantitative() >= puntosInicial) {
                            if (globalAPP.evaluacionesModels.get(i).getNomCalificacion().getQuantitative() <= puntosFinal) {
                                if (calendar2.before(calendar1)) {
                                    evaluaciones.add(globalAPP.evaluacionesModels.get(i));
                                }
                            }
                        }
                    } else if (globalAPP.evaluacionesModels.get(i).getSubjectId().equals(idSubject)) {
                        if (globalAPP.evaluacionesModels.get(i).getNomCalificacion().getQuantitative() >= puntosInicial) {
                            if (globalAPP.evaluacionesModels.get(i).getNomCalificacion().getQuantitative() <= puntosFinal) {
                                if (calendar2.before(calendar1)) {
                                    evaluaciones.add(globalAPP.evaluacionesModels.get(i));

                                }
                            }
                        }
                    }
                }
            }

            updateEvaluaciones(evaluaciones);
        } catch (Exception e) {
            Toasty.error(getContext(), getString(R.string.error_app), Toast.LENGTH_SHORT).show();
            Log.e(Constants.TAG, e.getMessage());
        }
    }


    public void updateEvaluaciones(List<StudentsEvaluationModel> evaluaciones) {
        try {
            if (evaluaciones != null && evaluaciones.size() > 0) {
                rvEvaluacionesTrimestrales.setVisibility(View.VISIBLE);
                tvNoDatos.setVisibility(View.GONE);

                mAdapter = new EvaluacionesAdapter(evaluaciones, R.layout.items_calificaciones);
                mLayoutManager = new LinearLayoutManager(getContext());

                rvEvaluacionesTrimestrales.setLayoutManager(mLayoutManager);
                rvEvaluacionesTrimestrales.setAdapter(mAdapter);
            } else {
                rvEvaluacionesTrimestrales.setVisibility(View.GONE);
                tvNoDatos.setVisibility(View.VISIBLE);
            }
        } catch (Exception e) {
            Toasty.error(getContext(), getString(R.string.error_app), Toast.LENGTH_SHORT).show();
            Log.e(Constants.TAG, e.getMessage());
        }
    }

}
