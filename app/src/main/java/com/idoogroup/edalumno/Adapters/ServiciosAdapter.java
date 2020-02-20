package com.idoogroup.edalumno.Adapters;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.JsonObject;
import com.idoogroup.edalumno.IdooGroupApplication;
import com.idoogroup.edalumno.Models.ComprarModel;
import com.idoogroup.edalumno.Models.ServicesModel;
import com.idoogroup.edalumno.R;
import com.idoogroup.edalumno.WebServicesIdooGroup;

import java.util.List;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ServiciosAdapter extends RecyclerView.Adapter<ServiciosAdapter.ViewHolder> {

    // VARIABLES DE LA CLASE
    private List<ServicesModel> serviciosModels;
    private int layout;


    // CONSTRUCTOR
    public ServiciosAdapter(List<ServicesModel> serviciosModels, int layout) {
        this.serviciosModels = serviciosModels;
        this.layout = layout;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        ViewHolder vh = new ViewHolder(v);

        return vh;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(serviciosModels.get(position));
    }


    // DEVUELVE LA CANTIDAD DE ELEMENTOS DE LA LISTA
    @Override
    public int getItemCount() {
        return serviciosModels.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvNameProducto, tvCalificacionProducto, tvCreditosProductos, tvStockProductos;
        public TextView tvCreditosTexto, tvStockTexto, tvFelicidades, tvTextoFelicidades;
        public ImageView ivProductosAbajo, ivProductoArriba, ivPayPal, ivProductoAbajoInactivo, ivServicios;
        public Animation animation;
        private Dialog dialogLoading;
        public IdooGroupApplication globalAPP;
        public WebServicesIdooGroup servicesEDUKO;


        public ViewHolder(final View itemView) {
            super(itemView);

            // ASIGANCION DE VARIABLES
            this.tvNameProducto = (TextView) itemView.findViewById(R.id.itemProducto);
            this.tvCalificacionProducto = (TextView) itemView.findViewById(R.id.tvCalificacionProducto);
            this.tvCreditosProductos = (TextView) itemView.findViewById(R.id.tvCreditosProductos);
            this.tvStockProductos = (TextView) itemView.findViewById(R.id.tvStockProductos);
            this.tvCreditosTexto = (TextView) itemView.findViewById(R.id.tvCreditosTexto);
            this.tvStockTexto = (TextView) itemView.findViewById(R.id.tvStockTexto);
            this.ivProductosAbajo = (ImageView) itemView.findViewById(R.id.ivProductoAbajo);
            this.ivProductoArriba = (ImageView) itemView.findViewById(R.id.ivProductoArriba);
            this.ivPayPal = (ImageView) itemView.findViewById(R.id.ivPayPal);
            this.ivProductoAbajoInactivo = (ImageView) itemView.findViewById(R.id.ivProductoAbajoInactivo);
            this.ivServicios = (ImageView) itemView.findViewById(R.id.ivServicios);
            animation = AnimationUtils.loadAnimation(itemView.getContext(), R.anim.bounce);
            globalAPP = (IdooGroupApplication) itemView.getContext().getApplicationContext();
            servicesEDUKO = globalAPP.getService();

            // ASIGNACION DE FUENTES
            this.tvNameProducto.setTypeface(globalAPP.getFuenteAvenidHeavy(itemView.getContext().getAssets()));
            this.tvCalificacionProducto.setTypeface(globalAPP.getFuenteAvenidRoman(itemView.getContext().getAssets()));
            this.tvCreditosProductos.setTypeface(globalAPP.getFuenteAvenidLight(itemView.getContext().getAssets()));
            this.tvCreditosTexto.setTypeface(globalAPP.getFuenteAvenidLight(itemView.getContext().getAssets()));
            this.tvStockProductos.setTypeface(globalAPP.getFuenteAvenidLight(itemView.getContext().getAssets()));
            this.tvStockTexto.setTypeface(globalAPP.getFuenteAvenidLight(itemView.getContext().getAssets()));
        }


        public void bind(final ServicesModel serviciosModel) {
            this.tvNameProducto.setText(serviciosModel.getName());
            this.tvCalificacionProducto.setText("0");
            this.tvCreditosProductos.setText(String.valueOf(serviciosModel.getPrice()));
            this.tvStockProductos.setText(String.valueOf(serviciosModel.getStock()));

            String token = globalAPP.sessionEDUAPP.getAccessToken(itemView.getContext());

            // CARGANDO IMAGENES DE LOS SERVICIOS
            Glide.with(itemView.getContext())
                    .load(Uri.parse(globalAPP.SERVER_URL + "/api/services/" + serviciosModel.getId() + "/photo?access_token=" + token))
                    .apply(RequestOptions.circleCropTransform())
                    .into(ivServicios);


            this.ivProductosAbajo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Integer tempCalificacion = Integer.parseInt((String) tvCalificacionProducto.getText());
                    Integer tempStock = Integer.parseInt((String) tvStockProductos.getText());
                    tempCalificacion--;

                    if (tempCalificacion == 0) {
                        ivProductosAbajo.setVisibility(View.GONE);
                        ivProductoAbajoInactivo.setVisibility(View.VISIBLE);
                    }

                    if (tempCalificacion >= 0) {
                        tvCalificacionProducto.setText(String.valueOf(tempCalificacion));
                        tempStock++;
                        tvStockProductos.setText(tempStock.toString());
                        // ANIMACION
                        tvStockProductos.startAnimation(animation);
                        tvCalificacionProducto.startAnimation(animation);
                    }
                }
            });

            this.ivProductoArriba.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Integer tempCalificacion = Integer.parseInt((String) tvCalificacionProducto.getText());
                    Integer tempStock = Integer.parseInt((String) tvStockProductos.getText());
                    tempCalificacion++;

                    if (tempCalificacion > 0) {
                        ivProductosAbajo.setVisibility(View.VISIBLE);
                        ivProductoAbajoInactivo.setVisibility(View.GONE);
                    }

                    if (tempStock > 0) {
                        tvCalificacionProducto.setText(String.valueOf(tempCalificacion));
                        tempStock--;
                        tvStockProductos.setText(tempStock.toString());
                        // ANIMACION
                        tvStockProductos.startAnimation(animation);
                        tvCalificacionProducto.startAnimation(animation);
                    }
                }
            });

            ivPayPal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Integer.parseInt(tvCalificacionProducto.getText().toString()) > 0) {
                        float valor = (Integer.valueOf(tvCalificacionProducto.getText().toString()) * Float.valueOf(tvCreditosProductos.getText().toString()));

                        if (valor < globalAPP.puntosPadre) {
                            ComprarModel comprarModel = new ComprarModel();
                            comprarModel.setAmount(tvCreditosProductos.getText().toString());
                            comprarModel.setProduct(serviciosModel.getId());
                            comprarModel.setCantidad(Integer.valueOf(tvCalificacionProducto.getText().toString()));

                            String token = globalAPP.sessionEDUAPP.getAccessToken(itemView.getContext());
                            String idStudent = globalAPP.sessionEDUAPP.getID(itemView.getContext());

                            mostrarCargando();
                            Call<JsonObject> call = servicesEDUKO.comprarProducto(idStudent, token, comprarModel);
                            call.enqueue(new Callback<JsonObject>() {
                                @Override
                                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                                    switch (response.code()) {
                                        case 200: {
                                            if (response.isSuccessful()) {
                                                ocultarCargando();
                                                AlertDialog.Builder builder = new AlertDialog.Builder(itemView.getContext());
                                                LayoutInflater inflater = (LayoutInflater) itemView.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                                View view = inflater.inflate(R.layout.dialog_servicios_paypal, null);

                                                // ASIGNACION DE VARIABLES
                                                TextView tvFelicidades = (TextView) view.findViewById(R.id.tvFelicidades);
                                                TextView tvDescripcion = (TextView) view.findViewById(R.id.tvDescripcion);
                                                ImageView ivServicioCerrar = (ImageView) view.findViewById(R.id.ivServicioCerrar);

                                                // ASIGNACION DE FUENTES
                                                tvFelicidades.setTypeface(globalAPP.getFuenteAvenidHeavy(itemView.getContext().getAssets()));
                                                tvDescripcion.setTypeface(globalAPP.getFuenteAvenidBook(itemView.getContext().getAssets()));

                                                builder.setView(view);
                                                final AlertDialog dialog = builder.create();
                                                dialog.getWindow().getAttributes().windowAnimations = R.style.CustomDialogAnimation;
                                                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                                dialog.show();

                                                ivServicioCerrar.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View v) {
                                                        dialog.dismiss();   // CERRAR VENTANA DE DIALOGO
                                                    }
                                                });
                                            } else {
                                                ocultarCargando();
                                                Toasty.error(itemView.getContext(), itemView.getContext().getString(R.string.error_compra), Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                        break;

                                        case 401: {
                                            ocultarCargando();
                                            Toasty.error(itemView.getContext(), itemView.getContext().getString(R.string.error_login_app), Toast.LENGTH_SHORT).show();
                                        }
                                        break;

                                        default: {
                                            ocultarCargando();
                                            Toasty.error(itemView.getContext(), itemView.getContext().getString(R.string.error_compra), Toast.LENGTH_SHORT).show();
                                        }
                                        break;
                                    }
                                }

                                @Override
                                public void onFailure(Call<JsonObject> call, Throwable throwable) {
                                    ocultarCargando();
                                    Toasty.error(itemView.getContext(), itemView.getContext().getString(R.string.error_compra), Toast.LENGTH_SHORT).show();
                                }
                            });
                        } else {
                            Toasty.error(itemView.getContext(), "No tiene puntos suficientes para realizar esta compra", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toasty.error(itemView.getContext(), "No tiene productos para comprar", Toast.LENGTH_SHORT).show();
                    }
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
