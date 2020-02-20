package com.idoogroup.edalumno.Helpers;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.idoogroup.edalumno.Models.UserModel;
import com.idoogroup.edalumno.SplashScreenActivity;

public class SessionManager {

    // VARIABLES
    public static final String SERVER_URL_PREF_TAG = "SERVER_URL_PREF_TAG";
    private static final String PREF_NAME = "ed+.app.alumno.sharedpref";
    private static final String GSON_TAG = "ed+.app.alumno.gson";
    private static UserModel userModel;
    private Gson gson = new GsonBuilder().create();
    public static SessionManager mySession;
    private Context context;
    private SharedPreferences prefs;


    public static final SessionManager getInstance() {
        synchronized (SessionManager.class) {
            if (mySession == null) {
                mySession = new SessionManager();
            }

            return mySession;
        }
    }


    // CONSTRUCTOR DE LA CLASE
    public SessionManager() {
    }



    // CREAR SESSION EDUAPP
    public void createLoginSession(Context context, UserModel userModel) {
        this.context = context;
        this.userModel = userModel;
        prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        String json = gson.toJson(userModel);
        prefs.edit().putString(GSON_TAG, json).commit();
        Log.e("CREATE LOGGING SESSION", json);
    }


    // SI EL USUARIO ESTA LOGEADO
    public boolean isLoggedIn(Context context) {
        if (userModel == null) {
            if (prefs == null) {
                prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
            }
        };

        return prefs.getString(GSON_TAG, null) != null;
    }


    // CERRAR SESSION EDUAPP
    public void cerrarSession(Context _context) {
        if (context == null)
            this.context = _context;

        if (prefs == null)
            prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);

        prefs.edit().clear().commit();

        // CAMBIANDO A LA VISTA DE LOGINACTIVITY
        Intent vistaLogin = new Intent(context, SplashScreenActivity.class);
        vistaLogin.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);    // CERRANDO TODAS LAS ACTIVIDADES
        vistaLogin.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(vistaLogin);

        System.exit(0);
    }


    public String getAccessToken(Context _context) {
        if (userModel == null)
            loadData(_context);
        return userModel.getToken();
    }


    public String getAccountID(Context _context) {
        if (userModel == null)
            loadData(_context);
        return userModel.getAccount().getId();
    }


    public void setName(Context _context, String name) {
        if (userModel == null)
            loadData(_context);
        userModel.getAccount().getOwner().setName(name);
    }


    public String getName(Context _context) {
        if (userModel == null)
            loadData(_context);
        return userModel.getAccount().getOwner().getName();
    }


    public void setLastName(Context _context, String lastName) {
        if (userModel == null)
            loadData(_context);
        userModel.getAccount().getOwner().setLastName(lastName);
    }


    public String getLastName(Context _context) {
        if (userModel == null)
            loadData(_context);
        return userModel.getAccount().getOwner().getLastName();
    }


    public String getID(Context _context) {
        if (userModel == null)
            loadData(_context);
        return userModel.getAccount().getOwner().getId();
    }


    public void setID(Context _context, String id) {
        if (userModel == null)
            loadData(_context);
        userModel.getAccount().getOwner().setId(id);
    }


    public String getCelular(Context _context) {
        if (userModel == null)
            loadData(_context);
        return userModel.getAccount().getOwner().getCellPhoneNumber();
    }


    public void setCelular(Context _context, String celular) {
        if (userModel == null)
            loadData(_context);
        userModel.getAccount().getOwner().setCellPhoneNumber(celular);
    }


    public String getEmail(Context _context) {
        if (userModel == null)
            loadData(_context);
        return userModel.getAccount().getOwner().getEmail();
    }


    public String getFechaUltimo(Context _context) {
        if (userModel == null)
            loadData(_context);
        return userModel.getAccount().getLastAccess();
    }


    public String getSchoolStudent(Context _context) {
        if (userModel == null)
            loadData(_context);
        return userModel.getAccount().getOwner().getSchoolId();
    }


    public void setSchoolStudent(Context _context, String idSchol) {
        if (userModel == null)
            loadData(_context);
        userModel.getAccount().getOwner().setSchoolId(idSchol);
    }


    public String getSexo(Context _context) {
        if (userModel == null)
            loadData(_context);
        return userModel.getAccount().getOwner().getGender();
    }


    public void setSexo(Context _context, String sexo) {
        if (userModel == null)
            loadData(_context);
        userModel.getAccount().getOwner().setGender(sexo);
    }


    public String getIDParents(Context _context) {
        if (userModel == null)
            loadData(_context);
        return userModel.getAccount().getOwner().getParentId();
    }


    public void setIDParents(Context _context, String idParents) {
        if (userModel == null)
            loadData(_context);
        userModel.getAccount().getOwner().setParentId(idParents);
    }


    public String getClassroomID(Context _context) {
        if (userModel == null)
            loadData(_context);
        return userModel.getAccount().getOwner().getClassroomId();
    }


    public void setClassroomID(Context _context, String idClassroom) {
        if (userModel == null)
            loadData(_context);
        userModel.getAccount().getOwner().setClassroomId(idClassroom);
    }


    private void loadData(Context _context) {
        if (prefs == null) {
            if (context == null) {
                context = _context;
            }
            prefs = context.getApplicationContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        }

        String json = prefs.getString(GSON_TAG, "");
        userModel = gson.fromJson(json, UserModel.class);
    }

}
