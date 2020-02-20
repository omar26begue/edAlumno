package com.idoogroup.edalumno;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.google.gson.JsonObject;
import com.idoogroup.edalumno.Helpers.Constants;
import com.idoogroup.edalumno.Models.NotificacionesModel;
import com.idoogroup.edalumno.ViewPageTransformer.DepthPageTransformer;
import com.idoogroup.edalumno.ViewPager.ViewPagerNotificacionesAdapter;

import java.util.List;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificacionesActivity extends AppCompatActivity {

    // VARIABLES DE LA CLASE
    private TabLayout tlNotificaciones;
    private ViewPager vpNotificaciones;
    private ViewPagerNotificacionesAdapter mViewPagerAdapter;
    private TextView tvNotificaciones;
    private View vTabNotificacionesTareas, vTabNotificacionesEventos, vTabNotificacionesEvaluaciones;
    private ImageView ivTabNotificacionesTareasActivo, ivTabNotificacionesTareasInactivo, ivTabNotificacionesEventosActivo, ivTabNotificacionesEventosInactivo, ivTabNotificacionesEvaluacionesActivo, ivTabNotificacionesEvaluacionesInactivo;

    private IdooGroupApplication globalAPP;
    private WebServicesIdooGroup servicesEDUKO;
    private Dialog dialogLoading = null;

    private int tabVista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notificaciones);

        try {
            // VARIABLE GLOBAL
            globalAPP = (IdooGroupApplication) getApplication();
            servicesEDUKO = globalAPP.getService();

            // PARAMETROS DEL ACTIVITY
            tabVista = getIntent().getIntExtra("vista", 0);

            // PARSEO DE VARIABLES
            tvNotificaciones = (TextView) findViewById(R.id.tvNotificaciones);
            tlNotificaciones = (TabLayout) findViewById(R.id.tlNotficaciones);
            vpNotificaciones = (ViewPager) findViewById(R.id.vpNotificaciones);

            // ASIGNACIONES DE FUENTES
            tvNotificaciones.setTypeface(globalAPP.getFuenteAvenidHeavy(getAssets()));

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            LayoutInflater inflaterLoading = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflaterLoading.inflate(R.layout.dialog_loading, null);

            builder.setView(view);
            dialogLoading = builder.create();
            dialogLoading.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialogLoading.setCancelable(false);

            // TABS
            mViewPagerAdapter = new ViewPagerNotificacionesAdapter(getSupportFragmentManager());
            vpNotificaciones.setAdapter(mViewPagerAdapter);
            vpNotificaciones.setPageTransformer(true, new DepthPageTransformer());
            vpNotificaciones.setOffscreenPageLimit(4);
            tlNotificaciones.setupWithViewPager(vpNotificaciones);

            // ASIGNACION DE FUENTES
            tvNotificaciones.setTypeface(globalAPP.getFuenteAvenidHeavy(getAssets()));

            // ICONOS DE LOS TABS
            LayoutInflater inflater = getLayoutInflater();
            vTabNotificacionesTareas = inflater.inflate(R.layout.tab_menu_notificaciones_tareas, null);
            vTabNotificacionesEventos = inflater.inflate(R.layout.tab_menu_notificaciones_eventos, null);
            vTabNotificacionesEvaluaciones = inflater.inflate(R.layout.tab_menu_notificaciones_evaluaciones, null);

            ivTabNotificacionesTareasActivo = (ImageView) vTabNotificacionesTareas.findViewById(R.id.ivTabNotificacionesTareasActivo);
            ivTabNotificacionesTareasInactivo = (ImageView) vTabNotificacionesTareas.findViewById(R.id.ivTabNotificacionesTareasInactivo);
            ivTabNotificacionesEventosActivo = (ImageView) vTabNotificacionesEventos.findViewById(R.id.ivTabNotificacionesEventosActivo);
            ivTabNotificacionesEventosInactivo = (ImageView) vTabNotificacionesEventos.findViewById(R.id.ivTabNotificacionesEventosInactivo);
            ivTabNotificacionesEvaluacionesActivo = (ImageView) vTabNotificacionesEvaluaciones.findViewById(R.id.ivTabNotificacionesEvaluacionesActivo);
            ivTabNotificacionesEvaluacionesInactivo = (ImageView) vTabNotificacionesEvaluaciones.findViewById(R.id.ivTabNotificacionesEvaluacionesInactivo);

            ivTabNotificacionesTareasActivo.setVisibility(View.VISIBLE);
            ivTabNotificacionesTareasInactivo.setVisibility(View.GONE);
            ivTabNotificacionesEventosActivo.setVisibility(View.GONE);
            ivTabNotificacionesEventosInactivo.setVisibility(View.VISIBLE);
            ivTabNotificacionesEvaluacionesActivo.setVisibility(View.GONE);
            ivTabNotificacionesEvaluacionesInactivo.setVisibility(View.VISIBLE);

            tlNotificaciones.getTabAt(0).setCustomView(null);
            tlNotificaciones.getTabAt(0).setCustomView(vTabNotificacionesTareas);
            tlNotificaciones.getTabAt(1).setCustomView(null);
            tlNotificaciones.getTabAt(1).setCustomView(vTabNotificacionesEventos);
            tlNotificaciones.getTabAt(2).setCustomView(null);
            tlNotificaciones.getTabAt(2).setCustomView(vTabNotificacionesEvaluaciones);

            // ASIGNACION DE LOS TAB
            LinearLayout tabsContainer = (LinearLayout) tlNotificaciones.getChildAt(0);
            LinearLayout childLayout0 = (LinearLayout) tabsContainer.getChildAt(0);
            LinearLayout childLayout1 = (LinearLayout) tabsContainer.getChildAt(1);
            LinearLayout childLayout2 = (LinearLayout) tabsContainer.getChildAt(2);
            final LinearLayout tabView0 = (LinearLayout) childLayout0.getChildAt(0).getParent();
            final LinearLayout tabView1 = (LinearLayout) childLayout1.getChildAt(0).getParent();
            final LinearLayout tabView2 = (LinearLayout) childLayout2.getChildAt(0).getParent();
            tabView0.setBackgroundResource(R.drawable.eduko_tab_notificaciones);
            tabView1.setBackgroundColor(Color.TRANSPARENT);
            tabView2.setBackgroundColor(Color.TRANSPARENT);

            tlNotificaciones.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    switch (tab.getPosition()) {
                        case 0: {
                            resetNotificaciones("homework");

                            ivTabNotificacionesTareasActivo.setVisibility(View.VISIBLE);
                            ivTabNotificacionesTareasInactivo.setVisibility(View.GONE);
                            ivTabNotificacionesEventosActivo.setVisibility(View.GONE);
                            ivTabNotificacionesEventosInactivo.setVisibility(View.VISIBLE);
                            ivTabNotificacionesEvaluacionesActivo.setVisibility(View.GONE);
                            ivTabNotificacionesEvaluacionesInactivo.setVisibility(View.VISIBLE);

                            tlNotificaciones.getTabAt(0).setCustomView(null);
                            tlNotificaciones.getTabAt(0).setCustomView(vTabNotificacionesTareas);
                            tlNotificaciones.getTabAt(1).setCustomView(null);
                            tlNotificaciones.getTabAt(1).setCustomView(vTabNotificacionesEventos);
                            tlNotificaciones.getTabAt(2).setCustomView(null);
                            tlNotificaciones.getTabAt(2).setCustomView(vTabNotificacionesEvaluaciones);

                            tabView0.setBackgroundResource(R.drawable.eduko_tab_notificaciones);
                            tabView1.setBackgroundColor(Color.TRANSPARENT);
                            tabView2.setBackgroundColor(Color.TRANSPARENT);

                            updateNotificacionesTareas();
                        }
                        break;

                        case 1: {
                            resetNotificaciones("event");

                            ivTabNotificacionesTareasActivo.setVisibility(View.GONE);
                            ivTabNotificacionesTareasInactivo.setVisibility(View.VISIBLE);
                            ivTabNotificacionesEventosActivo.setVisibility(View.VISIBLE);
                            ivTabNotificacionesEventosInactivo.setVisibility(View.GONE);
                            ivTabNotificacionesEvaluacionesActivo.setVisibility(View.GONE);
                            ivTabNotificacionesEvaluacionesInactivo.setVisibility(View.VISIBLE);

                            tlNotificaciones.getTabAt(0).setCustomView(null);
                            tlNotificaciones.getTabAt(0).setCustomView(vTabNotificacionesTareas);
                            tlNotificaciones.getTabAt(1).setCustomView(null);
                            tlNotificaciones.getTabAt(1).setCustomView(vTabNotificacionesEventos);
                            tlNotificaciones.getTabAt(2).setCustomView(null);
                            tlNotificaciones.getTabAt(2).setCustomView(vTabNotificacionesEvaluaciones);

                            tabView0.setBackgroundColor(Color.TRANSPARENT);
                            tabView1.setBackgroundResource(R.drawable.eduko_tab_notificaciones);
                            tabView2.setBackgroundColor(Color.TRANSPARENT);

                            updateNotificaionesEventos();
                        }
                        break;
                        case 2: {
                            resetNotificaciones("evaluation");

                            ivTabNotificacionesTareasActivo.setVisibility(View.GONE);
                            ivTabNotificacionesTareasInactivo.setVisibility(View.VISIBLE);
                            ivTabNotificacionesEventosActivo.setVisibility(View.GONE);
                            ivTabNotificacionesEventosInactivo.setVisibility(View.VISIBLE);
                            ivTabNotificacionesEvaluacionesActivo.setVisibility(View.VISIBLE);
                            ivTabNotificacionesEvaluacionesInactivo.setVisibility(View.GONE);

                            tlNotificaciones.getTabAt(0).setCustomView(null);
                            tlNotificaciones.getTabAt(0).setCustomView(vTabNotificacionesTareas);
                            tlNotificaciones.getTabAt(1).setCustomView(null);
                            tlNotificaciones.getTabAt(1).setCustomView(vTabNotificacionesEventos);
                            tlNotificaciones.getTabAt(2).setCustomView(null);
                            tlNotificaciones.getTabAt(2).setCustomView(vTabNotificacionesEvaluaciones);

                            tabView0.setBackgroundColor(Color.TRANSPARENT);
                            tabView1.setBackgroundColor(Color.TRANSPARENT);
                            tabView2.setBackgroundResource(R.drawable.eduko_tab_notificaciones);

                            updateNotificacionesEvaluaciones();
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
            Toasty.error(this, getString(R.string.error_app), Toast.LENGTH_SHORT).show();
            Log.e(Constants.TAG, e.getMessage());
        }
    }


    @Override
    protected void onStart() {
        super.onStart();

        // PARAMETROS DEL ACTIVITY
        tabVista = getIntent().getIntExtra("vista", 0);

        obtenerNotificaciones();
    }


    /**
     * Muestra el icono para la espera de una transición
     */
    private void mostrarCargando() {
        try {
            dialogLoading.show();
        } catch (Exception e) {
            Toasty.error(this, getString(R.string.error_app), Toast.LENGTH_SHORT).show();
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
            Toasty.error(this, getString(R.string.error_app), Toast.LENGTH_SHORT).show();
            Log.e(Constants.TAG, e.getMessage());
        }
    }


    public void volverAtrasNotificaciones(View view) {
        finish();
    }


    private void obtenerNotificaciones() {
        try {
            mostrarCargando();

            final String token = globalAPP.sessionEDUAPP.getAccessToken(NotificacionesActivity.this);
            String idAccount = globalAPP.sessionEDUAPP.getAccountID(NotificacionesActivity.this);
            String filtro = "{\"where\":{\"accountId\":\"" + idAccount + "\"},\"order\":\"created desc\"}";

            Call<List<NotificacionesModel>> call = servicesEDUKO.getNotificaciones(token, filtro);
            call.enqueue(new Callback<List<NotificacionesModel>>() {
                @Override
                public void onResponse(Call<List<NotificacionesModel>> call, Response<List<NotificacionesModel>> response) {
                    ocultarCargando(true);

                    switch (response.code()) {
                        case 200: {
                            if (response.isSuccessful()) {
                                Log.i("EDUKO-ALUMNO-NOTIF", "WS Listar notificaciones -> " + response.body().size());
                                globalAPP.notificacionesModels = response.body();
                                actualizarNotificaciones();
                            }
                        }
                        break;

                        default: {
                            Toasty.error(NotificacionesActivity.this, getString(R.string.error_app), Toasty.LENGTH_SHORT).show();
                        }
                        break;
                    }
                }

                @Override
                public void onFailure(Call<List<NotificacionesModel>> call, Throwable throwable) {
                    ocultarCargando(true);
                    Log.i("EDUKO-ALUMNO-NOTIF", "WS Listar notificaciones -> failure");
                    Toasty.error(NotificacionesActivity.this, getString(R.string.error_app), Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            Toasty.error(this, getString(R.string.error_app), Toast.LENGTH_SHORT).show();
            Log.e(Constants.TAG, e.getMessage());
        }
    }


    private void actualizarNotificaciones() {
        try {
            vpNotificaciones.setCurrentItem(tabVista);

            LinearLayout tabsContainer = (LinearLayout) tlNotificaciones.getChildAt(0);
            LinearLayout childLayout0 = (LinearLayout) tabsContainer.getChildAt(0);
            LinearLayout childLayout1 = (LinearLayout) tabsContainer.getChildAt(1);
            LinearLayout childLayout2 = (LinearLayout) tabsContainer.getChildAt(2);
            LinearLayout tabView0 = (LinearLayout) childLayout0.getChildAt(0).getParent();
            LinearLayout tabView1 = (LinearLayout) childLayout1.getChildAt(0).getParent();
            LinearLayout tabView2 = (LinearLayout) childLayout2.getChildAt(0).getParent();

            switch (tabVista) {
                case 0: {
                    //resetNotificaciones("homework");

                    ivTabNotificacionesTareasActivo.setVisibility(View.VISIBLE);
                    ivTabNotificacionesTareasInactivo.setVisibility(View.GONE);
                    ivTabNotificacionesEventosActivo.setVisibility(View.GONE);
                    ivTabNotificacionesEventosInactivo.setVisibility(View.VISIBLE);
                    ivTabNotificacionesEvaluacionesActivo.setVisibility(View.GONE);
                    ivTabNotificacionesEvaluacionesInactivo.setVisibility(View.VISIBLE);

                    tlNotificaciones.getTabAt(0).setCustomView(null);
                    tlNotificaciones.getTabAt(0).setCustomView(vTabNotificacionesTareas);
                    tlNotificaciones.getTabAt(1).setCustomView(null);
                    tlNotificaciones.getTabAt(1).setCustomView(vTabNotificacionesEventos);
                    tlNotificaciones.getTabAt(2).setCustomView(null);
                    tlNotificaciones.getTabAt(2).setCustomView(vTabNotificacionesEvaluaciones);

                    tabView0.setBackgroundResource(R.drawable.eduko_tab_notificaciones);
                    tabView1.setBackgroundColor(Color.TRANSPARENT);
                    tabView2.setBackgroundColor(Color.TRANSPARENT);
                }
                break;

                case 1: {
                    //resetNotificaciones("event");

                    ivTabNotificacionesTareasActivo.setVisibility(View.GONE);
                    ivTabNotificacionesTareasInactivo.setVisibility(View.VISIBLE);
                    ivTabNotificacionesEventosActivo.setVisibility(View.VISIBLE);
                    ivTabNotificacionesEventosInactivo.setVisibility(View.GONE);
                    ivTabNotificacionesEvaluacionesActivo.setVisibility(View.GONE);
                    ivTabNotificacionesEvaluacionesInactivo.setVisibility(View.VISIBLE);

                    tlNotificaciones.getTabAt(0).setCustomView(null);
                    tlNotificaciones.getTabAt(0).setCustomView(vTabNotificacionesTareas);
                    tlNotificaciones.getTabAt(1).setCustomView(null);
                    tlNotificaciones.getTabAt(1).setCustomView(vTabNotificacionesEventos);
                    tlNotificaciones.getTabAt(2).setCustomView(null);
                    tlNotificaciones.getTabAt(2).setCustomView(vTabNotificacionesEvaluaciones);

                    tabView0.setBackgroundColor(Color.TRANSPARENT);
                    tabView1.setBackgroundResource(R.drawable.eduko_tab_notificaciones);
                    tabView2.setBackgroundColor(Color.TRANSPARENT);
                }
                break;

                case 2: {
                    //resetNotificaciones("evaluation");

                    ivTabNotificacionesTareasActivo.setVisibility(View.GONE);
                    ivTabNotificacionesTareasInactivo.setVisibility(View.VISIBLE);
                    ivTabNotificacionesEventosActivo.setVisibility(View.GONE);
                    ivTabNotificacionesEventosInactivo.setVisibility(View.VISIBLE);
                    ivTabNotificacionesEvaluacionesActivo.setVisibility(View.VISIBLE);
                    ivTabNotificacionesEvaluacionesInactivo.setVisibility(View.GONE);

                    tlNotificaciones.getTabAt(0).setCustomView(null);
                    tlNotificaciones.getTabAt(0).setCustomView(vTabNotificacionesTareas);
                    tlNotificaciones.getTabAt(1).setCustomView(null);
                    tlNotificaciones.getTabAt(1).setCustomView(vTabNotificacionesEventos);
                    tlNotificaciones.getTabAt(2).setCustomView(null);
                    tlNotificaciones.getTabAt(2).setCustomView(vTabNotificacionesEvaluaciones);

                    tabView0.setBackgroundColor(Color.TRANSPARENT);
                    tabView1.setBackgroundColor(Color.TRANSPARENT);
                    tabView2.setBackgroundResource(R.drawable.eduko_tab_notificaciones);
                }
                break;
            }
        } catch (Exception e) {
            Toasty.error(this, getString(R.string.error_app), Toast.LENGTH_SHORT).show();
            Log.e(Constants.TAG, e.getMessage());
        }
    }


    private void resetNotificaciones(final String tipo) {
        try {
            String token = globalAPP.sessionEDUAPP.getAccessToken(NotificacionesActivity.this);
            String accointID = globalAPP.sessionEDUAPP.getAccountID(NotificacionesActivity.this);
            NotificacionesModel notificacionesModel = new NotificacionesModel();
            notificacionesModel.setLeido(true);

            Call<JsonObject> call = servicesEDUKO.resetNotificaciones(accointID, tipo, token, notificacionesModel);
            call.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    switch (response.code()) {
                        case 200: {
                            if (response.isSuccessful()) {
                                switch (tipo) {
                                    case "homework": globalAPP.notifTareas = 0; break;
                                    case "event": globalAPP.notifEvent = 0; break;
                                    case "evaluation": globalAPP.notifEvaluaciones = 0; break;
                                }
                            }
                        }
                        break;
                    }
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable throwable) {
                    Log.e("E_ALUMNO_NOTIF", throwable.getMessage());
                }
            });
        } catch (Exception e) {
            Toasty.error(this, getString(R.string.error_app), Toast.LENGTH_SHORT).show();
            Log.e(Constants.TAG, e.getMessage());
        }
    }


    private void updateNotificacionesTareas() {
        mViewPagerAdapter.notificacionesTareas.updateNotificaciones();
    }


    private void updateNotificaionesEventos() {
        mViewPagerAdapter.notificacionesEventos.updateNotificaciones();
    }


    private void updateNotificacionesEvaluaciones() {
        mViewPagerAdapter.notificacionesEvaluaciones.updateNotificaciones();
    }

}
