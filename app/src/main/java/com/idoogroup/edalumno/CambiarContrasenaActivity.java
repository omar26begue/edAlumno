package com.idoogroup.edalumno;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.JsonObject;
import com.idoogroup.edalumno.Helpers.Constants;
import com.idoogroup.edalumno.Models.PasswordModel;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CambiarContrasenaActivity extends AppCompatActivity {

    private TextView tvCambiarContrasena, tvCambiar;
    private TextInputLayout tilContrasenaActual, tilContrasenaNueva, tilContrasenaConfirmar;
    private EditText etContrasenaActual, etContrasenaNueva, etContrasenaConfirmar;

    private IdooGroupApplication globalAPP;
    private WebServicesIdooGroup servicesEDUKO;
    private Dialog dialogLoading = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cambiar_contrasena);

        try {
            // VARIABLE GLOBAL
            globalAPP = (IdooGroupApplication) getApplication();
            servicesEDUKO = globalAPP.getService();

            // PARSEO DE VARIABLES
            tvCambiarContrasena = (TextView) findViewById(R.id.tvCambiarContrasena);
            tvCambiar = (TextView) findViewById(R.id.tvCambiar);
            tilContrasenaActual = (TextInputLayout) findViewById(R.id.tilContrasenaActual);
            tilContrasenaNueva = (TextInputLayout) findViewById(R.id.tilContrasenaNueva);
            tilContrasenaConfirmar = (TextInputLayout) findViewById(R.id.tilContrasenaConfirmar);
            etContrasenaActual = (EditText) findViewById(R.id.etContrasenaActual);
            etContrasenaNueva = (EditText) findViewById(R.id.etContrasenaNueva);
            etContrasenaConfirmar = (EditText) findViewById(R.id.etContrasenaConfirmar);

            // ASIGNACIONES DE FUENTES
            tvCambiarContrasena.setTypeface(globalAPP.getFuenteAvenidRoman(getAssets()));
            tvCambiar.setTypeface(globalAPP.getFuenteAvenidRoman(getAssets()));
            tilContrasenaActual.setTypeface(globalAPP.getFuenteAvenidLight(getAssets()));
            tilContrasenaNueva.setTypeface(globalAPP.getFuenteAvenidLight(getAssets()));
            tilContrasenaConfirmar.setTypeface(globalAPP.getFuenteAvenidLight(getAssets()));
            etContrasenaActual.setTypeface(globalAPP.getFuenteAvenidLight(getAssets()));
            etContrasenaNueva.setTypeface(globalAPP.getFuenteAvenidLight(getAssets()));
            etContrasenaConfirmar.setTypeface(globalAPP.getFuenteAvenidLight(getAssets()));

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            LayoutInflater inflaterLoading = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflaterLoading.inflate(R.layout.dialog_loading, null);

            builder.setView(view);
            dialogLoading = builder.create();
            dialogLoading.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialogLoading.setCancelable(false);
        } catch (Exception e) {
            Toasty.error(CambiarContrasenaActivity.this, getString(R.string.error_app), Toast.LENGTH_SHORT).show();
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


    public void volverAtrasContrasena(View v) {
        finish();
    }


    /**
     * evento clic de cambiar contraseña
     * @param view
     */
    public void cambiarContrasena(View view) {
        // VALIDACION
        if (validacionCambiarContrasena()) {
            if (etContrasenaNueva.getText().toString().equals(etContrasenaConfirmar.getText().toString())) {
                mostrarCargando();

                String token = globalAPP.sessionEDUAPP.getAccessToken(CambiarContrasenaActivity.this);
                PasswordModel passwordModel = new PasswordModel();
                passwordModel.setOldPassword(etContrasenaActual.getText().toString());
                passwordModel.setNewPassword(etContrasenaNueva.getText().toString());

                Call<JsonObject> call = servicesEDUKO.resetPassword(token, passwordModel);
                call.enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        ocultarCargando(false);

                        switch (response.code()) {
                            case 204: {
                                Toasty.success(CambiarContrasenaActivity.this, getString(R.string.msgCambiarContrasena), Toast.LENGTH_SHORT).show();

                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        finish();
                                    }
                                }, 1000);
                            }
                            break;

                            case 400: {
                                Toasty.error(CambiarContrasenaActivity.this, "Contraseña incorrecta", Toast.LENGTH_SHORT).show();
                            }
                            break;

                            default: {
                                Toasty.error(CambiarContrasenaActivity.this, getString(R.string.error_change_password), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable throwable) {
                        ocultarCargando(true);
                        Toasty.error(CambiarContrasenaActivity.this, getString(R.string.error_change_password), Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                etContrasenaNueva.setText("");
                etContrasenaConfirmar.setText("");
                Toasty.error(CambiarContrasenaActivity.this, getString(R.string.error_contrasena_no_coinciden), Toast.LENGTH_SHORT).show();
            }
        }
    }


    /**
     * validacion del formulario
     * @return
     */
    private boolean validacionCambiarContrasena() {
        boolean validado = true;
        View focusView = null;

        // RESETEANDO EL VALOR DE LOS ERRORES
        etContrasenaActual.setError(null);
        etContrasenaNueva.setError(null);
        etContrasenaConfirmar.setError(null);

        // VALIDANDO CONTRASENA ACTUAL VACIO
        if (TextUtils.isEmpty(etContrasenaActual.getText().toString())) {
            validado = false;
            etContrasenaActual.setError(getString(R.string.error_required));
            focusView = etContrasenaActual;
        }

        // VALIDANDO CONTRASENA NUEVA VACIO
        if (TextUtils.isEmpty(etContrasenaNueva.getText().toString())) {
            validado = false;
            etContrasenaNueva.setError(getString(R.string.error_required));
            focusView = etContrasenaNueva;
        }

        // VALIDANDO CONTRASENA CONFIRMAR VACIO
        if (TextUtils.isEmpty(etContrasenaConfirmar.getText().toString())) {
            validado = false;
            etContrasenaConfirmar.setError(getString(R.string.error_required));
            focusView = etContrasenaConfirmar;
        }

        return validado;
    }

}
