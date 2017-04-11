package br.com.dev42.queridocarro.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

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

                //  **  Se tiver placas cadastrada mostra a lista senao a tela de insert    **
//                PlacaDao placaDao = new PlacaDao(context);
//                Integer qtdPlacas = placaDao.quantidadePlacasCadastradas();
//                HistoricoFragment tab1 = new HistoricoFragment();
//                return tab1;
                HistoricoFragment tab1 = new HistoricoFragment();
                return tab1;
            case 1:
//                OficinasFragment tab2 = new OficinasFragment();
//                return tab2;

                OficinaFragment tab2 = new OficinaFragment();
                return tab2;

            case 2:

                IntroFragment tab3 = new IntroFragment();
                return tab3;
            default:
                return null;
        }

    }
}
