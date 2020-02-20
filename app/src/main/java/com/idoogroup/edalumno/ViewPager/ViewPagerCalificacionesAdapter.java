package com.idoogroup.edalumno.ViewPager;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.idoogroup.edalumno.Fragments.FragmentCalificaciones.CalificacionesAnualFragment;
import com.idoogroup.edalumno.Fragments.FragmentCalificaciones.CalificacionesDiariasFragment;
import com.idoogroup.edalumno.Fragments.FragmentCalificaciones.CalificacionesMensualesFragment;
import com.idoogroup.edalumno.Fragments.FragmentCalificaciones.CalificacionesSemanalFragment;
import com.idoogroup.edalumno.Fragments.FragmentCalificaciones.CalificacionesSemestralesFragment;
import com.idoogroup.edalumno.Fragments.FragmentCalificaciones.CalificacionesTrimestralesFragment;


public class ViewPagerCalificacionesAdapter extends FragmentStatePagerAdapter {

    private static int TAB_COUNT = 6;
    public CalificacionesDiariasFragment calificacionesDiarias;
    public CalificacionesSemanalFragment calificacionesSemanal;
    public CalificacionesMensualesFragment calificacionesMensuales;
    public CalificacionesTrimestralesFragment calificacionesTrimestrales;
    public CalificacionesSemestralesFragment calificacionesSemestrales;
    public CalificacionesAnualFragment calificacionesAnual;


    public ViewPagerCalificacionesAdapter(FragmentManager fm) {
        super(fm);

        calificacionesDiarias = CalificacionesDiariasFragment.newInstance();
        calificacionesSemanal = CalificacionesSemanalFragment.newInstance();
        calificacionesMensuales = CalificacionesMensualesFragment.newInstance();
        calificacionesTrimestrales = CalificacionesTrimestralesFragment.newInstance();
        calificacionesSemestrales = CalificacionesSemestralesFragment.newInstance();
        calificacionesAnual = CalificacionesAnualFragment.newInstance();
    }


    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: return calificacionesDiarias;
            case 1: return calificacionesSemanal;
            case 2: return calificacionesMensuales;
            case 3: return calificacionesTrimestrales;
            case 4: return calificacionesSemestrales;
            case 5: return calificacionesAnual;
        }

        return null;
    }


    @Override
    public int getCount() {
        return TAB_COUNT;
    }
}
