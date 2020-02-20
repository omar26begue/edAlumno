package com.idoogroup.edalumno.ViewPager;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.idoogroup.edalumno.Fragments.FragmentEventos.EventosAsistenciaNoVerificadaFragment;
import com.idoogroup.edalumno.Fragments.FragmentEventos.EventosAsistenciaTodosFragment;
import com.idoogroup.edalumno.Fragments.FragmentEventos.EventosAsistenciaVerificadaFragment;

public class ViewPagerEventosAdapter extends FragmentPagerAdapter {

    private static int TAB_COUNT = 3;
    public EventosAsistenciaVerificadaFragment eventosAsistenciaVerificadaFragment;
    public EventosAsistenciaNoVerificadaFragment eventosAsistenciaNoVerificadaFragment;
    public EventosAsistenciaTodosFragment eventosAsistenciaTodosFragment;


    public ViewPagerEventosAdapter(FragmentManager fm) {
        super(fm);

        eventosAsistenciaVerificadaFragment = EventosAsistenciaVerificadaFragment.newInstance();
        eventosAsistenciaNoVerificadaFragment = EventosAsistenciaNoVerificadaFragment.newInstance();
        eventosAsistenciaTodosFragment = EventosAsistenciaTodosFragment.newInstance();
    }


    @Override
    public Fragment getItem(int position) {

        if (position == 0) {
            return eventosAsistenciaVerificadaFragment;
        } else if (position == 1) {
            return eventosAsistenciaNoVerificadaFragment;
        } else if (position == 2) {
            return eventosAsistenciaTodosFragment;
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
