package br.com.dev42.queridocarro.activities;

import android.os.Build;
import android.support.annotation.DrawableRes;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AppCompatActivity;

import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.transition.Explode;
import android.transition.Slide;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import br.com.dev42.queridocarro.R;
import br.com.dev42.queridocarro.adapters.TabAdapter;

public class MainActivity extends AppCompatActivity {

    private PagerAdapter pagerAdapter;
    private ViewPager viewPager;
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            Explode tran1 = new Explode();
            tran1.setDuration(200);

            Slide tran2 = new Slide();
            tran2.setDuration(200);

            getWindow().setExitTransition(tran1);
            getWindow().setReenterTransition(tran2);
        }


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // ** Remove a Sombra abaixo da actionbar   **
        getSupportActionBar().setElevation(0);

        tabLayout = (TabLayout)findViewById(R.id.tabs);
//        tabLayout.addTab(tabLayout.newTab().setText("Histórico").setIcon(R.drawable.ic_action_call));
//        tabLayout.addTab(tabLayout.newTab().setText("Oficinas"));
//        tabLayout.addTab(tabLayout.newTab().setText("Compare"));

        //  ** testando

        //View tab = LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        //tabLayout.getTabAt(0).setCustomView(tab);

        //  ** testando

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        viewPager = (ViewPager)findViewById(R.id.container);
        pagerAdapter = new TabAdapter(getSupportFragmentManager(), tabLayout.getTabCount(), this);
        viewPager.setAdapter(pagerAdapter);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

}
