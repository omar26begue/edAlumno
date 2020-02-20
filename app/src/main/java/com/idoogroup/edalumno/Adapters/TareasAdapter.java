package com.idoogroup.edalumno.Adapters;


import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.idoogroup.edalumno.Helpers.Moment.Moment;
import com.idoogroup.edalumno.IdooGroupApplication;
import com.idoogroup.edalumno.Interfaces.TareasCallback;
import com.idoogroup.edalumno.Models.HomeworksHomeworksModel;
import com.idoogroup.edalumno.Models.TareasModel;
import com.idoogroup.edalumno.R;
import com.idoogroup.edalumno.WebServicesIdooGroup;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TareasAdapter extends RecyclerView.Adapter<TareasAdapter.ViewHolder> {

    // VARIABLES DE LA CLASE
    private List<HomeworksHomeworksModel> tareas;
    private int layout;
    private TareasCallback tareasCallback;
    public AlertDialog dialog = null;


    public TareasAdapter(List<HomeworksHomeworksModel> tarea, int layout, TareasCallback tareasCallback) {
        this.tareas = tarea;
        this.layout = layout;
        this.tareasCallback = tareasCallback;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.bind(tareas.get(position), position == (tareas.size() - 1), tareasCallback);


        /*******************************************************************************************
         DETALLES TAREAS
         ******************************************************************************************/
        holder.ivTareasDetalles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());

                LayoutInflater inflater = (LayoutInflater) v.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View view = inflater.inflate(R.layout.dialog_tareas, null);

                // VARIABLES DE LA CLASE
                ImageView ivTareasCerrar = (ImageView) view.findViewById(R.id.ivTareasCerrar);
                TextView tvAsignaturasTareas = (TextView) view.findViewById(R.id.tvAsignaturasTareas);
                TextView tvAsignaturaNombreTarea = (TextView) view.findViewById(R.id.tvAsignaturaNombreTarea);
                TextView tvPuntosAsignatura = (TextView) view.findViewById(R.id.tvPuntosAsignatura);
                TextView tvDescripcionTareas = (TextView) view.findViewById(R.id.tvDescripcionTareas);
                TextView tvAceptar = (TextView) view.findViewById(R.id.tvAceptar);
                TextView tvFecha = (TextView) view.findViewById(R.id.tvFecha);
                TextView tvFechaEntrega = (TextView) view.findViewById(R.id.tvFechaEntrega);
                TextView tvFechaTextoResuelta = (TextView) view.findViewById(R.id.tvFechaTextoResuelta);
                TextView tvFechaResuelta = (TextView) view.findViewById(R.id.tvFechaResuelta);
                LinearLayout llFechaResuelta = (LinearLayout) view.findViewById(R.id.llFechaResuelta);

                // ASIGNACION DE FUENTES
                tvAsignaturasTareas.setTypeface(holder.globalAPP.getFuenteAvenidBook(holder.itemView.getContext().getAssets()));
                tvAsignaturaNombreTarea.setTypeface(holder.globalAPP.getFuenteAvenidHeavy(holder.itemView.getContext().getAssets()));
                tvPuntosAsignatura.setTypeface(holder.globalAPP.getFuenteAvenidBlack(holder.itemView.getContext().getAssets()));
                tvDescripcionTareas.setTypeface(holder.globalAPP.getFuenteAvenidBook(holder.itemView.getContext().getAssets()));
                tvAceptar.setTypeface(holder.globalAPP.getFuenteAvenidRoman(holder.itemView.getContext().getAssets()));
                tvFecha.setTypeface(holder.globalAPP.getFuenteAvenidLight(holder.itemView.getContext().getAssets()));
                tvFechaEntrega.setTypeface(holder.globalAPP.getFuenteAvenidRoman(holder.itemView.getContext().getAssets()));
                tvFechaTextoResuelta.setTypeface(holder.globalAPP.getFuenteAvenidLight(holder.itemView.getContext().getAssets()));
                tvFechaResuelta.setTypeface(holder.globalAPP.getFuenteAvenidRoman(holder.itemView.getContext().getAssets()));

                // VARIABLES DEL DIALOG
                tvAsignaturasTareas.setText(tareas.get(position).getSubjectName());
                tvAsignaturaNombreTarea.setText(tareas.get(position).getName());
                tvDescripcionTareas.setText(tareas.get(position).getDescription());

                try {
                    Moment moment = new Moment(tareas.get(position).getDateDeliver(), "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault());
                    moment.getCalendar().setTimeZone(TimeZone.getTimeZone("GMT-07:00"));
                    final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm aaa");
                    sdf.setTimeZone(TimeZone.getTimeZone("GMT-07:00"));
                    tvFechaEntrega.setText(sdf.format(moment.getDate()).substring(0, 10));

                    // MOSTRAR TAREAS RESUELTA
                    if (tareas.get(position).getTarea().getDateResolved() != null) {
                        llFechaResuelta.setVisibility(View.VISIBLE);
                        Moment moment1 = new Moment(tareas.get(position).getTarea().getDateResolved(), "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault());
                        moment1.getCalendar().setTimeZone(TimeZone.getTimeZone("GMT-07:00"));
                        final SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy h:mm a");
                        sdf1.setTimeZone(TimeZone.getTimeZone("GMT-07:00"));
                        tvFechaResuelta.setText(sdf1.format(moment1.getDate()));
                    }
                } catch (Exception e) {
                    Toasty.error(holder.itemView.getContext(), holder.itemView.getContext().getString(R.string.error_fecha), Toast.LENGTH_SHORT).show();
                }

                if (tareas.get(position).getTarea().getQualification() != -1)
                    tvPuntosAsignatura.setText(tareas.get(position).getTarea().getQualification() + " puntos");
                else tvPuntosAsignatura.setText("Sin calificación");

                builder.setView(view);
                final AlertDialog dialog = builder.create();
                dialog.getWindow().getAttributes().windowAnimations = R.style.CustomDialogAnimation;
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setCancelable(false);
                dialog.show();

                ivTareasCerrar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                tvAceptar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
            }
        });
    }


    @Override
    public int getItemCount() {
        return tareas.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvNombreTarea, tvPuntosTarea;
        public ImageView ivTareaVerificada, ivTareaNoVerificada, ivTareaRealizada, ivTareasDetalles;
        public View vSeparadorAsignaturas;
        public AlertDialog dialog = null, dialogLoading;
        public IdooGroupApplication globalAPP;
        public WebServicesIdooGroup servicesEDUKO;


        public ViewHolder(View itemView) {
            super(itemView);

            // ASIGNACION DE VARAIBLES
            tvNombreTarea = (TextView) itemView.findViewById(R.id.tvNombreTarea);
            tvPuntosTarea = (TextView) itemView.findViewById(R.id.tvPuntosTarea);
            ivTareaVerificada = (ImageView) itemView.findViewById(R.id.ivTareaVerificada);
            ivTareaNoVerificada = (ImageView) itemView.findViewById(R.id.ivTareaNoVerificada);
            ivTareaRealizada = (ImageView) itemView.findViewById(R.id.ivTareaRealizada);
            ivTareasDetalles = (ImageView) itemView.findViewById(R.id.ivTareasDetalles);
            vSeparadorAsignaturas = (View) itemView.findViewById(R.id.vSeparadorAsignaturas);
            globalAPP = (IdooGroupApplication) itemView.getContext().getApplicationContext();
            servicesEDUKO = globalAPP.getService();

            // ASIGNACION DE FUENTES
            tvNombreTarea.setTypeface(globalAPP.getFuenteAvenidBook(itemView.getContext().getAssets()));
            tvPuntosTarea.setTypeface(globalAPP.getFuenteAvenidBlack(itemView.getContext().getAssets()));
        }


        public void bind(final HomeworksHomeworksModel tareas, boolean separador, final TareasCallback tareasCallback) {
            this.tvNombreTarea.setText(tareas.getName());
            if (tareas.getTarea().getQualification() > 0)
                this.tvPuntosTarea.setText(String.valueOf(tareas.getTarea().getQualification() + " puntos"));
            else this.tvPuntosTarea.setText("Sin calificar");

            if (tareas.getTarea().getStatus().equals("pending")) {
                ivTareaRealizada.setVisibility(View.VISIBLE);
            } else {
                if (tareas.getTarea().getParentCheck()) {
                    ivTareaVerificada.setVisibility(View.VISIBLE);
                    ivTareaNoVerificada.setVisibility(View.GONE);
                } else {
                    ivTareaVerificada.setVisibility(View.GONE);
                    ivTareaNoVerificada.setVisibility(View.VISIBLE);
                }
            }


            if (separador == true) {
                this.vSeparadorAsignaturas.setVisibility(View.GONE);
            } else {
                this.vSeparadorAsignaturas.setVisibility(View.VISIBLE);
            }


            /*******************************************************************************************
             REALIZAR TAREAS
             ******************************************************************************************/
            ivTareaRealizada.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());

                    LayoutInflater inflater = (LayoutInflater) v.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    final View view = inflater.inflate(R.layout.dialog_tareas_xrealizar, null);

                    // VARIABLES DE LA CLASE
                    ImageView ivTareasCerrar = (ImageView) view.findViewById(R.id.ivTareasCerrar);
                    TextView tvTexto1 = (TextView) view.findViewById(R.id.tvTexto1);
                    TextView tvTexto2 = (TextView) view.findViewById(R.id.tvTexto2);
                    TextView tvNo = (TextView) view.findViewById(R.id.tvNo);
                    TextView tvSi = (TextView) view.findViewById(R.id.tvSi);
                    final LinearLayout llTareasXRealizar = (LinearLayout) view.findViewById(R.id.llTareasXRealizar);
                    final RelativeLayout rlLoading = (RelativeLayout) view.findViewById(R.id.rlLoading);

                    // ASIGNACION DE FUENTES
                    tvTexto1.setTypeface(globalAPP.getFuenteAvenidRoman(itemView.getContext().getAssets()));
                    tvTexto2.setTypeface(globalAPP.getFuenteAvenidRoman(itemView.getContext().getAssets()));
                    tvNo.setTypeface(globalAPP.getFuenteAvenidRoman(itemView.getContext().getAssets()));
                    tvSi.setTypeface(globalAPP.getFuenteAvenidRoman(itemView.getContext().getAssets()));

                    builder.setView(view);
                    dialog = builder.create();
                    dialog.getWindow().getAttributes().windowAnimations = R.style.CustomDialogAnimation;
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialog.setCancelable(false);
                    dialog.show();


                    ivTareasCerrar.setOnClickListener(new View.OnClickListener() {
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
                        public void onClick(final View v) {
                            final String token = globalAPP.sessionEDUAPP.getAccessToken(v.getContext());
                            tareas.getTarea().setQualification(0);
                            tareas.getTarea().setStatus("realizada");

                            Log.i("EDUKO-ALUMNO-TAREAS", "WS Tareas completadas");
                            Log.i("EDUKO-ALUMNO-TAREAS", "ID Tareas -> " + tareas.getName());
                            Log.i("EDUKO-ALUMNO-TAREAS", "ID Tareas -> " + tareas.getTarea().getId());

                            TareasModel tareasModel = new TareasModel();
                            tareasModel.setActive(tareas.getActive());
                            tareasModel.setStatus(tareas.getTarea().getStatus());
                            tareasModel.setQualification(tareas.getTarea().getQualification());
                            tareasModel.setDateResolved(tareas.getTarea().getDateResolved());
                            tareasModel.setParentCheck(tareas.getTarea().getParentCheck());
                            tareasModel.setDateDeliver(tareas.getTarea().getDateDeliver());
                            tareasModel.setId(tareas.getTarea().getId());
                            tareasModel.setChoresId(tareas.getTarea().getChoresId());
                            tareasModel.setId(tareas.getTarea().getId());

                            mostrarCargando();

                            Call<TareasModel> call = servicesEDUKO.updateTareas(tareas.getTarea().getId(), token, tareasModel);
                            call.enqueue(new Callback<TareasModel>() {
                                @Override
                                public void onResponse(Call<TareasModel> call, Response<TareasModel> response) {
                                    switch (response.code()) {
                                        case 200: {
                                            if (response.isSuccessful()) {
                                                ocultarCargando();

                                                // ACTUALIZAR TAREAS
                                                tareasCallback.actualizarItemsTareas();

                                                Toast.makeText(view.getContext(), "Tarea marcada como realizada", Toast.LENGTH_SHORT).show();
                                                dialog.dismiss();
                                            } else {
                                                Log.i("EDUKO-ALUMNO-TAREAS", "Error el marcar la tarea como completada -> response");
                                                ocultarCargando();
                                                dialog.dismiss();
                                            }
                                        }
                                        break;

                                        case 401: {
                                            ocultarCargando();
                                            Toast.makeText(view.getContext(), view.getContext().getString(R.string.error_login_app), Toast.LENGTH_SHORT).show();
                                            dialog.dismiss();
                                        }

                                        default: {
                                            Log.i("EDUKO-ALUMNO-TAREAS", "Error al marcar la tarea como completada -> default");
                                            ocultarCargando();
                                            Toast.makeText(view.getContext(), view.getContext().getString(R.string.error_tarea_estado), Toast.LENGTH_SHORT).show();
                                            dialog.dismiss();
                                        }
                                        break;
                                    }
                                }

                                @Override
                                public void onFailure(Call<TareasModel> call, Throwable throwable) {
                                    Log.i("EDUKO-ALUMNO-TAREAS", "Error al marcar la tarea como completada -> failure");
                                    ocultarCargando();
                                    Toast.makeText(view.getContext(), view.getContext().getString(R.string.error_tarea_estado), Toast.LENGTH_SHORT).show();
                                }
                            });

                        }
                    });
                }
            });
        }


        /**
         * Muestra el icono de void
         *
         * @return void
         */
        private void mostrarCargando() {
            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(itemView.getContext());

            LayoutInflater inflater = (LayoutInflater) itemView.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final View view = inflater.inflate(R.layout.dialog_loading, null);

            builder.setView(view);
            dialogLoading = builder.create();
            dialogLoading.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialogLoading.setCancelable(false);
            dialogLoading.show();
        }


        /**
         * Oculta el icono de cargando
         *
         * @return void
         */
        private void ocultarCargando() {
            if (dialogLoading != null) {
                dialogLoading.dismiss();
            }
        }

    }

}
