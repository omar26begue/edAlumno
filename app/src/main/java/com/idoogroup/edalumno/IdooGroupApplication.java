package com.idoogroup.edalumno;

import android.app.Application;
import android.content.res.AssetManager;
import android.graphics.Typeface;

import com.idoogroup.edalumno.Helpers.Constants;
import com.idoogroup.edalumno.Helpers.SessionManager;
import com.idoogroup.edalumno.Models.AssistancesModel;
import com.idoogroup.edalumno.Models.CalendarizacionModel;
import com.idoogroup.edalumno.Models.EventosModel;
import com.idoogroup.edalumno.Models.FrecuenciaModel;
import com.idoogroup.edalumno.Models.NotificacionesModel;
import com.idoogroup.edalumno.Models.ServiceCategoryModel;
import com.idoogroup.edalumno.Models.ServicesModel;
import com.idoogroup.edalumno.Models.StudentsEvaluationModel;
import com.idoogroup.edalumno.Models.StudentsRecognitionModel;
import com.idoogroup.edalumno.Models.SubjectsModel;
import com.idoogroup.edalumno.Models.TareasModel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class IdooGroupApplication extends Application {

    public SessionManager sessionEDUAPP;
    public Retrofit retrofit;
    public String SERVER_URL;
    private final String avenid_light = "fonts/Avenir_light.otf";
    private final String avenid_book = "fonts/AvenirLTStd_Book.otf";
    private final String avenid_heavy = "fonts/AEH.ttf";
    private final String avenid_roman = "fonts/AvenirLTStd_Roman.otf";
    private final String avenid_black = "fonts/Avenir Black.otf";
    public Boolean eventosActivo = true;                                // VARIABLE DE LOS EVENTOS [ACTIVO/INACTIVO]
    public float puntosPadre = 0;

    // ASIGNATURAS
    public List<SubjectsModel> subjectsModels;
    public List<FrecuenciaModel> frecuenciaModels;
    public int countEvent = 0;
    public List<TareasModel> tareasModels;
    public List<AssistancesModel> assistancesModels;
    public List<StudentsEvaluationModel> evaluacionesModels;
    public List<AssistancesModel> calificacionesModels;
    public List<EventosModel> eventosTodosModels;
    public List<StudentsRecognitionModel> reconocimientoModels;
    public List<CalendarizacionModel> calendarizacionModels;
    public List<ServiceCategoryModel> categoriasModels;
    public List<ServicesModel> servicesModels;
    public List<NotificacionesModel> notificacionesModels;
    public int notifTareas = 0, notifEvaluaciones = 0, notifEvent = 0;


    @Override
    public void onCreate() {
        super.onCreate();

        // CONSTRUCTOR DE LA CLASE SSESIONMANAGER
        sessionEDUAPP = new SessionManager();

        if (Constants.PRODUCTION_MODE)
            SERVER_URL = Constants.SERVER_URL_PRODUCTION;
        else SERVER_URL = Constants.SERVER_URL_DEVELOPER;

        OkHttpClient okHttpClient = new OkHttpClient.Builder() .readTimeout(60, TimeUnit.SECONDS) .connectTimeout(60, TimeUnit.SECONDS) .build();

        retrofit = new Retrofit.Builder()
                .baseUrl(SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        subjectsModels = new ArrayList<>();
        frecuenciaModels = new ArrayList<>();
        tareasModels = new ArrayList<>();
        assistancesModels = new ArrayList<>();
        evaluacionesModels = new ArrayList<>();
        calificacionesModels = new ArrayList<>();
        eventosTodosModels = new ArrayList<>();
        reconocimientoModels = new ArrayList<>();
        calendarizacionModels = new ArrayList<>();
        categoriasModels = new ArrayList<>();
        servicesModels = new ArrayList<>();
        notificacionesModels = new ArrayList<>();
    }


    public WebServicesIdooGroup getService() {
        return retrofit.create(WebServicesIdooGroup.class);
    }


    public void updateServicios(String URL) {
        this.SERVER_URL = URL;
        OkHttpClient okHttpClient = new OkHttpClient.Builder() .readTimeout(60, TimeUnit.SECONDS) .connectTimeout(60, TimeUnit.SECONDS) .build();
        retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
    }


    public Typeface getFuenteAvenidLight(AssetManager assetManager) {
        return Typeface.createFromAsset(assetManager, avenid_light);
    }


    public Typeface getFuenteAvenidBook(AssetManager assetManager) {
        return Typeface.createFromAsset(assetManager, avenid_book);
    }


    public Typeface getFuenteAvenidHeavy(AssetManager assetManager) {
        return Typeface.createFromAsset(assetManager, avenid_heavy);
    }


    public Typeface getFuenteAvenidRoman(AssetManager assetManager) {
        return Typeface.createFromAsset(assetManager, avenid_roman);
    }


    public Typeface getFuenteAvenidBlack(AssetManager assetManager) {
        return Typeface.createFromAsset(assetManager, avenid_black);
    }

}
