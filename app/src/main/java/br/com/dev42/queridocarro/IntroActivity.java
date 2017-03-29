package br.com.dev42.queridocarro;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Switch;

import java.util.ArrayList;
import java.util.List;

import br.com.dev42.queridocarro.adapters.FragmentListAdapter;
import br.com.dev42.queridocarro.fragments.IntroFragment;

public class IntroActivity extends FragmentActivity {

    private ImageView page1,page2,page3;

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

        List<Fragment> listaFragments = new ArrayList<>();
        listaFragments.add(Fragment.instantiate(this, IntroFragment.class.getName()));
        listaFragments.add(Fragment.instantiate(this, IntroFragment.class.getName()));
        listaFragments.add(Fragment.instantiate(this, IntroFragment.class.getName()));

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
