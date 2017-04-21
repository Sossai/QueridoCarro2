package br.com.dev42.queridocarro.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.nfc.Tag;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Slide;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import br.com.dev42.queridocarro.R;
import br.com.dev42.queridocarro.adapters.OsAdapter;
import br.com.dev42.queridocarro.extra.CorSigla;
import br.com.dev42.queridocarro.interfaces.QueridoCarroInterface;
import br.com.dev42.queridocarro.model.Historico;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.R.attr.button;

public class ListaOsActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    private static final String TAG = "DEV42";

    private QueridoCarroInterface service;
    private Activity activity;
    private FrameLayout frameload;
    private ListView listViewOs;
    private String ultimaData;
    private List<Historico.Retorno> retornoHistorico = null;

    private String placa;
    private String token;
    private Boolean carregarMaisItens = true;
    private OsAdapter adapterOs = null;
    private static final Integer QUANTIDADE_OS = 10;
    private SwipeRefreshLayout swipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TRANSITIONS
    //        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
//            Fade tran1 = new Fade();
//            tran1.setDuration(2000);
//
//            Slide tran2 = new Slide();
//            tran2.setDuration(200);
//
//            getWindow().setEnterTransition(tran1);
//            getWindow().setReturnTransition(tran2);
//        }

        //  **  Action Bar return   **
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        try{
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }catch (Exception e ){
            //Log.e("DEV42", e.getMessage());
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_os);

        activity = this;
        frameload = (FrameLayout)findViewById(R.id.frameload);

        service = new Retrofit.Builder()
                .baseUrl(QueridoCarroInterface.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(QueridoCarroInterface.class);

        placa = getIntent().getStringExtra("placa");
        token = getIntent().getStringExtra("token");

        listViewOs = (ListView)findViewById(R.id.listaOsV);

        swipe = (SwipeRefreshLayout)findViewById(R.id.swipe);
        swipe.setOnRefreshListener(this);

        listViewOs.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if(firstVisibleItem + visibleItemCount == totalItemCount && totalItemCount !=0 ){
                    if(carregarMaisItens){
                        //Toast.makeText(activity, "Load", Toast.LENGTH_SHORT).show();
                        getHistorico(placa, token, QUANTIDADE_OS, ultimaData);
//                        Log.e(TAG, "Mais : " + QUANTIDADE_OS.toString());
                        carregarMaisItens = false;
                    }

                }
            }
        });

        listViewOs.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Historico.Retorno os = retornoHistorico.get(position);
                Intent intent = new Intent(ListaOsActivity.this, ListaOsItensActivity.class);
                intent.putExtra("CNPJ", os.getCpfCnpj());
                intent.putExtra("NUMVENDA", os.getNumVenda());
                intent.putExtra("TOKEN", token);
                intent.putExtra("NOMEOFICINA", os.getOficina().trim());
                intent.putExtra("COR", CorSigla.escolheCor(os.getOficina()));

                startActivity(intent);
            }
        });

        getHistorico(placa, token, QUANTIDADE_OS, "");
    }

    @Override
    public void onRefresh() {
        retornoHistorico.clear();
        listViewOs.setAdapter(null);
        getHistorico(placa, token, QUANTIDADE_OS, "");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem){
        switch (menuItem.getItemId()){
            case android.R.id.home:
                super.onBackPressed();
                return true;
        }
        return true;
    }
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    //  ** Pego o Histórico **
    protected void getHistorico(String placa, String token, Integer quantidade, String ultimaDataParm){

        //Historico.Envio histEnvio = new Historico.Envio(placa.getText().toString().replace("-",""),"2016-11-28","2016-11-28", token);
        Historico.Envio histEnvio = new Historico.Envio(placa, ultimaDataParm,quantidade, token);
        Call<List<Historico.Retorno>> listHistorico = service.getHistorico(histEnvio);
        frameload.setVisibility(View.VISIBLE);

        listHistorico.enqueue(new Callback<List<Historico.Retorno>>() {
            @Override
            public void onResponse(Call<List<Historico.Retorno>> call, Response<List<Historico.Retorno>> response) {
                if(!response.isSuccessful()){
//                    Log.e(TAG, "Erro Historico.:" + response.code());
                    frameload.setVisibility(View.GONE);
                    swipe.setRefreshing(false);
                    swipe.clearAnimation();

                }else {
                    frameload.setVisibility(View.GONE);
                    swipe.setRefreshing(false);
                    swipe.clearAnimation();

                    List<Historico.Retorno> retornoHistoricoAux = response.body();

                    if(!retornoHistoricoAux.isEmpty()){

                        //  ** Data da ultima OS **
                        ultimaData = retornoHistoricoAux.get(retornoHistoricoAux.size()-1).getData();

                        if(listViewOs.getAdapter()==null){
                            retornoHistorico = retornoHistoricoAux;
                            adapterOs = new OsAdapter(retornoHistorico, activity);
                            listViewOs.setAdapter(adapterOs);
                        }else
                        {
                            retornoHistorico.addAll(retornoHistoricoAux);
                            adapterOs.notifyDataSetChanged();
                        }
                        carregarMaisItens = true;
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Historico.Retorno>> call, Throwable t) {
                //Log.e(TAG, "Erro. Historico:" + t.getMessage());
                frameload.setVisibility(View.GONE);
                swipe.setRefreshing(false);
                swipe.clearAnimation();
            }
        });

    }
}
