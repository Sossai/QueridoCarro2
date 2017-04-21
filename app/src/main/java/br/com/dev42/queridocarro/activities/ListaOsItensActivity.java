package br.com.dev42.queridocarro.activities;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.nfc.Tag;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import br.com.dev42.queridocarro.R;
import br.com.dev42.queridocarro.adapters.OsItemAdapter;
import br.com.dev42.queridocarro.extra.CorSigla;
import br.com.dev42.queridocarro.interfaces.QueridoCarroInterface;
import br.com.dev42.queridocarro.model.HistoricoCompleto;
import br.com.dev42.queridocarro.model.HistoricoItens;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ListaOsItensActivity extends AppCompatActivity {

    private static final String TAG = "DEV42";
    private String cnpj,token,cor, nomeOficina;
    private Integer numVenda, corOficina;
    private ListView lv_itens;
    private Activity activity;
    private android.support.v7.app.ActionBar actionBar;
    private View frameLoad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //  **  Action Bar return   **
        //android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar = getSupportActionBar();
        try{
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);

            // ** Remove a Sombra abaixo da actionbar   **
            actionBar.setElevation(0);
        }catch (Exception e ){
            //Log.e("DEV42", e.getMessage());
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_os_itens);

        lv_itens = (ListView)findViewById(R.id.list_itemV);
        frameLoad = findViewById(R.id.frameload);

        activity = this;
        cnpj = getIntent().getStringExtra("CNPJ");
        numVenda = getIntent().getIntExtra("NUMVENDA",0);
        token = getIntent().getStringExtra("TOKEN");
        cor = getIntent().getStringExtra("COR");
        nomeOficina = getIntent().getStringExtra("NOMEOFICINA");

        TextView nomeOficinaV = (TextView)findViewById(R.id.itens_os_oficina);
        nomeOficinaV.setText(nomeOficina);

        View grupoOficina = findViewById(R.id.grupo_itens_oficina);
        grupoOficina.setBackgroundColor(Color.parseColor(cor));

        View barraOficina = findViewById(R.id.barra_titulo);
        barraOficina.setBackgroundColor(Color.parseColor(cor));

//        lv_itens.setScrollingCacheEnabled(false);


        actionBar.setTitle("Itens da O.S. " + numVenda);
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor(cor)));

        // ** mudar a cor do status bar api 21 **
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(Color.parseColor(cor));
        }

        pegaHistoricoCompleto(cnpj, numVenda,token);
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

    protected void pegaHistoricoCompleto(String cnpj, final Integer numVenda, String token ){

        frameLoad.setVisibility(View.VISIBLE);
        QueridoCarroInterface service = new Retrofit.Builder()
                .baseUrl(QueridoCarroInterface.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(QueridoCarroInterface.class);

        HistoricoCompleto.Envio envio = new HistoricoCompleto.Envio(numVenda, cnpj, token);
        Call<HistoricoCompleto.Retorno> historicoCompleto = service.getHistoricoCompleto(envio);

        historicoCompleto.enqueue(new Callback<HistoricoCompleto.Retorno>() {
            @Override
            public void onResponse(Call<HistoricoCompleto.Retorno> call, Response<HistoricoCompleto.Retorno> response) {
                frameLoad.setVisibility(View.GONE);
                if(!response.isSuccessful()){
                    //Log.e(TAG,"Erro. :"+ response.code());
                    Toast.makeText(activity, R.string.erro_conexao, Toast.LENGTH_LONG).show();
                }else{
                    HistoricoCompleto.Retorno retornoHistoricoCompleto = response.body();

                    if(!retornoHistoricoCompleto.getItens().isEmpty())
                    {
                        OsItemAdapter adapter = new OsItemAdapter(retornoHistoricoCompleto.getItens(),activity);
                        lv_itens.setAdapter(adapter);
                    }

                }

            }

            @Override
            public void onFailure(Call<HistoricoCompleto.Retorno> call, Throwable t) {
                frameLoad.setVisibility(View.GONE);
                Toast.makeText(activity, getString(R.string.erro_retorno, t.getMessage()), Toast.LENGTH_LONG).show();

            }
        });


    }
}
