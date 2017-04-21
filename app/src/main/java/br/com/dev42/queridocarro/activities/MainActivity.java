package br.com.dev42.queridocarro.activities;

import android.os.Build;
import android.support.annotation.DrawableRes;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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
import br.com.dev42.queridocarro.fragments.CepFragment;
import br.com.dev42.queridocarro.fragments.GeolocationFragment;
import br.com.dev42.queridocarro.fragments.LocationFragment;
import br.com.dev42.queridocarro.interfaces.MenuOficinasInterface;

public class MainActivity extends AppCompatActivity implements MenuOficinasInterface {

    private PagerAdapter pagerAdapter;
    private ViewPager viewPager;
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

//        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
//            Explode tran1 = new Explode();
//            tran1.setDuration(200);
//
//            Slide tran2 = new Slide();
//            tran2.setDuration(200);
//
//            getWindow().setExitTransition(tran1);
//            getWindow().setReenterTransition(tran2);
//        }


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

    // ** Interface que muda as opçoes das oficinas **
    //  ** usado interface para não replicar codigo dentro dos fragments especialistas **
    @Override
    public void mudaMenu(String statusMenu) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        switch(statusMenu){
            case "GEOLOCATION":
                fragmentTransaction.replace(R.id.content_oficina, new GeolocationFragment());
                break;
            case "CEP":
                fragmentTransaction.replace(R.id.content_oficina, new CepFragment());

                break;
            case "LOCATION":
                fragmentTransaction.replace(R.id.content_oficina, new LocationFragment());
                break;

            default:
                fragmentTransaction.replace(R.id.content_oficina, new GeolocationFragment());
        }
        fragmentTransaction.commit();
    }

}
