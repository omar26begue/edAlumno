package com.idoogroup.edalumno;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.BounceInterpolator;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.idoogroup.edalumno.Helpers.Constants;
import com.idoogroup.edalumno.Models.SchoolModel;

import java.util.Random;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InformacionEscuelaActivity extends AppCompatActivity implements OnMapReadyCallback {

    // VARIABLES DE LA CLASE
    private TextView tvInformacionEscuela;
    private ImageView ivEscuela;
    private SupportMapFragment mapFragment;
    private GoogleMap map;
    private OnMapReadyCallback readyCallback;

    private IdooGroupApplication globalAPP;
    private WebServicesIdooGroup servicesEDUKO;
    private Dialog dialogLoading = null;

    private SchoolModel escuelaModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informacion_escuela);

        try {
            // VARIABLE GLOBAL
            globalAPP = (IdooGroupApplication) getApplication();
            servicesEDUKO = globalAPP.getService();

            escuelaModel = new SchoolModel();

            // ASIGNANDO VARIABLES
            tvInformacionEscuela = (TextView) findViewById(R.id.tvInformacionEscuela);
            ivEscuela = (ImageView) findViewById(R.id.ivEscuela);
            //mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapEscuela);

            // ASIGNACIONES DE FUENTES
            tvInformacionEscuela.setTypeface(globalAPP.getFuenteAvenidHeavy(getAssets()));

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            LayoutInflater inflaterLoading = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflaterLoading.inflate(R.layout.dialog_loading, null);

            builder.setView(view);
            dialogLoading = builder.create();
            dialogLoading.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialogLoading.setCancelable(false);

            String token = globalAPP.sessionEDUAPP.getAccessToken(InformacionEscuelaActivity.this);
            final String idStudent = globalAPP.sessionEDUAPP.getID(InformacionEscuelaActivity.this);
            String idSchool = globalAPP.sessionEDUAPP.getSchoolStudent(InformacionEscuelaActivity.this);

            Glide.with(InformacionEscuelaActivity.this)
                    .load(Uri.parse(globalAPP.SERVER_URL + "/api/schools/" + idSchool + "/photo?access_token=" + token))
                    .apply(RequestOptions.circleCropTransform())
                    .into(ivEscuela);

            readyCallback = this;
        } catch (Exception e) {
            Toasty.error(InformacionEscuelaActivity.this, getString(R.string.error_app), Toast.LENGTH_SHORT).show();
            Log.e(Constants.TAG, e.getMessage());
        }
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


    @Override
    public void onMapReady(GoogleMap googleMap) {

    }


    @Override
    protected void onStart() {
        super.onStart();
        obtenerInformacionEscuela();
    }


    /**
     * obtener la informacion de la escuela
     */
    private void obtenerInformacionEscuela() {
        mostrarCargando();

        String token = globalAPP.sessionEDUAPP.getAccessToken(InformacionEscuelaActivity.this);
        final String idStudent = globalAPP.sessionEDUAPP.getID(InformacionEscuelaActivity.this);

        Call<SchoolModel> call = servicesEDUKO.getInformacionEscuela(idStudent, token);
        call.enqueue(new Callback<SchoolModel>() {
            @Override
            public void onResponse(Call<SchoolModel> call, Response<SchoolModel> response) {
                switch (response.code()) {
                    case 200: {
                        if (response.isSuccessful()) {
                            ocultarCargando(true);

                            escuelaModel = response.body();

                            Log.i("EDUKO-ALUMNO-ESCUELA", "Escuela " + response.body().getName());
                            Log.i("EDUKO-ALUMNO-ESCUELA", "Numeros telefonicos " + response.body().getPhoneNumberList().size());

                            // SINCRONIZACION DEL MAPA
                            //mapFragment.getMapAsync(readyCallback);
                        } else ocultarCargando(true);
                    }
                    break;

                    case 401: {
                        ocultarCargando(true);
                        Toasty.error(InformacionEscuelaActivity.this, getString(R.string.error_login_app), Toast.LENGTH_SHORT).show();
                    }
                    break;

                    default: {
                        ocultarCargando(true);
                        Toasty.error(InformacionEscuelaActivity.this, getString(R.string.error_informacion_escuela), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<SchoolModel> call, Throwable throwable) {
                ocultarCargando(true);
                Toasty.error(InformacionEscuelaActivity.this, getString(R.string.error_informacion_escuela), Toast.LENGTH_SHORT).show();
                Log.e("EDUKO-ALUMNO-ESCUELA", throwable.getMessage());
            }
        });
    }


    // VOLVER A LA VENTANA PRINCIPAL
    public void volverAtrasEscuela(View v) {
        animarClick(v);
        finish();
    }


    /**
     * animacion
     * @param view
     */
    private void animarClick(View view) {
        Animation animation = AnimationUtils.loadAnimation(InformacionEscuelaActivity.this, R.anim.clicked_btn_anim);
        animation.setInterpolator(new BounceInterpolator());
        view.startAnimation(animation);
    }


    /**
     * realizar llamada numero escuela
     * @param view
     */
    public void llamarNumeroscuela(View view) {
        animarClick(view);

        int randow = escuelaModel.getPhoneNumberList().size();

        Log.i("EDUKO-ALUMNO-ESCUELA", "Llamar # escuela");

        if (isTelephonyEnabled()) {
            Log.i("EDUKO-ALUMNO-ESCUELA", "Llamada del telefono habilitada");

            if (randow > 0) {
                int positicion = new Random().nextInt(randow);
                Log.i("EDUKO-ALUMNO-ESCUELA", "Llamando a la escuela al # " + escuelaModel.getPhoneNumberList().get(positicion));

                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + escuelaModel.getPhoneNumberList().get(positicion)));
                startActivity(intent);
            } else
                Toasty.error(InformacionEscuelaActivity.this, getString(R.string.error_phone_null), Toast.LENGTH_SHORT).show();
        } else {
            Log.i("EDUKO-ALUMNO-ESCUELA", "Llamada del teléfono no esta habilitada");
            Toasty.error(InformacionEscuelaActivity.this, getString(R.string.error_phone_call), Toast.LENGTH_SHORT).show();
        }
    }


    private boolean isTelephonyEnabled() {
        TelephonyManager tm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        return tm != null && tm.getSimState() == TelephonyManager.SIM_STATE_READY;
    }

}
