package com.idoogroup.edalumno.ViewPager;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.idoogroup.edalumno.Fragments.FragmentTareas.TareasCalificadasFragment;
import com.idoogroup.edalumno.Fragments.FragmentTareas.TareasEntregarMananaFragment;
import com.idoogroup.edalumno.Fragments.FragmentTareas.TareasRealizadasFragment;
import com.idoogroup.edalumno.Fragments.FragmentTareas.TareasTareasFragment;
import com.idoogroup.edalumno.Fragments.FragmentTareas.TareasXRealizarFragment;
import com.idoogroup.edalumno.Interfaces.TareasCallback;

public class ViewPagerTareasAdapter extends FragmentStatePagerAdapter {

    private static int TAB_COUNT = 5;
    public TareasCalificadasFragment tareasCalificadas;
    public TareasRealizadasFragment tareasRealizadas;
    public TareasEntregarMananaFragment tareasEntregar;
    public TareasXRealizarFragment tareasXRealizar;
    public TareasTareasFragment tareasTareas;


    public ViewPagerTareasAdapter(FragmentManager fm, TareasCallback tareasCallback) {
        super(fm);

        tareasCalificadas = TareasCalificadasFragment.newInstance();
        tareasRealizadas = TareasRealizadasFragment.newInstance();
        tareasEntregar = TareasEntregarMananaFragment.newInstance(tareasCallback);
        tareasXRealizar = TareasXRealizarFragment.newInstance(tareasCallback);
        tareasTareas = TareasTareasFragment.newInstance();
    }


    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0: return tareasCalificadas;
            case 1: return tareasRealizadas;
            case 2: return tareasEntregar;
            case 3: return tareasXRealizar;
            case 4: return tareasTareas;

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
