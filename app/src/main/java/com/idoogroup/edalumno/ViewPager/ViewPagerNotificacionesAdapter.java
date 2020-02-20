package com.idoogroup.edalumno.ViewPager;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.idoogroup.edalumno.Fragments.FragmentNotificaciones.NotificacionesEvaluacionesFragment;
import com.idoogroup.edalumno.Fragments.FragmentNotificaciones.NotificacionesEventosFragment;
import com.idoogroup.edalumno.Fragments.FragmentNotificaciones.NotificacionesTareasFragment;

public class ViewPagerNotificacionesAdapter extends FragmentPagerAdapter {

    // VARIABLES DE LA CLASE
    public NotificacionesTareasFragment notificacionesTareas;
    public NotificacionesEventosFragment notificacionesEventos;
    public NotificacionesEvaluacionesFragment notificacionesEvaluaciones;
    private static int TAB_COUNT = 3;


    public ViewPagerNotificacionesAdapter(FragmentManager fm) {
        super(fm);

        notificacionesTareas = NotificacionesTareasFragment.newInstance();
        notificacionesEventos = NotificacionesEventosFragment.newInstance();
        notificacionesEvaluaciones = NotificacionesEvaluacionesFragment.newInstance();
    }


    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0: return notificacionesTareas;
            case 1: return notificacionesEventos;
            case 2: return notificacionesEvaluaciones;
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
