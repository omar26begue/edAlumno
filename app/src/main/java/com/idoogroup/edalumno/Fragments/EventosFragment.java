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
import com.idoogroup.edalumno.Models.EventosModel;
import com.idoogroup.edalumno.R;
import com.idoogroup.edalumno.ViewPageTransformer.DepthPageTransformer;
import com.idoogroup.edalumno.ViewPager.ViewPagerEventosAdapter;
import com.idoogroup.edalumno.WebServicesIdooGroup;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class EventosFragment extends Fragment {

    public static final String TITLE = "Eventos";
    private TabLayout tlEventos;
    public ViewPager vpEventos;
    public ViewPagerEventosAdapter eventosAdapter;
    private TextView tvEventosActivos, tvEventosInactivos, tvEventos;

    private IdooGroupApplication globalAPP;
    private WebServicesIdooGroup servicesEDUKO;
    private Dialog dialogLoading = null;


    public EventosFragment() {
        // Required empty public constructor
    }


    public static EventosFragment newInstance() {
        return new EventosFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_eventos, container, false);

        try {
            // VARIABLE GLOBAL
            globalAPP = (IdooGroupApplication) getActivity().getApplication();
            servicesEDUKO = globalAPP.getService();

            // ASIGNADO LAS VARIABLES
            tlEventos = (TabLayout) v.findViewById(R.id.tlEventos);
            vpEventos = (ViewPager) v.findViewById(R.id.vpEventos);
            tvEventosActivos = (TextView) v.findViewById(R.id.tvEventosActivos);
            tvEventosInactivos = (TextView) v.findViewById(R.id.tvEventosInactivos);
            tvEventos = (TextView) v.findViewById(R.id.tvEventos);

            // ASIGNACION DE FUENTES
            tvEventosActivos.setTypeface(globalAPP.getFuenteAvenidHeavy(getActivity().getAssets()));
            tvEventosInactivos.setTypeface(globalAPP.getFuenteAvenidBook(getActivity().getAssets()));
            tvEventos.setTypeface(globalAPP.getFuenteAvenidHeavy(getActivity().getAssets()));

            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            LayoutInflater inflaterLoading = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflaterLoading.inflate(R.layout.dialog_loading, null);

            builder.setView(view);
            dialogLoading = builder.create();
            dialogLoading.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialogLoading.setCancelable(false);

            // TABS
            eventosAdapter = new ViewPagerEventosAdapter(getFragmentManager());
            vpEventos.setAdapter(eventosAdapter);
            vpEventos.setPageTransformer(true, new DepthPageTransformer());
            tlEventos.setupWithViewPager(vpEventos);
            vpEventos.setCurrentItem(0);
            vpEventos.setOffscreenPageLimit(3);

            // ICONOS DEL TABLAYOUT
            final View vTabEventosAsistenciaVerificada = inflater.inflate(R.layout.tab_menu_eventos_asistencia_verificada, null);
            final View vTabEventosAsistenciaNoVerificada = inflater.inflate(R.layout.tab_menu_eventos_asistencia_no_verificada, null);
            final View vTabEventosAsistenciaTodos = inflater.inflate(R.layout.tab_menu_eventos_todos, null);

            final ImageView ivTabEventosAsistenciaVerificadaActivo = (ImageView) vTabEventosAsistenciaVerificada.findViewById(R.id.ivTabEventosAsistenciaVerificadaActivo);
            final ImageView ivTabEventosAsistenciaVerificadaInactivo = (ImageView) vTabEventosAsistenciaVerificada.findViewById(R.id.ivTabEventosAsistenciaVerificadaInactivo);
            final ImageView ivTabEventosAsistenciaNoVerificadaActivo = (ImageView) vTabEventosAsistenciaNoVerificada.findViewById(R.id.ivTabEventosAsistenciaNoVerificadaActivo);
            final ImageView ivTabEventosAsistenciaNoVerificadaInactivo = (ImageView) vTabEventosAsistenciaNoVerificada.findViewById(R.id.ivTabEventosAsistenciaNoVerificadaInactivo);
            final ImageView ivTabEventosTodosActivo = (ImageView) vTabEventosAsistenciaTodos.findViewById(R.id.ivTabEventosTodosActivo);
            final ImageView ivTabEventosTodosInactivo = (ImageView) vTabEventosAsistenciaTodos.findViewById(R.id.ivTabEventosTodosInactivo);
            ivTabEventosAsistenciaNoVerificadaActivo.setVisibility(View.GONE);
            ivTabEventosAsistenciaNoVerificadaInactivo.setVisibility(View.VISIBLE);
            ivTabEventosTodosActivo.setVisibility(View.GONE);
            ivTabEventosTodosInactivo.setVisibility(View.VISIBLE);

            tlEventos.getTabAt(0).setCustomView(null);
            tlEventos.getTabAt(0).setCustomView(vTabEventosAsistenciaVerificada);
            tlEventos.getTabAt(1).setCustomView(null);
            tlEventos.getTabAt(1).setCustomView(vTabEventosAsistenciaNoVerificada);
            tlEventos.getTabAt(2).setCustomView(null);
            tlEventos.getTabAt(2).setCustomView(vTabEventosAsistenciaTodos);


            // ASIGNACION DE LOS TAB
            LinearLayout tabsContainer = (LinearLayout) tlEventos.getChildAt(0);
            LinearLayout childLayout0 = (LinearLayout) tabsContainer.getChildAt(0);
            LinearLayout childLayout1 = (LinearLayout) tabsContainer.getChildAt(1);
            LinearLayout childLayout2 = (LinearLayout) tabsContainer.getChildAt(2);
            final LinearLayout tabView0 = (LinearLayout) childLayout0.getChildAt(0).getParent();
            final LinearLayout tabView1 = (LinearLayout) childLayout1.getChildAt(0).getParent();
            final LinearLayout tabView2 = (LinearLayout) childLayout2.getChildAt(0).getParent();
            tabView0.setBackgroundResource(R.drawable.eduko_tab_evaluaciones);
            tabView1.setBackgroundColor(Color.TRANSPARENT);
            tabView2.setBackgroundColor(Color.TRANSPARENT);


            tvEventosActivos.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tvEventosActivos.setAlpha(1);
                    tvEventosInactivos.setAlpha((float) 0.25);

                    tvEventosActivos.setBackgroundResource(R.drawable.eduko_btn_activo);
                    tvEventosInactivos.setBackgroundResource(R.drawable.eduko_btn_inactivo);

                    tvEventosActivos.setTextColor(Color.parseColor("#FFFFFF"));
                    tvEventosInactivos.setTextColor(Color.parseColor("#F1460C"));

                    globalAPP.eventosActivo = true;

                    switch (vpEventos.getCurrentItem()) {
                        case 0: actualizarTabAsistenciaVerificada(); break;
                        case 1: actualizarTabAsistenciaNoVerificada(); break;
                        case 2: actualizarTabAsistenciaTodosVerificada(); break;
                    }

                }
            });


            tvEventosInactivos.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tvEventosActivos.setAlpha((float) 0.25);
                    tvEventosInactivos.setAlpha(1);

                    tvEventosActivos.setBackgroundResource(R.drawable.eduko_btn_inactivo);
                    tvEventosInactivos.setBackgroundResource(R.drawable.eduko_btn_activo);

                    tvEventosActivos.setTextColor(Color.parseColor("#F1460C"));
                    tvEventosInactivos.setTextColor(Color.parseColor("#FFFFFF"));

                    globalAPP.eventosActivo = false;

                    switch (vpEventos.getCurrentItem()) {
                        case 0: actualizarTabAsistenciaVerificada(); break;
                        case 1: actualizarTabAsistenciaNoVerificada(); break;
                        case 2: actualizarTabAsistenciaTodosVerificada(); break;
                    }

                }
            });


            tlEventos.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    switch (tab.getPosition()) {
                        case 0: {
                            ivTabEventosAsistenciaVerificadaActivo.setVisibility(View.VISIBLE);
                            ivTabEventosAsistenciaVerificadaInactivo.setVisibility(View.GONE);
                            ivTabEventosAsistenciaNoVerificadaActivo.setVisibility(View.GONE);
                            ivTabEventosAsistenciaNoVerificadaInactivo.setVisibility(View.VISIBLE);
                            ivTabEventosTodosActivo.setVisibility(View.GONE);
                            ivTabEventosTodosInactivo.setVisibility(View.VISIBLE);

                            tlEventos.getTabAt(0).setCustomView(null);
                            tlEventos.getTabAt(0).setCustomView(vTabEventosAsistenciaVerificada);
                            tlEventos.getTabAt(1).setCustomView(null);
                            tlEventos.getTabAt(1).setCustomView(vTabEventosAsistenciaNoVerificada);
                            tlEventos.getTabAt(2).setCustomView(null);
                            tlEventos.getTabAt(2).setCustomView(vTabEventosAsistenciaTodos);

                            tabView0.setBackgroundResource(R.drawable.eduko_tab_evaluaciones);
                            tabView1.setBackgroundColor(Color.TRANSPARENT);
                            tabView2.setBackgroundColor(Color.TRANSPARENT);

                            actualizarTabAsistenciaVerificada();
                        }
                        break;

                        case 1: {
                            ivTabEventosAsistenciaVerificadaActivo.setVisibility(View.GONE);
                            ivTabEventosAsistenciaVerificadaInactivo.setVisibility(View.VISIBLE);
                            ivTabEventosAsistenciaNoVerificadaActivo.setVisibility(View.VISIBLE);
                            ivTabEventosAsistenciaNoVerificadaInactivo.setVisibility(View.GONE);
                            ivTabEventosTodosActivo.setVisibility(View.GONE);
                            ivTabEventosTodosInactivo.setVisibility(View.VISIBLE);

                            tlEventos.getTabAt(0).setCustomView(null);
                            tlEventos.getTabAt(0).setCustomView(vTabEventosAsistenciaVerificada);
                            tlEventos.getTabAt(1).setCustomView(null);
                            tlEventos.getTabAt(1).setCustomView(vTabEventosAsistenciaNoVerificada);
                            tlEventos.getTabAt(2).setCustomView(null);
                            tlEventos.getTabAt(2).setCustomView(vTabEventosAsistenciaTodos);

                            tabView0.setBackgroundColor(Color.TRANSPARENT);
                            tabView1.setBackgroundResource(R.drawable.eduko_tab_eventos_asistencia_no_verificada);
                            tabView2.setBackgroundColor(Color.TRANSPARENT);

                            actualizarTabAsistenciaNoVerificada();
                        }
                        break;

                        case 2: {
                            ivTabEventosAsistenciaVerificadaActivo.setVisibility(View.GONE);
                            ivTabEventosAsistenciaVerificadaInactivo.setVisibility(View.VISIBLE);
                            ivTabEventosAsistenciaNoVerificadaActivo.setVisibility(View.GONE);
                            ivTabEventosAsistenciaNoVerificadaInactivo.setVisibility(View.VISIBLE);
                            ivTabEventosTodosActivo.setVisibility(View.VISIBLE);
                            ivTabEventosTodosInactivo.setVisibility(View.GONE);

                            tlEventos.getTabAt(0).setCustomView(null);
                            tlEventos.getTabAt(0).setCustomView(vTabEventosAsistenciaVerificada);
                            tlEventos.getTabAt(1).setCustomView(null);
                            tlEventos.getTabAt(1).setCustomView(vTabEventosAsistenciaNoVerificada);
                            tlEventos.getTabAt(2).setCustomView(null);
                            tlEventos.getTabAt(2).setCustomView(vTabEventosAsistenciaTodos);

                            tabView0.setBackgroundColor(Color.TRANSPARENT);
                            tabView1.setBackgroundColor(Color.TRANSPARENT);
                            tabView2.setBackgroundResource(R.drawable.eduko_tab_eventos_todos);

                            actualizarTabAsistenciaTodosVerificada();
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


    private void actualizarEventos() {
        switch (vpEventos.getCurrentItem()) {
            case 0: actualizarTabAsistenciaVerificada(); break;
            case 1: actualizarTabAsistenciaNoVerificada(); break;
            case 2: actualizarTabAsistenciaTodosVerificada(); break;
        }
    }


    private void actualizarTabAsistenciaVerificada() {
        try {
            List<EventosModel> eventosModels = new ArrayList<>();

            for (int i = 0; i < globalAPP.eventosTodosModels.size(); i++) {
                if (globalAPP.eventosTodosModels.get(i).getActive() == globalAPP.eventosActivo) {
                    if (globalAPP.eventosTodosModels.get(i).getInfo().isConfirmed() == true) {
                        eventosModels.add(globalAPP.eventosTodosModels.get(i));
                    }
                }
            }

            eventosAdapter.eventosAsistenciaVerificadaFragment.updateEventos(eventosModels);
        } catch (Exception e) {
            Toasty.error(getContext(), getString(R.string.error_app), Toast.LENGTH_SHORT).show();
            Log.e(Constants.TAG, e.getMessage());
        }
    }


    private void actualizarTabAsistenciaNoVerificada() {
        try {
            List<EventosModel> eventosModels = new ArrayList<>();

            for (int i = 0; i < globalAPP.eventosTodosModels.size(); i++) {
                if (globalAPP.eventosTodosModels.get(i).getActive() == globalAPP.eventosActivo) {
                    if (globalAPP.eventosTodosModels.get(i).getInfo().isConfirmed() == false) {
                        eventosModels.add(globalAPP.eventosTodosModels.get(i));
                    }
                }
            }

            eventosAdapter.eventosAsistenciaNoVerificadaFragment.updateEventos(eventosModels);
            globalAPP.countEvent = eventosModels.size();
        } catch (Exception e) {
            Toasty.error(getContext(), getString(R.string.error_app), Toast.LENGTH_SHORT).show();
            Log.e(Constants.TAG, e.getMessage());
        }
    }


    private void actualizarTabAsistenciaTodosVerificada() {
        try {
            List<EventosModel> eventosModels = new ArrayList<>();

            for (int i = 0; i < globalAPP.eventosTodosModels.size(); i++) {
                if (globalAPP.eventosTodosModels.get(i).getActive() == globalAPP.eventosActivo) {
                    eventosModels.add(globalAPP.eventosTodosModels.get(i));
                }
            }

            eventosAdapter.eventosAsistenciaTodosFragment.updateEventos(eventosModels);
        } catch (Exception e) {
            Toasty.error(getContext(), getString(R.string.error_app), Toast.LENGTH_SHORT).show();
            Log.e(Constants.TAG, e.getMessage());
        }
    }


    public void obtenerEventos() {
        try {
            mostrarCargando();

            final String token = globalAPP.sessionEDUAPP.getAccessToken(getContext());
            final String idStudent = globalAPP.sessionEDUAPP.getID(getContext());
            String filtro = "{\"order\":\"date desc\"}";

            Call<List<EventosModel>> call = servicesEDUKO.getEventos(idStudent, token, filtro);
            call.enqueue(new Callback<List<EventosModel>>() {
                @Override
                public void onResponse(Call<List<EventosModel>> call, Response<List<EventosModel>> response) {
                    ocultarCargando(true);

                    switch (response.code()) {
                        case 200: {
                            if (response.isSuccessful()) {
                                Log.i("EDUKO-ALUMNO-EVENT", "WS Listar eventos -> " + response.body().size());

                                List<EventosModel> eventosModels = response.body();
                                globalAPP.eventosTodosModels = eventosModels;

                                int countEvent = 0;

                                for (int i = 0; i < eventosModels.size(); i++)
                                    if (eventosModels.get(i).getInfo().isConfirmed() == false)
                                        countEvent++;

                                globalAPP.countEvent = countEvent;

                                // CARGAR RECONOCIMIENTOS
                                actualizarEventos();
                            } else {
                                globalAPP.eventosTodosModels.clear();
                                actualizarEventos();
                            }
                        }
                        break;

                        case 401: {
                            globalAPP.eventosTodosModels.clear();
                            actualizarEventos();
                            Toasty.error(getContext(), getString(R.string.error_login_app), Toast.LENGTH_SHORT).show();
                        }
                        break;

                        default: {
                            Log.i("EDUKO-ALUMNO-EVENT", "WS Listar eventos -> failure");
                            globalAPP.eventosTodosModels.clear();
                            actualizarEventos();
                            Toasty.error(getContext(), getString(R.string.error_eventos), Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<List<EventosModel>> call, Throwable throwable) {
                    Log.i("EDUKO-ALUMNO-EVENT", "WS Listar eventos -> failure");
                    ocultarCargando(true);
                    globalAPP.eventosTodosModels.clear();
                    actualizarEventos();
                    Toasty.error(getContext(), getString(R.string.error_eventos), Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            Toasty.error(getContext(), getString(R.string.error_app), Toast.LENGTH_SHORT).show();
            Log.e(Constants.TAG, e.getMessage());
        }
    }

}
