package com.idoogroup.edalumno;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.JsonObject;
import com.idoogroup.edalumno.Helpers.Constants;
import com.idoogroup.edalumno.Helpers.Validators;
import com.idoogroup.edalumno.Models.LoginModel;
import com.idoogroup.edalumno.Models.ResetModel;
import com.idoogroup.edalumno.Models.UserModel;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginActivity extends AppCompatActivity {

    // VARIABLES DE LA CLASE
    private ImageView ivLogoEDUKO;
    private TextInputLayout tilEmail, tilContrasena;
    private EditText etEmail, etContrasena;
    private TextView tvRecordarContrasena, tvEntrar;

    private IdooGroupApplication globalAPP;
    private WebServicesIdooGroup servicesEDUKO;
    private Dialog dialogLoading = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        try {
            // VARIABLE GLOBAL
            globalAPP = (IdooGroupApplication) getApplication();
            servicesEDUKO = globalAPP.getService();

            // ASIGNANDO VARIABLES
            ivLogoEDUKO = (ImageView) findViewById(R.id.ivLogoEDUKO);
            tilEmail = (TextInputLayout) findViewById(R.id.tilEmail);
            tilContrasena = (TextInputLayout) findViewById(R.id.tilContrasena);
            etEmail = (EditText) findViewById(R.id.etEmail);
            etContrasena = (EditText) findViewById(R.id.etContrasena);
            tvRecordarContrasena = (TextView) findViewById(R.id.tvCambiarContrasena);
            tvEntrar = (TextView) findViewById(R.id.tvEntrar);

            // ASIGNACIONES DE FUENTES
            tilEmail.setTypeface(globalAPP.getFuenteAvenidLight(getAssets()));
            etEmail.setTypeface(globalAPP.getFuenteAvenidLight(getAssets()));
            tilContrasena.setTypeface(globalAPP.getFuenteAvenidLight(getAssets()));
            etContrasena.setTypeface(globalAPP.getFuenteAvenidLight(getAssets()));
            tvRecordarContrasena.setTypeface(globalAPP.getFuenteAvenidBook(getAssets()));
            tvEntrar.setTypeface(globalAPP.getFuenteAvenidRoman(getAssets()));


            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.dialog_loading, null);

            builder.setView(view);
            dialogLoading = builder.create();
            dialogLoading.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialogLoading.setCancelable(false);


            findViewById(R.id.ivLogoEDUKO).setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    final Dialog dialog = new Dialog(LoginActivity.this, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.dialog_change_address);
                    dialog.setCancelable(false);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    ((EditText) dialog.findViewById(R.id.serverAddressET)).setText(globalAPP.SERVER_URL);
                    dialog.show();

                    dialog.findViewById(R.id.okBT).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String address = ((EditText) dialog.findViewById(R.id.serverAddressET)).getText().toString();

                            globalAPP.updateServicios(address);

                            dialog.dismiss();
                        }
                    });

                    dialog.findViewById(R.id.cancelBT).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });

                    return true;
                }
            });
        } catch (Exception e) {
            Toasty.error(this, getString(R.string.error_app), Toasty.LENGTH_SHORT).show();
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


    /**
     * Validacion del usuario y contraseña suministrador por los usuarios
     *
     * @return boolean validacionLogin()
     */
    private boolean validacionLogin() {
        // VARIABLES
        boolean validado = true;
        /*View focusView = null;

        // RESETEANDO EL VALOR DE LOS ERRORES
        etEmail.setError(null);
        etContrasena.setError(null);

        // VALIDANDO DIRECCION DE CORREO ELECTRONICO
        if (Patterns.EMAIL_ADDRESS.matcher(etEmail.getText()).matches() == false) {
            validado = false;
            etEmail.setError(getString(R.string.error_field_correo_valid));
            focusView = etEmail;
        }

        // VALIDANDO CORREO VACIO
        if (TextUtils.isEmpty(etEmail.getText().toString())) {
            validado = false;
            etEmail.setError(getString(R.string.error_field_correo_required));
            focusView = etEmail;
        }

        // VALIDANDO CONTRASENA VACIA
        if (TextUtils.isEmpty(etContrasena.getText().toString())) {
            validado = false;
            etContrasena.setError(getString(R.string.error_field_contrasena_required));
            focusView = etContrasena;
        }

        if (validado == false) {
            // SELECCIONANDO EL ELEMENTO DE ERROR
            focusView.requestFocus();
        }*/

        return validado;
    }


    /**
     * Servicio de login del suaurio
     *
     * @return void serviceLogin()
     */
    public void clicLogin(View view) {
        try {
            String email = etEmail.getText().toString();

            if (/*Validators.validarEmail(email)*/true) {
                if (validacionLogin()) {
                    mostrarCargando();

                    // PARAMETROS
                    final LoginModel user = new LoginModel();
                    user.setRealm(Constants.APP_TYPE);
                    user.setEmail(etEmail.getText().toString());
                    user.setPassword(etContrasena.getText().toString());

                    servicesEDUKO = globalAPP.getService();
                    Call<UserModel> call = servicesEDUKO.login(user);
                    call.enqueue(new Callback<UserModel>() {
                        @Override
                        public void onResponse(Call<UserModel> call, final Response<UserModel> response) {
                            switch (response.code()) {
                                case 200:
                                    if (response.isSuccessful()) {
                                        ocultarCargando(false);

                                        Toasty.success(LoginActivity.this, getString(R.string.msgBienvenida), Toast.LENGTH_SHORT).show();

                                        new Handler().postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                UserModel userModel = response.body();
                                                if (userModel.getAccount().getActive()) {
                                                    globalAPP.sessionEDUAPP.createLoginSession(LoginActivity.this, userModel);

                                                    Intent vistaMain = new Intent(LoginActivity.this, MainActivity.class);
                                                    startActivity(vistaMain);
                                                    finish();
                                                } else {
                                                    Toast.makeText(LoginActivity.this, "El usuario " + userModel.getAccount().getOwner().getEmail() + " esta inactivo", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        }, 1000);
                                    } else ocultarCargando(true);
                                    break;

                                default:
                                    ocultarCargando(true);
                                    Toasty.error(LoginActivity.this, getString(R.string.error_session), Toast.LENGTH_SHORT).show();
                                    break;
                            }
                        }

                        @Override
                        public void onFailure(Call<UserModel> call, Throwable throwable) {
                            ocultarCargando(true);
                            Log.i("EDUKO-ALUMNO-LOGIN", "WS login incorrecto " + etEmail.getText().toString());
                            Toasty.error(LoginActivity.this, getString(R.string.error_solicitud_login), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            } else
                Toasty.error(this, "Dirección de correo electrónico incorrecta", Toasty.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toasty.error(this, getString(R.string.error_app), Toasty.LENGTH_SHORT).show();
            Log.e(Constants.TAG, e.getMessage());
        }
    }


    /**
     * Reseta el usuario seleccionado
     *
     * @return void serviceReset()
     */
    public void clicOlvideContrasena(View view) {
        try {
            if (Patterns.EMAIL_ADDRESS.matcher(etEmail.getText()).matches() == false) {
                etEmail.setError(getString(R.string.error_field_correo_valid));
                tilEmail.requestFocus();
                etEmail.requestFocus();
            } else {
                mostrarCargando();

                Log.i("EDUKO-ALUMNO-LOGIN", "WS Reset");

                ResetModel resetModel = new ResetModel();
                resetModel.setEmail(etEmail.getText().toString());

                servicesEDUKO = globalAPP.getService();
                Call<JsonObject> call = servicesEDUKO.reset(resetModel);
                call.enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        ocultarCargando(true);

                        if (response.isSuccessful() || response.code() == 204) {
                            Log.i("EDUKO-ALUMNO-LOGIN", "WS Reset correcto");
                            Toasty.success(LoginActivity.this, getString(R.string.msgEnvioCorreo), Toast.LENGTH_SHORT).show();
                        } else {
                            Log.i("EDUKO-ALUMNO-LOGIN", "WS Reset incorrecto");
                            Toasty.error(LoginActivity.this, getString(R.string.msgNoEnvioCorreo), Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable throwable) {
                        ocultarCargando(true);
                        Log.i("EDUKO-ALUMNO-LOGIN", "WS Reset incorrecto");
                        Toasty.error(LoginActivity.this, getString(R.string.error_reset_password), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        } catch (Exception e) {
            Toasty.error(this, getString(R.string.error_app), Toasty.LENGTH_SHORT).show();
            Log.e(Constants.TAG, e.getMessage());
        }
    }
}
