package com.idoogroup.edalumno.Adapters;


import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.idoogroup.edalumno.Helpers.Moment.Moment;
import com.idoogroup.edalumno.IdooGroupApplication;
import com.idoogroup.edalumno.Models.StudentsRecognitionModel;
import com.idoogroup.edalumno.R;
import com.idoogroup.edalumno.WebServicesIdooGroup;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import es.dmoral.toasty.Toasty;

public class ReconocimientosAdapter extends RecyclerView.Adapter<ReconocimientosAdapter.ViewHolder> {

    private List<StudentsRecognitionModel> reconocimientos;
    private int layout;


    public ReconocimientosAdapter(List<StudentsRecognitionModel> reconocimientos, int layout) {
        this.reconocimientos = reconocimientos;
        this.layout = layout;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(reconocimientos.get(position));
    }


    @Override
    public int getItemCount() {
        return reconocimientos.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvReconocimientosItes, tvTextoReconocimiento, tvDescripcionReconocimiento;
        public ImageView ivReconocimientosItems, ivReconocimientosCerrar;
        public LinearLayout llReconocimientos;
        private IdooGroupApplication globalAPP;
        private WebServicesIdooGroup servicesEDUKO;

        public ViewHolder(View itemView) {
            super(itemView);

            // ASIGNACION DE VARAIBLES
            tvReconocimientosItes = (TextView) itemView.findViewById(R.id.tvReconocimientosItes);
            ivReconocimientosItems = (ImageView) itemView.findViewById(R.id.ivReconocimientosItems);
            llReconocimientos = (LinearLayout) itemView.findViewById(R.id.llReconocimientos);
            globalAPP = (IdooGroupApplication) itemView.getContext().getApplicationContext();
            servicesEDUKO = globalAPP.getService();

            // ASIGNACION DE FUENTES
            tvReconocimientosItes.setTypeface(globalAPP.getFuenteAvenidHeavy(itemView.getContext().getAssets()));
        }


        public void bind(final StudentsRecognitionModel reconocimiento) {
            this.tvReconocimientosItes.setText(reconocimiento.getRecognition().getName());

            llReconocimientos.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String token = globalAPP.sessionEDUAPP.getAccessToken(v.getContext());
                    final AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());

                    LayoutInflater inflater = (LayoutInflater) v.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View view = inflater.inflate(R.layout.dialog_reconocimientos, null);

                    // VARIABLES DE LA CLASE
                    ivReconocimientosCerrar = (ImageView) view.findViewById(R.id.ivReconocimientosCerrar);
                    ImageView ivReconocimientos = (ImageView) view.findViewById(R.id.ivReconocimientos);
                    tvTextoReconocimiento = (TextView) view.findViewById(R.id.tvTextoReconocimiento);
                    TextView tvObtenidoReconocimiento = (TextView) view.findViewById(R.id.tvObtenidoReconocimiento);
                    tvDescripcionReconocimiento = (TextView) view.findViewById(R.id.tvDescripcionReconocimiento);

                    // ASIGNACION DE FUENTES
                    tvTextoReconocimiento.setTypeface(globalAPP.getFuenteAvenidHeavy(view.getContext().getAssets()));
                    tvObtenidoReconocimiento.setTypeface(globalAPP.getFuenteAvenidBlack(view.getContext().getAssets()));
                    tvDescripcionReconocimiento.setTypeface(globalAPP.getFuenteAvenidBook(view.getContext().getAssets()));

                    tvTextoReconocimiento.setText(reconocimiento.getRecognition().getName());

                    try {
                        Moment moment = new Moment(reconocimiento.getCreated(), "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault());
                        moment.getCalendar().setTimeZone(TimeZone.getTimeZone("GMT-07:00"));
                        final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm aaa");
                        sdf.setTimeZone(TimeZone.getTimeZone("GMT-07:00"));
                        tvObtenidoReconocimiento.setText(sdf.format(moment.getDate()).substring(0, 10));
                    } catch (Exception e) {
                        Toasty.error(itemView.getContext(), itemView.getContext().getString(R.string.error_fecha), Toast.LENGTH_SHORT).show();
                    }

                    tvDescripcionReconocimiento.setText(reconocimiento.getRecognition().getDescription());

                    builder.setView(view);
                    final AlertDialog dialog = builder.create();
                    dialog.getWindow().getAttributes().windowAnimations = R.style.CustomDialogAnimation;
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialog.setCancelable(false);
                    dialog.show();

                    Glide.with(view.getContext())
                            .load(Uri.parse(globalAPP.SERVER_URL + "/api/recognitions/" + reconocimiento.getRecognition().getId() + "/photo?access_token=" + token))
                            .apply(RequestOptions.circleCropTransform())
                            .into(ivReconocimientos);

                    ivReconocimientosCerrar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                }
            });
        }

    }
}
