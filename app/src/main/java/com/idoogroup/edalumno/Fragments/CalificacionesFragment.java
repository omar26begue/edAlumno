package com.idoogroup.edalumno.Fragments;


import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.idoogroup.edalumno.Helpers.Constants;
import com.idoogroup.edalumno.IdooGroupApplication;
import com.idoogroup.edalumno.Models.AssistancesModel;
import com.idoogroup.edalumno.R;
import com.idoogroup.edalumno.ViewPager.ViewPagerCalificacionesAdapter;
import com.idoogroup.edalumno.WebServicesIdooGroup;

import java.util.List;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CalificacionesFragment extends Fragment {

    public static final String TITLE = "Calificaciones";
    private TextView tvCalificaciones, tvAsignaturas;
    private ImageView ivFlechaAsignatura;
    private LinearLayout llCalificaciones;
    private TabLayout tlCalificaciones;
    private ViewPager vpCalificaciones;
    private ViewPagerCalificacionesAdapter calificacionesAdapter;

    private IdooGroupApplication globalAPP;
    private WebServicesIdooGroup servicesEDUKO;
    private Dialog dialogLoading = null;

    private String idSubject = "-1";


    public CalificacionesFragment() {
        // Required empty public constructor
    }


    public static CalificacionesFragment newInstance() {
        return new CalificacionesFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_calificaciones, container, false);

        try {
            // VARIABLE GLOBAL
            globalAPP = (IdooGroupApplication) getActivity().getApplication();
            servicesEDUKO = globalAPP.getService();

            // ASIGNADO LAS VARIABLES
            tvCalificaciones = (TextView) v.findViewById(R.id.tvCalificaciones);
            tvAsignaturas = (TextView) v.findViewById(R.id.tvAsignaturas);
            ivFlechaAsignatura = (ImageView) v.findViewById(R.id.ivFlechaAsignatura);
            llCalificaciones = (LinearLayout) v.findViewById(R.id.llCalificaciones);
            tlCalificaciones = (TabLayout) v.findViewById(R.id.tlCalificaciones);
            vpCalificaciones = (ViewPager) v.findViewById(R.id.vpCalificaciones);

            // ASIGNACION DE FUENTES
            tvCalificaciones.setTypeface(globalAPP.getFuenteAvenidBlack(getActivity().getAssets()));

            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            LayoutInflater inflaterLoading = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflaterLoading.inflate(R.layout.dialog_loading, null);

            builder.setView(view);
            dialogLoading = builder.create();
            dialogLoading.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialogLoading.setCancelable(false);

            // TABS
            calificacionesAdapter = new ViewPagerCalificacionesAdapter(getFragmentManager());
            vpCalificaciones.setAdapter(calificacionesAdapter);
            tlCalificaciones.setupWithViewPager(vpCalificaciones);
            vpCalificaciones.setOffscreenPageLimit(6);
            vpCalificaciones.setCurrentItem(0);

            ivFlechaAsignatura.setColorFilter(Color.parseColor("#A2E62E"));

            // ICONOS DEL TABLAYOUT
            View vTabCalificacionesDiarias = inflater.inflate(R.layout.tab_menu_calificaciones_diarias, null);
            View vTabCalificacionesSemanales = inflater.inflate(R.layout.tab_menu_calificaciones_semanales, null);
            View vTabCalificacionesMensuales = inflater.inflate(R.layout.tab_menu_calificaciones_mensuales, null);
            View vTabCalificacionesTrimestral = inflater.inflate(R.layout.tab_menu_calificaciones_trimestral, null);
            View vTabCalificacionesSemestrales = inflater.inflate(R.layout.tab_menu_calificaciones_semestrales, null);
            View vTabCalificacionesAnual = inflater.inflate(R.layout.tab_menu_calificaciones_anual, null);

            tlCalificaciones.getTabAt(0).setCustomView(null);
            tlCalificaciones.getTabAt(0).setCustomView(vTabCalificacionesDiarias);
            tlCalificaciones.getTabAt(1).setCustomView(null);
            tlCalificaciones.getTabAt(1).setCustomView(vTabCalificacionesSemanales);
            tlCalificaciones.getTabAt(2).setCustomView(null);
            tlCalificaciones.getTabAt(2).setCustomView(vTabCalificacionesMensuales);
            tlCalificaciones.getTabAt(3).setCustomView(null);
            tlCalificaciones.getTabAt(3).setCustomView(vTabCalificacionesTrimestral);
            tlCalificaciones.getTabAt(4).setCustomView(null);
            tlCalificaciones.getTabAt(4).setCustomView(vTabCalificacionesSemestrales);
            tlCalificaciones.getTabAt(5).setCustomView(null);
            tlCalificaciones.getTabAt(5).setCustomView(vTabCalificacionesAnual);

            // ASIGNACION DE LOS TAB
            LinearLayout tabsContainer = (LinearLayout) tlCalificaciones.getChildAt(0);
            LinearLayout childLayout0 = (LinearLayout) tabsContainer.getChildAt(0);
            LinearLayout childLayout1 = (LinearLayout) tabsContainer.getChildAt(1);
            LinearLayout childLayout2 = (LinearLayout) tabsContainer.getChildAt(2);
            LinearLayout childLayout3 = (LinearLayout) tabsContainer.getChildAt(3);
            LinearLayout childLayout4 = (LinearLayout) tabsContainer.getChildAt(4);
            LinearLayout childLayout5 = (LinearLayout) tabsContainer.getChildAt(5);
            final LinearLayout tabView0 = (LinearLayout) childLayout0.getChildAt(0).getParent();
            final LinearLayout tabView1 = (LinearLayout) childLayout1.getChildAt(0).getParent();
            final LinearLayout tabView2 = (LinearLayout) childLayout2.getChildAt(0).getParent();
            final LinearLayout tabView3 = (LinearLayout) childLayout3.getChildAt(0).getParent();
            final LinearLayout tabView4 = (LinearLayout) childLayout4.getChildAt(0).getParent();
            final LinearLayout tabView5 = (LinearLayout) childLayout5.getChildAt(0).getParent();

            final ImageView ivTabCalificacionesDiariasActivo = (ImageView) vTabCalificacionesDiarias.findViewById(R.id.ivTabEvaluacionesDiariasActivo);
            final ImageView ivTabCalificacionesDiariasInactivo = (ImageView) vTabCalificacionesDiarias.findViewById(R.id.ivTabEvaluacionesDiariasInactivo);
            final ImageView ivTabCalificacionesSemanalesActivo = (ImageView) vTabCalificacionesSemanales.findViewById(R.id.ivTabEvaluacionesSemanalesActivo);
            final ImageView ivTabCalificacionesSemanalesInactivo = (ImageView) vTabCalificacionesSemanales.findViewById(R.id.ivTabEvaluacionesSemanalesInactivo);
            final ImageView ivTabCalificacionesMensualesActivo = (ImageView) vTabCalificacionesMensuales.findViewById(R.id.ivTabEvaluacionesMensualesActivo);
            final ImageView ivTabCalificacionesMensualesInactivo = (ImageView) vTabCalificacionesMensuales.findViewById(R.id.ivTabEvaluacionesMensualesInactivo);
            final ImageView ivTabCalificacionesTrimestralesActivo = (ImageView) vTabCalificacionesTrimestral.findViewById(R.id.ivTabEvaluacionesTrimestralActivo);
            final ImageView ivTabCalificacionesTrimestralesInactivo = (ImageView) vTabCalificacionesTrimestral.findViewById(R.id.ivTabEvaluacionesTrimestralInactivo);

            final ImageView ivTabCalificacionesSemestralesActivo = (ImageView) vTabCalificacionesSemestrales.findViewById(R.id.ivTabCalificacionesSemestralesActivo);
            final ImageView ivTabCalificacionesSemestralesInactivo = (ImageView) vTabCalificacionesSemestrales.findViewById(R.id.ivTabCalificacionesSemestralesInactivo);
            final ImageView ivTabCalificacionesAnualActivo = (ImageView) vTabCalificacionesAnual.findViewById(R.id.ivTabEvaluacionesAnualActivo);
            final ImageView ivTabCalificacionesAnualInactivo = (ImageView) vTabCalificacionesAnual.findViewById(R.id.ivTabEvaluacionesAnualInactivo);

            ivTabCalificacionesDiariasActivo.setAlpha((float) 0.5);
            ivTabCalificacionesDiariasInactivo.setAlpha((float) 0.5);
            ivTabCalificacionesSemanalesInactivo.setColorFilter(Color.parseColor("#A2E62E"));

            // COLORES ICONOS
            ivTabCalificacionesDiariasActivo.setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.SRC_IN);
            ivTabCalificacionesSemanalesInactivo.setColorFilter(Color.parseColor("#A2E62E"), PorterDuff.Mode.SRC_IN);
            ivTabCalificacionesMensualesInactivo.setColorFilter(Color.parseColor("#A2E62E"), PorterDuff.Mode.SRC_IN);
            ivTabCalificacionesTrimestralesInactivo.setColorFilter(Color.parseColor("#A2E62E"), PorterDuff.Mode.SRC_IN);
            ivTabCalificacionesSemestralesInactivo.setColorFilter(Color.parseColor("#A2E62E"), PorterDuff.Mode.SRC_IN);
            ivTabCalificacionesAnualInactivo.setColorFilter(Color.parseColor("#A2E62E"), PorterDuff.Mode.SRC_IN);

            ivTabCalificacionesDiariasActivo.setVisibility(View.VISIBLE);
            ivTabCalificacionesDiariasInactivo.setVisibility(View.GONE);
            ivTabCalificacionesSemanalesActivo.setVisibility(View.GONE);
            ivTabCalificacionesSemanalesInactivo.setVisibility(View.VISIBLE);
            ivTabCalificacionesMensualesActivo.setVisibility(View.GONE);
            ivTabCalificacionesMensualesInactivo.setVisibility(View.VISIBLE);
            ivTabCalificacionesTrimestralesActivo.setVisibility(View.GONE);
            ivTabCalificacionesTrimestralesInactivo.setVisibility(View.VISIBLE);
            ivTabCalificacionesSemestralesActivo.setVisibility(View.GONE);
            ivTabCalificacionesSemestralesInactivo.setVisibility(View.VISIBLE);
            ivTabCalificacionesAnualActivo.setVisibility(View.GONE);
            ivTabCalificacionesAnualInactivo.setVisibility(View.VISIBLE);

            tabView0.setBackgroundResource(R.drawable.eduko_tab_calificaciones);
            tabView1.setBackgroundColor(Color.TRANSPARENT);
            tabView2.setBackgroundColor(Color.TRANSPARENT);
            tabView3.setBackgroundColor(Color.TRANSPARENT);
            tabView4.setBackgroundColor(Color.TRANSPARENT);
            tabView5.setBackgroundColor(Color.TRANSPARENT);


            llCalificaciones.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showAsignaturas(v);
                }
            });

            tlCalificaciones.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    switch (tab.getPosition()) {
                        // DIARIAS
                        case 0: {
                            ivTabCalificacionesDiariasActivo.setVisibility(View.VISIBLE);
                            ivTabCalificacionesDiariasInactivo.setVisibility(View.GONE);
                            ivTabCalificacionesSemanalesActivo.setVisibility(View.GONE);
                            ivTabCalificacionesSemanalesInactivo.setVisibility(View.VISIBLE);
                            ivTabCalificacionesMensualesActivo.setVisibility(View.GONE);
                            ivTabCalificacionesMensualesInactivo.setVisibility(View.VISIBLE);
                            ivTabCalificacionesTrimestralesActivo.setVisibility(View.GONE);
                            ivTabCalificacionesTrimestralesInactivo.setVisibility(View.VISIBLE);
                            ivTabCalificacionesSemestralesActivo.setVisibility(View.GONE);
                            ivTabCalificacionesSemestralesInactivo.setVisibility(View.VISIBLE);
                            ivTabCalificacionesAnualActivo.setVisibility(View.GONE);
                            ivTabCalificacionesAnualInactivo.setVisibility(View.VISIBLE);

                            ivTabCalificacionesDiariasActivo.setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.SRC_IN);
                            ivTabCalificacionesSemanalesInactivo.setColorFilter(Color.parseColor("#A2E62E"), PorterDuff.Mode.SRC_IN);
                            ivTabCalificacionesMensualesInactivo.setColorFilter(Color.parseColor("#A2E62E"), PorterDuff.Mode.SRC_IN);
                            ivTabCalificacionesTrimestralesInactivo.setColorFilter(Color.parseColor("#A2E62E"), PorterDuff.Mode.SRC_IN);
                            ivTabCalificacionesSemestralesInactivo.setColorFilter(Color.parseColor("#A2E62E"), PorterDuff.Mode.SRC_IN);
                            ivTabCalificacionesAnualInactivo.setColorFilter(Color.parseColor("#A2E62E"), PorterDuff.Mode.SRC_IN);

                            tabView0.setBackgroundResource(R.drawable.eduko_tab_calificaciones);
                            tabView1.setBackgroundColor(Color.TRANSPARENT);
                            tabView2.setBackgroundColor(Color.TRANSPARENT);
                            tabView3.setBackgroundColor(Color.TRANSPARENT);
                            tabView4.setBackgroundColor(Color.TRANSPARENT);
                            tabView5.setBackgroundColor(Color.TRANSPARENT);

                            actualizarCalificacionesDiarias();
                        }
                        break;

                        // SEMANALES
                        case 1: {
                            ivTabCalificacionesDiariasActivo.setVisibility(View.GONE);
                            ivTabCalificacionesDiariasInactivo.setVisibility(View.VISIBLE);
                            ivTabCalificacionesSemanalesActivo.setVisibility(View.VISIBLE);
                            ivTabCalificacionesSemanalesInactivo.setVisibility(View.GONE);
                            ivTabCalificacionesMensualesActivo.setVisibility(View.GONE);
                            ivTabCalificacionesMensualesInactivo.setVisibility(View.VISIBLE);
                            ivTabCalificacionesTrimestralesActivo.setVisibility(View.GONE);
                            ivTabCalificacionesTrimestralesInactivo.setVisibility(View.VISIBLE);
                            ivTabCalificacionesSemestralesActivo.setVisibility(View.GONE);
                            ivTabCalificacionesSemestralesInactivo.setVisibility(View.VISIBLE);
                            ivTabCalificacionesAnualActivo.setVisibility(View.GONE);
                            ivTabCalificacionesAnualInactivo.setVisibility(View.VISIBLE);

                            ivTabCalificacionesDiariasInactivo.setColorFilter(Color.parseColor("#A2E62E"), PorterDuff.Mode.SRC_IN);
                            ivTabCalificacionesSemanalesActivo.setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.SRC_IN);
                            ivTabCalificacionesMensualesInactivo.setColorFilter(Color.parseColor("#A2E62E"), PorterDuff.Mode.SRC_IN);
                            ivTabCalificacionesTrimestralesInactivo.setColorFilter(Color.parseColor("#A2E62E"), PorterDuff.Mode.SRC_IN);
                            ivTabCalificacionesSemestralesInactivo.setColorFilter(Color.parseColor("#A2E62E"), PorterDuff.Mode.SRC_IN);
                            ivTabCalificacionesAnualInactivo.setColorFilter(Color.parseColor("#A2E62E"), PorterDuff.Mode.SRC_IN);

                            tabView0.setBackgroundColor(Color.TRANSPARENT);
                            tabView1.setBackgroundResource(R.drawable.eduko_tab_calificaciones);
                            tabView2.setBackgroundColor(Color.TRANSPARENT);
                            tabView3.setBackgroundColor(Color.TRANSPARENT);
                            tabView4.setBackgroundColor(Color.TRANSPARENT);
                            tabView5.setBackgroundColor(Color.TRANSPARENT);

                            actualizarCalificacionesSemanales();
                        }
                        break;

                        // MENSUALES
                        case 2: {
                            ivTabCalificacionesDiariasActivo.setVisibility(View.GONE);
                            ivTabCalificacionesDiariasInactivo.setVisibility(View.VISIBLE);
                            ivTabCalificacionesSemanalesActivo.setVisibility(View.GONE);
                            ivTabCalificacionesSemanalesInactivo.setVisibility(View.VISIBLE);
                            ivTabCalificacionesMensualesActivo.setVisibility(View.VISIBLE);
                            ivTabCalificacionesMensualesInactivo.setVisibility(View.GONE);
                            ivTabCalificacionesTrimestralesActivo.setVisibility(View.GONE);
                            ivTabCalificacionesTrimestralesInactivo.setVisibility(View.VISIBLE);
                            ivTabCalificacionesSemestralesActivo.setVisibility(View.GONE);
                            ivTabCalificacionesSemestralesInactivo.setVisibility(View.VISIBLE);
                            ivTabCalificacionesAnualActivo.setVisibility(View.GONE);
                            ivTabCalificacionesAnualInactivo.setVisibility(View.VISIBLE);

                            ivTabCalificacionesDiariasInactivo.setColorFilter(Color.parseColor("#A2E62E"), PorterDuff.Mode.SRC_IN);
                            ivTabCalificacionesSemanalesInactivo.setColorFilter(Color.parseColor("#A2E62E"), PorterDuff.Mode.SRC_IN);
                            ivTabCalificacionesMensualesActivo.setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.SRC_IN);
                            ivTabCalificacionesTrimestralesInactivo.setColorFilter(Color.parseColor("#A2E62E"), PorterDuff.Mode.SRC_IN);
                            ivTabCalificacionesSemestralesInactivo.setColorFilter(Color.parseColor("#A2E62E"), PorterDuff.Mode.SRC_IN);
                            ivTabCalificacionesAnualInactivo.setColorFilter(Color.parseColor("#A2E62E"), PorterDuff.Mode.SRC_IN);

                            tabView0.setBackgroundColor(Color.TRANSPARENT);
                            tabView1.setBackgroundColor(Color.TRANSPARENT);
                            tabView2.setBackgroundResource(R.drawable.eduko_tab_calificaciones);
                            tabView3.setBackgroundColor(Color.TRANSPARENT);
                            tabView4.setBackgroundColor(Color.TRANSPARENT);
                            tabView5.setBackgroundColor(Color.TRANSPARENT);

                            actualizarCalificacionesMensuales();
                        }
                        break;

                        // TRIMESTRALES
                        case 3: {
                            ivTabCalificacionesDiariasActivo.setVisibility(View.GONE);
                            ivTabCalificacionesDiariasInactivo.setVisibility(View.VISIBLE);
                            ivTabCalificacionesSemanalesActivo.setVisibility(View.GONE);
                            ivTabCalificacionesSemanalesInactivo.setVisibility(View.VISIBLE);
                            ivTabCalificacionesMensualesActivo.setVisibility(View.GONE);
                            ivTabCalificacionesMensualesInactivo.setVisibility(View.VISIBLE);
                            ivTabCalificacionesTrimestralesActivo.setVisibility(View.VISIBLE);
                            ivTabCalificacionesTrimestralesInactivo.setVisibility(View.GONE);
                            ivTabCalificacionesSemestralesActivo.setVisibility(View.GONE);
                            ivTabCalificacionesSemestralesInactivo.setVisibility(View.VISIBLE);
                            ivTabCalificacionesAnualActivo.setVisibility(View.GONE);
                            ivTabCalificacionesAnualInactivo.setVisibility(View.VISIBLE);

                            ivTabCalificacionesDiariasInactivo.setColorFilter(Color.parseColor("#A2E62E"), PorterDuff.Mode.SRC_IN);
                            ivTabCalificacionesSemanalesInactivo.setColorFilter(Color.parseColor("#A2E62E"), PorterDuff.Mode.SRC_IN);
                            ivTabCalificacionesMensualesInactivo.setColorFilter(Color.parseColor("#A2E62E"), PorterDuff.Mode.SRC_IN);
                            ivTabCalificacionesTrimestralesActivo.setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.SRC_IN);
                            ivTabCalificacionesSemestralesInactivo.setColorFilter(Color.parseColor("#A2E62E"), PorterDuff.Mode.SRC_IN);
                            ivTabCalificacionesAnualInactivo.setColorFilter(Color.parseColor("#A2E62E"), PorterDuff.Mode.SRC_IN);

                            tabView0.setBackgroundColor(Color.TRANSPARENT);
                            tabView1.setBackgroundColor(Color.TRANSPARENT);
                            tabView2.setBackgroundColor(Color.TRANSPARENT);
                            tabView3.setBackgroundResource(R.drawable.eduko_tab_calificaciones);
                            tabView4.setBackgroundColor(Color.TRANSPARENT);
                            tabView5.setBackgroundColor(Color.TRANSPARENT);

                            actualizarCalificacionesTrimestrales();
                        }
                        break;

                        // SEMESTRALES
                        case 4: {
                            ivTabCalificacionesDiariasActivo.setVisibility(View.GONE);
                            ivTabCalificacionesDiariasInactivo.setVisibility(View.VISIBLE);
                            ivTabCalificacionesSemanalesActivo.setVisibility(View.GONE);
                            ivTabCalificacionesSemanalesInactivo.setVisibility(View.VISIBLE);
                            ivTabCalificacionesMensualesActivo.setVisibility(View.GONE);
                            ivTabCalificacionesMensualesInactivo.setVisibility(View.VISIBLE);
                            ivTabCalificacionesTrimestralesActivo.setVisibility(View.GONE);
                            ivTabCalificacionesTrimestralesInactivo.setVisibility(View.VISIBLE);
                            ivTabCalificacionesSemestralesActivo.setVisibility(View.VISIBLE);
                            ivTabCalificacionesSemestralesInactivo.setVisibility(View.GONE);
                            ivTabCalificacionesAnualActivo.setVisibility(View.GONE);
                            ivTabCalificacionesAnualInactivo.setVisibility(View.VISIBLE);

                            ivTabCalificacionesDiariasInactivo.setColorFilter(Color.parseColor("#A2E62E"), PorterDuff.Mode.SRC_IN);
                            ivTabCalificacionesSemanalesInactivo.setColorFilter(Color.parseColor("#A2E62E"), PorterDuff.Mode.SRC_IN);
                            ivTabCalificacionesMensualesInactivo.setColorFilter(Color.parseColor("#A2E62E"), PorterDuff.Mode.SRC_IN);
                            ivTabCalificacionesTrimestralesInactivo.setColorFilter(Color.parseColor("#A2E62E"), PorterDuff.Mode.SRC_IN);
                            ivTabCalificacionesSemestralesInactivo.setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.SRC_IN);
                            ivTabCalificacionesAnualInactivo.setColorFilter(Color.parseColor("#A2E62E"), PorterDuff.Mode.SRC_IN);

                            tabView0.setBackgroundColor(Color.TRANSPARENT);
                            tabView1.setBackgroundColor(Color.TRANSPARENT);
                            tabView2.setBackgroundColor(Color.TRANSPARENT);
                            tabView3.setBackgroundColor(Color.TRANSPARENT);
                            tabView4.setBackgroundResource(R.drawable.eduko_tab_calificaciones);
                            tabView5.setBackgroundColor(Color.TRANSPARENT);

                            actualizarCalificacionesSemestrales();
                        }
                        break;

                        // ANUALES
                        case 5: {
                            ivTabCalificacionesDiariasActivo.setVisibility(View.GONE);
                            ivTabCalificacionesDiariasInactivo.setVisibility(View.VISIBLE);
                            ivTabCalificacionesSemanalesActivo.setVisibility(View.GONE);
                            ivTabCalificacionesSemanalesInactivo.setVisibility(View.VISIBLE);
                            ivTabCalificacionesMensualesActivo.setVisibility(View.GONE);
                            ivTabCalificacionesMensualesInactivo.setVisibility(View.VISIBLE);
                            ivTabCalificacionesTrimestralesActivo.setVisibility(View.GONE);
                            ivTabCalificacionesTrimestralesInactivo.setVisibility(View.VISIBLE);
                            ivTabCalificacionesSemestralesActivo.setVisibility(View.GONE);
                            ivTabCalificacionesSemestralesInactivo.setVisibility(View.VISIBLE);
                            ivTabCalificacionesAnualActivo.setVisibility(View.GONE);
                            ivTabCalificacionesAnualInactivo.setVisibility(View.VISIBLE);

                            ivTabCalificacionesDiariasInactivo.setColorFilter(Color.parseColor("#A2E62E"), PorterDuff.Mode.SRC_IN);
                            ivTabCalificacionesSemanalesInactivo.setColorFilter(Color.parseColor("#A2E62E"), PorterDuff.Mode.SRC_IN);
                            ivTabCalificacionesMensualesInactivo.setColorFilter(Color.parseColor("#A2E62E"), PorterDuff.Mode.SRC_IN);
                            ivTabCalificacionesTrimestralesInactivo.setColorFilter(Color.parseColor("#A2E62E"), PorterDuff.Mode.SRC_IN);
                            ivTabCalificacionesSemestralesInactivo.setColorFilter(Color.parseColor("#A2E62E"), PorterDuff.Mode.SRC_IN);
                            ivTabCalificacionesAnualInactivo.setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.SRC_IN);

                            tabView0.setBackgroundColor(Color.TRANSPARENT);
                            tabView1.setBackgroundColor(Color.TRANSPARENT);
                            tabView2.setBackgroundColor(Color.TRANSPARENT);
                            tabView3.setBackgroundColor(Color.TRANSPARENT);
                            tabView4.setBackgroundColor(Color.TRANSPARENT);
                            tabView5.setBackgroundResource(R.drawable.eduko_tab_calificaciones);

                            actualizarCalificacionesAnuales();
                        }
                        break;
                    }

                    vpCalificaciones.setCurrentItem(tab.getPosition());
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {

                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {

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
                    tvAsignaturas.setText(item.getTitle());
                    if (item.getItemId() == 0)
                        idSubject = "-1";
                    else
                        idSubject = globalAPP.subjectsModels.get(item.getItemId() - 1).getId();

                    switch (vpCalificaciones.getCurrentItem()) {
                        case 0: actualizarCalificacionesDiarias(); break;
                        case 1: actualizarCalificacionesSemanales(); break;
                        case 2: actualizarCalificacionesMensuales(); break;
                        case 3: actualizarCalificacionesTrimestrales(); break;
                        case 4: actualizarCalificacionesSemestrales(); break;
                        case 5: actualizarCalificacionesAnuales(); break;
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


    public void actualizarCalificaciones() {
        switch (vpCalificaciones.getCurrentItem()) {
            case 0: actualizarCalificacionesDiarias(); break;
            case 1: actualizarCalificacionesSemanales(); break;
            case 2: actualizarCalificacionesMensuales(); break;
            case 3: actualizarCalificacionesTrimestrales(); break;
            case 4: actualizarCalificacionesSemestrales(); break;
            case 5: actualizarCalificacionesAnuales(); break;
        }
    }


    private void actualizarCalificacionesDiarias() {
        calificacionesAdapter.calificacionesDiarias.idSubject = idSubject;
        calificacionesAdapter.calificacionesDiarias.actualizarCalificacionesDiarias();
    }


    private void actualizarCalificacionesSemanales() {
        calificacionesAdapter.calificacionesSemanal.idSubject = idSubject;
        calificacionesAdapter.calificacionesSemanal.updateCalificaciones();
    }


    private void actualizarCalificacionesMensuales() {
        calificacionesAdapter.calificacionesMensuales.idSubject = idSubject;
        calificacionesAdapter.calificacionesMensuales.updateCalificaciones();
    }


    private void actualizarCalificacionesTrimestrales() {
        calificacionesAdapter.calificacionesTrimestrales.idSubject = idSubject;
        calificacionesAdapter.calificacionesTrimestrales.updateCalificaciones();
    }


    private void actualizarCalificacionesSemestrales() {
        calificacionesAdapter.calificacionesSemestrales.idSubject = idSubject;
        calificacionesAdapter.calificacionesSemestrales.updateCalificaciones();
    }


    private void actualizarCalificacionesAnuales() {
        calificacionesAdapter.calificacionesAnual.idSubject = idSubject;
        calificacionesAdapter.calificacionesAnual.updateCalificaciones();
    }


    public void obtenerCalificaciones() {
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
                                actualizarCalificaciones();
                            } else {
                                globalAPP.assistancesModels.clear();
                                actualizarCalificaciones();
                            }
                        }
                        break;

                        case 401: {
                            globalAPP.assistancesModels.clear();
                            actualizarCalificaciones();
                            Toasty.error(getContext(), getString(R.string.error_login_app), Toast.LENGTH_SHORT).show();
                        }
                        break;

                        default: {
                            Log.i("EDUKO-ALUMNO-CALIF", "WS Listar calificaciones -> default");
                            Toasty.error(getContext(), getString(R.string.error_calificaciones), Toast.LENGTH_SHORT).show();
                            globalAPP.assistancesModels.clear();
                            actualizarCalificaciones();
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
                    actualizarCalificaciones();
                }
            });
        } catch (Exception e) {
            Toasty.error(getContext(), getString(R.string.error_app), Toast.LENGTH_SHORT).show();
            Log.e(Constants.TAG, e.getMessage());
        }
    }

}
