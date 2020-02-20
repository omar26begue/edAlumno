package com.idoogroup.edalumno.Fragments;


import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
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
import com.idoogroup.edalumno.Helpers.Moment.Moment;
import com.idoogroup.edalumno.IdooGroupApplication;
import com.idoogroup.edalumno.Interfaces.TareasCallback;
import com.idoogroup.edalumno.Models.SubjectsModel;
import com.idoogroup.edalumno.Models.TareasModel;
import com.idoogroup.edalumno.R;
import com.idoogroup.edalumno.ViewPageTransformer.DepthPageTransformer;
import com.idoogroup.edalumno.ViewPager.ViewPagerTareasAdapter;
import com.idoogroup.edalumno.WebServicesIdooGroup;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class TareasFragment extends Fragment implements TareasCallback {

    private List<SubjectsModel> subjectsModels;

    public static final String TITLE = "Tareas";
    private TextView tvTareas, tvAsignaturas;
    private ImageView ivFlechaAsignatura;
    private TabLayout tlTareas;
    private LinearLayout llLoading;
    public ViewPager vpTareas;
    public ViewPagerTareasAdapter tareasAdapter;

    private IdooGroupApplication globalAPP;
    private WebServicesIdooGroup servicesEDUKO;
    private Dialog dialogLoading = null;

    private String idAsignatura = "-1";


    public TareasFragment() {
        // Required empty public constructor
    }


    public static TareasFragment newInstance() {
        return new TareasFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tareas, container, false);

        try {
            // VARIABLE GLOBAL
            globalAPP = (IdooGroupApplication) getActivity().getApplication();
            servicesEDUKO = globalAPP.getService();

            // ASIGNADO LAS VARIABLES
            tvTareas = (TextView) v.findViewById(R.id.tvTareas);
            tvAsignaturas = (TextView) v.findViewById(R.id.tvAsignaturas);
            ivFlechaAsignatura = (ImageView) v.findViewById(R.id.ivFlechaAsignatura);
            tlTareas = (TabLayout) v.findViewById(R.id.tlTareas);
            vpTareas = (ViewPager) v.findViewById(R.id.vpTareas);

            subjectsModels = globalAPP.subjectsModels;

            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            LayoutInflater inflaterLoading = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflaterLoading.inflate(R.layout.dialog_loading, null);

            builder.setView(view);
            dialogLoading = builder.create();
            dialogLoading.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialogLoading.setCancelable(false);

            // ASIGNACION DE FUENTES
            tvTareas.setTypeface(globalAPP.getFuenteAvenidBlack(getActivity().getAssets()));
            tvAsignaturas.setTypeface(globalAPP.getFuenteAvenidBook(getActivity().getAssets()));

            // TABS
            tareasAdapter = new ViewPagerTareasAdapter(getFragmentManager(), this);
            vpTareas.setAdapter(tareasAdapter);
            vpTareas.setPageTransformer(true, new DepthPageTransformer());
            tlTareas.setupWithViewPager(vpTareas);
            vpTareas.setCurrentItem(0);
            vpTareas.setOffscreenPageLimit(5);

            // ICONOS DE LOS TABS
            LinearLayout tabsContainer = (LinearLayout) tlTareas.getChildAt(0);

            LinearLayout childLayout0 = (LinearLayout) tabsContainer.getChildAt(0);
            LinearLayout childLayout1 = (LinearLayout) tabsContainer.getChildAt(1);
            LinearLayout childLayout2 = (LinearLayout) tabsContainer.getChildAt(2);
            LinearLayout childLayout3 = (LinearLayout) tabsContainer.getChildAt(3);
            LinearLayout childLayout4 = (LinearLayout) tabsContainer.getChildAt(4);

            final LinearLayout tabView0 = (LinearLayout) childLayout0.getChildAt(0).getParent();
            final LinearLayout tabView1 = (LinearLayout) childLayout1.getChildAt(0).getParent();
            final LinearLayout tabView2 = (LinearLayout) childLayout2.getChildAt(0).getParent();
            final LinearLayout tabView3 = (LinearLayout) childLayout3.getChildAt(0).getParent();
            final LinearLayout tabView4 = (LinearLayout) childLayout4.getChildAt(0).getParent();

            final View vTabTareasCalificadas = inflater.inflate(R.layout.tab_menu_tareas_calificadas, null);
            final View vTabTareasRealizadas = inflater.inflate(R.layout.tab_menu_tareas_realizadas, null);
            final View vTabTareasEntregar = inflater.inflate(R.layout.tab_menu_tareas_entregar_manana, null);
            final View vTabTareasXRealizadas = inflater.inflate(R.layout.tab_menu_tareas_xrealizar, null);
            final View vTabTareasTodas = inflater.inflate(R.layout.tab_menu_tareas_todas, null);

            final ImageView ivTabTareasCalificadasActivo = (ImageView) vTabTareasCalificadas.findViewById(R.id.ivTabTareasCalificadasActivo);
            final ImageView ivTabTareasCalificadasInactivo = (ImageView) vTabTareasCalificadas.findViewById(R.id.ivTabTareasCalificadasInactivo);
            final ImageView ivTabTareasRealizadasActivo = (ImageView) vTabTareasRealizadas.findViewById(R.id.ivTabTareasRealizadasActivo);
            final ImageView ivTabTareasRealizadasInactivo = (ImageView) vTabTareasRealizadas.findViewById(R.id.ivTabTareasRealizadasInactivo);
            final ImageView ivTabTareasEntregarActivo = (ImageView) vTabTareasEntregar.findViewById(R.id.ivTabTareasEntregarActivo);
            final ImageView ivTabTareasEntregarInactivo = (ImageView) vTabTareasEntregar.findViewById(R.id.ivTabTareasEntregarInactivo);
            final ImageView ivTabTareasXRealizadasActivo = (ImageView) vTabTareasXRealizadas.findViewById(R.id.ivTabTareasXRealizadasActivo);
            final ImageView ivTabTareasXRealizadasInactivo = (ImageView) vTabTareasXRealizadas.findViewById(R.id.ivTabTareasXRealizadasInactivo);
            final ImageView ivTabTareasTareasActivo = (ImageView) vTabTareasTodas.findViewById(R.id.ivTabTareasTareasActivo);
            final ImageView ivTabTareasTareasInactivo = (ImageView) vTabTareasTodas.findViewById(R.id.ivTabTareasTareasInactivo);

            ivTabTareasCalificadasActivo.setVisibility(View.VISIBLE);
            ivTabTareasCalificadasInactivo.setVisibility(View.GONE);
            ivTabTareasRealizadasActivo.setVisibility(View.GONE);
            ivTabTareasRealizadasInactivo.setVisibility(View.VISIBLE);
            ivTabTareasEntregarActivo.setVisibility(View.GONE);
            ivTabTareasEntregarInactivo.setVisibility(View.VISIBLE);
            ivTabTareasXRealizadasActivo.setVisibility(View.GONE);
            ivTabTareasXRealizadasInactivo.setVisibility(View.VISIBLE);
            ivTabTareasTareasActivo.setVisibility(View.GONE);
            ivTabTareasTareasInactivo.setVisibility(View.VISIBLE);

            tlTareas.getTabAt(0).setCustomView(null);
            tlTareas.getTabAt(0).setCustomView(vTabTareasCalificadas);
            tlTareas.getTabAt(1).setCustomView(null);
            tlTareas.getTabAt(1).setCustomView(vTabTareasRealizadas);
            tlTareas.getTabAt(2).setCustomView(null);
            tlTareas.getTabAt(2).setCustomView(vTabTareasEntregar);
            tlTareas.getTabAt(3).setCustomView(null);
            tlTareas.getTabAt(3).setCustomView(vTabTareasXRealizadas);
            tlTareas.getTabAt(4).setCustomView(null);
            tlTareas.getTabAt(4).setCustomView(vTabTareasTodas);

            tabView0.setBackgroundResource(R.drawable.eduko_tab_tareas);
            tabView1.setBackgroundColor(Color.TRANSPARENT);
            tabView2.setBackgroundColor(Color.TRANSPARENT);
            tabView3.setBackgroundColor(Color.TRANSPARENT);
            tabView4.setBackgroundColor(Color.TRANSPARENT);


            // ASIGNACION DE FUENTES
            tvTareas.setTypeface(globalAPP.getFuenteAvenidLight(getContext().getAssets()));

            tlTareas.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {

                    switch (tab.getPosition()) {
                        case 0: {
                            ivTabTareasCalificadasActivo.setVisibility(View.VISIBLE);
                            ivTabTareasCalificadasInactivo.setVisibility(View.GONE);
                            ivTabTareasRealizadasActivo.setVisibility(View.GONE);
                            ivTabTareasRealizadasInactivo.setVisibility(View.VISIBLE);
                            ivTabTareasEntregarActivo.setVisibility(View.GONE);
                            ivTabTareasEntregarInactivo.setVisibility(View.VISIBLE);
                            ivTabTareasXRealizadasActivo.setVisibility(View.GONE);
                            ivTabTareasXRealizadasInactivo.setVisibility(View.VISIBLE);
                            ivTabTareasTareasActivo.setVisibility(View.GONE);
                            ivTabTareasTareasInactivo.setVisibility(View.VISIBLE);

                            tlTareas.getTabAt(0).setCustomView(null);
                            tlTareas.getTabAt(0).setCustomView(vTabTareasCalificadas);
                            tlTareas.getTabAt(1).setCustomView(null);
                            tlTareas.getTabAt(1).setCustomView(vTabTareasRealizadas);
                            tlTareas.getTabAt(2).setCustomView(null);
                            tlTareas.getTabAt(2).setCustomView(vTabTareasEntregar);
                            tlTareas.getTabAt(3).setCustomView(null);
                            tlTareas.getTabAt(3).setCustomView(vTabTareasXRealizadas);
                            tlTareas.getTabAt(4).setCustomView(null);
                            tlTareas.getTabAt(4).setCustomView(vTabTareasTodas);

                            tabView0.setBackgroundResource(R.drawable.eduko_tab_tareas);
                            tabView1.setBackgroundColor(Color.TRANSPARENT);
                            tabView2.setBackgroundColor(Color.TRANSPARENT);
                            tabView3.setBackgroundColor(Color.TRANSPARENT);
                            tabView4.setBackgroundColor(Color.TRANSPARENT);

                            actualizarTareasCalificadas();
                        }
                        break;

                        case 1: {
                            ivTabTareasCalificadasActivo.setVisibility(View.GONE);
                            ivTabTareasCalificadasInactivo.setVisibility(View.VISIBLE);
                            ivTabTareasRealizadasActivo.setVisibility(View.VISIBLE);
                            ivTabTareasRealizadasInactivo.setVisibility(View.GONE);
                            ivTabTareasEntregarActivo.setVisibility(View.GONE);
                            ivTabTareasEntregarInactivo.setVisibility(View.VISIBLE);
                            ivTabTareasXRealizadasActivo.setVisibility(View.GONE);
                            ivTabTareasXRealizadasInactivo.setVisibility(View.VISIBLE);
                            ivTabTareasTareasActivo.setVisibility(View.GONE);
                            ivTabTareasTareasInactivo.setVisibility(View.VISIBLE);

                            tlTareas.getTabAt(0).setCustomView(null);
                            tlTareas.getTabAt(0).setCustomView(vTabTareasCalificadas);
                            tlTareas.getTabAt(1).setCustomView(null);
                            tlTareas.getTabAt(1).setCustomView(vTabTareasRealizadas);
                            tlTareas.getTabAt(2).setCustomView(null);
                            tlTareas.getTabAt(2).setCustomView(vTabTareasEntregar);
                            tlTareas.getTabAt(3).setCustomView(null);
                            tlTareas.getTabAt(3).setCustomView(vTabTareasXRealizadas);
                            tlTareas.getTabAt(4).setCustomView(null);
                            tlTareas.getTabAt(4).setCustomView(vTabTareasTodas);

                            tabView0.setBackgroundColor(Color.TRANSPARENT);
                            tabView1.setBackgroundResource(R.drawable.eduko_tab_tareas);
                            tabView2.setBackgroundColor(Color.TRANSPARENT);
                            tabView3.setBackgroundColor(Color.TRANSPARENT);
                            tabView4.setBackgroundColor(Color.TRANSPARENT);

                            actualizarTareasRealizadas();
                        }
                        break;

                        case 2: {
                            ivTabTareasCalificadasActivo.setVisibility(View.GONE);
                            ivTabTareasCalificadasInactivo.setVisibility(View.VISIBLE);
                            ivTabTareasRealizadasActivo.setVisibility(View.GONE);
                            ivTabTareasRealizadasInactivo.setVisibility(View.VISIBLE);
                            ivTabTareasEntregarActivo.setVisibility(View.VISIBLE);
                            ivTabTareasEntregarInactivo.setVisibility(View.GONE);
                            ivTabTareasXRealizadasActivo.setVisibility(View.GONE);
                            ivTabTareasXRealizadasInactivo.setVisibility(View.VISIBLE);
                            ivTabTareasTareasActivo.setVisibility(View.GONE);
                            ivTabTareasTareasInactivo.setVisibility(View.VISIBLE);

                            tlTareas.getTabAt(0).setCustomView(null);
                            tlTareas.getTabAt(0).setCustomView(vTabTareasCalificadas);
                            tlTareas.getTabAt(1).setCustomView(null);
                            tlTareas.getTabAt(1).setCustomView(vTabTareasRealizadas);
                            tlTareas.getTabAt(2).setCustomView(null);
                            tlTareas.getTabAt(2).setCustomView(vTabTareasEntregar);
                            tlTareas.getTabAt(3).setCustomView(null);
                            tlTareas.getTabAt(3).setCustomView(vTabTareasXRealizadas);
                            tlTareas.getTabAt(4).setCustomView(null);
                            tlTareas.getTabAt(4).setCustomView(vTabTareasTodas);

                            tabView0.setBackgroundColor(Color.TRANSPARENT);
                            tabView1.setBackgroundColor(Color.TRANSPARENT);
                            tabView2.setBackgroundResource(R.drawable.eduko_tab_tareas);
                            tabView3.setBackgroundColor(Color.TRANSPARENT);
                            tabView4.setBackgroundColor(Color.TRANSPARENT);

                            actualizarTareasEntregarManana();
                        }
                        break;

                        case 3: {
                            ivTabTareasCalificadasActivo.setVisibility(View.GONE);
                            ivTabTareasCalificadasInactivo.setVisibility(View.VISIBLE);
                            ivTabTareasRealizadasActivo.setVisibility(View.GONE);
                            ivTabTareasRealizadasInactivo.setVisibility(View.VISIBLE);
                            ivTabTareasEntregarActivo.setVisibility(View.GONE);
                            ivTabTareasEntregarInactivo.setVisibility(View.VISIBLE);
                            ivTabTareasXRealizadasActivo.setVisibility(View.VISIBLE);
                            ivTabTareasXRealizadasInactivo.setVisibility(View.GONE);
                            ivTabTareasTareasActivo.setVisibility(View.GONE);
                            ivTabTareasTareasInactivo.setVisibility(View.VISIBLE);

                            tlTareas.getTabAt(0).setCustomView(null);
                            tlTareas.getTabAt(0).setCustomView(vTabTareasCalificadas);
                            tlTareas.getTabAt(1).setCustomView(null);
                            tlTareas.getTabAt(1).setCustomView(vTabTareasRealizadas);
                            tlTareas.getTabAt(2).setCustomView(null);
                            tlTareas.getTabAt(2).setCustomView(vTabTareasEntregar);
                            tlTareas.getTabAt(3).setCustomView(null);
                            tlTareas.getTabAt(3).setCustomView(vTabTareasXRealizadas);
                            tlTareas.getTabAt(4).setCustomView(null);
                            tlTareas.getTabAt(4).setCustomView(vTabTareasTodas);

                            tabView0.setBackgroundColor(Color.TRANSPARENT);
                            tabView1.setBackgroundColor(Color.TRANSPARENT);
                            tabView2.setBackgroundColor(Color.TRANSPARENT);
                            tabView3.setBackgroundResource(R.drawable.eduko_tab_tareas);
                            tabView4.setBackgroundColor(Color.TRANSPARENT);

                            actualizarTareasXRealizar();
                        }
                        break;

                        case 4: {
                            ivTabTareasCalificadasActivo.setVisibility(View.GONE);
                            ivTabTareasCalificadasInactivo.setVisibility(View.VISIBLE);
                            ivTabTareasRealizadasActivo.setVisibility(View.GONE);
                            ivTabTareasRealizadasInactivo.setVisibility(View.VISIBLE);
                            ivTabTareasEntregarActivo.setVisibility(View.GONE);
                            ivTabTareasEntregarInactivo.setVisibility(View.VISIBLE);
                            ivTabTareasXRealizadasActivo.setVisibility(View.GONE);
                            ivTabTareasXRealizadasInactivo.setVisibility(View.VISIBLE);
                            ivTabTareasTareasActivo.setVisibility(View.VISIBLE);
                            ivTabTareasTareasInactivo.setVisibility(View.GONE);

                            tlTareas.getTabAt(0).setCustomView(null);
                            tlTareas.getTabAt(0).setCustomView(vTabTareasCalificadas);
                            tlTareas.getTabAt(1).setCustomView(null);
                            tlTareas.getTabAt(1).setCustomView(vTabTareasRealizadas);
                            tlTareas.getTabAt(2).setCustomView(null);
                            tlTareas.getTabAt(2).setCustomView(vTabTareasEntregar);
                            tlTareas.getTabAt(3).setCustomView(null);
                            tlTareas.getTabAt(3).setCustomView(vTabTareasXRealizadas);
                            tlTareas.getTabAt(4).setCustomView(null);
                            tlTareas.getTabAt(4).setCustomView(vTabTareasTodas);

                            tabView0.setBackgroundColor(Color.TRANSPARENT);
                            tabView1.setBackgroundColor(Color.TRANSPARENT);
                            tabView2.setBackgroundColor(Color.TRANSPARENT);
                            tabView3.setBackgroundColor(Color.TRANSPARENT);
                            tabView4.setBackgroundResource(R.drawable.eduko_tab_tareas);

                            actualizarTareasTodas();
                        }
                        break;
                    }

                    vpTareas.setCurrentItem(tab.getPosition());
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {


                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {

                    vpTareas.setCurrentItem(tab.getPosition());
                }
            });

            tvAsignaturas.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showAsignaturas(v);
                }
            });
            ivFlechaAsignatura.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showAsignaturas(v);
                }
            });
        } catch (Exception e) {
            Toasty.error(getContext(), getString(R.string.error_app), Toast.LENGTH_SHORT).show();
            Log.e(Constants.TAG, e.getMessage());
        }

        return v;
    }


    @Override
    public void actualizarItemsTareas() {
        obtenerTareas();
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


    public void obtenerTareas() {
        try {
            mostrarCargando();
            final String token = globalAPP.sessionEDUAPP.getAccessToken(getContext());
            final String idStudent = globalAPP.sessionEDUAPP.getID(getContext());
            String filtro = "{\"include\":{\"homework\":\"subject\"},\"where\":{\"active\":true,\"studentId\":\"" + idStudent + "\"},\"order\":\"dateDeliver DESC\"}";

            Call<List<TareasModel>> call = servicesEDUKO.getTareas(token, filtro);
            call.enqueue(new Callback<List<TareasModel>>() {
                @Override
                public void onResponse(Call<List<TareasModel>> call, Response<List<TareasModel>> response) {
                    ocultarCargando(true);

                    switch (response.code()) {
                        case 200: {
                            if (response.isSuccessful()) {
                                globalAPP.tareasModels = ordenarTareas(response.body());

                                actualizarTareas();
                            } else {
                                globalAPP.tareasModels.clear();
                                actualizarTareas();
                            }
                        }
                        break;

                        case 401: {
                            globalAPP.tareasModels.clear();
                            actualizarTareas();
                            Toast.makeText(getContext(), getString(R.string.error_login_app), Toast.LENGTH_SHORT).show();
                        }
                        break;

                        default: {
                            globalAPP.tareasModels.clear();
                            actualizarTareas();
                            Toast.makeText(getContext(), getString(R.string.error_tareas), Toast.LENGTH_SHORT).show();
                        }
                        break;
                    }
                }

                @Override
                public void onFailure(Call<List<TareasModel>> call, Throwable throwable) {
                    ocultarCargando(true);
                    globalAPP.tareasModels.clear();
                    actualizarTareas();
                    Toast.makeText(getContext(), getString(R.string.error_tareas), Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            Toasty.error(getContext(), getString(R.string.error_app), Toast.LENGTH_SHORT).show();
            Log.e(Constants.TAG, e.getMessage());
        }
    }


    private List<TareasModel> ordenarTareas(List<TareasModel> tareas) {
        for (int i = 0; i < tareas.size(); i++) {
            for (int j = 1; j < tareas.size(); j++) {
                Date dateI = convertirFecha(tareas.get(i).getHomework().getDateDeliver());
                Date dateJ = convertirFecha(tareas.get(j).getHomework().getDateDeliver());

                if (dateI.getYear() <= dateJ.getYear() && dateI.getMonth() <= dateJ.getMonth() && dateI.getDay() <= dateJ.getDay())
                    if (dateI.getHours() <= dateJ.getHours() && dateI.getMinutes() <= dateJ.getMinutes() && dateI.getSeconds() <= dateJ.getSeconds()) {
                        TareasModel temp = tareas.get(i);
                        tareas.set(i, tareas.get(j));
                        tareas.set(j, temp);
                    }
            }
        }

        return tareas;
    }


    private Date convertirFecha(String fecha) {
        Date date = new Date();
        try {
            Moment momentOrdenar = new Moment(fecha, "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault());
            momentOrdenar.getCalendar().setTimeZone(TimeZone.getTimeZone("GMT-07:00"));
            SimpleDateFormat sdfOrdenar = new SimpleDateFormat("dd/MM/yyyy hh:mm aaa");
            sdfOrdenar.setTimeZone(TimeZone.getTimeZone("GMT-07:00"));
            date = momentOrdenar.getDate();
        } catch (Exception e) {
            Toast.makeText(getContext(), "Error procesando la fecha de la tarea", Toast.LENGTH_SHORT).show();
        }

        return date;
    }


    private void actualizarTareas() {
        switch (vpTareas.getCurrentItem()) {
            case 0: actualizarTareasCalificadas(); break;
            case 1: actualizarTareasRealizadas(); break;
            case 2: actualizarTareasEntregarManana(); break;
            case 3: actualizarTareasXRealizar(); break;
            case 4: actualizarTareasTodas(); break;
        }
    }


    public void actualizarTareasCalificadas() {
        tareasAdapter.tareasCalificadas.idSubject = idAsignatura;
        tareasAdapter.tareasCalificadas.updateTareas();
    }


    public void actualizarTareasRealizadas() {
        tareasAdapter.tareasRealizadas.idSubject = idAsignatura;
        tareasAdapter.tareasRealizadas.updateTareas();
    }


    public void actualizarTareasEntregarManana() {
        tareasAdapter.tareasEntregar.idSubject = idAsignatura;
        tareasAdapter.tareasEntregar.updateTareas();
    }


    public void actualizarTareasXRealizar() {
        tareasAdapter.tareasXRealizar.idSubject = idAsignatura;
        tareasAdapter.tareasXRealizar.updateTareas();
    }


    public void actualizarTareasTodas() {
        tareasAdapter.tareasTareas.idSubject = idAsignatura;
        tareasAdapter.tareasTareas.updateTareas();
    }


    private void showAsignaturas(final View view) {
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
                    if ((item.getItemId() - 1) > -1)
                        idAsignatura = globalAPP.subjectsModels.get(item.getItemId() - 1).getId();
                    else idAsignatura = "-1";

                    // ACTUALIZACION DE LOS TABS
                    switch (vpTareas.getCurrentItem()) {
                        case 0: {
                            tareasAdapter.tareasCalificadas.idSubject = idAsignatura;
                            tareasAdapter.tareasCalificadas.updateTareas();
                        }
                        break;

                        case 1: {
                            tareasAdapter.tareasRealizadas.idSubject = idAsignatura;
                            tareasAdapter.tareasRealizadas.updateTareas();
                        }
                        break;

                        case 2: {
                            tareasAdapter.tareasEntregar.idSubject = idAsignatura;
                            tareasAdapter.tareasEntregar.updateTareas();
                        }
                        break;

                        case 3: {
                            tareasAdapter.tareasXRealizar.idSubject = idAsignatura;
                            tareasAdapter.tareasXRealizar.updateTareas();
                        }
                        break;

                        case 4: {
                            tareasAdapter.tareasTareas.idSubject = idAsignatura;
                            tareasAdapter.tareasTareas.updateTareas();
                        }
                        break;
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

}
