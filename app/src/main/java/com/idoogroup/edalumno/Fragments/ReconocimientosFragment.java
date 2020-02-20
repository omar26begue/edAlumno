package com.idoogroup.edalumno.Fragments;


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
import com.idoogroup.edalumno.Models.StudentsRecognitionModel;
import com.idoogroup.edalumno.R;
import com.idoogroup.edalumno.ViewPageTransformer.DepthPageTransformer;
import com.idoogroup.edalumno.ViewPager.ViewPagerReconocimientosAdapter;
import com.idoogroup.edalumno.WebServicesIdooGroup;

import java.util.List;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ReconocimientosFragment extends Fragment {

    public static final String TITLE = "Reconocimientos";
    private TextView tvReconocimientos;
    private TabLayout tlReconocimientos;
    public ViewPager vpReconocimientos;
    public ViewPagerReconocimientosAdapter reconocimientosAdapter;

    private IdooGroupApplication globalAPP;
    private WebServicesIdooGroup servicesEDUKO;
    private Dialog dialogLoading = null;


    public ReconocimientosFragment() {
        // Required empty public constructor
    }


    public static ReconocimientosFragment newInstance() {
        return new ReconocimientosFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_reconocimientos, container, false);

        try {
            // VARIABLE GLOBAL
            globalAPP = (IdooGroupApplication) getActivity().getApplication();
            servicesEDUKO = globalAPP.getService();

            // ASIGNADO LAS VARIABLES
            tvReconocimientos = (TextView) v.findViewById(R.id.tvReconocimientos);
            tlReconocimientos = (TabLayout) v.findViewById(R.id.tlReconocimientos);
            vpReconocimientos = (ViewPager) v.findViewById(R.id.vpReconocimientos);

            // ASIGNACION DE FUENTES
            tvReconocimientos.setTypeface(globalAPP.getFuenteAvenidHeavy(getActivity().getAssets()));

            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            LayoutInflater inflaterLoading = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflaterLoading.inflate(R.layout.dialog_loading, null);

            builder.setView(view);
            dialogLoading = builder.create();
            dialogLoading.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialogLoading.setCancelable(false);

            // TABS
            reconocimientosAdapter = new ViewPagerReconocimientosAdapter(getFragmentManager());
            vpReconocimientos.setAdapter(reconocimientosAdapter);
            vpReconocimientos.setPageTransformer(true, new DepthPageTransformer());
            tlReconocimientos.setupWithViewPager(vpReconocimientos);
            vpReconocimientos.setOffscreenPageLimit(6);
            vpReconocimientos.setCurrentItem(0);

            // ICONOS DE LOS TABS
            final View vTabReconocimientosDiarios = inflater.inflate(R.layout.tab_menu_reconocomientos_diarios, null);
            final View vTabReconocimientosSemanal = inflater.inflate(R.layout.tab_menu_reconocomientos_semanal, null);
            final View vTabReconocimientosMensual = inflater.inflate(R.layout.tab_menu_reconocomientos_mensual, null);
            final View vTabReconocimientosTrimestrales = inflater.inflate(R.layout.tab_menu_reconocomientos_trimestrales, null);
            final View vTabReconocimientosSemestrales = inflater.inflate(R.layout.tab_menu_reconocomientos_semestrales, null);
            final View vTabReconocimientosAnual = inflater.inflate(R.layout.tab_menu_reconocomientos_anual, null);

            final ImageView ivTabReconocimientosDiariosActivo = (ImageView) vTabReconocimientosDiarios.findViewById(R.id.ivTabReconocimientosDiariosActivo);
            final ImageView ivTabReconocimientosDiariosInactivo = (ImageView) vTabReconocimientosDiarios.findViewById(R.id.ivTabReconocimientosDiariosInactivo);
            final ImageView ivTabReconocimientosSemanalActivo = (ImageView) vTabReconocimientosSemanal.findViewById(R.id.ivTabReconocimientosSemanalActivo);
            final ImageView ivTabReconocimientosSemanalInactivo = (ImageView) vTabReconocimientosSemanal.findViewById(R.id.ivTabReconocimientosSemanalInactivo);
            ivTabReconocimientosSemanalActivo.setVisibility(View.GONE);
            ivTabReconocimientosSemanalInactivo.setVisibility(View.VISIBLE);
            final ImageView ivTabReconocimientosMensualActivo = (ImageView) vTabReconocimientosMensual.findViewById(R.id.ivTabReconocimientosMensualActivo);
            final ImageView ivTabReconocimientosMensualInactivo = (ImageView) vTabReconocimientosMensual.findViewById(R.id.ivTabReconocimientosMensualInactivo);
            ivTabReconocimientosMensualActivo.setVisibility(View.GONE);
            ivTabReconocimientosMensualInactivo.setVisibility(View.VISIBLE);
            final ImageView ivTabReconocimientosTrimestralesActivo = (ImageView) vTabReconocimientosTrimestrales.findViewById(R.id.ivTabReconocimientosTrimestralesActivo);
            final ImageView ivTabReconocimientosTrimestralesInactivo = (ImageView) vTabReconocimientosTrimestrales.findViewById(R.id.ivTabReconocimientosTrimestralesInactivo);
            ivTabReconocimientosTrimestralesActivo.setVisibility(View.GONE);
            ivTabReconocimientosTrimestralesInactivo.setVisibility(View.VISIBLE);
            final ImageView ivTabReconocimientosSemestralesActivo = (ImageView) vTabReconocimientosSemestrales.findViewById(R.id.ivTabReconocimientosSemestralesActivo);
            final ImageView ivTabReconocimientosSemestralesInactivo = (ImageView) vTabReconocimientosSemestrales.findViewById(R.id.ivTabReconocimientosSemestralesInactivo);
            ivTabReconocimientosSemestralesActivo.setVisibility(View.GONE);
            ivTabReconocimientosSemestralesInactivo.setVisibility(View.VISIBLE);
            final ImageView ivTabReconocimientosAnualActivo = (ImageView) vTabReconocimientosAnual.findViewById(R.id.ivTabReconocimientosAnualActivo);
            final ImageView ivTabReconocimientosAnualInactivo = (ImageView) vTabReconocimientosAnual.findViewById(R.id.ivTabReconocimientosAnualInactivo);
            ivTabReconocimientosAnualActivo.setVisibility(View.GONE);
            ivTabReconocimientosAnualInactivo.setVisibility(View.VISIBLE);

            ivTabReconocimientosDiariosActivo.setAlpha((float) 0.5);
            ivTabReconocimientosDiariosInactivo.setAlpha((float) 0.5);
            ivTabReconocimientosDiariosInactivo.setColorFilter(Color.parseColor("#D41B55"));
            ivTabReconocimientosSemanalActivo.setColorFilter(Color.parseColor("#FFFFFF"));
            ivTabReconocimientosMensualActivo.setColorFilter(Color.parseColor("#FFFFFF"));
            ivTabReconocimientosTrimestralesActivo.setColorFilter(Color.parseColor("#FFFFFF"));
            ivTabReconocimientosSemestralesActivo.setColorFilter(Color.parseColor("#FFFFFF"));
            ivTabReconocimientosAnualActivo.setColorFilter(Color.parseColor("#FFFFFF"));

            tlReconocimientos.getTabAt(0).setCustomView(null);
            tlReconocimientos.getTabAt(0).setCustomView(vTabReconocimientosDiarios);
            tlReconocimientos.getTabAt(1).setCustomView(null);
            tlReconocimientos.getTabAt(1).setCustomView(vTabReconocimientosSemanal);
            tlReconocimientos.getTabAt(2).setCustomView(null);
            tlReconocimientos.getTabAt(2).setCustomView(vTabReconocimientosMensual);
            tlReconocimientos.getTabAt(3).setCustomView(null);
            tlReconocimientos.getTabAt(3).setCustomView(vTabReconocimientosTrimestrales);
            tlReconocimientos.getTabAt(4).setCustomView(null);
            tlReconocimientos.getTabAt(4).setCustomView(vTabReconocimientosSemestrales);
            tlReconocimientos.getTabAt(5).setCustomView(null);
            tlReconocimientos.getTabAt(5).setCustomView(vTabReconocimientosAnual);


            // ASIGNACION DE LOS TAB
            LinearLayout tabsContainer = (LinearLayout) tlReconocimientos.getChildAt(0);

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

            tabView0.setBackgroundResource(R.drawable.eduko_tab_reconocimiento);
            tabView1.setBackgroundColor(Color.TRANSPARENT);
            tabView2.setBackgroundColor(Color.TRANSPARENT);
            tabView3.setBackgroundColor(Color.TRANSPARENT);
            tabView4.setBackgroundColor(Color.TRANSPARENT);
            tabView5.setBackgroundColor(Color.TRANSPARENT);


            tlReconocimientos.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    switch (tab.getPosition()) {
                        // DIARIOS
                        case 0: {
                            ivTabReconocimientosDiariosActivo.setVisibility(View.VISIBLE);
                            ivTabReconocimientosDiariosInactivo.setVisibility(View.GONE);
                            ivTabReconocimientosSemanalActivo.setVisibility(View.GONE);
                            ivTabReconocimientosSemanalInactivo.setVisibility(View.VISIBLE);
                            ivTabReconocimientosMensualActivo.setVisibility(View.GONE);
                            ivTabReconocimientosMensualInactivo.setVisibility(View.VISIBLE);
                            ivTabReconocimientosTrimestralesActivo.setVisibility(View.GONE);
                            ivTabReconocimientosTrimestralesInactivo.setVisibility(View.VISIBLE);
                            ivTabReconocimientosSemestralesActivo.setVisibility(View.GONE);
                            ivTabReconocimientosSemestralesInactivo.setVisibility(View.VISIBLE);
                            ivTabReconocimientosAnualActivo.setVisibility(View.GONE);
                            ivTabReconocimientosAnualInactivo.setVisibility(View.VISIBLE);

                            tlReconocimientos.getTabAt(0).setCustomView(null);
                            tlReconocimientos.getTabAt(0).setCustomView(vTabReconocimientosDiarios);
                            tlReconocimientos.getTabAt(1).setCustomView(null);
                            tlReconocimientos.getTabAt(1).setCustomView(vTabReconocimientosSemanal);
                            tlReconocimientos.getTabAt(2).setCustomView(null);
                            tlReconocimientos.getTabAt(2).setCustomView(vTabReconocimientosMensual);
                            tlReconocimientos.getTabAt(3).setCustomView(null);
                            tlReconocimientos.getTabAt(3).setCustomView(vTabReconocimientosTrimestrales);
                            tlReconocimientos.getTabAt(4).setCustomView(null);
                            tlReconocimientos.getTabAt(4).setCustomView(vTabReconocimientosSemestrales);
                            tlReconocimientos.getTabAt(5).setCustomView(null);
                            tlReconocimientos.getTabAt(5).setCustomView(vTabReconocimientosAnual);

                            tabView0.setBackgroundResource(R.drawable.eduko_tab_reconocimiento);
                            tabView1.setBackgroundColor(Color.TRANSPARENT);
                            tabView2.setBackgroundColor(Color.TRANSPARENT);
                            tabView3.setBackgroundColor(Color.TRANSPARENT);
                            tabView4.setBackgroundColor(Color.TRANSPARENT);
                            tabView5.setBackgroundColor(Color.TRANSPARENT);

                            actualizarReconocimientosDiarios();
                        }
                        break;

                        // SEMANAL
                        case 1: {
                            ivTabReconocimientosDiariosActivo.setVisibility(View.GONE);
                            ivTabReconocimientosDiariosInactivo.setVisibility(View.VISIBLE);
                            ivTabReconocimientosSemanalActivo.setVisibility(View.VISIBLE);
                            ivTabReconocimientosSemanalInactivo.setVisibility(View.GONE);
                            ivTabReconocimientosMensualActivo.setVisibility(View.GONE);
                            ivTabReconocimientosMensualInactivo.setVisibility(View.VISIBLE);
                            ivTabReconocimientosTrimestralesActivo.setVisibility(View.GONE);
                            ivTabReconocimientosTrimestralesInactivo.setVisibility(View.VISIBLE);
                            ivTabReconocimientosSemestralesActivo.setVisibility(View.GONE);
                            ivTabReconocimientosSemestralesInactivo.setVisibility(View.VISIBLE);
                            ivTabReconocimientosAnualActivo.setVisibility(View.GONE);
                            ivTabReconocimientosAnualInactivo.setVisibility(View.VISIBLE);

                            ivTabReconocimientosSemanalActivo.setColorFilter(Color.parseColor("#FFFFFF"));

                            tlReconocimientos.getTabAt(0).setCustomView(null);
                            tlReconocimientos.getTabAt(0).setCustomView(vTabReconocimientosDiarios);
                            tlReconocimientos.getTabAt(1).setCustomView(null);
                            tlReconocimientos.getTabAt(1).setCustomView(vTabReconocimientosSemanal);
                            tlReconocimientos.getTabAt(2).setCustomView(null);
                            tlReconocimientos.getTabAt(2).setCustomView(vTabReconocimientosMensual);
                            tlReconocimientos.getTabAt(3).setCustomView(null);
                            tlReconocimientos.getTabAt(3).setCustomView(vTabReconocimientosTrimestrales);
                            tlReconocimientos.getTabAt(4).setCustomView(null);
                            tlReconocimientos.getTabAt(4).setCustomView(vTabReconocimientosSemestrales);
                            tlReconocimientos.getTabAt(5).setCustomView(null);
                            tlReconocimientos.getTabAt(5).setCustomView(vTabReconocimientosAnual);

                            tabView0.setBackgroundColor(Color.TRANSPARENT);
                            tabView1.setBackgroundResource(R.drawable.eduko_tab_reconocimiento);
                            tabView2.setBackgroundColor(Color.TRANSPARENT);
                            tabView3.setBackgroundColor(Color.TRANSPARENT);
                            tabView4.setBackgroundColor(Color.TRANSPARENT);
                            tabView5.setBackgroundColor(Color.TRANSPARENT);

                            actualizarReconocimientosSemanales();
                        }
                        break;

                        // MENSUAL
                        case 2: {
                            ivTabReconocimientosDiariosActivo.setVisibility(View.GONE);
                            ivTabReconocimientosDiariosInactivo.setVisibility(View.VISIBLE);
                            ivTabReconocimientosSemanalActivo.setVisibility(View.GONE);
                            ivTabReconocimientosSemanalInactivo.setVisibility(View.VISIBLE);
                            ivTabReconocimientosMensualActivo.setVisibility(View.VISIBLE);
                            ivTabReconocimientosMensualInactivo.setVisibility(View.GONE);
                            ivTabReconocimientosTrimestralesActivo.setVisibility(View.GONE);
                            ivTabReconocimientosTrimestralesInactivo.setVisibility(View.VISIBLE);
                            ivTabReconocimientosSemestralesActivo.setVisibility(View.GONE);
                            ivTabReconocimientosSemestralesInactivo.setVisibility(View.VISIBLE);
                            ivTabReconocimientosAnualActivo.setVisibility(View.GONE);
                            ivTabReconocimientosAnualInactivo.setVisibility(View.VISIBLE);

                            tlReconocimientos.getTabAt(0).setCustomView(null);
                            tlReconocimientos.getTabAt(0).setCustomView(vTabReconocimientosDiarios);
                            tlReconocimientos.getTabAt(1).setCustomView(null);
                            tlReconocimientos.getTabAt(1).setCustomView(vTabReconocimientosSemanal);
                            tlReconocimientos.getTabAt(2).setCustomView(null);
                            tlReconocimientos.getTabAt(2).setCustomView(vTabReconocimientosMensual);
                            tlReconocimientos.getTabAt(3).setCustomView(null);
                            tlReconocimientos.getTabAt(3).setCustomView(vTabReconocimientosTrimestrales);
                            tlReconocimientos.getTabAt(4).setCustomView(null);
                            tlReconocimientos.getTabAt(4).setCustomView(vTabReconocimientosSemestrales);
                            tlReconocimientos.getTabAt(5).setCustomView(null);
                            tlReconocimientos.getTabAt(5).setCustomView(vTabReconocimientosAnual);

                            tabView0.setBackgroundColor(Color.TRANSPARENT);
                            tabView1.setBackgroundColor(Color.TRANSPARENT);
                            tabView2.setBackgroundResource(R.drawable.eduko_tab_reconocimiento);
                            tabView3.setBackgroundColor(Color.TRANSPARENT);

                            actualizarReconocmientosMensual();
                        }
                        break;

                        // TRIMESTRALES
                        case 3: {
                            ivTabReconocimientosDiariosActivo.setVisibility(View.GONE);
                            ivTabReconocimientosDiariosInactivo.setVisibility(View.VISIBLE);
                            ivTabReconocimientosSemanalActivo.setVisibility(View.GONE);
                            ivTabReconocimientosSemanalInactivo.setVisibility(View.VISIBLE);
                            ivTabReconocimientosMensualActivo.setVisibility(View.GONE);
                            ivTabReconocimientosMensualInactivo.setVisibility(View.VISIBLE);
                            ivTabReconocimientosTrimestralesActivo.setVisibility(View.VISIBLE);
                            ivTabReconocimientosTrimestralesInactivo.setVisibility(View.GONE);
                            ivTabReconocimientosSemestralesActivo.setVisibility(View.GONE);
                            ivTabReconocimientosSemestralesInactivo.setVisibility(View.VISIBLE);
                            ivTabReconocimientosAnualActivo.setVisibility(View.GONE);
                            ivTabReconocimientosAnualInactivo.setVisibility(View.VISIBLE);

                            tlReconocimientos.getTabAt(0).setCustomView(null);
                            tlReconocimientos.getTabAt(0).setCustomView(vTabReconocimientosDiarios);
                            tlReconocimientos.getTabAt(1).setCustomView(null);
                            tlReconocimientos.getTabAt(1).setCustomView(vTabReconocimientosSemanal);
                            tlReconocimientos.getTabAt(2).setCustomView(null);
                            tlReconocimientos.getTabAt(2).setCustomView(vTabReconocimientosMensual);
                            tlReconocimientos.getTabAt(3).setCustomView(null);
                            tlReconocimientos.getTabAt(3).setCustomView(vTabReconocimientosTrimestrales);
                            tlReconocimientos.getTabAt(4).setCustomView(null);
                            tlReconocimientos.getTabAt(4).setCustomView(vTabReconocimientosSemestrales);
                            tlReconocimientos.getTabAt(5).setCustomView(null);
                            tlReconocimientos.getTabAt(5).setCustomView(vTabReconocimientosAnual);

                            tabView0.setBackgroundColor(Color.TRANSPARENT);
                            tabView1.setBackgroundColor(Color.TRANSPARENT);
                            tabView2.setBackgroundColor(Color.TRANSPARENT);
                            tabView3.setBackgroundResource(R.drawable.eduko_tab_reconocimiento);
                            tabView4.setBackgroundColor(Color.TRANSPARENT);
                            tabView5.setBackgroundColor(Color.TRANSPARENT);

                            actualizarReconocimientosTrimestrales();
                        }
                        break;

                        // SEMESTRALES
                        case 4: {
                            ivTabReconocimientosDiariosActivo.setVisibility(View.GONE);
                            ivTabReconocimientosDiariosInactivo.setVisibility(View.VISIBLE);
                            ivTabReconocimientosSemanalActivo.setVisibility(View.GONE);
                            ivTabReconocimientosSemanalInactivo.setVisibility(View.VISIBLE);
                            ivTabReconocimientosMensualActivo.setVisibility(View.GONE);
                            ivTabReconocimientosMensualInactivo.setVisibility(View.VISIBLE);
                            ivTabReconocimientosTrimestralesActivo.setVisibility(View.GONE);
                            ivTabReconocimientosTrimestralesInactivo.setVisibility(View.VISIBLE);
                            ivTabReconocimientosSemestralesActivo.setVisibility(View.VISIBLE);
                            ivTabReconocimientosSemestralesInactivo.setVisibility(View.GONE);
                            ivTabReconocimientosAnualActivo.setVisibility(View.GONE);
                            ivTabReconocimientosAnualInactivo.setVisibility(View.VISIBLE);

                            tlReconocimientos.getTabAt(0).setCustomView(null);
                            tlReconocimientos.getTabAt(0).setCustomView(vTabReconocimientosDiarios);
                            tlReconocimientos.getTabAt(1).setCustomView(null);
                            tlReconocimientos.getTabAt(1).setCustomView(vTabReconocimientosSemanal);
                            tlReconocimientos.getTabAt(2).setCustomView(null);
                            tlReconocimientos.getTabAt(2).setCustomView(vTabReconocimientosMensual);
                            tlReconocimientos.getTabAt(3).setCustomView(null);
                            tlReconocimientos.getTabAt(3).setCustomView(vTabReconocimientosTrimestrales);
                            tlReconocimientos.getTabAt(4).setCustomView(null);
                            tlReconocimientos.getTabAt(4).setCustomView(vTabReconocimientosSemestrales);
                            tlReconocimientos.getTabAt(5).setCustomView(null);
                            tlReconocimientos.getTabAt(5).setCustomView(vTabReconocimientosAnual);

                            tabView0.setBackgroundColor(Color.TRANSPARENT);
                            tabView1.setBackgroundColor(Color.TRANSPARENT);
                            tabView2.setBackgroundColor(Color.TRANSPARENT);
                            tabView3.setBackgroundColor(Color.TRANSPARENT);
                            tabView4.setBackgroundResource(R.drawable.eduko_tab_reconocimiento);
                            tabView5.setBackgroundColor(Color.TRANSPARENT);

                            actualizarReconocimientosSemestrales();
                        }
                        break;

                        // ANUALES
                        case 5: {
                            ivTabReconocimientosDiariosActivo.setVisibility(View.GONE);
                            ivTabReconocimientosDiariosInactivo.setVisibility(View.VISIBLE);
                            ivTabReconocimientosSemanalActivo.setVisibility(View.GONE);
                            ivTabReconocimientosSemanalInactivo.setVisibility(View.VISIBLE);
                            ivTabReconocimientosMensualActivo.setVisibility(View.GONE);
                            ivTabReconocimientosMensualInactivo.setVisibility(View.VISIBLE);
                            ivTabReconocimientosTrimestralesActivo.setVisibility(View.GONE);
                            ivTabReconocimientosTrimestralesInactivo.setVisibility(View.VISIBLE);
                            ivTabReconocimientosSemestralesActivo.setVisibility(View.GONE);
                            ivTabReconocimientosSemestralesInactivo.setVisibility(View.VISIBLE);
                            ivTabReconocimientosAnualActivo.setVisibility(View.VISIBLE);
                            ivTabReconocimientosAnualInactivo.setVisibility(View.GONE);

                            tlReconocimientos.getTabAt(0).setCustomView(null);
                            tlReconocimientos.getTabAt(0).setCustomView(vTabReconocimientosDiarios);
                            tlReconocimientos.getTabAt(1).setCustomView(null);
                            tlReconocimientos.getTabAt(1).setCustomView(vTabReconocimientosSemanal);
                            tlReconocimientos.getTabAt(2).setCustomView(null);
                            tlReconocimientos.getTabAt(2).setCustomView(vTabReconocimientosMensual);
                            tlReconocimientos.getTabAt(3).setCustomView(null);
                            tlReconocimientos.getTabAt(3).setCustomView(vTabReconocimientosTrimestrales);
                            tlReconocimientos.getTabAt(4).setCustomView(null);
                            tlReconocimientos.getTabAt(4).setCustomView(vTabReconocimientosSemestrales);
                            tlReconocimientos.getTabAt(5).setCustomView(null);
                            tlReconocimientos.getTabAt(5).setCustomView(vTabReconocimientosAnual);

                            tabView0.setBackgroundColor(Color.TRANSPARENT);
                            tabView1.setBackgroundColor(Color.TRANSPARENT);
                            tabView2.setBackgroundColor(Color.TRANSPARENT);
                            tabView3.setBackgroundColor(Color.TRANSPARENT);
                            tabView4.setBackgroundColor(Color.TRANSPARENT);
                            tabView5.setBackgroundResource(R.drawable.eduko_tab_reconocimiento);

                            actualizarReconocimientosAnual();
                        }
                        break;
                    }

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


    private void actualizarReconocimientos() {
        try {
            switch (vpReconocimientos.getCurrentItem()) {
                case 0: actualizarReconocimientosDiarios(); break;
                case 1: actualizarReconocimientosSemanales(); break;
                case 2: actualizarReconocmientosMensual(); break;
                case 3: actualizarReconocimientosTrimestrales(); break;
                case 4: actualizarReconocimientosSemestrales(); break;
                case 5: actualizarReconocimientosAnual(); break;
            }
        } catch (Exception e) {
            Toasty.error(getContext(), getString(R.string.error_app), Toast.LENGTH_SHORT).show();
            Log.e(Constants.TAG, e.getMessage());
        }
    }


    public void actualizarReconocimientosDiarios() {
        reconocimientosAdapter.reconocimientosDiarioFragment.updateReconocimiento();
    }


    private void actualizarReconocimientosSemanales() {
        reconocimientosAdapter.reconocimientosSemanalFragment.updateReconocimiento();
    }


    private void actualizarReconocmientosMensual() {
        reconocimientosAdapter.reconocimientosMensualFragment.updateReconocimiento();
    }


    private void actualizarReconocimientosTrimestrales() {
        reconocimientosAdapter.reconocimientosTrimestrales.updateReconocimiento();
    }


    private void actualizarReconocimientosSemestrales() {
        reconocimientosAdapter.reconocimientosSemestrales.updateReconocimiento();
    }


    private void actualizarReconocimientosAnual() {
        reconocimientosAdapter.reconocimientosAnualFragment.updateReconocimiento();
    }


    public void obtenerReconocimientos() {
        try {
            mostrarCargando();
            final String token = globalAPP.sessionEDUAPP.getAccessToken(getContext());
            final String idStudent = globalAPP.sessionEDUAPP.getID(getContext());
            String filtro = "{\"include\":[\"recognition\",\"frecuencia\"],\"where\":{\"studentId\":\"" + idStudent + "\"},\"order\":\"created desc\"}";

            Call<List<StudentsRecognitionModel>> call = servicesEDUKO.getReconocimientos(token, filtro);
            call.enqueue(new Callback<List<StudentsRecognitionModel>>() {
                @Override
                public void onResponse(Call<List<StudentsRecognitionModel>> call, Response<List<StudentsRecognitionModel>> response) {
                    ocultarCargando(true);

                    switch (response.code()) {
                        case 200: {
                            if (response.isSuccessful()) {
                                globalAPP.reconocimientoModels = response.body();
                                actualizarReconocimientos();
                            } else {
                                globalAPP.reconocimientoModels.clear();
                                actualizarReconocimientos();
                            }
                        }
                        break;

                        case 401: {
                            globalAPP.reconocimientoModels.clear();
                            actualizarReconocimientos();
                            Toasty.error(getContext(), getString(R.string.error_login_app), Toast.LENGTH_SHORT).show();
                        }
                        break;

                        default: {
                            Log.i("EDUKO-ALUMNO-RECON", "WS Listar reconocimientos -> default");
                            globalAPP.reconocimientoModels.clear();
                            actualizarReconocimientos();
                            Toasty.error(getContext(), getString(R.string.error_reconocimientos), Toast.LENGTH_SHORT).show();
                        }
                        break;
                    }
                }

                @Override
                public void onFailure(Call<List<StudentsRecognitionModel>> call, Throwable throwable) {
                    Log.i("EDUKO-ALUMNO-RECON", "WS Listar reconocimientos -> default");
                    ocultarCargando(true);
                    globalAPP.reconocimientoModels.clear();
                    actualizarReconocimientos();
                    Toasty.error(getContext(), getString(R.string.error_reconocimientos), Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            Toasty.error(getContext(), getString(R.string.error_app), Toast.LENGTH_SHORT).show();
            Log.e(Constants.TAG, e.getMessage());
        }
    }

}
