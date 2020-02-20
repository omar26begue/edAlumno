package com.idoogroup.edalumno.ViewPager;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.idoogroup.edalumno.Fragments.FragmentEvaluaciones.EvaluacionesAnualFragment;
import com.idoogroup.edalumno.Fragments.FragmentEvaluaciones.EvaluacionesDiariasFragment;
import com.idoogroup.edalumno.Fragments.FragmentEvaluaciones.EvaluacionesMensualesFragment;
import com.idoogroup.edalumno.Fragments.FragmentEvaluaciones.EvaluacionesSemanalesFragment;
import com.idoogroup.edalumno.Fragments.FragmentEvaluaciones.EvaluacionesSemestralesFragment;
import com.idoogroup.edalumno.Fragments.FragmentEvaluaciones.EvaluacionesTrimestralesFragment;

public class ViewPagerEvaluacionesAdapter extends FragmentPagerAdapter {

    private static int TAB_COUNT = 6;
    public EvaluacionesDiariasFragment evaluacionesDiarias;
    public EvaluacionesSemanalesFragment evaluacionesSemanales;
    public EvaluacionesMensualesFragment evaluacionesMensuales;
    public EvaluacionesTrimestralesFragment evaluacionesTrimestrales;
    public EvaluacionesSemestralesFragment evaluacionesSemestrales;
    public EvaluacionesAnualFragment evaluacionesAnual;


    public ViewPagerEvaluacionesAdapter(FragmentManager fm) {
        super(fm);

        evaluacionesDiarias = EvaluacionesDiariasFragment.newInstance();
        evaluacionesSemanales = EvaluacionesSemanalesFragment.newInstance();
        evaluacionesMensuales = EvaluacionesMensualesFragment.newInstance();
        evaluacionesTrimestrales = EvaluacionesTrimestralesFragment.newInstance();
        evaluacionesSemestrales = EvaluacionesSemestralesFragment.newInstance();
        evaluacionesAnual = EvaluacionesAnualFragment.newInstance();
    }


    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0: return evaluacionesDiarias;
            case 1: return evaluacionesSemanales;
            case 2: return evaluacionesMensuales;
            case 3: return evaluacionesTrimestrales;
            case 4: return evaluacionesSemestrales;
            case 5: return evaluacionesAnual;
        }
        return null;
    }


    @Override
    public int getCount() {
        return TAB_COUNT;
    }


    @Override
    public CharSequence getPageTitle(int position) {
        return "";
    }

}
