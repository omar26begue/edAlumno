package com.idoogroup.edalumno.Fragments;


import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.idoogroup.edalumno.Helpers.Constants;
import com.idoogroup.edalumno.IdooGroupApplication;
import com.idoogroup.edalumno.Models.StudentsEvaluationModel;
import com.idoogroup.edalumno.R;
import com.idoogroup.edalumno.ViewPager.ViewPagerEvaluacionesAdapter;
import com.idoogroup.edalumno.WebServicesIdooGroup;

import java.util.List;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class EvaluacionesFragment extends Fragment {

    public static final String TITLE = "Evaluaciones";
    private TextView tvEvaluaciones;
    private TabLayout tlEvaluaciones;
    public ViewPager vpEvaluaciones;
    private ViewPagerEvaluacionesAdapter evaluacionesAdapter;

    private IdooGroupApplication globalAPP;
    private WebServicesIdooGroup servicesEDUKO;
    private Dialog dialogLoading = null;


    public EvaluacionesFragment() {
        // Required empty public constructor
    }


    public static EvaluacionesFragment newInstance() {
        return new EvaluacionesFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_evaluaciones, container, false);

        try {
            // VARIABLE GLOBAL
            globalAPP = (IdooGroupApplication) getActivity().getApplication();
            servicesEDUKO = globalAPP.getService();

            // ASIGNADO LAS VARIABLES
            tvEvaluaciones = (TextView) v.findViewById(R.id.tvEvaluaciones);
            tlEvaluaciones = (TabLayout) v.findViewById(R.id.tlEvaluaciones);
            vpEvaluaciones = (ViewPager) v.findViewById(R.id.vpEvaluaciones);

            // ASIGNACION DE FUENTES
            tvEvaluaciones.setTypeface(globalAPP.getFuenteAvenidBlack(getActivity().getAssets()));

            // TABS
            evaluacionesAdapter = new ViewPagerEvaluacionesAdapter(getFragmentManager());
            vpEvaluaciones.setAdapter(evaluacionesAdapter);
            tlEvaluaciones.setupWithViewPager(vpEvaluaciones);
            vpEvaluaciones.setOffscreenPageLimit(5);
            vpEvaluaciones.setCurrentItem(0);

            // ICONOS DEL TABLAYOUT
            View vTabEvaluacionesDiarias = inflater.inflate(R.layout.tab_menu_evaluaciones_diarias, null);
            View vTabEvaluacionesSemanales = inflater.inflate(R.layout.tab_menu_evaluaciones_semanales, null);
            View vTabEvaluacionesMensuales = inflater.inflate(R.layout.tab_menu_evaluaciones_mensuales, null);
            View vTabEvaluacionesTrimestral = inflater.inflate(R.layout.tab_menu_evaluaciones_trimestral, null);
            View vTabEvaluacionesSemestral = inflater.inflate(R.layout.tab_menu_evaluaciones_semestral, null);
            View vTabEvaluacionesAnual = inflater.inflate(R.layout.tab_menu_evaluaciones_anual, null);

            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            LayoutInflater inflaterLoading = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflaterLoading.inflate(R.layout.dialog_loading, null);

            builder.setView(view);
            dialogLoading = builder.create();
            dialogLoading.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialogLoading.setCancelable(false);


            // ASIGNACION DE LOS TAB
            LinearLayout tabsContainer = (LinearLayout) tlEvaluaciones.getChildAt(0);
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

            final ImageView ivTabEvaluacionesDiariasActivo = (ImageView) vTabEvaluacionesDiarias.findViewById(R.id.ivTabEvaluacionesDiariasActivo);
            final ImageView ivTabEvaluacionesDiariasInactivo = (ImageView) vTabEvaluacionesDiarias.findViewById(R.id.ivTabEvaluacionesDiariasInactivo);
            final ImageView ivTabEvaluacionesSemanalesActivo = (ImageView) vTabEvaluacionesSemanales.findViewById(R.id.ivTabEvaluacionesSemanalesActivo);
            final ImageView ivTabEvaluacionesSemanalesInactivo = (ImageView) vTabEvaluacionesSemanales.findViewById(R.id.ivTabEvaluacionesSemanalesInactivo);
            final ImageView ivTabEvaluacionesMensualesActivo = (ImageView) vTabEvaluacionesMensuales.findViewById(R.id.ivTabEvaluacionesMensualesActivo);
            final ImageView ivTabEvaluacionesMensualesInactivo = (ImageView) vTabEvaluacionesMensuales.findViewById(R.id.ivTabEvaluacionesMensualesInactivo);
            final ImageView ivTabEvaluacionesTrimestralActivo = (ImageView) vTabEvaluacionesTrimestral.findViewById(R.id.ivTabEvaluacionesTrimestralActivo);
            final ImageView ivTabEvaluacionesTrimestralInactivo = (ImageView) vTabEvaluacionesTrimestral.findViewById(R.id.ivTabEvaluacionesTrimestralInactivo);
            final ImageView ivTabEvaluacionesSemestralActivo = (ImageView) vTabEvaluacionesSemestral.findViewById(R.id.ivTabEvaluacionesSemestralActivo);
            final ImageView ivTabEvaluacionesSemestralInactivo = (ImageView) vTabEvaluacionesSemestral.findViewById(R.id.ivTabEvaluacionesSemestralInactivo);
            final ImageView ivTabEvaluacionesAnualActivo = (ImageView) vTabEvaluacionesAnual.findViewById(R.id.ivTabEvaluacionesAnualActivo);
            final ImageView ivTabEvaluacionesAnualInactivo = (ImageView) vTabEvaluacionesAnual.findViewById(R.id.ivTabEvaluacionesAnualInactivo);

            ivTabEvaluacionesDiariasActivo.setVisibility(View.VISIBLE);
            ivTabEvaluacionesDiariasInactivo.setVisibility(View.GONE);
            ivTabEvaluacionesSemanalesActivo.setVisibility(View.GONE);
            ivTabEvaluacionesSemanalesInactivo.setVisibility(View.VISIBLE);
            ivTabEvaluacionesMensualesActivo.setVisibility(View.GONE);
            ivTabEvaluacionesMensualesInactivo.setVisibility(View.VISIBLE);
            ivTabEvaluacionesTrimestralActivo.setVisibility(View.GONE);
            ivTabEvaluacionesTrimestralInactivo.setVisibility(View.VISIBLE);
            ivTabEvaluacionesSemestralActivo.setVisibility(View.GONE);
            ivTabEvaluacionesSemestralInactivo.setVisibility(View.VISIBLE);
            ivTabEvaluacionesAnualActivo.setVisibility(View.GONE);
            ivTabEvaluacionesAnualInactivo.setVisibility(View.VISIBLE);

            ivTabEvaluacionesDiariasActivo.setAlpha((float) 0.5);
            ivTabEvaluacionesDiariasInactivo.setAlpha((float) 0.5);
            ivTabEvaluacionesDiariasInactivo.setColorFilter(Color.parseColor("#ff3300"));
            ivTabEvaluacionesSemanalesInactivo.setColorFilter(Color.parseColor("#ff3300"), PorterDuff.Mode.SRC_IN);
            ivTabEvaluacionesMensualesInactivo.setColorFilter(Color.parseColor("#ff3300"), PorterDuff.Mode.SRC_IN);
            ivTabEvaluacionesTrimestralInactivo.setColorFilter(Color.parseColor("#ff3300"), PorterDuff.Mode.SRC_IN);
            ivTabEvaluacionesSemestralInactivo.setColorFilter(Color.parseColor("#ff3300"), PorterDuff.Mode.SRC_IN);
            ivTabEvaluacionesAnualInactivo.setColorFilter(Color.parseColor("#ff3300"), PorterDuff.Mode.SRC_IN);

            tlEvaluaciones.getTabAt(0).setCustomView(null);
            tlEvaluaciones.getTabAt(0).setCustomView(vTabEvaluacionesDiarias);
            tlEvaluaciones.getTabAt(1).setCustomView(null);
            tlEvaluaciones.getTabAt(1).setCustomView(vTabEvaluacionesSemanales);
            tlEvaluaciones.getTabAt(2).setCustomView(null);
            tlEvaluaciones.getTabAt(2).setCustomView(vTabEvaluacionesMensuales);
            tlEvaluaciones.getTabAt(3).setCustomView(null);
            tlEvaluaciones.getTabAt(3).setCustomView(vTabEvaluacionesTrimestral);
            tlEvaluaciones.getTabAt(4).setCustomView(null);
            tlEvaluaciones.getTabAt(4).setCustomView(vTabEvaluacionesSemestral);
            tlEvaluaciones.getTabAt(5).setCustomView(null);
            tlEvaluaciones.getTabAt(5).setCustomView(vTabEvaluacionesAnual);

            tabView0.setBackgroundResource(R.drawable.eduko_tab_evaluaciones);
            tabView1.setBackgroundColor(Color.TRANSPARENT);
            tabView2.setBackgroundColor(Color.TRANSPARENT);
            tabView3.setBackgroundColor(Color.TRANSPARENT);
            tabView4.setBackgroundColor(Color.TRANSPARENT);
            tabView5.setBackgroundColor(Color.TRANSPARENT);


            tlEvaluaciones.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    switch (tab.getPosition()) {
                        // EVALUACIONES DIARIAS
                        case 0: {
                            ivTabEvaluacionesDiariasActivo.setVisibility(View.VISIBLE);
                            ivTabEvaluacionesDiariasInactivo.setVisibility(View.GONE);
                            ivTabEvaluacionesSemanalesActivo.setVisibility(View.GONE);
                            ivTabEvaluacionesSemanalesInactivo.setVisibility(View.VISIBLE);
                            ivTabEvaluacionesMensualesActivo.setVisibility(View.GONE);
                            ivTabEvaluacionesMensualesInactivo.setVisibility(View.VISIBLE);
                            ivTabEvaluacionesTrimestralActivo.setVisibility(View.GONE);
                            ivTabEvaluacionesTrimestralInactivo.setVisibility(View.VISIBLE);
                            ivTabEvaluacionesSemestralActivo.setVisibility(View.GONE);
                            ivTabEvaluacionesSemestralInactivo.setVisibility(View.VISIBLE);
                            ivTabEvaluacionesAnualActivo.setVisibility(View.GONE);
                            ivTabEvaluacionesAnualInactivo.setVisibility(View.VISIBLE);

                            ivTabEvaluacionesDiariasActivo.setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.SRC_IN);
                            ivTabEvaluacionesDiariasInactivo.setColorFilter(Color.parseColor("#ff3300"), PorterDuff.Mode.SRC_IN);
                            ivTabEvaluacionesSemanalesActivo.setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.SRC_IN);
                            ivTabEvaluacionesSemanalesInactivo.setColorFilter(Color.parseColor("#ff3300"), PorterDuff.Mode.SRC_IN);
                            ivTabEvaluacionesMensualesActivo.setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.SRC_IN);
                            ivTabEvaluacionesMensualesInactivo.setColorFilter(Color.parseColor("#ff3300"), PorterDuff.Mode.SRC_IN);
                            ivTabEvaluacionesTrimestralActivo.setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.SRC_IN);
                            ivTabEvaluacionesTrimestralInactivo.setColorFilter(Color.parseColor("#ff3300"), PorterDuff.Mode.SRC_IN);
                            ivTabEvaluacionesSemestralActivo.setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.SRC_IN);
                            ivTabEvaluacionesSemestralInactivo.setColorFilter(Color.parseColor("#ff3300"), PorterDuff.Mode.SRC_IN);
                            ivTabEvaluacionesAnualActivo.setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.SRC_IN);
                            ivTabEvaluacionesAnualInactivo.setColorFilter(Color.parseColor("#ff3300"), PorterDuff.Mode.SRC_IN);

                            tabView0.setBackgroundResource(R.drawable.eduko_tab_evaluaciones);
                            tabView1.setBackgroundColor(Color.TRANSPARENT);
                            tabView2.setBackgroundColor(Color.TRANSPARENT);
                            tabView3.setBackgroundColor(Color.TRANSPARENT);
                            tabView4.setBackgroundColor(Color.TRANSPARENT);
                            tabView5.setBackgroundColor(Color.TRANSPARENT);

                            actualizarEvaluacionesDiarias();
                        }
                        break;

                        // EVALUACIONES SEMANALES
                        case 1: {
                            ivTabEvaluacionesDiariasActivo.setVisibility(View.GONE);
                            ivTabEvaluacionesDiariasInactivo.setVisibility(View.VISIBLE);
                            ivTabEvaluacionesSemanalesActivo.setVisibility(View.VISIBLE);
                            ivTabEvaluacionesSemanalesInactivo.setVisibility(View.GONE);
                            ivTabEvaluacionesMensualesActivo.setVisibility(View.GONE);
                            ivTabEvaluacionesMensualesInactivo.setVisibility(View.VISIBLE);
                            ivTabEvaluacionesTrimestralActivo.setVisibility(View.GONE);
                            ivTabEvaluacionesTrimestralInactivo.setVisibility(View.VISIBLE);
                            ivTabEvaluacionesSemestralActivo.setVisibility(View.GONE);
                            ivTabEvaluacionesSemestralInactivo.setVisibility(View.VISIBLE);
                            ivTabEvaluacionesAnualActivo.setVisibility(View.GONE);
                            ivTabEvaluacionesAnualInactivo.setVisibility(View.VISIBLE);

                            ivTabEvaluacionesDiariasActivo.setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.SRC_IN);
                            ivTabEvaluacionesDiariasInactivo.setColorFilter(Color.parseColor("#ff3300"), PorterDuff.Mode.SRC_IN);
                            ivTabEvaluacionesSemanalesActivo.setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.SRC_IN);
                            ivTabEvaluacionesSemanalesInactivo.setColorFilter(Color.parseColor("#ff3300"), PorterDuff.Mode.SRC_IN);
                            ivTabEvaluacionesMensualesActivo.setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.SRC_IN);
                            ivTabEvaluacionesMensualesInactivo.setColorFilter(Color.parseColor("#ff3300"), PorterDuff.Mode.SRC_IN);
                            ivTabEvaluacionesTrimestralActivo.setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.SRC_IN);
                            ivTabEvaluacionesTrimestralInactivo.setColorFilter(Color.parseColor("#ff3300"), PorterDuff.Mode.SRC_IN);
                            ivTabEvaluacionesSemestralActivo.setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.SRC_IN);
                            ivTabEvaluacionesSemestralInactivo.setColorFilter(Color.parseColor("#ff3300"), PorterDuff.Mode.SRC_IN);
                            ivTabEvaluacionesAnualActivo.setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.SRC_IN);
                            ivTabEvaluacionesAnualInactivo.setColorFilter(Color.parseColor("#ff3300"), PorterDuff.Mode.SRC_IN);

                            tabView0.setBackgroundColor(Color.TRANSPARENT);
                            tabView1.setBackgroundResource(R.drawable.eduko_tab_evaluaciones);
                            tabView2.setBackgroundColor(Color.TRANSPARENT);
                            tabView3.setBackgroundColor(Color.TRANSPARENT);
                            tabView4.setBackgroundColor(Color.TRANSPARENT);
                            tabView5.setBackgroundColor(Color.TRANSPARENT);

                            actualizarEvaluacionesSemanal();
                        }
                        break;

                        // MENSUALES
                        case 2: {
                            ivTabEvaluacionesDiariasActivo.setVisibility(View.GONE);
                            ivTabEvaluacionesDiariasInactivo.setVisibility(View.VISIBLE);
                            ivTabEvaluacionesSemanalesActivo.setVisibility(View.GONE);
                            ivTabEvaluacionesSemanalesInactivo.setVisibility(View.VISIBLE);
                            ivTabEvaluacionesMensualesActivo.setVisibility(View.VISIBLE);
                            ivTabEvaluacionesMensualesInactivo.setVisibility(View.GONE);
                            ivTabEvaluacionesTrimestralActivo.setVisibility(View.GONE);
                            ivTabEvaluacionesTrimestralInactivo.setVisibility(View.VISIBLE);
                            ivTabEvaluacionesSemestralActivo.setVisibility(View.GONE);
                            ivTabEvaluacionesSemestralInactivo.setVisibility(View.VISIBLE);
                            ivTabEvaluacionesAnualActivo.setVisibility(View.GONE);
                            ivTabEvaluacionesAnualInactivo.setVisibility(View.VISIBLE);

                            ivTabEvaluacionesDiariasActivo.setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.SRC_IN);
                            ivTabEvaluacionesDiariasInactivo.setColorFilter(Color.parseColor("#ff3300"), PorterDuff.Mode.SRC_IN);
                            ivTabEvaluacionesSemanalesActivo.setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.SRC_IN);
                            ivTabEvaluacionesSemanalesInactivo.setColorFilter(Color.parseColor("#ff3300"), PorterDuff.Mode.SRC_IN);
                            ivTabEvaluacionesMensualesActivo.setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.SRC_IN);
                            ivTabEvaluacionesMensualesInactivo.setColorFilter(Color.parseColor("#ff3300"), PorterDuff.Mode.SRC_IN);
                            ivTabEvaluacionesTrimestralActivo.setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.SRC_IN);
                            ivTabEvaluacionesTrimestralInactivo.setColorFilter(Color.parseColor("#ff3300"), PorterDuff.Mode.SRC_IN);
                            ivTabEvaluacionesSemestralActivo.setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.SRC_IN);
                            ivTabEvaluacionesSemestralInactivo.setColorFilter(Color.parseColor("#ff3300"), PorterDuff.Mode.SRC_IN);
                            ivTabEvaluacionesAnualActivo.setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.SRC_IN);
                            ivTabEvaluacionesAnualInactivo.setColorFilter(Color.parseColor("#ff3300"), PorterDuff.Mode.SRC_IN);

                            tabView0.setBackgroundColor(Color.TRANSPARENT);
                            tabView1.setBackgroundColor(Color.TRANSPARENT);
                            tabView2.setBackgroundResource(R.drawable.eduko_tab_evaluaciones);
                            tabView3.setBackgroundColor(Color.TRANSPARENT);
                            tabView4.setBackgroundColor(Color.TRANSPARENT);
                            tabView5.setBackgroundColor(Color.TRANSPARENT);

                            actualizarEvaluacionesMensuales();
                        }
                        break;

                        // TRIMESTRALES
                        case 3: {
                            ivTabEvaluacionesDiariasActivo.setVisibility(View.GONE);
                            ivTabEvaluacionesDiariasInactivo.setVisibility(View.VISIBLE);
                            ivTabEvaluacionesSemanalesActivo.setVisibility(View.GONE);
                            ivTabEvaluacionesSemanalesInactivo.setVisibility(View.VISIBLE);
                            ivTabEvaluacionesMensualesActivo.setVisibility(View.GONE);
                            ivTabEvaluacionesMensualesInactivo.setVisibility(View.VISIBLE);
                            ivTabEvaluacionesTrimestralActivo.setVisibility(View.VISIBLE);
                            ivTabEvaluacionesTrimestralInactivo.setVisibility(View.GONE);
                            ivTabEvaluacionesSemestralActivo.setVisibility(View.GONE);
                            ivTabEvaluacionesSemestralInactivo.setVisibility(View.VISIBLE);
                            ivTabEvaluacionesAnualActivo.setVisibility(View.GONE);
                            ivTabEvaluacionesAnualInactivo.setVisibility(View.VISIBLE);

                            ivTabEvaluacionesDiariasActivo.setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.SRC_IN);
                            ivTabEvaluacionesDiariasInactivo.setColorFilter(Color.parseColor("#ff3300"), PorterDuff.Mode.SRC_IN);
                            ivTabEvaluacionesSemanalesActivo.setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.SRC_IN);
                            ivTabEvaluacionesSemanalesInactivo.setColorFilter(Color.parseColor("#ff3300"), PorterDuff.Mode.SRC_IN);
                            ivTabEvaluacionesMensualesActivo.setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.SRC_IN);
                            ivTabEvaluacionesMensualesInactivo.setColorFilter(Color.parseColor("#ff3300"), PorterDuff.Mode.SRC_IN);
                            ivTabEvaluacionesTrimestralActivo.setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.SRC_IN);
                            ivTabEvaluacionesTrimestralInactivo.setColorFilter(Color.parseColor("#ff3300"), PorterDuff.Mode.SRC_IN);
                            ivTabEvaluacionesSemestralActivo.setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.SRC_IN);
                            ivTabEvaluacionesSemestralInactivo.setColorFilter(Color.parseColor("#ff3300"), PorterDuff.Mode.SRC_IN);
                            ivTabEvaluacionesAnualActivo.setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.SRC_IN);
                            ivTabEvaluacionesAnualInactivo.setColorFilter(Color.parseColor("#ff3300"), PorterDuff.Mode.SRC_IN);

                            tabView0.setBackgroundColor(Color.TRANSPARENT);
                            tabView1.setBackgroundColor(Color.TRANSPARENT);
                            tabView2.setBackgroundColor(Color.TRANSPARENT);
                            tabView3.setBackgroundResource(R.drawable.eduko_tab_evaluaciones);
                            tabView4.setBackgroundColor(Color.TRANSPARENT);
                            tabView5.setBackgroundColor(Color.TRANSPARENT);

                            actualizarEvaluacionesTrimestrales();
                        }
                        break;

                        // SEMESTRALES
                        case 4: {
                            ivTabEvaluacionesDiariasActivo.setVisibility(View.GONE);
                            ivTabEvaluacionesDiariasInactivo.setVisibility(View.VISIBLE);
                            ivTabEvaluacionesSemanalesActivo.setVisibility(View.GONE);
                            ivTabEvaluacionesSemanalesInactivo.setVisibility(View.VISIBLE);
                            ivTabEvaluacionesMensualesActivo.setVisibility(View.GONE);
                            ivTabEvaluacionesMensualesInactivo.setVisibility(View.VISIBLE);
                            ivTabEvaluacionesTrimestralActivo.setVisibility(View.GONE);
                            ivTabEvaluacionesTrimestralInactivo.setVisibility(View.VISIBLE);
                            ivTabEvaluacionesSemestralActivo.setVisibility(View.VISIBLE);
                            ivTabEvaluacionesSemestralInactivo.setVisibility(View.GONE);
                            ivTabEvaluacionesAnualActivo.setVisibility(View.GONE);
                            ivTabEvaluacionesAnualInactivo.setVisibility(View.VISIBLE);

                            ivTabEvaluacionesDiariasActivo.setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.SRC_IN);
                            ivTabEvaluacionesDiariasInactivo.setColorFilter(Color.parseColor("#ff3300"), PorterDuff.Mode.SRC_IN);
                            ivTabEvaluacionesSemanalesActivo.setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.SRC_IN);
                            ivTabEvaluacionesSemanalesInactivo.setColorFilter(Color.parseColor("#ff3300"), PorterDuff.Mode.SRC_IN);
                            ivTabEvaluacionesMensualesActivo.setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.SRC_IN);
                            ivTabEvaluacionesMensualesInactivo.setColorFilter(Color.parseColor("#ff3300"), PorterDuff.Mode.SRC_IN);
                            ivTabEvaluacionesTrimestralActivo.setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.SRC_IN);
                            ivTabEvaluacionesTrimestralInactivo.setColorFilter(Color.parseColor("#ff3300"), PorterDuff.Mode.SRC_IN);
                            ivTabEvaluacionesSemestralActivo.setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.SRC_IN);
                            ivTabEvaluacionesSemestralInactivo.setColorFilter(Color.parseColor("#ff3300"), PorterDuff.Mode.SRC_IN);
                            ivTabEvaluacionesAnualActivo.setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.SRC_IN);
                            ivTabEvaluacionesAnualInactivo.setColorFilter(Color.parseColor("#ff3300"), PorterDuff.Mode.SRC_IN);

                            tabView0.setBackgroundColor(Color.TRANSPARENT);
                            tabView1.setBackgroundColor(Color.TRANSPARENT);
                            tabView2.setBackgroundColor(Color.TRANSPARENT);
                            tabView3.setBackgroundColor(Color.TRANSPARENT);
                            tabView4.setBackgroundResource(R.drawable.eduko_tab_evaluaciones);
                            tabView5.setBackgroundColor(Color.TRANSPARENT);

                            actualizarEvaluacionesSemestrales();
                        }
                        break;

                        // ANUALES
                        case 5: {
                            ivTabEvaluacionesDiariasActivo.setVisibility(View.GONE);
                            ivTabEvaluacionesDiariasInactivo.setVisibility(View.VISIBLE);
                            ivTabEvaluacionesSemanalesActivo.setVisibility(View.GONE);
                            ivTabEvaluacionesSemanalesInactivo.setVisibility(View.VISIBLE);
                            ivTabEvaluacionesMensualesActivo.setVisibility(View.GONE);
                            ivTabEvaluacionesMensualesInactivo.setVisibility(View.VISIBLE);
                            ivTabEvaluacionesTrimestralActivo.setVisibility(View.GONE);
                            ivTabEvaluacionesTrimestralInactivo.setVisibility(View.VISIBLE);
                            ivTabEvaluacionesSemestralActivo.setVisibility(View.GONE);
                            ivTabEvaluacionesSemestralInactivo.setVisibility(View.VISIBLE);
                            ivTabEvaluacionesAnualActivo.setVisibility(View.VISIBLE);
                            ivTabEvaluacionesAnualInactivo.setVisibility(View.GONE);

                            ivTabEvaluacionesDiariasActivo.setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.SRC_IN);
                            ivTabEvaluacionesDiariasInactivo.setColorFilter(Color.parseColor("#ff3300"), PorterDuff.Mode.SRC_IN);
                            ivTabEvaluacionesSemanalesActivo.setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.SRC_IN);
                            ivTabEvaluacionesSemanalesInactivo.setColorFilter(Color.parseColor("#ff3300"), PorterDuff.Mode.SRC_IN);
                            ivTabEvaluacionesMensualesActivo.setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.SRC_IN);
                            ivTabEvaluacionesMensualesInactivo.setColorFilter(Color.parseColor("#ff3300"), PorterDuff.Mode.SRC_IN);
                            ivTabEvaluacionesTrimestralActivo.setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.SRC_IN);
                            ivTabEvaluacionesTrimestralInactivo.setColorFilter(Color.parseColor("#ff3300"), PorterDuff.Mode.SRC_IN);
                            ivTabEvaluacionesSemestralActivo.setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.SRC_IN);
                            ivTabEvaluacionesSemestralInactivo.setColorFilter(Color.parseColor("#ff3300"), PorterDuff.Mode.SRC_IN);
                            ivTabEvaluacionesAnualActivo.setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.SRC_IN);
                            ivTabEvaluacionesAnualInactivo.setColorFilter(Color.parseColor("#ff3300"), PorterDuff.Mode.SRC_IN);

                            tabView0.setBackgroundColor(Color.TRANSPARENT);
                            tabView1.setBackgroundColor(Color.TRANSPARENT);
                            tabView2.setBackgroundColor(Color.TRANSPARENT);
                            tabView3.setBackgroundColor(Color.TRANSPARENT);
                            tabView4.setBackgroundColor(Color.TRANSPARENT);
                            tabView5.setBackgroundResource(R.drawable.eduko_tab_evaluaciones);

                            actualizarEvaluacionesAnual();
                        }
                        break;
                    }

                    vpEvaluaciones.setCurrentItem(tab.getPosition());
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


    private void actualizarEvaluaciones() {
        try {
            switch (vpEvaluaciones.getCurrentItem()) {
                case 0: actualizarEvaluacionesDiarias(); break;
                case 1: actualizarEvaluacionesSemanal(); break;
                case 2: actualizarEvaluacionesMensuales(); break;
                case 3: actualizarEvaluacionesTrimestrales(); break;
                case 4: actualizarEvaluacionesSemestrales(); break;
                case 5: actualizarEvaluacionesAnual(); break;
            }
        } catch (Exception e) {
            Toasty.error(getContext(), getString(R.string.error_app), Toast.LENGTH_SHORT).show();
            Log.e(Constants.TAG, e.getMessage());
        }
    }


    private void actualizarEvaluacionesDiarias() {
        evaluacionesAdapter.evaluacionesDiarias.obtenerEvaluaciones();
    }


    private void actualizarEvaluacionesSemanal() {
        evaluacionesAdapter.evaluacionesSemanales.actualizarEvaluaciones();
    }


    private void actualizarEvaluacionesMensuales() {
        evaluacionesAdapter.evaluacionesMensuales.actualizarEvaluaciones();
    }


    private void actualizarEvaluacionesTrimestrales() {
        evaluacionesAdapter.evaluacionesTrimestrales.actualizarEvaluaciones();
    }


    private void actualizarEvaluacionesSemestrales() {
        evaluacionesAdapter.evaluacionesSemestrales.actualizarEvaluaciones();
    }


    private void actualizarEvaluacionesAnual() {
        evaluacionesAdapter.evaluacionesAnual.actualizarEvaluaciones();
    }


    public void obtenerEvaluaciones() {
        try {
            mostrarCargando();
            final String token = globalAPP.sessionEDUAPP.getAccessToken(getContext());
            final String idStudent = globalAPP.sessionEDUAPP.getID(getContext());
            String filtro = "{\"include\":[{\"subject\":\"teachers\"},\"frecuencia\",\"nomCalificacion\"],\"where\":{\"studentId\":\"" + idStudent + "\"},\"order\":\"fecha desc\"}";

            Call<List<StudentsEvaluationModel>> call = servicesEDUKO.getEvaluaciones(token, filtro);
            call.enqueue(new Callback<List<StudentsEvaluationModel>>() {
                @Override
                public void onResponse(Call<List<StudentsEvaluationModel>> call, Response<List<StudentsEvaluationModel>> response) {
                    ocultarCargando(true);

                    switch (response.code()) {
                        case 200: {
                            if (response.isSuccessful()) {
                                Log.i("EDUKO-ALUMNO-EVALUAC", "WS Listar evaluaciones -> " + response.body().size());
                                globalAPP.evaluacionesModels = response.body();
                                actualizarEvaluaciones();
                            } else {
                                globalAPP.evaluacionesModels.clear();
                                actualizarEvaluaciones();
                            }
                        }
                        break;

                        case 401: {
                            globalAPP.evaluacionesModels.clear();
                            actualizarEvaluaciones();
                            Toasty.error(getContext(), getString(R.string.error_login_app), Toast.LENGTH_SHORT).show();
                        }
                        break;

                        default: {
                            Log.i("EDUKO-ALUMNO-EVALUAC", "WS Listar evaluaciones -> default");
                            globalAPP.evaluacionesModels.clear();
                            actualizarEvaluaciones();
                            Toasty.error(getContext(), getString(R.string.error_ealuaciones), Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<List<StudentsEvaluationModel>> call, Throwable throwable) {
                    Log.i("EDUKO-ALUMNO-EVALUAC", "WS Listar evaluaciones -> failure");
                    ocultarCargando(true);
                    globalAPP.evaluacionesModels.clear();
                    actualizarEvaluaciones();
                    Toasty.error(getContext(), getString(R.string.error_ealuaciones), Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            Toasty.error(getContext(), getString(R.string.error_app), Toast.LENGTH_SHORT).show();
            Log.e(Constants.TAG, e.getMessage());
        }
    }

}
