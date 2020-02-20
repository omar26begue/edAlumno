package com.idoogroup.edalumno.Fragments.FragmentTareas;


import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.idoogroup.edalumno.Adapters.AsignaturasAdapter;
import com.idoogroup.edalumno.Helpers.Constants;
import com.idoogroup.edalumno.IdooGroupApplication;
import com.idoogroup.edalumno.Models.HomeworksHomeworksModel;
import com.idoogroup.edalumno.Models.SubjectModelHomeworks;
import com.idoogroup.edalumno.Models.TareasModel;
import com.idoogroup.edalumno.Models.TareasModelHomeworks;
import com.idoogroup.edalumno.R;
import com.idoogroup.edalumno.WebServicesIdooGroup;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;


public class TareasRealizadasFragment extends Fragment {

    private TextView tvTareasRealizadas, tvTareasRealizadasNumero, tvNoDatos;
    private RecyclerView rvTareasRealizadas;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private IdooGroupApplication globalAPP;
    private WebServicesIdooGroup servicesEDUKO;
    private Dialog dialogLoading = null;

    public String idSubject = "-1";


    public TareasRealizadasFragment() {
        // Required empty public constructor
    }


    public static TareasRealizadasFragment newInstance() {
        return new TareasRealizadasFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tareas_realizadas, container, false);

        try {
            // VARIABLE GLOBAL
            globalAPP = (IdooGroupApplication) getActivity().getApplication();
            servicesEDUKO = globalAPP.getService();

            // ASIGNADO LAS VARIABLES
            tvTareasRealizadas = (TextView) v.findViewById(R.id.tvTareasRealizadas);
            tvTareasRealizadasNumero = (TextView) v.findViewById(R.id.tvTareasRealizadasNumero);
            tvNoDatos = (TextView) v.findViewById(R.id.tvNoDatos);
            rvTareasRealizadas = (RecyclerView) v.findViewById(R.id.rvTareasRealizadas);


            // ASIGNACION DE FUENTES
            tvTareasRealizadas.setTypeface(globalAPP.getFuenteAvenidHeavy(getActivity().getAssets()));
            tvTareasRealizadasNumero.setTypeface(globalAPP.getFuenteAvenidHeavy(getActivity().getAssets()));
            tvNoDatos.setTypeface(globalAPP.getFuenteAvenidRoman(getActivity().getAssets()));
        } catch (Exception e) {
            Toasty.error(getContext(), getString(R.string.error_app), Toast.LENGTH_SHORT).show();
            Log.e(Constants.TAG, e.getMessage());
        }

        return v;
    }


    public void updateTareas() {
        List<TareasModel> tareasModels = new ArrayList<>();
        List<SubjectModelHomeworks> subjects = new ArrayList<>();

        for (int i = 0; i < globalAPP.tareasModels.size(); i++) {
            if (!globalAPP.tareasModels.get(i).getStatus().equals("pending")) {
                if (idSubject.equals("-1")) {
                    if (globalAPP.tareasModels.get(i).getQualification() == 0) {
                        tareasModels.add(globalAPP.tareasModels.get(i));
                    }
                } else {
                    String subject = globalAPP.tareasModels.get(i).getHomework().getSubject().getId();
                    if (subject.equals(idSubject)) {
                        if (globalAPP.tareasModels.get(i).getQualification() == 0) {
                            tareasModels.add(globalAPP.tareasModels.get(i));
                        }
                    }
                }
            }
        }

        for (int i = 0; i < tareasModels.size(); i++) {
            Boolean agregar = true;
            int position = -1;

            for (int j = 0; j < subjects.size(); j++) {
                if (tareasModels.get(i).getHomework().getSubject().getId().equals(subjects.get(j).getId())) {
                    if (!(tareasModels.get(i).getStatus().equals("pending"))) {
                        if (/*tareasModels.get(i).getQualification() == 0*/true) {
                            agregar = false;
                            position = j;
                        }
                    }
                }
            }

            if (agregar == true) {
                SubjectModelHomeworks homeworks = new SubjectModelHomeworks();
                homeworks.setName(tareasModels.get(i).getHomework().getSubject().getName());
                homeworks.setHours(tareasModels.get(i).getHomework().getSubject().getHours());
                homeworks.setDescription(tareasModels.get(i).getHomework().getSubject().getDescription());
                homeworks.setId(tareasModels.get(i).getHomework().getSubject().getId());

                HomeworksHomeworksModel homeworksModel = new HomeworksHomeworksModel();
                homeworksModel.setName(tareasModels.get(i).getHomework().getName());
                homeworksModel.setDescription(tareasModels.get(i).getHomework().getDescription());
                homeworksModel.setActive(tareasModels.get(i).getHomework().getActive());
                homeworksModel.setCreated(tareasModels.get(i).getHomework().getCreated());
                homeworksModel.setDateDeliver(tareasModels.get(i).getHomework().getDateDeliver());
                homeworksModel.setId(tareasModels.get(i).getHomework().getId());
                homeworksModel.setTeacherId(tareasModels.get(i).getHomework().getTeacherId());
                homeworksModel.setSubjectId(tareasModels.get(i).getHomework().getSubject().getId());
                homeworksModel.setSubjectName(tareasModels.get(i).getHomework().getSubject().getName());
                homeworksModel.setFrecuenciaId(tareasModels.get(i).getHomework().getFrecuenciaId());

                TareasModelHomeworks califi = new TareasModelHomeworks();
                califi.setActive(tareasModels.get(i).getActive());
                califi.setStatus(tareasModels.get(i).getStatus());
                califi.setQualification(tareasModels.get(i).getQualification());
                califi.setDateResolved(tareasModels.get(i).getDateResolved());
                califi.setParentCheck(tareasModels.get(i).getParentCheck());
                califi.setDateDeliver(tareasModels.get(i).getDateDeliver());
                califi.setId(tareasModels.get(i).getId());
                califi.setChoresId(tareasModels.get(i).getChoresId());
                califi.setStudentId(tareasModels.get(i).getStudentId());

                homeworksModel.setTarea(califi);
                homeworks.getHomeworksModels().add(homeworksModel);
                subjects.add(homeworks);
            } else {
                HomeworksHomeworksModel homeworksModel = new HomeworksHomeworksModel();
                homeworksModel.setName(tareasModels.get(i).getHomework().getName());
                homeworksModel.setDescription(tareasModels.get(i).getHomework().getDescription());
                homeworksModel.setActive(tareasModels.get(i).getHomework().getActive());
                homeworksModel.setCreated(tareasModels.get(i).getHomework().getCreated());
                homeworksModel.setDateDeliver(tareasModels.get(i).getHomework().getDateDeliver());
                homeworksModel.setId(tareasModels.get(i).getHomework().getId());
                homeworksModel.setTeacherId(tareasModels.get(i).getHomework().getTeacherId());
                homeworksModel.setFrecuenciaId(tareasModels.get(i).getHomework().getFrecuenciaId());

                TareasModelHomeworks califi = new TareasModelHomeworks();
                califi.setActive(tareasModels.get(i).getActive());
                califi.setStatus(tareasModels.get(i).getStatus());
                califi.setQualification(tareasModels.get(i).getQualification());
                califi.setDateResolved(tareasModels.get(i).getDateResolved());
                califi.setParentCheck(tareasModels.get(i).getParentCheck());
                califi.setDateDeliver(tareasModels.get(i).getDateDeliver());
                califi.setId(tareasModels.get(i).getId());
                califi.setChoresId(tareasModels.get(i).getChoresId());
                califi.setStudentId(tareasModels.get(i).getStudentId());

                homeworksModel.setTarea(califi);
                subjects.get(position).getHomeworksModels().add(homeworksModel);
            }
        }

        if (subjects != null && subjects.size() > 0) {
            rvTareasRealizadas.setVisibility(View.VISIBLE);
            tvNoDatos.setVisibility(View.GONE);

            mAdapter = new AsignaturasAdapter(subjects, R.layout.items_tareas);
            mLayoutManager = new LinearLayoutManager(getContext());

            rvTareasRealizadas.setLayoutManager(mLayoutManager);
            rvTareasRealizadas.setAdapter(mAdapter);
        } else {
            rvTareasRealizadas.setVisibility(View.GONE);
            tvNoDatos.setVisibility(View.VISIBLE);
        }

        tvTareasRealizadasNumero.setText(" " + String.valueOf(tareasModels.size()));
    }

}
