package br.com.dev42.queridocarro.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import java.util.List;

/**
 * Created by sossai on 29/03/17.
 */

public class FragmentListAdapter extends FragmentPagerAdapter implements ViewPager.OnPageChangeListener {

    private List<Fragment> listaFragments;

    public FragmentListAdapter(FragmentManager fm, List<Fragment> listaFragments) {
        super(fm);
        this.listaFragments = listaFragments;
    }

    @Override
    public int getCount() {
        return listaFragments.size();
    }

    @Override
    public Fragment getItem(int position) {
        return this.listaFragments.get(position);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
