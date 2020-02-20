package com.idoogroup.edalumno;


import com.google.gson.JsonObject;
import com.idoogroup.edalumno.Models.AssistancesModel;
import com.idoogroup.edalumno.Models.CalendarizacionModel;
import com.idoogroup.edalumno.Models.ComprarModel;
import com.idoogroup.edalumno.Models.EventosModel;
import com.idoogroup.edalumno.Models.FrecuenciaModel;
import com.idoogroup.edalumno.Models.LoginModel;
import com.idoogroup.edalumno.Models.NotificacionesModel;
import com.idoogroup.edalumno.Models.PasswordModel;
import com.idoogroup.edalumno.Models.ResetModel;
import com.idoogroup.edalumno.Models.SchoolModel;
import com.idoogroup.edalumno.Models.ServiceCategoryModel;
import com.idoogroup.edalumno.Models.ServicesModel;
import com.idoogroup.edalumno.Models.StudentsEvaluationModel;
import com.idoogroup.edalumno.Models.StudentsModel;
import com.idoogroup.edalumno.Models.StudentsRecognitionModel;
import com.idoogroup.edalumno.Models.SubjectsModel;
import com.idoogroup.edalumno.Models.TareasModel;
import com.idoogroup.edalumno.Models.UserModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface WebServicesIdooGroup {

    // AUTENTICAR ALUMNO
    @POST("api/accounts/login")
    Call<UserModel> login(@Body LoginModel users);

    // RESET PASSWORD
    @POST("api/accounts/reset")
    Call<JsonObject> reset(@Body ResetModel email);

    // CERRAR SESSION
    @POST("api/accounts/logout")
    Call<JsonObject> logout(@Query("access_token") String token);

    // DATOS DE ESTUDIANTE
    @GET("api/students/{id}")
    Call<StudentsModel> getDataStudents(@Path("id") String id, @Query("access_token") String token);

    /**********************************************************************************************/

    @POST("api/accounts/change-password")
    Call<JsonObject> resetPassword(@Query("access_token") String token, @Body PasswordModel password);

    /**********************************************************************************************/

    // INFORMACION ESCUELA
    @GET("api/students/{id}/school")
    Call<SchoolModel> getInformacionEscuela(@Path("id") String id, @Query("access_token") String token);

    /**********************************************************************************************/

    // OBTENER CATEGORIAS DE LOS SERVICIOS
    @GET("api/servicescategories")
    Call<List<ServiceCategoryModel>> getServicesCategorias(@Query("access_token") String token, @Query("filter") String filtro);

    @GET("api/schools/{idSchool}/services")
    Call<List<ServicesModel>> getServicesStudents(@Path("idSchool") String idSchool, @Query("access_token") String token, @Query("filter") String filtro);

    /**********************************************************************************************/

    // COMPRAR PRODUCTOS
    @POST("api/students/{idStudent}/orders")
    Call<JsonObject> comprarProducto(@Path("idStudent") String idStudent, @Query("access_token") String token, @Body ComprarModel compra);

    /**********************************************************************************************/

    // LISTAR CALENDARIZACION ESTUDIANTES
    @GET("api/students/{idStudent}/calendarizacions")
    Call<List<CalendarizacionModel>> getCalendarizacion(@Path("idStudent") String idStudent, @Query("access_token") String token, @Query("filter") String filtro);

    /**********************************************************************************************/

    // OBTENER LISTADO DE TAREAS
    @GET("api/tasks")
    Call<List<TareasModel>> getTareas(@Query("access_token") String token, @Query("filter") String filtro);

    // TAREA REALIZADA
    @PATCH("api/tasks/{idTaks}")
    Call<TareasModel> updateTareas(@Path("idTaks") String idTaks, @Query("access_token") String token, @Body TareasModel tarea);

    /**********************************************************************************************/

    // OBTENER LAS ASIGNATURAS
    //@GET("api/classrooms/{idClassroom}/subjects")
    @GET("api/classrooms/{idClassroom}/subjects")
    Call<List<SubjectsModel>> getAsignaturas(@Path("idClassroom") String idClassroom, @Query("access_token") String token);

    /**********************************************************************************************/

    @GET("api/frecuencia")
    Call<List<FrecuenciaModel>> getFrecuencia(@Query("access_token") String token);

    /**********************************************************************************************/

    // OBTENER LISTADO DE ASISTENCIA DIARIAS
    @GET("api/assistances")
    Call<List<AssistancesModel>> getAsistencia(@Query("access_token") String token, @Query("filter") String filtro);

    /**********************************************************************************************/

    // LISTAR EVALUACIONES
    @GET("api/student-evaluations")
    Call<List<StudentsEvaluationModel>> getEvaluaciones(@Query("access_token") String token, @Query("filter") String filtro);

    /**********************************************************************************************/

    // OBTENER EVENTOS ALUMNO
    @GET("api/students/{id}/events")
    Call<List<EventosModel>> getEventos(@Path("id") String id, @Query("access_token") String token, @Query("filter") String filtro);

    /**********************************************************************************************/

    // OBTENER LISTADO DE RECONOCIMIENTOS
    @GET("api/student-recognitions")
    Call<List<StudentsRecognitionModel>> getReconocimientos(@Query("access_token") String token, @Query("filter") String filtro);

    /**********************************************************************************************/

    // LISTAR LAS NOTIFICACIONES
    @GET("api/notifis")
    Call<List<NotificacionesModel>> getNotificaciones(@Query("access_token") String token, @Query("filter") String filtro);

    // RESET NOTIFICACIONES
    @POST("api/notifis/{accountID}/{tipo}/reset")
    Call<JsonObject> resetNotificaciones(@Path("accountID") String accountID, @Path("tipo") String tipo, @Query("access_token") String token, @Body NotificacionesModel notificacionesModel);

}
