package br.com.dev42.queridocarro.activities;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import br.com.dev42.queridocarro.R;
import br.com.dev42.queridocarro.adapters.FragmentListAdapter;
import br.com.dev42.queridocarro.fragments.IntroFragment;
import br.com.dev42.queridocarro.fragments.IntroFragmentDados;

public class IntroActivity extends FragmentActivity {

    private ImageView page1,page2,page3;
    private Button btnInicio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        inicioIntro();
    }
    private void inicioIntro(){

        page1 = (ImageView)findViewById(R.id.page1);
        page2 = (ImageView)findViewById(R.id.page2);
        page3 = (ImageView)findViewById(R.id.page3);

        btnInicio = (Button)findViewById(R.id.btnInicio);

        List<Fragment> listaFragments = new ArrayList<>();
        listaFragments.add(Fragment.instantiate(this, IntroFragment.class.getName()));
        listaFragments.add(Fragment.instantiate(this, IntroFragment.class.getName()));
        listaFragments.add(Fragment.instantiate(this, IntroFragment.class.getName()));
//        listaFragments.add(Fragment.instantiate(this, IntroFragmentDados.class.getName()));

        Bundle bundle = new Bundle();
        bundle.putInt("intro",0);
        listaFragments.get(0).setArguments(bundle);

        bundle = new Bundle();
        bundle.putInt("intro",1);
        listaFragments.get(1).setArguments(bundle);

        bundle = new Bundle();
        bundle.putInt("intro",2);
        listaFragments.get(2).setArguments(bundle);


        PagerAdapter pagerAdapter = new FragmentListAdapter(this.getSupportFragmentManager(), listaFragments);

        ViewPager viewPager = (ViewPager)findViewById(R.id.viewp);
        viewPager.setAdapter(pagerAdapter);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                mudaMarcadorPagina(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        btnInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IntroActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void mudaMarcadorPagina(Integer pagina){
        switch(pagina){
            case 0:
                page1.setBackground(ContextCompat.getDrawable(IntroActivity.this,R.drawable.page_on));
                page2.setBackground(ContextCompat.getDrawable(IntroActivity.this,R.drawable.page_off));
                page3.setBackground(ContextCompat.getDrawable(IntroActivity.this,R.drawable.page_off));
                break;
            case 1:
                page1.setBackground(ContextCompat.getDrawable(IntroActivity.this,R.drawable.page_off));
                page2.setBackground(ContextCompat.getDrawable(IntroActivity.this,R.drawable.page_on));
                page3.setBackground(ContextCompat.getDrawable(IntroActivity.this,R.drawable.page_off));

                break;
            case 2:
                page1.setBackground(ContextCompat.getDrawable(IntroActivity.this,R.drawable.page_off));
                page2.setBackground(ContextCompat.getDrawable(IntroActivity.this,R.drawable.page_off));
                page3.setBackground(ContextCompat.getDrawable(IntroActivity.this,R.drawable.page_on));
                break;
        }
    }
}
