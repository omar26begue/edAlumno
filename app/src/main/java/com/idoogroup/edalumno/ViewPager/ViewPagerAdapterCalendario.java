package com.idoogroup.edalumno.ViewPager;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.idoogroup.edalumno.Fragments.FragmentCalendario.JuevesFragment;
import com.idoogroup.edalumno.Fragments.FragmentCalendario.LunesFragment;
import com.idoogroup.edalumno.Fragments.FragmentCalendario.MartesFragment;
import com.idoogroup.edalumno.Fragments.FragmentCalendario.MiercolesFragment;
import com.idoogroup.edalumno.Fragments.FragmentCalendario.ViernesFragment;

public class ViewPagerAdapterCalendario extends FragmentPagerAdapter {

    private static int TAB_COUNT = 5;
    public LunesFragment lunesFragment;
    public MartesFragment martesFragment;
    public MiercolesFragment miercolesFragment;
    public JuevesFragment juevesFragment;
    public ViernesFragment viernesFragment;


    // CONSTRUCTOR
    public ViewPagerAdapterCalendario(FragmentManager fm) {
        super(fm);

        lunesFragment = LunesFragment.newInstance();
        martesFragment = MartesFragment.newInstance();
        miercolesFragment = MiercolesFragment.newInstance();
        juevesFragment = JuevesFragment.newInstance();
        viernesFragment = ViernesFragment.newInstance();
    }


    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: return lunesFragment;
            case 1: return martesFragment;
            case 2: return miercolesFragment;
            case 3: return juevesFragment;
            case 4: return viernesFragment;
        }

        return null;
    }


    @Override
    public int getCount() {
        return TAB_COUNT;
    }


    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0: return LunesFragment.TITLE;
            case 1: return MartesFragment.TITLE;
            case 2: return MiercolesFragment.TITLE;
            case 3: return JuevesFragment.TITLE;
            case 4: return ViernesFragment.TITLE;
        }

        return null;
    }
}
