package com.idoogroup.edalumno.ViewPager;

// LIBERIAS DE LA CLASE

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.idoogroup.edalumno.Fragments.CalificacionesFragment;
import com.idoogroup.edalumno.Fragments.EvaluacionesFragment;
import com.idoogroup.edalumno.Fragments.EventosFragment;
import com.idoogroup.edalumno.Fragments.ReconocimientosFragment;
import com.idoogroup.edalumno.Fragments.TareasFragment;


public class ViewPagerPrincipalAdapter extends FragmentPagerAdapter {

    private static int TAB_COUNT = 5;
    public TareasFragment tareasFragment;
    public CalificacionesFragment calificacionesFragment;
    public EvaluacionesFragment evaluacionesFragment;
    public EventosFragment eventosFragment;
    public ReconocimientosFragment reconocimientosFragment;


    // CONSTRUCTOR
    public ViewPagerPrincipalAdapter(FragmentManager fm) {
        super(fm);

        tareasFragment = TareasFragment.newInstance();
        calificacionesFragment = CalificacionesFragment.newInstance();
        evaluacionesFragment = EvaluacionesFragment.newInstance();
        eventosFragment = EventosFragment.newInstance();
        reconocimientosFragment = ReconocimientosFragment.newInstance();
    }


    @Override
    public Fragment getItem(int i) {
        switch (i) {
            case 0: return tareasFragment;
            case 1: return calificacionesFragment;
            case 2: return evaluacionesFragment;
            case 3: return eventosFragment;
            case 4: return reconocimientosFragment;
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
