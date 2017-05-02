package br.com.dev42.queridocarro.activities;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AppCompatActivity;

import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import br.com.dev42.queridocarro.R;
import br.com.dev42.queridocarro.adapters.TabAdapter;
import br.com.dev42.queridocarro.dao.PlacaDao;
import br.com.dev42.queridocarro.extra.ActivityHelper;
import br.com.dev42.queridocarro.extra.HideKeyboard;
import br.com.dev42.queridocarro.fragments.CepFragment;
import br.com.dev42.queridocarro.fragments.GeolocationFragment;
import br.com.dev42.queridocarro.fragments.ListaPlacasFragment;
import br.com.dev42.queridocarro.fragments.LocationFragment;
import br.com.dev42.queridocarro.fragments.PlacaAcessoFragment;
import br.com.dev42.queridocarro.interfaces.MenuOficinasInterface;
import br.com.dev42.queridocarro.interfaces.QueridoCarroInterface;
import br.com.dev42.queridocarro.model.Placa;
import br.com.dev42.queridocarro.model.Token;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements MenuOficinasInterface {

    private PagerAdapter pagerAdapter;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private Activity activity = this;
    private View frameLoad;

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

        ActivityHelper activityHelper = new ActivityHelper(this);
        activityHelper.mudaStatusCorTransparent();

        // ** Remove a Sombra abaixo da actionbar   **
        getSupportActionBar().setElevation(0);

        tabLayout = (TabLayout)findViewById(R.id.tabs);
//        tabLayout.addTab(tabLayout.newTab().setText("Histórico").setIcon(R.drawable.ic_action_call));
//        tabLayout.addTab(tabLayout.newTab().setText("Oficinas"));
//        tabLayout.addTab(tabLayout.newTab().setText("Compare"));

        frameLoad = findViewById(R.id.frameload);

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
                //  ** Esconde o menu na tela de Oficinas e compare **
                if(tab.getPosition() == 1 || tab.getPosition() == 2 ){
                    HideKeyboard hideKeyboard = new HideKeyboard(activity);
                }
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

            case "PLACA":
                fragmentTransaction.replace(R.id.content_historico, new PlacaAcessoFragment());
                break;

            case "LISTAPLACA":
                fragmentTransaction.replace(R.id.content_historico, new ListaPlacasFragment());
                break;
//            default:
//                fragmentTransaction.replace(R.id.content_oficina, new GeolocationFragment());
        }
        fragmentTransaction.commit();
    }

    @Override
    public void getToken(final String placa, final String senha, final Boolean salvar) {

        QueridoCarroInterface service = new Retrofit.Builder()
                .baseUrl(QueridoCarroInterface.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(QueridoCarroInterface.class);

        Token.Envio tokenEnvio = new Token.Envio(placa.replace("-",""),senha);
        Call<Token.Retorno> retToken = service.getToken(tokenEnvio);
        frameLoad.setVisibility(View.VISIBLE);

        retToken.enqueue(new Callback<Token.Retorno>() {
            @Override
            public void onResponse(Call<Token.Retorno> call, Response<Token.Retorno> response) {
                frameLoad.setVisibility(View.GONE);
                if(!response.isSuccessful()){
                    Toast.makeText(activity, R.string.erro_conexao,Toast.LENGTH_LONG ).show();
                }else {
                    Token.Retorno retornoToken = response.body();

                    if(retornoToken.getTokenHash().length() > 5){
                        Intent intent = new Intent(activity, ListaOsActivity.class);
                        intent.putExtra("placa", placa.replace("-",""));
                        intent.putExtra("token", retornoToken.getTokenHash());

                        // TRANSITIONS
//                        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
//                            ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(),null);
//                            startActivity(intent, options.toBundle());
//                        }else
//                        {

                        //  ** Salva placa **
                        if(salvar) {
                            PlacaDao placaDao = new PlacaDao(activity);
                            Placa placaModel = new Placa(placa, senha);
                            //Integer placaId = placaDao.jaCadastrada(placa);

                            if(placaDao.jaCadastrada(placa) > 0){
//                                placaModel.setId(placaId);
//                                placaDao.alterar(placaModel);
//                                Toast.makeText(activity, "UPDATE", Toast.LENGTH_LONG).show();
                            }
                            else {
                                placaDao.inserir(placaModel);
//                                Toast.makeText(activity, "INSERT", Toast.LENGTH_LONG).show();
                            }
                        }
                        startActivity(intent);
//                        }

                    }else {
                        Toast.makeText(activity, getString(R.string.erro_retorno,retornoToken.getErro()),Toast.LENGTH_LONG ).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<Token.Retorno> call, Throwable t) {
                Toast.makeText(activity, "Erro:" + t.getMessage(),Toast.LENGTH_LONG ).show();
                frameLoad.setVisibility(View.GONE);
            }
        });

    }

//    @Override
//    protected void onResume() {
//        Toast.makeText(activity, "RESUME", Toast.LENGTH_LONG).show();
//        super.onResume();
//    }
}
