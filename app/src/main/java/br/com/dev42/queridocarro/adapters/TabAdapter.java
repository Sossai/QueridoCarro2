package br.com.dev42.queridocarro.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import br.com.dev42.queridocarro.fragments.CompareFragment;
import br.com.dev42.queridocarro.fragments.HistoricoFragment;
import br.com.dev42.queridocarro.fragments.IntroFragment;
import br.com.dev42.queridocarro.fragments.OficinaFragment;

/**
 * Created by Fernando on 01/08/2016.
 */
public class TabAdapter extends FragmentStatePagerAdapter {

    int mNumOfTabs;
    Context context;

    public TabAdapter(FragmentManager fm, int NumOfTabs, Context context){
        super(fm);
        this.mNumOfTabs = NumOfTabs;
        this.context = context;
    }
    @Override
    public int getCount() {
        return mNumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:

                HistoricoFragment tab1 = new HistoricoFragment();
                return tab1;
            case 1:

                OficinaFragment tab2 = new OficinaFragment();
                return tab2;

//            case 2:
//
//                CompareFragment tab3 = new CompareFragment();
//                return tab3;

            default:
                return null;
        }

    }
}
