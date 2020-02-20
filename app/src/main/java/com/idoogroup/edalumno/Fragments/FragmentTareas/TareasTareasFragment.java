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
import androidx.recyclerview.widget.DefaultItemAnimator;
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


public class TareasTareasFragment extends Fragment {

    // VARIABLES DE LA CLASE
    private TextView tvTareasTodas, tvTareasTodasCalificadas, tvTareasTodasRealizadas, tvTareasTodasXRealizar;
    private TextView tvNoDatosCalificadas, tvNoDatosRealizadas, tvNoDatosXRealizar;
    private RecyclerView rvTareasTodasCalificadas, rvTareasTodasRealizadas, rvTareasTodasXRealizadas;
    private RecyclerView.Adapter mAdapterCalificadas, mAdapterRealizadas, mAdapterXRealizar;
    private RecyclerView.LayoutManager mLayoutManagerCalicadas, mLayoutManagerRealizadas, mLayoutManagerXRealizar;

    private IdooGroupApplication globalAPP;
    private WebServicesIdooGroup servicesEDUKO;
    private Dialog dialogLoading = null;

    public String idSubject = "-1";


    public TareasTareasFragment() {
        // Required empty public constructor
    }


    public static TareasTareasFragment newInstance() {
        return new TareasTareasFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_tareas_tareas, container, false);

        try {
            // VARIABLE GLOBAL
            globalAPP = (IdooGroupApplication) getActivity().getApplication();
            servicesEDUKO = globalAPP.getService();

            // ASIGNADO LAS VARIABLES
            tvTareasTodas = (TextView) v.findViewById(R.id.tvTareasTodas);
            tvTareasTodasCalificadas = (TextView) v.findViewById(R.id.tvTareasTodasCalificadas);
            tvTareasTodasRealizadas = (TextView) v.findViewById(R.id.tvTareasTodasRealizadas);
            tvTareasTodasXRealizar = (TextView) v.findViewById(R.id.tvTareasTodasXRealizar);
            tvNoDatosCalificadas = (TextView) v.findViewById(R.id.tvNoDatosCalificadas);
            tvNoDatosRealizadas = (TextView) v.findViewById(R.id.tvNoDatosRealizadas);
            tvNoDatosXRealizar = (TextView) v.findViewById(R.id.tvNoDatosXRealizar);
            rvTareasTodasCalificadas = (RecyclerView) v.findViewById(R.id.rvTareasTodasCalificadas);
            rvTareasTodasRealizadas = (RecyclerView) v.findViewById(R.id.rvTareasTodasRealizadas);
            rvTareasTodasXRealizadas = (RecyclerView) v.findViewById(R.id.rvTareasTodasXRealizadas);


            // ASIGNACION DE FUENTES
            tvTareasTodas.setTypeface(globalAPP.getFuenteAvenidHeavy(getActivity().getAssets()));
            tvTareasTodasCalificadas.setTypeface(globalAPP.getFuenteAvenidBook(getActivity().getAssets()));
            tvTareasTodasRealizadas.setTypeface(globalAPP.getFuenteAvenidBook(getActivity().getAssets()));
            tvTareasTodasXRealizar.setTypeface(globalAPP.getFuenteAvenidBook(getActivity().getAssets()));
            tvNoDatosCalificadas.setTypeface(globalAPP.getFuenteAvenidRoman(getActivity().getAssets()));
            tvNoDatosRealizadas.setTypeface(globalAPP.getFuenteAvenidRoman(getActivity().getAssets()));
            tvNoDatosXRealizar.setTypeface(globalAPP.getFuenteAvenidRoman(getActivity().getAssets()));


            mLayoutManagerCalicadas = new LinearLayoutManager(v.getContext());
            mLayoutManagerRealizadas = new LinearLayoutManager(v.getContext());
            mLayoutManagerXRealizar = new LinearLayoutManager(v.getContext());

            // ANIMACIONES RECYCLERVIEW
            rvTareasTodasCalificadas.setHasFixedSize(true);
            rvTareasTodasRealizadas.setHasFixedSize(true);
            rvTareasTodasXRealizadas.setHasFixedSize(true);
            rvTareasTodasCalificadas.setItemAnimator(new DefaultItemAnimator());
            rvTareasTodasRealizadas.setItemAnimator(new DefaultItemAnimator());
            rvTareasTodasXRealizadas.setItemAnimator(new DefaultItemAnimator());

            rvTareasTodasCalificadas.setLayoutManager(mLayoutManagerCalicadas);
            rvTareasTodasRealizadas.setLayoutManager(mLayoutManagerRealizadas);
            rvTareasTodasXRealizadas.setLayoutManager(mLayoutManagerXRealizar);
            rvTareasTodasCalificadas.setAdapter(mAdapterCalificadas);
            rvTareasTodasRealizadas.setAdapter(mAdapterRealizadas);
            rvTareasTodasXRealizadas.setAdapter(mAdapterXRealizar);
        } catch (Exception e) {
            Toasty.error(getContext(), getString(R.string.error_app), Toast.LENGTH_SHORT).show();
            Log.e(Constants.TAG, e.getMessage());
        }

        return v;
    }


    public void updateTareas() {
        updateTareasCalificadas();
        updateTareasRealizadas();
        updateTareaXRealizar();
    }


    private void updateTareasCalificadas() {
        try {
            List<TareasModel> tareasModels = new ArrayList<>();
            List<SubjectModelHomeworks> subjects = new ArrayList<>();

            for (int i = 0; i < globalAPP.tareasModels.size(); i++) {
                if (globalAPP.tareasModels.get(i).getQualification() > 0)
                    if (idSubject.equals("-1")) {
                        tareasModels.add(globalAPP.tareasModels.get(i));
                    } else {
                        String subject = globalAPP.tareasModels.get(i).getHomework().getSubject().getId();
                        if (subject.equals(idSubject))
                            tareasModels.add(globalAPP.tareasModels.get(i));
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
                rvTareasTodasCalificadas.setVisibility(View.VISIBLE);
                tvNoDatosCalificadas.setVisibility(View.GONE);

                mAdapterCalificadas = new AsignaturasAdapter(subjects, R.layout.items_tareas);
                mLayoutManagerCalicadas = new LinearLayoutManager(getContext());

                rvTareasTodasCalificadas.setLayoutManager(mLayoutManagerCalicadas);
                rvTareasTodasCalificadas.setAdapter(mAdapterCalificadas);
            } else {
                rvTareasTodasCalificadas.setVisibility(View.GONE);
                tvNoDatosCalificadas.setVisibility(View.VISIBLE);
            }
        } catch (Exception e) {
            Toasty.error(getContext(), getString(R.string.error_app), Toast.LENGTH_SHORT).show();
            Log.e(Constants.TAG, e.getMessage());
        }
    }


    private void updateTareasRealizadas() {
        try {
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
                        if (subject.equals(idSubject))
                            if (globalAPP.tareasModels.get(i).getQualification() == 0) {
                                tareasModels.add(globalAPP.tareasModels.get(i));
                            }
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
                rvTareasTodasRealizadas.setVisibility(View.VISIBLE);
                tvNoDatosRealizadas.setVisibility(View.GONE);

                mAdapterRealizadas = new AsignaturasAdapter(subjects, R.layout.items_tareas);
                mLayoutManagerRealizadas = new LinearLayoutManager(getContext());

                rvTareasTodasRealizadas.setLayoutManager(mLayoutManagerRealizadas);
                rvTareasTodasRealizadas.setAdapter(mAdapterRealizadas);
            } else {
                rvTareasTodasRealizadas.setVisibility(View.GONE);
                tvNoDatosRealizadas.setVisibility(View.VISIBLE);
            }
        } catch (Exception e) {
            Toasty.error(getContext(), getString(R.string.error_app), Toast.LENGTH_SHORT).show();
            Log.e(Constants.TAG, e.getMessage());
        }
    }


    private void updateTareaXRealizar() {
        try {
            List<TareasModel> tareasModels = new ArrayList<>();
            List<SubjectModelHomeworks> subjects = new ArrayList<>();

            for (int i = 0; i < globalAPP.tareasModels.size(); i++) {
                if (globalAPP.tareasModels.get(i).getStatus().equals("pending")) {
                    if (idSubject.equals("-1")) {
                        tareasModels.add(globalAPP.tareasModels.get(i));
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
                rvTareasTodasXRealizadas.setVisibility(View.VISIBLE);
                tvNoDatosXRealizar.setVisibility(View.GONE);

                mAdapterXRealizar = new AsignaturasAdapter(subjects, R.layout.items_tareas);
                mLayoutManagerXRealizar = new LinearLayoutManager(getContext());

                rvTareasTodasXRealizadas.setLayoutManager(mLayoutManagerXRealizar);
            /*SlideInBottomAnimatorAdapter animatorAdapter = new SlideInBottomAnimatorAdapter(mAdapterXRealizar, rvTareasTodasXRealizadas);
            ScaleInAnimatorAdapter animatorAdapter1 = new ScaleInAnimatorAdapter(animatorAdapter, rvTareasTodasXRealizadas);*/
                rvTareasTodasXRealizadas.setAdapter(mAdapterXRealizar);
            } else {
                rvTareasTodasXRealizadas.setVisibility(View.GONE);
                tvNoDatosXRealizar.setVisibility(View.VISIBLE);
            }
        } catch (Exception e) {
            Toasty.error(getContext(), getString(R.string.error_app), Toast.LENGTH_SHORT).show();
            Log.e(Constants.TAG, e.getMessage());
        }
    }

}
