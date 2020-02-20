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

import com.idoogroup.edalumno.Adapters.TareasXRealizarAdaper;
import com.idoogroup.edalumno.Helpers.Constants;
import com.idoogroup.edalumno.IdooGroupApplication;
import com.idoogroup.edalumno.Interfaces.TareasCallback;
import com.idoogroup.edalumno.Models.HomeworksHomeworksModel;
import com.idoogroup.edalumno.Models.SubjectModelHomeworks;
import com.idoogroup.edalumno.Models.TareasModel;
import com.idoogroup.edalumno.Models.TareasModelHomeworks;
import com.idoogroup.edalumno.R;
import com.idoogroup.edalumno.WebServicesIdooGroup;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import es.dmoral.toasty.Toasty;


public class TareasEntregarMananaFragment extends Fragment {

    public String idSubject = "-1";


    private TextView tvTareasEntregarManana, tvNoDatos;
    private RecyclerView rvTareasEntregarManana;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private TareasCallback tareasCallback;

    private IdooGroupApplication globalAPP;
    private WebServicesIdooGroup servicesEDUKO;
    private Dialog dialogLoading = null;


    public TareasEntregarMananaFragment(TareasCallback tareasCallback) {
        this.tareasCallback = tareasCallback;
    }


    public static TareasEntregarMananaFragment newInstance(TareasCallback tareasCallback) {
        return new TareasEntregarMananaFragment(tareasCallback);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_tareas_entregar_manana, container, false);

        try {
            // VARIABLE GLOBAL
            globalAPP = (IdooGroupApplication) getActivity().getApplication();
            servicesEDUKO = globalAPP.getService();

            // ASIGNADO LAS VARIABLES
            tvTareasEntregarManana = (TextView) v.findViewById(R.id.tvTareasEntregarManana);
            tvNoDatos = (TextView) v.findViewById(R.id.tvNoDatos);
            rvTareasEntregarManana = (RecyclerView) v.findViewById(R.id.rvTareasEntregarManana);

            // ASIGNACION DE FUENTES
            tvTareasEntregarManana.setTypeface(globalAPP.getFuenteAvenidHeavy(getActivity().getAssets()));
            tvNoDatos.setTypeface(globalAPP.getFuenteAvenidRoman(getActivity().getAssets()));
        } catch (Exception e) {
            Toasty.error(getContext(), getString(R.string.error_app), Toast.LENGTH_SHORT).show();
            Log.e(Constants.TAG, e.getMessage());
        }

        return v;
    }


    public void updateTareas() {
        try {
            List<TareasModel> tareasModels = new ArrayList<>();
            List<SubjectModelHomeworks> subjects = new ArrayList<>();
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            int dia = calendar.get(Calendar.DAY_OF_MONTH);
            int mes = calendar.get(Calendar.MONTH);
            int ano = calendar.get(Calendar.YEAR);

            for (int i = 0; i < globalAPP.tareasModels.size(); i++) {
                if (globalAPP.tareasModels.get(i).getStatus().equals("pending")) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    Date date = sdf.parse(globalAPP.tareasModels.get(i).getHomework().getDateDeliver().substring(0, 10));
                    Calendar calendar1 = Calendar.getInstance();
                    calendar1.setTime(date);
                    int diaResuelta = calendar1.get(Calendar.DAY_OF_MONTH);
                    int mesResuelta = calendar1.get(Calendar.MONTH);
                    int anoResuelta = calendar1.get(Calendar.YEAR);

                    if (idSubject.equals("-1")) {
                        if (ano == anoResuelta) {
                            if (mes == mesResuelta) {
                                if (dia == diaResuelta) {
                                    tareasModels.add(globalAPP.tareasModels.get(i));
                                }
                            }
                        }
                    } else {
                        String subject = globalAPP.tareasModels.get(i).getHomework().getSubject().getId();
                        if (subject.equals(idSubject))
                            tareasModels.add(globalAPP.tareasModels.get(i));
                    }
                }
            }

            for (int i = 0; i < tareasModels.size(); i++) {
                Boolean agregar = true;
                int position = -1;

                for (int j = 0; j < subjects.size(); j++) {
                    if (tareasModels.get(i).getHomework().getSubject().getId().equals(subjects.get(j).getId())) {
                        agregar = false;
                        position = j;
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
                rvTareasEntregarManana.setVisibility(View.VISIBLE);
                tvNoDatos.setVisibility(View.GONE);

                mAdapter = new TareasXRealizarAdaper(subjects, R.layout.items_tareas, tareasCallback);
                mLayoutManager = new LinearLayoutManager(getContext());

                rvTareasEntregarManana.setLayoutManager(mLayoutManager);
                rvTareasEntregarManana.setAdapter(mAdapter);
            } else {
                rvTareasEntregarManana.setVisibility(View.GONE);
                tvNoDatos.setVisibility(View.VISIBLE);
            }
        } catch (Exception e) {
            Toasty.error(getContext(), getString(R.string.error_app), Toast.LENGTH_SHORT).show();
            Log.e(Constants.TAG, e.getMessage());
        }
    }

}
