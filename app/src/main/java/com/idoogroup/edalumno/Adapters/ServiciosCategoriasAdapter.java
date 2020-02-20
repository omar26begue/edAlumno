package com.idoogroup.edalumno.Adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.idoogroup.edalumno.IdooGroupApplication;
import com.idoogroup.edalumno.Models.ServiceCategoryModel;
import com.idoogroup.edalumno.R;

import java.util.List;

public class ServiciosCategoriasAdapter extends RecyclerView.Adapter<ServiciosCategoriasAdapter.ViewHolder> {

    private List<ServiceCategoryModel> categorias;
    private int layout;


    public ServiciosCategoriasAdapter(List<ServiceCategoryModel> categorias, int layout) {
        this.categorias = categorias;
        this.layout = layout;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        ViewHolder vh = new ViewHolder(v);

        return vh;
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.bind(categorias.get(position));

        holder.ivCategoriaUnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.ivCategoriaUnSelect.setVisibility(View.GONE);
                holder.ivCategoriaSelect.setVisibility(View.VISIBLE);
                holder.tvNombreCategoria.setTextColor(Color.parseColor("#FFFFFF"));
                holder.llCategorias.setBackgroundResource(R.drawable.eduko_back_categoria_servicio);
                categorias.get(position).setActive(true);
            }
        });

        holder.ivCategoriaSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.ivCategoriaSelect.setVisibility(View.GONE);
                holder.ivCategoriaUnSelect.setVisibility(View.VISIBLE);
                holder.tvNombreCategoria.setTextColor(Color.parseColor("#019DC0"));
                holder.llCategorias.setBackgroundResource(R.drawable.eduko_back_categoria_servicio_unselect);
                categorias.get(position).setActive(false);
            }
        });

        holder.tvNombreCategoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.ivCategoriaUnSelect.getVisibility() == View.VISIBLE) {
                    holder.ivCategoriaUnSelect.setVisibility(View.GONE);
                    holder.ivCategoriaSelect.setVisibility(View.VISIBLE);
                    holder.tvNombreCategoria.setTextColor(Color.parseColor("#FFFFFF"));
                    holder.llCategorias.setBackgroundResource(R.drawable.eduko_back_categoria_servicio);
                    categorias.get(position).setActive(true);
                } else {
                    holder.ivCategoriaSelect.setVisibility(View.GONE);
                    holder.ivCategoriaUnSelect.setVisibility(View.VISIBLE);
                    holder.tvNombreCategoria.setTextColor(Color.parseColor("#019DC0"));
                    holder.llCategorias.setBackgroundResource(R.drawable.eduko_back_categoria_servicio_unselect);
                    categorias.get(position).setActive(false);
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return categorias.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvNombreCategoria;
        public ImageView ivCategoriaUnSelect, ivCategoriaSelect;
        public LinearLayout llCategorias;
        public IdooGroupApplication globalAPP;

        public ViewHolder(View itemView) {
            super(itemView);

            // ASIGANCION DE VARIABLES
            this.tvNombreCategoria = (TextView) itemView.findViewById(R.id.tvNombreCategoria);
            this.ivCategoriaUnSelect = (ImageView) itemView.findViewById(R.id.ivCategoriaUnSelect);
            this.ivCategoriaSelect = (ImageView) itemView.findViewById(R.id.ivCategoriaSelect);
            this.llCategorias = (LinearLayout) itemView.findViewById(R.id.llCategorias);
            globalAPP = (IdooGroupApplication) itemView.getContext().getApplicationContext();

            // ASIGNACION DE FUENTES
            this.tvNombreCategoria.setTypeface(globalAPP.getFuenteAvenidRoman(itemView.getContext().getAssets()));
        }


        public void bind(ServiceCategoryModel categorias) {
            this.tvNombreCategoria.setText(categorias.getName());

            if (categorias.getActive()) {
                ivCategoriaUnSelect.setVisibility(View.GONE);
                ivCategoriaSelect.setVisibility(View.VISIBLE);
                tvNombreCategoria.setTextColor(Color.parseColor("#FFFFFF"));
                llCategorias.setBackgroundResource(R.drawable.eduko_back_categoria_servicio);
            } else {
                ivCategoriaSelect.setVisibility(View.GONE);
                ivCategoriaUnSelect.setVisibility(View.VISIBLE);
                tvNombreCategoria.setTextColor(Color.parseColor("#019DC0"));
                llCategorias.setBackgroundResource(R.drawable.eduko_back_categoria_servicio_unselect);
            }
        }
    }

}
