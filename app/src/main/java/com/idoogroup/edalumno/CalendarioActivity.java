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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.idoogroup.edalumno.Helpers.Constants;
import com.idoogroup.edalumno.Models.CalendarizacionModel;
import com.idoogroup.edalumno.ViewPager.ViewPagerAdapterCalendario;

import java.util.List;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CalendarioActivity extends AppCompatActivity {

    // VARIABLES DE A CLASE
    private TextView tvHorario;
    private ViewPager vpCalendario;
    private ViewPagerAdapterCalendario mViewPagerAdapter;
    private TabLayout tlCalendario;

    private IdooGroupApplication globalAPP;
    private WebServicesIdooGroup servicesEDUKO;
    private Dialog dialogLoading = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendario);

        try {
            // VARIABLE GLOBAL
            globalAPP = (IdooGroupApplication) getApplication();
            servicesEDUKO = globalAPP.getService();

            // ASIGNACION DE VARIABLES
            tvHorario = (TextView) findViewById(R.id.tvHorario);
            vpCalendario = (ViewPager) findViewById(R.id.vpCalendario);
            tlCalendario = (TabLayout) findViewById(R.id.tlCalendario);

            // ASIGNACIONES DE FUENTES
            tvHorario.setTypeface(globalAPP.getFuenteAvenidHeavy(getAssets()));

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            LayoutInflater inflaterLoading = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflaterLoading.inflate(R.layout.dialog_loading, null);

            builder.setView(view);
            dialogLoading = builder.create();
            dialogLoading.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialogLoading.setCancelable(false);

            // ASIGNACION TABS
            mViewPagerAdapter = new ViewPagerAdapterCalendario(getSupportFragmentManager());
            vpCalendario.setAdapter(mViewPagerAdapter);
            tlCalendario.setupWithViewPager(vpCalendario);
            vpCalendario.setOffscreenPageLimit(5);

            // ASIGNACION DE LOS TAB
            LinearLayout tabsContainer = (LinearLayout) tlCalendario.getChildAt(0);

            LinearLayout childLayout0 = (LinearLayout) tabsContainer.getChildAt(0);
            LinearLayout childLayout1 = (LinearLayout) tabsContainer.getChildAt(1);
            LinearLayout childLayout2 = (LinearLayout) tabsContainer.getChildAt(2);
            LinearLayout childLayout3 = (LinearLayout) tabsContainer.getChildAt(3);
            LinearLayout childLayout4 = (LinearLayout) tabsContainer.getChildAt(4);

            LinearLayout tabView0 = (LinearLayout) childLayout0.getChildAt(0).getParent();
            LinearLayout tabView1 = (LinearLayout) childLayout1.getChildAt(0).getParent();
            LinearLayout tabView2 = (LinearLayout) childLayout2.getChildAt(0).getParent();
            LinearLayout tabView3 = (LinearLayout) childLayout3.getChildAt(0).getParent();
            LinearLayout tabView4 = (LinearLayout) childLayout4.getChildAt(0).getParent();

            tabView0.setBackgroundResource(R.drawable.prueba);
            tabView1.setBackgroundColor(Color.TRANSPARENT);
            tabView2.setBackgroundColor(Color.TRANSPARENT);
            tabView3.setBackgroundColor(Color.TRANSPARENT);
            tabView4.setBackgroundColor(Color.TRANSPARENT);


            // TAB SELECCIONADO
            tlCalendario.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {

                    LinearLayout tabsContainer = (LinearLayout) tlCalendario.getChildAt(0);

                    LinearLayout childLayout0 = (LinearLayout) tabsContainer.getChildAt(0);
                    LinearLayout childLayout1 = (LinearLayout) tabsContainer.getChildAt(1);
                    LinearLayout childLayout2 = (LinearLayout) tabsContainer.getChildAt(2);
                    LinearLayout childLayout3 = (LinearLayout) tabsContainer.getChildAt(3);
                    LinearLayout childLayout4 = (LinearLayout) tabsContainer.getChildAt(4);

                    LinearLayout tabView0 = (LinearLayout) childLayout0.getChildAt(0).getParent();
                    LinearLayout tabView1 = (LinearLayout) childLayout1.getChildAt(0).getParent();
                    LinearLayout tabView2 = (LinearLayout) childLayout2.getChildAt(0).getParent();
                    LinearLayout tabView3 = (LinearLayout) childLayout3.getChildAt(0).getParent();
                    LinearLayout tabView4 = (LinearLayout) childLayout4.getChildAt(0).getParent();

                    switch (tab.getPosition()) {
                        case 0: {
                            tabView0.setBackgroundResource(R.drawable.prueba);
                            tabView1.setBackgroundColor(Color.TRANSPARENT);
                            tabView2.setBackgroundColor(Color.TRANSPARENT);
                            tabView3.setBackgroundColor(Color.TRANSPARENT);
                            tabView4.setBackgroundColor(Color.TRANSPARENT);

                            actualizarLunes();
                        }
                        break;

                        case 1: {
                            tabView0.setBackgroundColor(Color.TRANSPARENT);
                            tabView1.setBackgroundResource(R.drawable.prueba);
                            tabView2.setBackgroundColor(Color.TRANSPARENT);
                            tabView3.setBackgroundColor(Color.TRANSPARENT);
                            tabView4.setBackgroundColor(Color.TRANSPARENT);

                            actualizarMartes();
                        }
                        break;

                        case 2: {
                            tabView0.setBackgroundColor(Color.TRANSPARENT);
                            tabView1.setBackgroundColor(Color.TRANSPARENT);
                            tabView2.setBackgroundResource(R.drawable.prueba);
                            tabView3.setBackgroundColor(Color.TRANSPARENT);
                            tabView4.setBackgroundColor(Color.TRANSPARENT);

                            actualizarMiercoles();
                        }
                        break;

                        case 3: {
                            tabView0.setBackgroundColor(Color.TRANSPARENT);
                            tabView1.setBackgroundColor(Color.TRANSPARENT);
                            tabView2.setBackgroundColor(Color.TRANSPARENT);
                            tabView3.setBackgroundResource(R.drawable.prueba);
                            tabView4.setBackgroundColor(Color.TRANSPARENT);

                            actualizarJueves();
                        }
                        break;

                        case 4: {
                            tabView0.setBackgroundColor(Color.TRANSPARENT);
                            tabView1.setBackgroundColor(Color.TRANSPARENT);
                            tabView2.setBackgroundColor(Color.TRANSPARENT);
                            tabView3.setBackgroundColor(Color.TRANSPARENT);
                            tabView4.setBackgroundResource(R.drawable.prueba);

                            actualizarViernes();
                        }
                    }

                    //vpCalendario.setCurrentItem(tab.getPosition());
                    //vpCalendario.refreshDrawableState();
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {

                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {

                }
            });

            // ASIGNACION DE FUENTES
            tvHorario.setTypeface(globalAPP.getFuenteAvenidHeavy(getAssets()));
        } catch (Exception e) {
            Toasty.error(this, getString(R.string.error_app), Toast.LENGTH_SHORT).show();
            Log.e(Constants.TAG, e.getMessage());
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        obtenerCalendarizacion();
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


    public void volverAtrasCalendario(View view) {
        finish();
    }


    private void actualizarLunes() {
        mViewPagerAdapter.lunesFragment.updateHorario();
    }


    private void actualizarMartes() {
        mViewPagerAdapter.martesFragment.updateHorario();
    }


    private void actualizarMiercoles() {
        mViewPagerAdapter.miercolesFragment.updateHorario();
    }


    private void actualizarJueves() {
        mViewPagerAdapter.juevesFragment.updateHorario();
    }


    private void actualizarViernes() {
        mViewPagerAdapter.viernesFragment.updateHorario();
    }


    private void actualizarCalendario() {
        switch (vpCalendario.getCurrentItem()) {
            case 0: actualizarLunes(); break;
            case 1: actualizarMartes(); break;
            case 2: actualizarMiercoles(); break;
            case 3: actualizarJueves(); break;
            case 4: actualizarViernes(); break;
        }
    }


    private void obtenerCalendarizacion() {
        try {
            mostrarCargando();

            final String token = globalAPP.sessionEDUAPP.getAccessToken(CalendarioActivity.this);
            final String idStudent = globalAPP.sessionEDUAPP.getID(CalendarioActivity.this);
            String filtro = "{\"include\":[\"teacher\",\"diaSemana\",\"nomTurno\",\"subject\"]}";

            Call<List<CalendarizacionModel>> call = servicesEDUKO.getCalendarizacion(idStudent, token, filtro);
            call.enqueue(new Callback<List<CalendarizacionModel>>() {
                @Override
                public void onResponse(Call<List<CalendarizacionModel>> call, Response<List<CalendarizacionModel>> response) {
                    ocultarCargando(true);

                    switch (response.code()) {
                        case 200: {
                            if (response.isSuccessful()) {

                                globalAPP.calendarizacionModels = response.body();
                                actualizarCalendario();
                            } else {
                                globalAPP.calendarizacionModels.clear();
                                actualizarCalendario();
                            }
                        }
                        break;

                        case 401: {
                            Toasty.error(CalendarioActivity.this, getString(R.string.error_login_app), Toast.LENGTH_SHORT).show();
                            globalAPP.calendarizacionModels.clear();
                            actualizarCalendario();
                        }

                        default: {
                            Toasty.error(CalendarioActivity.this, getString(R.string.error_calendario), Toast.LENGTH_SHORT).show();
                            globalAPP.calendarizacionModels.clear();
                            actualizarCalendario();
                            Log.e("E_ALUMNO_CALENDARIO", response.message());
                        }
                    }
                }

                @Override
                public void onFailure(Call<List<CalendarizacionModel>> call, Throwable throwable) {
                    Toasty.error(CalendarioActivity.this, getString(R.string.error_calendario), Toast.LENGTH_SHORT).show();
                    globalAPP.calendarizacionModels.clear();
                    actualizarCalendario();
                    Log.e("E_ALUMNO_CALENDARIO", throwable.getMessage());
                }
            });
        } catch (Exception e) {
            Toasty.error(this, getString(R.string.error_app), Toast.LENGTH_SHORT).show();
            Log.e(Constants.TAG, e.getMessage());
        }
    }
}
