package com.idoogroup.edalumno.ViewPager;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.idoogroup.edalumno.Fragments.FragmentReconocimientos.ReconocimientosAnualFragment;
import com.idoogroup.edalumno.Fragments.FragmentReconocimientos.ReconocimientosDiarioFragment;
import com.idoogroup.edalumno.Fragments.FragmentReconocimientos.ReconocimientosMensualFragment;
import com.idoogroup.edalumno.Fragments.FragmentReconocimientos.ReconocimientosSemanalFragment;
import com.idoogroup.edalumno.Fragments.FragmentReconocimientos.ReconocimientosSemestralesFragment;
import com.idoogroup.edalumno.Fragments.FragmentReconocimientos.ReconocimientosTrimestralesFragment;

public class ViewPagerReconocimientosAdapter extends FragmentPagerAdapter {

    // VARIABLES DE LA CLASE
    private static int TAB_COUNT = 6;
    public ReconocimientosDiarioFragment reconocimientosDiarioFragment;
    public ReconocimientosSemanalFragment reconocimientosSemanalFragment;
    public ReconocimientosMensualFragment reconocimientosMensualFragment;
    public ReconocimientosTrimestralesFragment reconocimientosTrimestrales;
    public ReconocimientosSemestralesFragment reconocimientosSemestrales;
    public ReconocimientosAnualFragment reconocimientosAnualFragment;


    public ViewPagerReconocimientosAdapter(FragmentManager fm) {
        super(fm);

        reconocimientosDiarioFragment = ReconocimientosDiarioFragment.newInstance();
        reconocimientosSemanalFragment = ReconocimientosSemanalFragment.newInstance();
        reconocimientosMensualFragment = ReconocimientosMensualFragment.newInstance();
        reconocimientosTrimestrales = ReconocimientosTrimestralesFragment.newInstance();
        reconocimientosSemestrales = ReconocimientosSemestralesFragment.newInstance();
        reconocimientosAnualFragment = ReconocimientosAnualFragment.newInstance();
    }


    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0: return reconocimientosDiarioFragment;
            case 1: return reconocimientosSemanalFragment;
            case 2: return reconocimientosMensualFragment;
            case 3: return reconocimientosTrimestrales;
            case 4: return reconocimientosSemestrales;
            case 5: return reconocimientosAnualFragment;
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
