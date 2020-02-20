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


public class TareasCalificadasFragment extends Fragment {

    private TextView tvTareasCalificadas, tvSelectCero, tvSelectCeroNum, tvSelectCeroText, tvSelectOne, tvSelectOneNum, tvSelectOneText, tvSelectTho, tvSelectThoNum;
    private TextView tvSelectThoText, tvSelectThree, tvSelectThreeNum, tvSelectThreeText, tvSelectFour;
    private TextView tvSelectFourNum, tvSelectFourText, tvSelectFive, tvSelectFiveNum, tvSelectFiveText, tvNoDatos;
    private TextView tvSelectSeis, tvSelectSeisNum, tvSelectSeisText, tvSelectSiete, tvSelectSieteNum, tvSelectSieteText;
    private TextView tvSelectOcho, tvSelectOchoNum, tvSelectOchoText, tvSelectNueve, tvSelectNueveNum, tvSelectNueveText;
    private TextView tvSelectDiez, tvSelectDiezNum, tvSelectDiezText;
    private RecyclerView rvTareasCalificadas;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private View vLineaSeparadora;

    private IdooGroupApplication globalAPP;
    private WebServicesIdooGroup servicesEDUKO;
    private Dialog dialogLoading = null;

    public int calificacion = -1;
    public String idSubject = "-1";


    public TareasCalificadasFragment() {
        // Required empty public constructor
    }


    public static TareasCalificadasFragment newInstance() {
        return new TareasCalificadasFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tareas_calificadas, container, false);

        try {
            // VARIABLE GLOBAL
            globalAPP = (IdooGroupApplication) getActivity().getApplication();
            servicesEDUKO = globalAPP.getService();

            // ASIGNADO LAS VARIABLES
            tvTareasCalificadas = (TextView) v.findViewById(R.id.tvTareasCalificadas);
            tvSelectCero = (TextView) v.findViewById(R.id.tvSelectCero);
            tvSelectOne = (TextView) v.findViewById(R.id.tvSelectOne);
            tvSelectTho = (TextView) v.findViewById(R.id.tvSelectTho);
            tvSelectThree = (TextView) v.findViewById(R.id.tvSelectThree);
            tvSelectFour = (TextView) v.findViewById(R.id.tvSelectFour);
            tvSelectFive = (TextView) v.findViewById(R.id.tvSelectFive);
            tvSelectSeis = (TextView) v.findViewById(R.id.tvSelectSeis);
            tvSelectSiete = (TextView) v.findViewById(R.id.tvSelectSiete);
            tvSelectOcho = (TextView) v.findViewById(R.id.tvSelectOcho);
            tvSelectNueve = (TextView) v.findViewById(R.id.tvSelectNueve);
            tvSelectDiez = (TextView) v.findViewById(R.id.tvSelectDiez);
            tvSelectCeroNum = (TextView) v.findViewById(R.id.tvSelectCeroNum);
            tvSelectOneNum = (TextView) v.findViewById(R.id.tvSelectOneNum);
            tvSelectThoNum = (TextView) v.findViewById(R.id.tvSelectThoNum);
            tvSelectThreeNum = (TextView) v.findViewById(R.id.tvSelectThreeNum);
            tvSelectFourNum = (TextView) v.findViewById(R.id.tvSelectFourNum);
            tvSelectFiveNum = (TextView) v.findViewById(R.id.tvSelectFiveNum);
            tvSelectSeisNum = (TextView) v.findViewById(R.id.tvSelectSeisNum);
            tvSelectSieteNum = (TextView) v.findViewById(R.id.tvSelectSieteNum);
            tvSelectOchoNum = (TextView) v.findViewById(R.id.tvSelectOchoNum);
            tvSelectNueveNum = (TextView) v.findViewById(R.id.tvSelectNueveNum);
            tvSelectDiezNum = (TextView) v.findViewById(R.id.tvSelectDiezNum);
            tvSelectCeroText = (TextView) v.findViewById(R.id.tvSelectCeroText);
            tvSelectOneText = (TextView) v.findViewById(R.id.tvSelectOneText);
            tvSelectThoText = (TextView) v.findViewById(R.id.tvSelectThoText);
            tvSelectThreeText = (TextView) v.findViewById(R.id.tvSelectThreeText);
            tvSelectFourText = (TextView) v.findViewById(R.id.tvSelectFourText);
            tvSelectFiveText = (TextView) v.findViewById(R.id.tvSelectFiveText);
            tvSelectSeisText = (TextView) v.findViewById(R.id.tvSelectSeisText);
            tvSelectSieteText = (TextView) v.findViewById(R.id.tvSelectSieteText);
            tvSelectOchoText = (TextView) v.findViewById(R.id.tvSelectOchoText);
            tvSelectNueveText = (TextView) v.findViewById(R.id.tvSelectNueveText);
            tvSelectDiezText = (TextView) v.findViewById(R.id.tvSelectDiezText);
            tvNoDatos = (TextView) v.findViewById(R.id.tvNoDatos);
            rvTareasCalificadas = (RecyclerView) v.findViewById(R.id.rvTareasCalificadas);
            vLineaSeparadora = (View) v.findViewById(R.id.vLineaSeparadora);

            // ASIGNACION DE FUENTES
            tvTareasCalificadas.setTypeface(globalAPP.getFuenteAvenidHeavy(getActivity().getAssets()));

            tvSelectCero.setTypeface(globalAPP.getFuenteAvenidLight(getActivity().getAssets()));
            tvSelectOne.setTypeface(globalAPP.getFuenteAvenidLight(getActivity().getAssets()));
            tvSelectTho.setTypeface(globalAPP.getFuenteAvenidLight(getActivity().getAssets()));
            tvSelectThree.setTypeface(globalAPP.getFuenteAvenidLight(getActivity().getAssets()));
            tvSelectFour.setTypeface(globalAPP.getFuenteAvenidLight(getActivity().getAssets()));
            tvSelectFive.setTypeface(globalAPP.getFuenteAvenidLight(getActivity().getAssets()));
            tvSelectSeis.setTypeface(globalAPP.getFuenteAvenidLight(getActivity().getAssets()));
            tvSelectSiete.setTypeface(globalAPP.getFuenteAvenidLight(getActivity().getAssets()));
            tvSelectOcho.setTypeface(globalAPP.getFuenteAvenidLight(getActivity().getAssets()));
            tvSelectNueve.setTypeface(globalAPP.getFuenteAvenidLight(getActivity().getAssets()));
            tvSelectDiez.setTypeface(globalAPP.getFuenteAvenidLight(getActivity().getAssets()));

            tvSelectCeroNum.setTypeface(globalAPP.getFuenteAvenidHeavy(getActivity().getAssets()));
            tvSelectOneNum.setTypeface(globalAPP.getFuenteAvenidLight(getActivity().getAssets()));
            tvSelectThoNum.setTypeface(globalAPP.getFuenteAvenidLight(getActivity().getAssets()));
            tvSelectThreeNum.setTypeface(globalAPP.getFuenteAvenidLight(getActivity().getAssets()));
            tvSelectFourNum.setTypeface(globalAPP.getFuenteAvenidLight(getActivity().getAssets()));
            tvSelectFiveNum.setTypeface(globalAPP.getFuenteAvenidLight(getActivity().getAssets()));
            tvSelectSeisNum.setTypeface(globalAPP.getFuenteAvenidLight(getActivity().getAssets()));
            tvSelectSieteNum.setTypeface(globalAPP.getFuenteAvenidLight(getActivity().getAssets()));
            tvSelectOchoNum.setTypeface(globalAPP.getFuenteAvenidLight(getActivity().getAssets()));
            tvSelectNueveNum.setTypeface(globalAPP.getFuenteAvenidLight(getActivity().getAssets()));
            tvSelectDiezNum.setTypeface(globalAPP.getFuenteAvenidLight(getActivity().getAssets()));

            tvSelectCeroText.setTypeface(globalAPP.getFuenteAvenidHeavy(getActivity().getAssets()));
            tvSelectOneText.setTypeface(globalAPP.getFuenteAvenidHeavy(getActivity().getAssets()));
            tvSelectThoText.setTypeface(globalAPP.getFuenteAvenidHeavy(getActivity().getAssets()));
            tvSelectThreeText.setTypeface(globalAPP.getFuenteAvenidHeavy(getActivity().getAssets()));
            tvSelectFourText.setTypeface(globalAPP.getFuenteAvenidHeavy(getActivity().getAssets()));
            tvSelectFiveText.setTypeface(globalAPP.getFuenteAvenidHeavy(getActivity().getAssets()));
            tvSelectSeisText.setTypeface(globalAPP.getFuenteAvenidHeavy(getActivity().getAssets()));
            tvSelectSieteText.setTypeface(globalAPP.getFuenteAvenidHeavy(getActivity().getAssets()));
            tvSelectOchoText.setTypeface(globalAPP.getFuenteAvenidHeavy(getActivity().getAssets()));
            tvSelectNueveText.setTypeface(globalAPP.getFuenteAvenidHeavy(getActivity().getAssets()));
            tvSelectDiezText.setTypeface(globalAPP.getFuenteAvenidHeavy(getActivity().getAssets()));

            tvNoDatos.setTypeface(globalAPP.getFuenteAvenidRoman(getActivity().getAssets()));


            tvSelectCero.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clicCero(v);
                }
            });
            tvSelectCeroNum.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clicCero(v);
                }
            });
            tvSelectCeroText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clicCero(v);
                }
            });

            tvSelectOne.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clicOne(v);
                }
            });
            tvSelectOneNum.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clicOne(v);
                }
            });
            tvSelectOneText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clicOne(v);
                }
            });

            tvSelectTho.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clicTho(v);
                }
            });
            tvSelectThoNum.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clicTho(v);
                }
            });
            tvSelectThoText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clicTho(v);
                }
            });

            tvSelectThree.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clicThree(v);
                }
            });
            tvSelectThreeNum.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clicThree(v);
                }
            });
            tvSelectThreeText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clicThree(v);
                }
            });

            tvSelectFour.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clicFour(v);
                }
            });
            tvSelectFourNum.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clicFour(v);
                }
            });
            tvSelectFourText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clicFour(v);
                }
            });

            tvSelectFive.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clicFive(v);
                }
            });
            tvSelectFiveNum.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clicFive(v);
                }
            });
            tvSelectFiveText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clicFive(v);
                }
            });

            tvSelectSeis.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clicSeis(v);
                }
            });
            tvSelectSeisNum.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clicSeis(v);
                }
            });
            tvSelectSeisText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clicSeis(v);
                }
            });

            tvSelectSiete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clicSiete(v);
                }
            });
            tvSelectSieteNum.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clicSiete(v);
                }
            });
            tvSelectSieteText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clicSiete(v);
                }
            });

            tvSelectOcho.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clicOcho(v);
                }
            });
            tvSelectOchoNum.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clicOcho(v);
                }
            });
            tvSelectOchoText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clicOcho(v);
                }
            });

            tvSelectNueve.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clicNueve(v);
                }
            });
            tvSelectNueveNum.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clicNueve(v);
                }
            });
            tvSelectNueveText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clicNueve(v);
                }
            });

            tvSelectDiez.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clicDiez(v);
                }
            });
            tvSelectDiezNum.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clicDiez(v);
                }
            });
            tvSelectDiezText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clicDiez(v);
                }
            });

            clicFive(v);
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

            if (globalAPP.tareasModels != null) {
                for (int i = 0; i < globalAPP.tareasModels.size(); i++) {
                    if (globalAPP.tareasModels.get(i).getQualification() == calificacion)
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
                rvTareasCalificadas.setVisibility(View.VISIBLE);
                tvNoDatos.setVisibility(View.GONE);

                mAdapter = new AsignaturasAdapter(subjects, R.layout.items_tareas);
                mLayoutManager = new LinearLayoutManager(getContext());

                rvTareasCalificadas.setLayoutManager(mLayoutManager);
                rvTareasCalificadas.setAdapter(mAdapter);
            } else {
                rvTareasCalificadas.setVisibility(View.GONE);
                tvNoDatos.setVisibility(View.VISIBLE);
            }
        } catch (Exception e) {
            Toasty.error(getContext(), getString(R.string.error_app), Toast.LENGTH_SHORT).show();
            Log.e(Constants.TAG, e.getMessage());
        }
    }


    // CLIC CERO
    private void clicCero(View view) {
        if (tvSelectCeroText.getVisibility() == View.VISIBLE) {
            restaurarFiltro(view);
        } else {
            restaurarFiltro(view);
            tvSelectCero.setVisibility(View.GONE);
            tvSelectCeroNum.setVisibility(View.GONE);
            tvSelectCeroText.setVisibility(View.VISIBLE);

            calificacion = 0;
            updateTareas();
        }
    }


    // CLIC ONE
    private void clicOne(View view) {
        if (tvSelectOneText.getVisibility() == View.VISIBLE) {
            restaurarFiltro(view);
        } else {
            restaurarFiltro(view);
            tvSelectOne.setVisibility(View.GONE);
            tvSelectOneNum.setVisibility(View.GONE);
            tvSelectOneText.setVisibility(View.VISIBLE);

            calificacion = 1;
            updateTareas();
        }
    }


    // CLIC THOW
    private void clicTho(View view) {
        if (tvSelectThoText.getVisibility() == View.VISIBLE) {
            restaurarFiltro(view);
        } else {
            restaurarFiltro(view);
            tvSelectTho.setVisibility(View.GONE);
            tvSelectThoNum.setVisibility(View.GONE);
            tvSelectThoText.setVisibility(View.VISIBLE);

            calificacion = 2;
            updateTareas();
        }
    }


    // CLIC THREE
    private void clicThree(View view) {
        if (tvSelectThreeText.getVisibility() == View.VISIBLE) {
            restaurarFiltro(view);
        } else {
            restaurarFiltro(view);
            tvSelectThree.setVisibility(View.GONE);
            tvSelectThreeNum.setVisibility(View.GONE);
            tvSelectThreeText.setVisibility(View.VISIBLE);

            calificacion = 3;
            updateTareas();
        }
    }


    // CLIC FOUR
    private void clicFour(View view) {
        if (tvSelectFourText.getVisibility() == View.VISIBLE) {
            restaurarFiltro(view);
        } else {
            restaurarFiltro(view);
            tvSelectFour.setVisibility(View.GONE);
            tvSelectFourNum.setVisibility(View.GONE);
            tvSelectFourText.setVisibility(View.VISIBLE);

            calificacion = 4;
            updateTareas();
        }
    }


    // CLIC FIVE
    private void clicFive(View view) {
        if (tvSelectFiveText.getVisibility() == View.VISIBLE) {
            restaurarFiltro(view);
        } else {
            restaurarFiltro(view);
            tvSelectFive.setVisibility(View.GONE);
            tvSelectFiveNum.setVisibility(View.GONE);
            tvSelectFiveText.setVisibility(View.VISIBLE);

            calificacion = 5;
            updateTareas();
        }
    }


    // CLIC SEIS
    private void clicSeis(View view) {
        if (tvSelectSeisText.getVisibility() == View.VISIBLE) {
            restaurarFiltro(view);
        } else {
            restaurarFiltro(view);
            tvSelectSeis.setVisibility(View.GONE);
            tvSelectSeisNum.setVisibility(View.GONE);
            tvSelectSeisText.setVisibility(View.VISIBLE);

            calificacion = 6;
            updateTareas();
        }
    }


    // CLIC SIETE
    private void clicSiete(View view) {
        if (tvSelectSieteText.getVisibility() == View.VISIBLE) {
            restaurarFiltro(view);
        } else {
            restaurarFiltro(view);
            tvSelectSiete.setVisibility(View.GONE);
            tvSelectSieteNum.setVisibility(View.GONE);
            tvSelectSieteText.setVisibility(View.VISIBLE);

            calificacion = 7;
            updateTareas();
        }
    }


    // CLIC OCHO
    private void clicOcho(View view) {
        if (tvSelectOchoText.getVisibility() == View.VISIBLE) {
            restaurarFiltro(view);
        } else {
            restaurarFiltro(view);
            tvSelectOcho.setVisibility(View.GONE);
            tvSelectOchoNum.setVisibility(View.GONE);
            tvSelectOchoText.setVisibility(View.VISIBLE);

            calificacion = 8;
            updateTareas();
        }
    }


    // CLIC NUEVE
    private void clicNueve(View view) {
        if (tvSelectNueveText.getVisibility() == View.VISIBLE) {
            restaurarFiltro(view);
        } else {
            restaurarFiltro(view);
            tvSelectNueve.setVisibility(View.GONE);
            tvSelectNueveNum.setVisibility(View.GONE);
            tvSelectNueveText.setVisibility(View.VISIBLE);

            calificacion = 9;
            updateTareas();
        }
    }


    // CLIC DIEZ
    private void clicDiez(View view) {
        if (tvSelectDiezText.getVisibility() == View.VISIBLE) {
            restaurarFiltro(view);
        } else {
            restaurarFiltro(view);
            tvSelectDiez.setVisibility(View.GONE);
            tvSelectDiezNum.setVisibility(View.GONE);
            tvSelectDiezText.setVisibility(View.VISIBLE);

            calificacion = 10;
            updateTareas();
        }
    }


    private void restaurarFiltro(View view) {
        calificacion = -1;

        tvSelectCeroText.setVisibility(View.GONE);
        tvSelectOneText.setVisibility(View.GONE);
        tvSelectThoText.setVisibility(View.GONE);
        tvSelectThreeText.setVisibility(View.GONE);
        tvSelectFourText.setVisibility(View.GONE);
        tvSelectFiveText.setVisibility(View.GONE);
        tvSelectSeisText.setVisibility(View.GONE);
        tvSelectSieteText.setVisibility(View.GONE);
        tvSelectOchoText.setVisibility(View.GONE);
        tvSelectNueveText.setVisibility(View.GONE);
        tvSelectDiezText.setVisibility(View.GONE);

        tvSelectCero.setVisibility(View.VISIBLE);
        tvSelectCeroNum.setVisibility(View.VISIBLE);

        tvSelectOne.setVisibility(View.VISIBLE);
        tvSelectOneNum.setVisibility(View.VISIBLE);

        tvSelectTho.setVisibility(View.VISIBLE);
        tvSelectThoNum.setVisibility(View.VISIBLE);

        tvSelectThree.setVisibility(View.VISIBLE);
        tvSelectThreeNum.setVisibility(View.VISIBLE);

        tvSelectFour.setVisibility(View.VISIBLE);
        tvSelectFourNum.setVisibility(View.VISIBLE);

        tvSelectFive.setVisibility(View.VISIBLE);
        tvSelectFiveNum.setVisibility(View.VISIBLE);

        tvSelectSeis.setVisibility(View.VISIBLE);
        tvSelectSeisNum.setVisibility(View.VISIBLE);

        tvSelectSiete.setVisibility(View.VISIBLE);
        tvSelectSieteNum.setVisibility(View.VISIBLE);

        tvSelectOcho.setVisibility(View.VISIBLE);
        tvSelectOchoNum.setVisibility(View.VISIBLE);

        tvSelectNueve.setVisibility(View.VISIBLE);
        tvSelectNueveNum.setVisibility(View.VISIBLE);

        tvSelectDiez.setVisibility(View.VISIBLE);
        tvSelectDiezNum.setVisibility(View.VISIBLE);
    }

}
