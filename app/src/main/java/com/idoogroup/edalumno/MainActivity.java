package com.idoogroup.edalumno;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.JsonObject;
import com.idoogroup.edalumno.Helpers.Connectivity;
import com.idoogroup.edalumno.Helpers.Constants;
import com.idoogroup.edalumno.Models.FrecuenciaModel;
import com.idoogroup.edalumno.Models.NotificacionesModel;
import com.idoogroup.edalumno.Models.StudentsModel;
import com.idoogroup.edalumno.Models.SubjectsModel;
import com.idoogroup.edalumno.ViewPageTransformer.ZoomOutPageTransformer;
import com.idoogroup.edalumno.ViewPager.ViewPagerPrincipalAdapter;

import java.security.spec.ECField;
import java.util.List;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {

    // VARIABLES DE LA CLASE
    private DrawerLayout dlPrincipal;
    private NavigationView nvPrincipal;
    private LinearLayout llMain;
    private ViewPager vpPrincipal;
    private TabLayout tlPrincipal;
    private TextView tvMenuPerfil, tvMenuCalificarApp, tvCompartir, tvInformacionEscuela, tvServiciosProductos,
            tvPuntosPadre, tvRedesSociales, tvSalir, tvNombre, tvPerfil, tvPhone, tvEmail, tvUltimoAcceso,
            tvFecha, tvCambiarContrasena, tvNotificacionesTarea, tvNotificacionesEventos,
            tvNotificacionesReconocimientos, tvNotificacionesEvaluaciones;
    private ImageView ivBackAClaro, ivBackMOscuro, ivBackAVerde, ivBackAMaron, ivBackARosado, ivMenu;
    private ImageView ivBackAOscuro, ivCalendario, ivImagenTarea, ivImagenCalificacion, ivImagenEvaluaciones;
    private ImageView ivImagenEventos, ivImagenReconocimientos, ivTabTareasActivo, ivTabTareasInactivo,
            ivHeader;
    private ImageView ivTabCalificacionesActivo, ivTabEvaluacionesActivo, ivTabEventosActivo, ivTabReconocimientosActivo;
    private LinearLayout llTabMenuTareas, llTabMenuCalificaciones, llTabMenuEvaluaciones, llTabMenuEventos, llTabMenuReconocimientos;
    public ViewPagerPrincipalAdapter mViewPagerPrincipalAdapter;
    private View vTabTareas, vTabCalificaciones, vTabEvaluaciones, vTabEventos, vTabReconocimientos;

    private IdooGroupApplication globalAPP;
    private WebServicesIdooGroup servicesEDUKO;
    private Dialog dialogLoading = null;

    private boolean cargado = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            // VARIABLE GLOBAL
            globalAPP = (IdooGroupApplication) getApplication();
            servicesEDUKO = globalAPP.getService();

            // ASIGNANDO VARIABLES
            dlPrincipal = (DrawerLayout) findViewById(R.id.dlPrincipal);
            nvPrincipal = (NavigationView) findViewById(R.id.nvPrincipal);
            llMain = (LinearLayout) findViewById(R.id.llMain);
            vpPrincipal = (ViewPager) findViewById(R.id.vpPrincipal);
            tlPrincipal = (TabLayout) findViewById(R.id.tlPrincipal);
            tvPuntosPadre = (TextView) nvPrincipal.getHeaderView(0).findViewById(R.id.tvPuntosPadre);
            ivBackAClaro = (ImageView) findViewById(R.id.ivBackAClaro);
            ivBackMOscuro = (ImageView) findViewById(R.id.ivBackMOscuro);
            ivBackAVerde = (ImageView) findViewById(R.id.ivBackAVerde);
            ivBackAMaron = (ImageView) findViewById(R.id.ivBackAMaron);
            ivBackARosado = (ImageView) findViewById(R.id.ivBackARosado);
            ivMenu = (ImageView) findViewById(R.id.ivMenu);
            ivBackAOscuro = (ImageView) findViewById(R.id.ivBackAOscuro);
            ivCalendario = (ImageView) findViewById(R.id.ivCalendario);
            ivHeader = (ImageView) nvPrincipal.getHeaderView(0).findViewById(R.id.ivHeader);

            // ASIGNACIONES DE FUENTES


            mViewPagerPrincipalAdapter = new ViewPagerPrincipalAdapter(getSupportFragmentManager());
            vpPrincipal.setOffscreenPageLimit(5);
            vpPrincipal.setAdapter(mViewPagerPrincipalAdapter);
            vpPrincipal.setCurrentItem(0);
            vpPrincipal.setPageTransformer(true, new ZoomOutPageTransformer());
            tlPrincipal.setupWithViewPager(vpPrincipal);


            // ICONOS DE LOS TABS
            final LayoutInflater inflater = getLayoutInflater();
            tlPrincipal.setBackgroundResource(R.drawable.eduko_main_tab_tareas);
            vTabTareas = inflater.inflate(R.layout.tab_menu_tareas, null);
            vTabCalificaciones = inflater.inflate(R.layout.tab_menu_calificaciones, null);
            vTabEvaluaciones = inflater.inflate(R.layout.tab_menu_evaluaciones, null);
            vTabEventos = inflater.inflate(R.layout.tab_menu_eventos, null);
            vTabReconocimientos = inflater.inflate(R.layout.tab_menu_reconocimientos, null);

            ivImagenTarea = (ImageView) vTabTareas.findViewById(R.id.ivImagenTarea);
            ivImagenCalificacion = (ImageView) vTabCalificaciones.findViewById(R.id.ivImagenCalificacion);
            ivImagenEvaluaciones = (ImageView) vTabEvaluaciones.findViewById(R.id.ivImagenEvaluaciones);
            ivImagenEventos = (ImageView) vTabEventos.findViewById(R.id.ivImagenEventos);
            ivImagenReconocimientos = (ImageView) vTabReconocimientos.findViewById(R.id.ivImagenReconocimientos);

            ivTabTareasActivo = (ImageView) vTabTareas.findViewById(R.id.ivTabTareasActivo);
            ivTabTareasInactivo = (ImageView) vTabTareas.findViewById(R.id.ivTabTareasInactivo);
            ivTabCalificacionesActivo = (ImageView) vTabCalificaciones.findViewById(R.id.ivTabCalificacionesActivo);
            final ImageView ivTabCalificacionesInactivo = (ImageView) vTabCalificaciones.findViewById(R.id.ivTabCalificacionesInactivo);
            ivTabEvaluacionesActivo = (ImageView) vTabEvaluaciones.findViewById(R.id.ivTabEvaluacionesActivo);
            final ImageView ivTabEvaluacionesInactivo = (ImageView) vTabEvaluaciones.findViewById(R.id.ivTabEvaluacionesInactivo);
            ivTabEventosActivo = (ImageView) vTabEventos.findViewById(R.id.ivTabEventosActivo);
            final ImageView ivTabEventosInactivo = (ImageView) vTabEventos.findViewById(R.id.ivTabEventosInactivo);
            ivTabReconocimientosActivo = (ImageView) vTabReconocimientos.findViewById(R.id.ivTabReconocimientosActivo);
            final ImageView ivTabReconocimientosInactivo = (ImageView) vTabReconocimientos.findViewById(R.id.ivTabReconocimientosInactivo);

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            LayoutInflater inflaterLoading = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflaterLoading.inflate(R.layout.dialog_loading, null);

            builder.setView(view);
            dialogLoading = builder.create();
            dialogLoading.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialogLoading.setCancelable(false);

            ivTabTareasActivo.setVisibility(View.VISIBLE);
            ivTabTareasInactivo.setVisibility(View.GONE);
            ivTabCalificacionesActivo.setVisibility(View.GONE);
            ivTabCalificacionesInactivo.setVisibility(View.VISIBLE);
            ivTabEvaluacionesActivo.setVisibility(View.GONE);
            ivTabEvaluacionesInactivo.setVisibility(View.VISIBLE);
            ivTabEventosActivo.setVisibility(View.GONE);
            ivTabEventosInactivo.setVisibility(View.VISIBLE);
            ivTabReconocimientosActivo.setVisibility(View.GONE);
            ivTabReconocimientosInactivo.setVisibility(View.VISIBLE);


            //  NOTIFICACIONES TAREAS
            tvNotificacionesTarea = (TextView) vTabTareas.findViewById(R.id.tvNotificacionesTarea);
            tvNotificacionesTarea.setTypeface(globalAPP.getFuenteAvenidRoman(getAssets()));
            tvNotificacionesTarea.setText(String.valueOf(globalAPP.notifTareas));
            llTabMenuTareas = (LinearLayout) vTabTareas.findViewById(R.id.llTabMenuTareas);
            llTabMenuTareas.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clicNotificaciones(0);
                }
            });
            ivTabTareasActivo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clicNotificaciones(0);
                }
            });

            // NOTIFICACIONES CALIFICACIONES
            llTabMenuCalificaciones = (LinearLayout) vTabCalificaciones.findViewById(R.id.llTabMenuCalificaciones);
            final TextView tvNotificacionesCalificacion = (TextView) vTabCalificaciones.findViewById(R.id.tvNotificacionesCalificacion);
            tvNotificacionesCalificacion.setTypeface(globalAPP.getFuenteAvenidBlack(getAssets()));

            // NOTIFICACIONES EVALUACIONES
            tvNotificacionesEvaluaciones = (TextView) vTabEvaluaciones.findViewById(R.id.tvNotificacionesEvaluaciones);
            tvNotificacionesEvaluaciones.setTypeface(globalAPP.getFuenteAvenidRoman(getAssets()));
            tvNotificacionesEvaluaciones.setText(String.valueOf(globalAPP.notifEvaluaciones));
            llTabMenuEvaluaciones = (LinearLayout) vTabEvaluaciones.findViewById(R.id.llTabMenuEvaluaciones);
            final TextView tvNotificacionesEvaluaciones = (TextView) vTabEvaluaciones.findViewById(R.id.tvNotificacionesEvaluaciones);
            tvNotificacionesEvaluaciones.setTypeface(globalAPP.getFuenteAvenidBlack(getAssets()));
            llTabMenuEvaluaciones.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clicNotificaciones(2);
                }
            });
            ivTabEvaluacionesActivo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clicNotificaciones(2);
                }
            });

            // NOTIFICACIONES EVENTOS
            llTabMenuEventos = (LinearLayout) vTabEventos.findViewById(R.id.llTabMenuEventos);
            tvNotificacionesEventos = (TextView) vTabEventos.findViewById(R.id.tvNotificacionesEventos);
            tvNotificacionesEventos.setTypeface(globalAPP.getFuenteAvenidBlack(getAssets()));
            llTabMenuEventos.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clicNotificaciones(1);
                }
            });
            ivTabEventosActivo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clicNotificaciones(1);
                }
            });

            // NOTIFICACIONES RECONOCIMIENTOS
            llTabMenuReconocimientos = (LinearLayout) vTabReconocimientos.findViewById(R.id.llTabMenuReconocimientos);
            tvNotificacionesReconocimientos = (TextView) vTabReconocimientos.findViewById(R.id.tvNotificacionesReconocimientos);
            tvNotificacionesReconocimientos.setTypeface(globalAPP.getFuenteAvenidBlack(getAssets()));
            tvNotificacionesReconocimientos.setText("1");

            actualizarTabMain();

            mostrarIconosTareas();

            tlPrincipal.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    switch (tab.getPosition()) {
                        case 0:
                            ivTabTareasActivo.setVisibility(View.VISIBLE);
                            ivTabTareasInactivo.setVisibility(View.GONE);
                            ivTabCalificacionesActivo.setVisibility(View.GONE);
                            ivTabCalificacionesInactivo.setVisibility(View.VISIBLE);
                            ivTabEvaluacionesActivo.setVisibility(View.GONE);
                            ivTabEvaluacionesInactivo.setVisibility(View.VISIBLE);
                            ivTabEventosActivo.setVisibility(View.GONE);
                            ivTabEventosInactivo.setVisibility(View.VISIBLE);
                            ivTabReconocimientosActivo.setVisibility(View.GONE);
                            ivTabReconocimientosInactivo.setVisibility(View.VISIBLE);
                            tlPrincipal.setBackgroundResource(R.drawable.eduko_main_tab_tareas);

                            tlPrincipal.setBackgroundResource(R.drawable.eduko_main_tab_tareas);

                            mostrarIconosTareas();

                            mViewPagerPrincipalAdapter.tareasFragment.obtenerTareas();
                            break;

                        case 1:
                            ivTabTareasActivo.setVisibility(View.GONE);
                            ivTabTareasInactivo.setVisibility(View.VISIBLE);
                            ivTabCalificacionesActivo.setVisibility(View.VISIBLE);
                            ivTabCalificacionesInactivo.setVisibility(View.GONE);
                            ivTabEvaluacionesActivo.setVisibility(View.GONE);
                            ivTabEvaluacionesInactivo.setVisibility(View.VISIBLE);
                            ivTabEventosActivo.setVisibility(View.GONE);
                            ivTabEventosInactivo.setVisibility(View.VISIBLE);
                            ivTabReconocimientosActivo.setVisibility(View.GONE);
                            ivTabReconocimientosInactivo.setVisibility(View.VISIBLE);

                            tlPrincipal.setBackgroundResource(R.drawable.eduko_main_tab_calificaciones);

                            mostrarIconosCalificaciones();

                            mViewPagerPrincipalAdapter.calificacionesFragment.obtenerCalificaciones();
                            break;

                        case 2:
                            ivTabTareasActivo.setVisibility(View.GONE);
                            ivTabTareasInactivo.setVisibility(View.VISIBLE);
                            ivTabCalificacionesActivo.setVisibility(View.GONE);
                            ivTabCalificacionesInactivo.setVisibility(View.VISIBLE);
                            ivTabEvaluacionesActivo.setVisibility(View.VISIBLE);
                            ivTabEvaluacionesInactivo.setVisibility(View.GONE);
                            ivTabEventosActivo.setVisibility(View.GONE);
                            ivTabEventosInactivo.setVisibility(View.VISIBLE);
                            ivTabReconocimientosActivo.setVisibility(View.GONE);
                            ivTabReconocimientosInactivo.setVisibility(View.VISIBLE);

                            tlPrincipal.setBackgroundResource(R.drawable.eduko_main_tab_evaluaciones);

                            mostrarIconosEvaluaciones();

                            mViewPagerPrincipalAdapter.evaluacionesFragment.obtenerEvaluaciones();
                            break;

                        case 3:
                            ivTabTareasActivo.setVisibility(View.GONE);
                            ivTabTareasInactivo.setVisibility(View.VISIBLE);
                            ivTabCalificacionesActivo.setVisibility(View.GONE);
                            ivTabCalificacionesInactivo.setVisibility(View.VISIBLE);
                            ivTabEvaluacionesActivo.setVisibility(View.GONE);
                            ivTabEvaluacionesInactivo.setVisibility(View.VISIBLE);
                            ivTabEventosActivo.setVisibility(View.VISIBLE);
                            ivTabEventosInactivo.setVisibility(View.GONE);
                            ivTabReconocimientosActivo.setVisibility(View.GONE);
                            ivTabReconocimientosInactivo.setVisibility(View.VISIBLE);

                            tlPrincipal.setBackgroundResource(R.drawable.eduko_main_tab_eventos);

                            mostrarIconosEventos();

                            mViewPagerPrincipalAdapter.eventosFragment.obtenerEventos();
                            break;

                        case 4:
                            ivTabTareasActivo.setVisibility(View.GONE);
                            ivTabTareasInactivo.setVisibility(View.VISIBLE);
                            ivTabCalificacionesActivo.setVisibility(View.GONE);
                            ivTabCalificacionesInactivo.setVisibility(View.VISIBLE);
                            ivTabEvaluacionesActivo.setVisibility(View.GONE);
                            ivTabEvaluacionesInactivo.setVisibility(View.VISIBLE);
                            ivTabEventosActivo.setVisibility(View.GONE);
                            ivTabEventosInactivo.setVisibility(View.VISIBLE);
                            ivTabReconocimientosActivo.setVisibility(View.VISIBLE);
                            ivTabReconocimientosInactivo.setVisibility(View.GONE);

                            tlPrincipal.setBackgroundResource(R.drawable.eduko_main_tab_reconocimientos);

                            mostrarIconosReconocimientos();

                            mViewPagerPrincipalAdapter.reconocimientosFragment.obtenerReconocimientos();
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

            // carga la foto del perfil del alumno
            final String token = globalAPP.sessionEDUAPP.getAccessToken(MainActivity.this);
            final String idStudent = globalAPP.sessionEDUAPP.getID(MainActivity.this);

            initNavigation(idStudent, token);

            // OBTENER ASIGNATURAS DEL ALUMNO
            obtenerAsignaturas();
        } catch (Exception e) {
            Toasty.error(this, getString(R.string.error_app), Toasty.LENGTH_SHORT).show();
            Log.e(Constants.TAG, e.getMessage());
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        checkLogin();
        obtenerNotificaciones();
    }


    @Override
    protected void onResume() {
        super.onResume();

        if (cargado == false)
            actualizarDatos();
    }


    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {
        abrirDrawer(getCurrentFocus());

        return super.onMenuOpened(featureId, menu);
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
            Toast.makeText(this, getString(R.string.error_app), Toast.LENGTH_SHORT).show();
            Log.e(Constants.TAG, e.getMessage());
        }
    }


    /**
     * realiza el chequeo del usuario logeado
     */
    private void checkLogin() {
        try {
            final String token = globalAPP.sessionEDUAPP.getAccessToken(MainActivity.this);
            final String idStudent = globalAPP.sessionEDUAPP.getID(MainActivity.this);

            Call<StudentsModel> call = servicesEDUKO.getDataStudents(idStudent, token);
            call.enqueue(new Callback<StudentsModel>() {
                @Override
                public void onResponse(Call<StudentsModel> call, Response<StudentsModel> response) {
                    switch (response.code()) {
                        case 200: {
                            if (response.isSuccessful()) {
                                //ocultarCargando();
                                Log.i("EDUKO-ALUMNO-LOGIN", "WS Check Login OK");

                                StudentsModel studentsModel = response.body();

                                globalAPP.sessionEDUAPP.setName(MainActivity.this, studentsModel.getName());
                                globalAPP.sessionEDUAPP.setLastName(MainActivity.this, studentsModel.getLastName());
                                globalAPP.sessionEDUAPP.setCelular(MainActivity.this, studentsModel.getCellPhoneNumber());
                                globalAPP.sessionEDUAPP.setSchoolStudent(MainActivity.this, studentsModel.getSchoolId());
                                globalAPP.sessionEDUAPP.setSexo(MainActivity.this, studentsModel.getGender());
                                globalAPP.sessionEDUAPP.setClassroomID(MainActivity.this, studentsModel.getClassroomId());
                                globalAPP.sessionEDUAPP.setIDParents(MainActivity.this, studentsModel.getParentId());

                                // PUNTOS HIJO
                                float puntos = response.body().getPoints();

                                if (String.valueOf(puntos) == null)
                                    puntos = 0;

                                tvPuntosPadre.setText("$" + puntos + " créditos");
                                globalAPP.puntosPadre = response.body().getPoints();

                                actualizarPush();
                            }
                        }
                        break;

                        case 401: {
                            Log.i("EDUKO-ALUMNO-LOGIN", "WS Check Login incorrecto -> 401");

                            Connectivity connectivity = new Connectivity();
                            if (connectivity.isOnline(MainActivity.this)) {
                                globalAPP.sessionEDUAPP.cerrarSession(MainActivity.this);

                                Toasty.error(MainActivity.this, getString(R.string.error_login_app), Toast.LENGTH_SHORT).show();
                            }
                        }
                        break;

                        default: {
                            Log.i("EDUKO-ALUMNO-LOGIN", "WS Check Login incorrecto -> default");
                            Toast.makeText(MainActivity.this, "Error al chequear la cuenta del alumno", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<StudentsModel> call, Throwable throwable) {
                    Log.i("EDUKO-ALUMNO-LOGIN", "WS Check Login incorrecto -> failure");
                    Toast.makeText(MainActivity.this, "Error al chequear la cuenta del alumno", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            Toasty.error(MainActivity.this, getString(R.string.error_app), Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * actualiza las push notification
     */
    private void actualizarPush() {

    }


    /**
     * abre el menu lateral
     * @param v
     */
    public void abrirDrawer(View v) {
        fuenteMenuPerfil();
        dlPrincipal.openDrawer(GravityCompat.START, true);
    }


    /**
     * cierra el menu lateral
     * @param v
     */
    public void cerrarDrawer(View v) {
        dlPrincipal.closeDrawer(GravityCompat.START, true);
    }


    /**
     * fuentes menu lateral
     */
    private void fuenteMenuPerfil() {
        try {
            if (tvMenuPerfil == null)
                tvMenuPerfil = (TextView) findViewById(R.id.tvMenuPerfil);
            tvMenuPerfil.setTypeface(globalAPP.getFuenteAvenidBook(getAssets()));
            if (tvMenuCalificarApp == null)
                tvMenuCalificarApp = (TextView) findViewById(R.id.tvMenuCalificarApp);
            tvMenuCalificarApp.setTypeface(globalAPP.getFuenteAvenidBook(getAssets()));
            if (tvCompartir == null)
                tvCompartir = (TextView) findViewById(R.id.tvCompartir);
            tvCompartir.setTypeface(globalAPP.getFuenteAvenidBook(getAssets()));
            if (tvInformacionEscuela == null)
                tvInformacionEscuela = (TextView) findViewById(R.id.tvInformacionEscuela);
            tvInformacionEscuela.setTypeface(globalAPP.getFuenteAvenidBook(getAssets()));
            if (tvServiciosProductos == null)
                tvServiciosProductos = (TextView) findViewById(R.id.tvServiciosProductos);
            tvServiciosProductos.setTypeface(globalAPP.getFuenteAvenidBook(getAssets()));
            if (tvPuntosPadre == null)
                tvPuntosPadre = (TextView) findViewById(R.id.tvPuntosPadre);
            tvPuntosPadre.setTypeface(globalAPP.getFuenteAvenidBook(getAssets()));
            tvPuntosPadre.setText("+" + globalAPP.puntosPadre);
            if (tvRedesSociales == null)
                tvRedesSociales = (TextView) findViewById(R.id.tvRedesSociales);
            tvRedesSociales.setTypeface(globalAPP.getFuenteAvenidBook(getAssets()));
            if (tvSalir == null)
                tvSalir = (TextView) findViewById(R.id.tvSalir);
            tvSalir.setTypeface(globalAPP.getFuenteAvenidBook(getAssets()));
        } catch (Exception e) {
            Toasty.error(MainActivity.this, getString(R.string.error_app), Toast.LENGTH_SHORT).show();
            Log.e(Constants.TAG, e.getMessage());
        }
    }


    /**
     * carga la foto de perfil del alumno
     * @param idStudent identificador del estudiante
     * @param token token
     */
    private void initNavigation(String idStudent, String token) {
        try {
            Glide.with(MainActivity.this)
                    .load(Uri.parse(globalAPP.SERVER_URL + "/api/students/" + idStudent + "/photo?access_token=" + token))
                    .apply(RequestOptions.circleCropTransform())
                    .into(ivHeader);
        } catch (Exception e) {
            Toasty.error(MainActivity.this, "Error al carga la foto de perfil", Toasty.LENGTH_SHORT).show();
        }
    }


    /**
     * accion menu perfil
     * @param v
     */
    public void clicMenuPerfil(View v) {
        try {
            View headerOld = nvPrincipal.getHeaderView(0);
            nvPrincipal.removeHeaderView(headerOld);
            nvPrincipal.inflateHeaderView(R.layout.header_navigation_drawer_perfil);
            nvPrincipal.getMenu().clear();

            // ASIGNANDO VARIABLE
            tvNombre = (TextView) nvPrincipal.findViewById(R.id.tvNombre);
            tvPerfil = (TextView) nvPrincipal.findViewById(R.id.tvPerfil);
            tvPhone = (TextView) nvPrincipal.findViewById(R.id.tvPhone);
            tvEmail = (TextView) nvPrincipal.findViewById(R.id.tvEmail);
            tvUltimoAcceso = (TextView) nvPrincipal.findViewById(R.id.tvUltimoAcceso);
            tvFecha = (TextView) nvPrincipal.findViewById(R.id.tvFecha);
            tvCambiarContrasena = (TextView) nvPrincipal.findViewById(R.id.tvCambiarContrasena);
            ImageView ivAvatarPerfil = (ImageView) nvPrincipal.findViewById(R.id.ivAvatarPerfil);
            ivAvatarPerfil.setImageDrawable(ivHeader.getDrawable());

            // ASIGNANDO FUENTES
            tvNombre.setTypeface(globalAPP.getFuenteAvenidBook(getAssets()));
            tvPerfil.setTypeface(globalAPP.getFuenteAvenidBook(getAssets()));
            tvPhone.setTypeface(globalAPP.getFuenteAvenidBook(getAssets()));
            tvEmail.setTypeface(globalAPP.getFuenteAvenidBook(getAssets()));
            tvUltimoAcceso.setTypeface(globalAPP.getFuenteAvenidBook(getAssets()));
            tvFecha.setTypeface(globalAPP.getFuenteAvenidBook(getAssets()));
            tvCambiarContrasena.setTypeface(globalAPP.getFuenteAvenidRoman(getAssets()));

            tvNombre.setText(globalAPP.sessionEDUAPP.getName(MainActivity.this) + " " + globalAPP.sessionEDUAPP.getLastName(MainActivity.this));
            tvPhone.setText(globalAPP.sessionEDUAPP.getCelular(MainActivity.this));
            tvEmail.setText(globalAPP.sessionEDUAPP.getEmail(MainActivity.this));
            tvFecha.setText(globalAPP.sessionEDUAPP.getFechaUltimo(MainActivity.this));
        } catch (Exception e) {
            Toasty.error(MainActivity.this, getString(R.string.error_app), Toast.LENGTH_SHORT).show();
            Log.e(Constants.TAG, e.getMessage());
        }
    }


    /**
     * retorna el menu a su estado inicial
     * @param v
     */
    public void volverMenuPrincipal(View v) {
        try {
            View headerOld = nvPrincipal.getHeaderView(0);
            nvPrincipal.removeHeaderView(headerOld);
            nvPrincipal.inflateHeaderView(R.layout.header_navigation_drawer);

            ImageView prueba = (ImageView) nvPrincipal.getHeaderView(0).findViewById(R.id.ivHeader);
            prueba.setImageDrawable(ivHeader.getDrawable());

            tvMenuPerfil = (TextView) nvPrincipal.getHeaderView(0).findViewById(R.id.tvMenuPerfil);
            tvMenuCalificarApp = (TextView) nvPrincipal.getHeaderView(0).findViewById(R.id.tvMenuCalificarApp);
            tvCompartir = (TextView) nvPrincipal.getHeaderView(0).findViewById(R.id.tvCompartir);
            tvInformacionEscuela = (TextView) nvPrincipal.getHeaderView(0).findViewById(R.id.tvInformacionEscuela);
            tvServiciosProductos = (TextView) nvPrincipal.getHeaderView(0).findViewById(R.id.tvServiciosProductos);
            tvPuntosPadre = (TextView) nvPrincipal.getHeaderView(0).findViewById(R.id.tvPuntosPadre);
            tvRedesSociales = (TextView) nvPrincipal.getHeaderView(0).findViewById(R.id.tvRedesSociales);
            tvSalir = (TextView) nvPrincipal.getHeaderView(0).findViewById(R.id.tvSalir);

            fuenteMenuPerfil();
        } catch (Exception e) {
            Toasty.error(MainActivity.this, getString(R.string.error_app), Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * carga la vista de cambiar contraseña
     * @param v
     */
    public void clicCambiarContrasena(View v) {
        dlPrincipal.closeDrawer(GravityCompat.START, true);    // CERRAR DRAWER
        volverMenuPrincipal(v);

        // CAMBIANDO LA VISTA
        Intent vistaCambiarContrasena = new Intent(MainActivity.this, CambiarContrasenaActivity.class);
        startActivity(vistaCambiarContrasena);
    }


    /**
     * accion menu calificar app
     * @param v
     */
    public void clicMenuCalificarApp(View v) {
        try {
            dlPrincipal.closeDrawer(GravityCompat.START, true);

            Uri uri = Uri.parse("market://details?id=" + getPackageName());
            Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);

            goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                    Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                    Intent.FLAG_ACTIVITY_MULTIPLE_TASK);

            startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=" + getPackageName())));
        }
    }


    /**
     * accion menu compartir
     * @param v
     */
    public void clicMenuCompartir(View v) {
        try {
            dlPrincipal.closeDrawer(GravityCompat.START, true);

            Intent intent = new Intent(android.content.Intent.ACTION_SEND);
            intent.setType("text/plain");
            String shareBodyText = "ed+ Alumno " + Uri.parse(new StringBuilder()
                    .append("http://play.google.com/store/apps/details?id=")
                    .append(getPackageName()).toString());

            intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Disfruta " + getString(R.string.app_name));
            intent.putExtra(android.content.Intent.EXTRA_TEXT, shareBodyText);
            startActivity(Intent.createChooser(intent, "Compartir usando..."));
        } catch (Exception e) {
            Toasty.error(MainActivity.this, getString(R.string.error_app), Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * accion menu informacion escuela
     * @param v
     */
    public void clicMenuInformacionEscuela(View v) {
        dlPrincipal.closeDrawer(GravityCompat.START, true);

        Intent vistaEscuela = new Intent(MainActivity.this, InformacionEscuelaActivity.class);
        startActivity(vistaEscuela);
    }


    /**
     * accion menu servicio
     * @param v
     */
    public void clicmenuServiciosProductos(View v) {
        dlPrincipal.closeDrawer(GravityCompat.START, true);    // CERRAR DRAWER

        Intent vistaServicios = new Intent(MainActivity.this, ServiciosActivity.class);
        startActivity(vistaServicios);
    }


    /**
     * accion menu redes sociales
     * @param v
     */
    public void clicMenuRedesSociales(View v) {
        Toast.makeText(getApplicationContext(), "No implementado", Toast.LENGTH_SHORT).show();
    }


    /**
     * opcion menu cerrar session
     * @param v
     */
    public void clicMenuCerrar(View v) {
        try {
            dlPrincipal.closeDrawer(GravityCompat.START, true);    // CERRAR DRAWER

            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

            LayoutInflater inflater = (LayoutInflater) v.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.dialog_confirm_salir, null);

            // ASIGNACION DE VARIABLES
            TextView tvDescripcion = (TextView) view.findViewById(R.id.tvDescripcion);
            TextView tvSi = (TextView) view.findViewById(R.id.tvSi);
            TextView tvNo = (TextView) view.findViewById(R.id.tvNo);
            ImageView ivCerrar = (ImageView) view.findViewById(R.id.ivCerrar);

            // ASIGNACION DE FUENTES
            tvDescripcion.setTypeface(globalAPP.getFuenteAvenidBook(view.getContext().getAssets()));
            tvSi.setTypeface(globalAPP.getFuenteAvenidHeavy(view.getContext().getAssets()));
            tvNo.setTypeface(globalAPP.getFuenteAvenidHeavy(view.getContext().getAssets()));

            builder.setView(view);
            final AlertDialog dialog = builder.create();
            dialog.getWindow().getAttributes().windowAnimations = R.style.CustomDialogAnimation;
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.setCancelable(false);
            dialog.show();


            ivCerrar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });


            tvNo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });


            tvSi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toasty.success(MainActivity.this, getString(R.string.msgBye) + " " + globalAPP.sessionEDUAPP.getName(MainActivity.this) + " " + globalAPP.sessionEDUAPP.getLastName(MainActivity.this), Toast.LENGTH_SHORT).show();

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            String token = globalAPP.sessionEDUAPP.getAccessToken(MainActivity.this);

                            Call<JsonObject> call = servicesEDUKO.logout(token);
                            call.enqueue(new Callback<JsonObject>() {
                                @Override
                                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                                    globalAPP.subjectsModels.clear();
                                    globalAPP.frecuenciaModels.clear();
                                    globalAPP.tareasModels.clear();
                                    globalAPP.assistancesModels.clear();
                                    globalAPP.evaluacionesModels.clear();
                                    globalAPP.calificacionesModels.clear();
                                    globalAPP.eventosTodosModels.clear();
                                    globalAPP.reconocimientoModels.clear();
                                    globalAPP.calendarizacionModels.clear();
                                    globalAPP.categoriasModels.clear();
                                    globalAPP.servicesModels.clear();
                                    globalAPP.notificacionesModels.clear();
                                    globalAPP.sessionEDUAPP.cerrarSession(MainActivity.this);
                                }

                                @Override
                                public void onFailure(Call<JsonObject> call, Throwable throwable) {
                                    globalAPP.subjectsModels.clear();
                                    globalAPP.frecuenciaModels.clear();
                                    globalAPP.tareasModels.clear();
                                    globalAPP.assistancesModels.clear();
                                    globalAPP.evaluacionesModels.clear();
                                    globalAPP.calificacionesModels.clear();
                                    globalAPP.eventosTodosModels.clear();
                                    globalAPP.reconocimientoModels.clear();
                                    globalAPP.calendarizacionModels.clear();
                                    globalAPP.categoriasModels.clear();
                                    globalAPP.servicesModels.clear();
                                    globalAPP.notificacionesModels.clear();
                                    globalAPP.sessionEDUAPP.cerrarSession(MainActivity.this);
                                }
                            });
                        }
                    }, 2000);
                }
            });
        } catch (Exception e) {
            Toasty.error(this, getString(R.string.error_app), Toasty.LENGTH_SHORT).show();
            Log.e(Constants.TAG, e.getMessage());
        }
    }


    /**
     * opcion menu calendario
     * @param view
     */
    public void clicCalendario(View view) {
        Intent vistaCalendario = new Intent(MainActivity.this, CalendarioActivity.class);
        startActivity(vistaCalendario);
    }


    private void mostrarIconosTareas() {
        try {
            llTabMenuTareas.setAlpha(1);
            llTabMenuTareas.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clicNotificaciones(0);
                }
            });
            ivImagenTarea.setVisibility(View.VISIBLE);

            llTabMenuCalificaciones.setVisibility(View.GONE);
            ivImagenCalificacion.setVisibility(View.GONE);
            ivTabCalificacionesActivo.setOnClickListener(null);

            llTabMenuEvaluaciones.setVisibility(View.GONE);
            ivImagenEvaluaciones.setVisibility(View.GONE);
            ivTabEvaluacionesActivo.setOnClickListener(null);

            llTabMenuEventos.setVisibility(View.GONE);
            ivImagenEventos.setVisibility(View.GONE);
            ivTabEventosActivo.setOnClickListener(null);

            llTabMenuReconocimientos.setVisibility(View.GONE);
            ivImagenReconocimientos.setVisibility(View.GONE);
            ivTabReconocimientosActivo.setOnClickListener(null);

            actualizarIconosTareas();
        } catch (Exception e) {
            Toasty.error(this, getString(R.string.error_app), Toasty.LENGTH_SHORT).show();
            Log.e(Constants.TAG, e.getMessage());
        }
    }


    private void mostrarIconosCalificaciones() {
        try {
            llTabMenuTareas.setVisibility(View.GONE);
            ivImagenTarea.setVisibility(View.GONE);
            ivTabTareasActivo.setOnClickListener(null);

            llTabMenuCalificaciones.setAlpha((float) 1);
            llTabMenuCalificaciones.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            ivImagenCalificacion.setVisibility(View.VISIBLE);

            llTabMenuEvaluaciones.setVisibility(View.GONE);
            ivImagenEvaluaciones.setVisibility(View.GONE);
            ivTabEvaluacionesActivo.setOnClickListener(null);

            llTabMenuEventos.setVisibility(View.GONE);
            ivImagenEventos.setVisibility(View.GONE);
            ivTabEventosActivo.setOnClickListener(null);

            llTabMenuReconocimientos.setVisibility(View.GONE);
            ivImagenReconocimientos.setVisibility(View.GONE);
            ivTabReconocimientosActivo.setOnClickListener(null);
        } catch (Exception e) {
            Toasty.error(this, getString(R.string.error_app), Toasty.LENGTH_SHORT).show();
            Log.e(Constants.TAG, e.getMessage());
        }
    }


    private void mostrarIconosEvaluaciones() {
        try {
            llTabMenuTareas.setVisibility(View.GONE);
            ivImagenTarea.setVisibility(View.GONE);
            ivTabTareasActivo.setOnClickListener(null);

            llTabMenuCalificaciones.setVisibility(View.GONE);
            ivImagenCalificacion.setVisibility(View.GONE);
            ivTabCalificacionesActivo.setOnClickListener(null);

            llTabMenuEvaluaciones.setAlpha((float) 1);
            llTabMenuEvaluaciones.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clicNotificaciones(2);
                }
            });
            ivImagenEvaluaciones.setVisibility(View.VISIBLE);

            llTabMenuEventos.setVisibility(View.GONE);
            ivImagenEventos.setVisibility(View.GONE);
            ivTabEventosActivo.setOnClickListener(null);

            llTabMenuReconocimientos.setVisibility(View.GONE);
            ivImagenReconocimientos.setVisibility(View.GONE);
            ivTabReconocimientosActivo.setOnClickListener(null);

            actualizarIconosEvaluaciones();
        } catch (Exception e) {
            Toasty.error(this, getString(R.string.error_app), Toasty.LENGTH_SHORT).show();
            Log.e(Constants.TAG, e.getMessage());
        }
    }


    private void mostrarIconosEventos() {
        try {
            llTabMenuTareas.setVisibility(View.GONE);
            ivImagenTarea.setVisibility(View.GONE);
            ivTabTareasActivo.setOnClickListener(null);

            llTabMenuCalificaciones.setVisibility(View.GONE);
            ivImagenCalificacion.setVisibility(View.GONE);
            ivTabCalificacionesActivo.setOnClickListener(null);

            llTabMenuEvaluaciones.setVisibility(View.GONE);
            ivImagenEvaluaciones.setVisibility(View.GONE);
            ivTabEvaluacionesActivo.setOnClickListener(null);

            llTabMenuEventos.setVisibility(View.VISIBLE);
            llTabMenuEventos.setAlpha(1);
            llTabMenuEventos.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clicNotificaciones(1);
                }
            });
            ivImagenEventos.setVisibility(View.VISIBLE);

            llTabMenuReconocimientos.setVisibility(View.GONE);
            ivImagenReconocimientos.setVisibility(View.GONE);
            ivTabReconocimientosActivo.setOnClickListener(null);
        } catch (Exception e) {
            Toasty.error(this, getString(R.string.error_app), Toasty.LENGTH_SHORT).show();
            Log.e(Constants.TAG, e.getMessage());
        }
    }


    private void mostrarIconosReconocimientos() {
        try {
            llTabMenuTareas.setVisibility(View.GONE);
            ivImagenTarea.setVisibility(View.GONE);
            ivTabTareasActivo.setOnClickListener(null);

            llTabMenuCalificaciones.setVisibility(View.GONE);
            ivImagenCalificacion.setVisibility(View.GONE);
            ivTabCalificacionesActivo.setOnClickListener(null);

            llTabMenuEvaluaciones.setVisibility(View.GONE);
            ivImagenEvaluaciones.setVisibility(View.GONE);
            ivTabEvaluacionesActivo.setOnClickListener(null);

            llTabMenuEventos.setVisibility(View.GONE);
            ivImagenEventos.setVisibility(View.GONE);
            ivTabEventosActivo.setOnClickListener(null);

            llTabMenuReconocimientos.setAlpha((float) 1);
            llTabMenuReconocimientos.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            ivImagenReconocimientos.setVisibility(View.VISIBLE);
        } catch (Exception e) {
            Toasty.error(this, getString(R.string.error_app), Toasty.LENGTH_SHORT).show();
            Log.e(Constants.TAG, e.getMessage());
        }
    }


    public void clicNotificaciones(int vista) {
        // CAMBIANDO A LA VISTA DE NOTIFICACIONES
        Intent vistaNotificaciones = new Intent(MainActivity.this, NotificacionesActivity.class);
        vistaNotificaciones.putExtra("vista", vista);
        startActivityForResult(vistaNotificaciones, 101);
    }


    private void actualizarIconosTareas() {
        try {
            if (globalAPP.notifTareas == 0) {
                llTabMenuTareas.setVisibility(View.VISIBLE);
                tvNotificacionesTarea.setVisibility(View.GONE);
                ivTabTareasActivo.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        clicNotificaciones(0);
                        return true;
                    }
                });
            } else if (globalAPP.notifTareas > 0) {
                llTabMenuTareas.setVisibility(View.VISIBLE);
                tvNotificacionesTarea.setVisibility(View.VISIBLE);
                tvNotificacionesTarea.setText(String.valueOf(globalAPP.notifTareas));
                if (globalAPP.notifTareas > 9)
                    tvNotificacionesTarea.setText("+");
            }

            actualizarTabMain();
        } catch (Exception e) {
            Toasty.error(this, getString(R.string.error_app), Toasty.LENGTH_SHORT).show();
            Log.e(Constants.TAG, e.getMessage());
        }
    }


    private void actualizarIconosEvaluaciones() {
        try {
            if (globalAPP.notifEvaluaciones == 0) {
                llTabMenuEvaluaciones.setVisibility(View.VISIBLE);
                tvNotificacionesEvaluaciones.setVisibility(View.GONE);
                ivTabEvaluacionesActivo.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        clicNotificaciones(2);
                        return true;
                    }
                });
            } else if (globalAPP.notifEvaluaciones > 0) {
                llTabMenuEvaluaciones.setVisibility(View.VISIBLE);
                tvNotificacionesEvaluaciones.setVisibility(View.VISIBLE);
                tvNotificacionesEvaluaciones.setText(String.valueOf(globalAPP.notifEvaluaciones));
                if (globalAPP.notifEvaluaciones > 9)
                    tvNotificacionesEvaluaciones.setText("+");
            }
        } catch (Exception e) {
            Toasty.error(this, getString(R.string.error_app), Toasty.LENGTH_SHORT).show();
            Log.e(Constants.TAG, e.getMessage());
        }
    }


    private void actualizarIconosEventos() {
        try {
            if (globalAPP.notifEvent == 0) {
                llTabMenuEventos.setVisibility(View.VISIBLE);
                tvNotificacionesEventos.setVisibility(View.GONE);
                ivTabEventosActivo.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        clicNotificaciones(1);
                        return true;
                    }
                });
            } else if (globalAPP.notifEvent > 0) {
                llTabMenuEventos.setVisibility(View.VISIBLE);
                tvNotificacionesEventos.setVisibility(View.VISIBLE);
                tvNotificacionesEventos.setText(String.valueOf(globalAPP.notifEvent));
                if (globalAPP.notifEvent > 9)
                    tvNotificacionesEventos.setText("+");
            }

            actualizarTabMain();
        } catch (Exception e) {
            Toasty.error(this, getString(R.string.error_app), Toast.LENGTH_SHORT).show();
            Log.e(Constants.TAG, e.getMessage());
        }
    }


    private void actualizarTabMain() {
        try {
            tlPrincipal.getTabAt(0).setCustomView(null);
            tlPrincipal.getTabAt(0).setCustomView(vTabTareas);
            tlPrincipal.getTabAt(1).setCustomView(null);
            tlPrincipal.getTabAt(1).setCustomView(vTabCalificaciones);
            tlPrincipal.getTabAt(2).setCustomView(null);
            tlPrincipal.getTabAt(2).setCustomView(vTabEvaluaciones);
            tlPrincipal.getTabAt(3).setCustomView(null);
            tlPrincipal.getTabAt(3).setCustomView(vTabEventos);
            tlPrincipal.getTabAt(4).setCustomView(null);
            tlPrincipal.getTabAt(4).setCustomView(vTabReconocimientos);
        } catch (Exception e) {
            Toasty.error(this, getString(R.string.error_app), Toasty.LENGTH_SHORT).show();
            Log.e(Constants.TAG, e.getMessage());
        }
    }


    private void obtenerAsignaturas() {
        try {
            mostrarCargando();

            String token = globalAPP.sessionEDUAPP.getAccessToken(MainActivity.this);
            String idClassroom = globalAPP.sessionEDUAPP.getClassroomID(MainActivity.this);

            Call<List<SubjectsModel>> call = servicesEDUKO.getAsignaturas(idClassroom, token);
            call.enqueue(new Callback<List<SubjectsModel>>() {
                @Override
                public void onResponse(Call<List<SubjectsModel>> call, Response<List<SubjectsModel>> response) {
                    ocultarCargando(true);

                    switch (response.code()) {
                        case 200: {
                            if (response.isSuccessful()) {
                                globalAPP.subjectsModels = response.body();

                                // CARGAR FRECUENCIAS
                                obtenerFrecuencias();
                            } else {
                                Log.i("EDUKO-ALUMNO-ASIGN", "WS Asignaturas Error");
                                globalAPP.subjectsModels.clear();
                            }
                        }
                        break;

                        case 401: {
                            globalAPP.subjectsModels.clear();
                            Toast.makeText(MainActivity.this, getString(R.string.error_login_app), Toast.LENGTH_SHORT).show();
                        }
                        break;

                        default: {
                            globalAPP.subjectsModels.clear();
                            Toast.makeText(MainActivity.this, getString(R.string.error_asignaturas), Toast.LENGTH_SHORT).show();
                        }
                        break;
                    }
                }

                @Override
                public void onFailure(Call<List<SubjectsModel>> call, Throwable throwable) {
                    ocultarCargando(true);
                    globalAPP.subjectsModels.clear();
                    Toast.makeText(MainActivity.this, getString(R.string.error_asignaturas), Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            Toasty.error(this, getString(R.string.error_app), Toast.LENGTH_SHORT).show();
            Log.e(Constants.TAG, e.getMessage());
        }
    }


    private void obtenerFrecuencias() {
        try {
            String token = globalAPP.sessionEDUAPP.getAccessToken(MainActivity.this);

            Call<List<FrecuenciaModel>> call = servicesEDUKO.getFrecuencia(token);
            call.enqueue(new Callback<List<FrecuenciaModel>>() {
                @Override
                public void onResponse(Call<List<FrecuenciaModel>> call, Response<List<FrecuenciaModel>> response) {
                    ocultarCargando(true);

                    switch (response.code()) {
                        case 200: {
                            if (response.isSuccessful()) {
                                Log.i("EDUKO-ALUMNO-ASIGN", "WS Frecuencias OK -> " + response.body().size());

                                globalAPP.frecuenciaModels = response.body();

                                if (cargado == true) {
                                    cargado = false;
                                    mViewPagerPrincipalAdapter.tareasFragment.obtenerTareas();
                                }
                            } else {
                                Log.i("EDUKO-ALUMNO-ASIGN", "WS Frecuencias -> response");
                                globalAPP.frecuenciaModels.clear();
                            }
                        }
                        break;

                        case 401: {
                            globalAPP.frecuenciaModels.clear();
                            Toast.makeText(MainActivity.this, getString(R.string.error_login_app), Toast.LENGTH_SHORT).show();
                        }
                        break;

                        default: {
                            globalAPP.frecuenciaModels.clear();
                            Toast.makeText(MainActivity.this, getString(R.string.error_frecuencias), Toast.LENGTH_SHORT).show();
                        }
                        break;
                    }
                }

                @Override
                public void onFailure(Call<List<FrecuenciaModel>> call, Throwable throwable) {
                    ocultarCargando(true);
                    globalAPP.frecuenciaModels.clear();
                    Toast.makeText(MainActivity.this, getString(R.string.error_frecuencias), Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            Toasty.error(this, getString(R.string.error_app), Toast.LENGTH_SHORT).show();
            Log.e(Constants.TAG, e.getMessage());
        }
    }


    private void obtenerNotificaciones() {
        try {
            globalAPP.notifTareas = 0;
            globalAPP.notifEvaluaciones = 0;
            globalAPP.notifEvent = 0;

            String token = globalAPP.sessionEDUAPP.getAccessToken(MainActivity.this);
            String idAccount = globalAPP.sessionEDUAPP.getAccountID(MainActivity.this);
            String filtro = "{\"where\":{\"accountId\":\"" + idAccount + "\"},\"order\":\"created DESC\"}";

            Call<List<NotificacionesModel>> call = servicesEDUKO.getNotificaciones(token, filtro);
            call.enqueue(new Callback<List<NotificacionesModel>>() {
                @Override
                public void onResponse(Call<List<NotificacionesModel>> call, Response<List<NotificacionesModel>> response) {
                    switch (response.code()) {
                        case 200: {
                            if (response.isSuccessful()) {
                                Log.i("EDUKO-ALUMNO-NOTIF", "WS Listar notificaciones -> " + response.body().size());

                                globalAPP.notificacionesModels = response.body();

                                for (int i = 0; i < response.body().size(); i++) {
                                    switch (response.body().get(i).getTipo()) {
                                        case "homework": {
                                            if (response.body().get(i).getLeido() == false)
                                                globalAPP.notifTareas = globalAPP.notifTareas + 1;
                                        }
                                        break;

                                        case "evaluation": {
                                            if (response.body().get(i).getLeido() == false)
                                                globalAPP.notifEvaluaciones = globalAPP.notifEvaluaciones + 1;
                                        }
                                        break;

                                        case "event": {
                                            if (response.body().get(i).getLeido() == false)
                                                globalAPP.notifEvent = globalAPP.notifEvent + 1;
                                        }
                                        break;
                                    }
                                }

                                Log.i("EDUKO-ALUMNO-NOTIF", "WS Listar notificaciones tareas-> " + globalAPP.notifTareas);
                                Log.i("EDUKO-ALUMNO-NOTIF", "WS Listar notificaciones evaluaciones-> " + globalAPP.notifEvaluaciones);
                                Log.i("EDUKO-ALUMNO-NOTIF", "WS Listar notificaciones eventos-> " + globalAPP.notifEvent);

                                actualizarIconosTareas();
                                actualizarIconosEvaluaciones();
                                actualizarIconosEventos();
                            }
                        }
                        break;

                        default: {
                            Log.i("EDUKO-ALUMNO-NOTIF", "WS Listar notificaciones -> default");
                            ocultarCargando(true);
                            Toasty.error(MainActivity.this, getString(R.string.error_app), Toast.LENGTH_SHORT).show();
                        }
                        break;
                    }
                }

                @Override
                public void onFailure(Call<List<NotificacionesModel>> call, Throwable throwable) {
                    Log.i("EDUKO-ALUMNO-NOTIF", "WS Listar notificaciones -> failure");
                    ocultarCargando(true);
                    Toasty.error(MainActivity.this, getString(R.string.error_app), Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            Toasty.error(this, getString(R.string.error_app), Toast.LENGTH_SHORT).show();
            Log.e(Constants.TAG, e.getMessage());
        }
    }


    private void actualizarDatos() {
        switch (vpPrincipal.getCurrentItem()) {
            case 0:
                actualizarTareas();
                break;
            case 1:
                actualizarCalificaciones();
                break;
            case 2:
                actualizarEvaluaciones();
                break;
            case 3:
                actualizarEventos();
                break;
            case 4:
                actualizarReconocimientos();
                break;
        }
    }


    private void actualizarTareas() {
        if (globalAPP.subjectsModels != null && globalAPP.subjectsModels.size() > 0) {
            mViewPagerPrincipalAdapter.tareasFragment.obtenerTareas();
        }
    }


    private void actualizarCalificaciones() {
        mViewPagerPrincipalAdapter.calificacionesFragment.obtenerCalificaciones();
    }


    private void actualizarEvaluaciones() {
        mViewPagerPrincipalAdapter.evaluacionesFragment.obtenerEvaluaciones();
    }


    private void actualizarEventos() {
        mViewPagerPrincipalAdapter.eventosFragment.obtenerEventos();
    }


    private void actualizarReconocimientos() {
        mViewPagerPrincipalAdapter.reconocimientosFragment.obtenerReconocimientos();
    }

}
