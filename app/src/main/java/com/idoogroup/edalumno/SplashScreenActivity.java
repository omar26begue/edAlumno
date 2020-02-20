package com.idoogroup.edalumno;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.idoogroup.edalumno.Helpers.Constants;
import com.idoogroup.edalumno.Helpers.SessionManager;

import es.dmoral.toasty.Toasty;


public class SplashScreenActivity extends AppCompatActivity {

    // VARIABLES DE LA CLASE
    private SessionManager sessionEDUKO;
    private IdooGroupApplication globalAPP;
    private WebServicesIdooGroup servicesEDUKO;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        try {
            // VARIABLE GLOBAL
            globalAPP = (IdooGroupApplication) getApplication();
            servicesEDUKO = globalAPP.getService();

            // CAMBIAR ACTIVITY EN 2 SEGUNDOS
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    // COMPROBANDO SI EL USUARIO ESTA LOGEADO
                    if (globalAPP.sessionEDUAPP.isLoggedIn(getApplicationContext()) == true) {
                        Intent vistaPrincipal = new Intent(SplashScreenActivity.this, MainActivity.class);
                        startActivity(vistaPrincipal);
                        finish();
                    } else {
                        // CAMBIANDO LA VISTA A LOGIN
                        Intent vistaLogin = new Intent(SplashScreenActivity.this, LoginActivity.class);
                        startActivity(vistaLogin);
                        finish();
                    }
                }
            }, 2500);
        } catch (Exception e) {
            Toasty.error(this, getString(R.string.error_app), Toasty.LENGTH_SHORT).show();
            Log.e(Constants.TAG, e.getMessage());
        }
    }
}
