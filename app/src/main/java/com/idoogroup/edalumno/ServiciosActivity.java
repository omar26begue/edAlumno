package com.idoogroup.edalumno;

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
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.idoogroup.edalumno.Adapters.ServiciosAdapter;
import com.idoogroup.edalumno.Adapters.ServiciosCategoriasAdapter;
import com.idoogroup.edalumno.Helpers.Constants;
import com.idoogroup.edalumno.Models.ServiceCategoryModel;
import com.idoogroup.edalumno.Models.ServicesModel;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ServiciosActivity extends AppCompatActivity {

    private TextView tvCalificacionServicio, tvServiciosProductos, tvPuntosServicio,
            tvServiciosCategorias, tvCategoriaProductos, tvAceptarServicio, tvNoDatos;
    private ImageView ivCategoriasCerrar;
    private RecyclerView rvProductos, rvCategoriasServicios;
    private RecyclerView.Adapter mAdapter, mAdapterCategorias;
    private RecyclerView.LayoutManager mLayoutManager;

    private IdooGroupApplication globalAPP;
    private WebServicesIdooGroup servicesEDUKO;
    private Dialog dialogLoading = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servicios);

        try {
            // VARIABLE GLOBAL
            globalAPP = (IdooGroupApplication) getApplication();
            servicesEDUKO = globalAPP.getService();

            // PARSEO DE VARIABLES
            tvCalificacionServicio = (TextView) findViewById(R.id.tvCalificacionServicios);
            tvServiciosProductos = (TextView) findViewById(R.id.tvServiciosProductos);
            tvPuntosServicio = (TextView) findViewById(R.id.tvPuntosServicio);
            tvCategoriaProductos = (TextView) findViewById(R.id.tvCategoriaProductos);
            tvNoDatos = (TextView) findViewById(R.id.tvNoDatos);
            rvProductos = (RecyclerView) findViewById(R.id.rvProductos);
            mLayoutManager = new LinearLayoutManager(this);

            // ASIGNACION DE FUENTES
            tvServiciosProductos.setTypeface(globalAPP.getFuenteAvenidHeavy(getAssets()));
            tvCategoriaProductos.setTypeface(globalAPP.getFuenteAvenidHeavy(getAssets()));
            tvCalificacionServicio.setTypeface(globalAPP.getFuenteAvenidLight(getAssets()));
            tvPuntosServicio.setTypeface(globalAPP.getFuenteAvenidLight(getAssets()));
            tvNoDatos.setTypeface(globalAPP.getFuenteAvenidRoman(getAssets()));

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            LayoutInflater inflaterLoading = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflaterLoading.inflate(R.layout.dialog_loading, null);

            builder.setView(view);
            dialogLoading = builder.create();
            dialogLoading.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialogLoading.setCancelable(false);

            tvCalificacionServicio.setText("$" + globalAPP.puntosPadre);

            // OBTENER CATEGORIAS SERVICIOS
            obtenerCategoriaServicios();
        } catch (Exception e) {
            Toasty.error(this, getString(R.string.error_app), Toast.LENGTH_SHORT).show();
            Log.e(Constants.TAG, e.getMessage());
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        obtenerServicios();
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


    /**
     * accion volver atras pantalla
     * @param view
     */
    public void volverAtrasServicios(View view) {
        finish();
    }


    /**
     * clici accion categorias productos
     * @param view
     */
    public void clicCategoriasProductos(View view) {
        try {
            final AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());

            LayoutInflater inflater = getLayoutInflater();
            View v = inflater.inflate(R.layout.dialog_servicios_categorias, null);

            // ASIGNACION DE VARIABLES
            tvServiciosCategorias = (TextView) v.findViewById(R.id.tvServiciosCategorias);
            tvAceptarServicio = (TextView) v.findViewById(R.id.tvAceptarServicio);
            TextView tvNoDatosCategoria = (TextView) v.findViewById(R.id.tvNoDatosCategoria);
            ivCategoriasCerrar = (ImageView) v.findViewById(R.id.ivCategoriasCerrar);
            rvCategoriasServicios = (RecyclerView) v.findViewById(R.id.rvCategoriasServicios);

            // ASIGNACION DE FUENTES
            tvServiciosCategorias.setTypeface(globalAPP.getFuenteAvenidHeavy(getAssets()));
            tvAceptarServicio.setTypeface(globalAPP.getFuenteAvenidRoman(getAssets()));
            tvNoDatosCategoria.setTypeface(globalAPP.getFuenteAvenidRoman(view.getContext().getAssets()));

            if (globalAPP.categoriasModels != null && globalAPP.categoriasModels.size() > 0) {
                rvCategoriasServicios.setVisibility(View.VISIBLE);
                tvNoDatosCategoria.setVisibility(View.GONE);

                mAdapterCategorias = new ServiciosCategoriasAdapter(globalAPP.categoriasModels, R.layout.items_servicios_categorias);
                mLayoutManager = new LinearLayoutManager(this);

                rvCategoriasServicios.setLayoutManager(mLayoutManager);

                rvCategoriasServicios.setAdapter(mAdapterCategorias);
            } else {
                rvCategoriasServicios.setVisibility(View.GONE);
                tvNoDatosCategoria.setVisibility(View.VISIBLE);
            }

            builder.setView(v);
            final AlertDialog dialog = builder.create();
            dialog.getWindow().getAttributes().windowAnimations = R.style.CustomDialogAnimation;
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.setCancelable(false);
            dialog.show();

            tvAceptarServicio.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateServicios();
                    dialog.dismiss();
                }
            });

            ivCategoriasCerrar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
        } catch (Exception e) {
            Toasty.error(this, getString(R.string.error_app), Toast.LENGTH_SHORT).show();
            Log.e(Constants.TAG, e.getMessage());
        }
    }


    private void obtenerCategoriaServicios() {
        try {
            final String token = globalAPP.sessionEDUAPP.getAccessToken(ServiciosActivity.this);
            String filtro = "{\"where\":{\"schoolId\":\"" + globalAPP.sessionEDUAPP.getSchoolStudent(ServiciosActivity.this) + "\"}}";

            Call<List<ServiceCategoryModel>> call = servicesEDUKO.getServicesCategorias(token, filtro);
            call.enqueue(new Callback<List<ServiceCategoryModel>>() {
                @Override
                public void onResponse(Call<List<ServiceCategoryModel>> call, Response<List<ServiceCategoryModel>> response) {
                    switch (response.code()) {
                        case 200: {
                            if (response.isSuccessful()) {
                                Log.i("EDUKO-ALUMNO-CAT", "WS Categoria servicios OK -> " + response.body().size());
                                globalAPP.categoriasModels = response.body();
                            }
                        }
                        break;

                        case 401: {
                            globalAPP.subjectsModels.clear();
                            Toasty.error(ServiciosActivity.this, getString(R.string.error_login_app), Toast.LENGTH_SHORT).show();
                        }
                        break;

                        default: {
                            Log.i("EDUKO-ALUMNO-CAT", "WS Categoria servicios -> default");
                            Toasty.error(ServiciosActivity.this, getString(R.string.error_categoria_servicios), Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<List<ServiceCategoryModel>> call, Throwable throwable) {
                    Log.i("EDUKO-ALUMNO-CAT", "WS Categoria servicios -> failure");
                    Toasty.error(ServiciosActivity.this, getString(R.string.error_categoria_servicios), Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            Toasty.error(this, getString(R.string.error_app), Toast.LENGTH_SHORT).show();
            Log.e(Constants.TAG, e.getMessage());
        }
    }


    private void obtenerServicios() {
        try {
            mostrarCargando();

            Log.i(Constants.TAG, "WS Listar servicios");

            String idSchool = globalAPP.sessionEDUAPP.getSchoolStudent(this);
            String token = globalAPP.sessionEDUAPP.getAccessToken(ServiciosActivity.this);
            String filtro = "{\"where\":{\"type\": \"purchase\",\"active\":true}}";

            Call<List<ServicesModel>> call = servicesEDUKO.getServicesStudents(idSchool, token, filtro);
            call.enqueue(new Callback<List<ServicesModel>>() {
                @Override
                public void onResponse(Call<List<ServicesModel>> call, Response<List<ServicesModel>> response) {
                    ocultarCargando(true);

                    switch (response.code()) {
                        case 200: {
                            if (response.isSuccessful()) {
                                Log.i("EDUKO-ALUMNO-SERVICIOS", "Productos listados -> " + response.body().size());
                                globalAPP.servicesModels = response.body();

                                updateServicios();
                            } else {
                                globalAPP.servicesModels.clear();
                                updateServicios();
                            }
                        }
                        break;

                        case 401: {
                            globalAPP.servicesModels.clear();
                            updateServicios();
                            Toasty.error(ServiciosActivity.this, getString(R.string.error_login_app), Toast.LENGTH_SHORT).show();
                        }
                        break;

                        default: {
                            Log.i("EDUKO-ALUMNO-SERVICIOS", "Error al listar los servicios -> default");
                            globalAPP.servicesModels.clear();
                            updateServicios();
                            Toasty.error(ServiciosActivity.this, getString(R.string.error_servicios), Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<List<ServicesModel>> call, Throwable throwable) {
                    ocultarCargando(true);
                    globalAPP.servicesModels.clear();
                    updateServicios();
                    Log.i("EDUKO-ALUMNO-SERVICIOS", "Error al listar los servicios -> failure");
                    Toasty.error(ServiciosActivity.this, getString(R.string.error_servicios), Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            Toasty.error(this, getString(R.string.error_app), Toast.LENGTH_SHORT).show();
            Log.e(Constants.TAG, e.getMessage());
        }
    }


    private void updateServicios() {
        try {
            List<ServicesModel> servicesModels = new ArrayList<>();

            for (int i = 0; i < globalAPP.servicesModels.size(); i++) {
                for (int j = 0; j < globalAPP.categoriasModels.size(); j++) {
                    if (globalAPP.servicesModels.get(i).getServiceCategoryId().equals(globalAPP.categoriasModels.get(j).getId())) {
                        if (globalAPP.categoriasModels.get(j).getActive() == true) {
                            servicesModels.add(globalAPP.servicesModels.get(i));
                            j = globalAPP.categoriasModels.size();
                        }
                    }
                }
            }

            if (servicesModels != null && servicesModels.size() > 0) {
                rvProductos.setVisibility(View.VISIBLE);
                tvNoDatos.setVisibility(View.GONE);

                mAdapter = new ServiciosAdapter(servicesModels, R.layout.items_servicios_productos);
                mLayoutManager = new LinearLayoutManager(this);

                rvProductos.setLayoutManager(mLayoutManager);
                rvProductos.setAdapter(mAdapter);
            } else {
                rvProductos.setVisibility(View.GONE);
                tvNoDatos.setVisibility(View.VISIBLE);
            }
        } catch (Exception e) {
            Toasty.error(this, getString(R.string.error_app), Toast.LENGTH_SHORT).show();
            Log.e(Constants.TAG, e.getMessage());
        }
    }

}
