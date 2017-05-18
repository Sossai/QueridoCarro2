package br.com.dev42.queridocarro.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.transition.ChangeBounds;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import br.com.dev42.queridocarro.R;
import br.com.dev42.queridocarro.adapters.OsItemAdapter;
import br.com.dev42.queridocarro.extra.ActivityHelper;
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
    private String cnpj,token,cor, nomeOficina, dataOs;
    private Integer numVenda;
    private ListView lv_itens;
    private Activity activity;
//    private android.support.v7.app.ActionBar actionBar;
    private View frameLoad;

    private String telefoneOficina, enderecoOficina;

    private String permissaoligar = android.Manifest.permission.CALL_PHONE;
    private static final int REQUEST_LIGACAO = 11;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_os_itens);

        ActivityHelper activityHelper = new ActivityHelper(this);
        activityHelper.mudaStatusCorTransparent();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.hideOverflowMenu();
        setSupportActionBar(toolbar);

        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);



/*        // TRANSITIONS
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            Fade tran1 = new Fade();
            tran1.setDuration(2000);

            Slide tran2 = new Slide();
            tran2.setDuration(200);

            this.setEnterTransition(tran1);*//*
//            getWindow().setSharedElementExitTransition();

            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getWindow().setSharedElementEnterTransition(new ChangeBounds());
            }
        }*/



        //  **  Action Bar return   **
/*        actionBar = getSupportActionBar();
        try{
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);

            // ** Remove a Sombra abaixo da actionbar   **
            actionBar.setElevation(0);
        }catch (NullPointerException e ){
            //Log.e("DEV42", e.getMessage());
        }*/



        lv_itens = (ListView)findViewById(R.id.list_itemV);
        frameLoad = findViewById(R.id.frameload);

        activity = this;
        cnpj = getIntent().getStringExtra("CNPJ");
        numVenda = getIntent().getIntExtra("NUMVENDA",0);
        token = getIntent().getStringExtra("TOKEN");
        cor = getIntent().getStringExtra("COR");
        nomeOficina = getIntent().getStringExtra("NOMEOFICINA");
        dataOs = getIntent().getStringExtra("DATAOS");

        telefoneOficina = getIntent().getStringExtra("TELEFONEOFICINA");
        enderecoOficina = getIntent().getStringExtra("ENDERECOOFICINA");

//        actionBar.setTitle("O.S. " + numVenda + " - " + dataOs);
//        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor(cor)));

        TextView nomeOficinaV = (TextView)findViewById(R.id.itens_os_oficina);
        nomeOficinaV.setText(nomeOficina);

        TextView osData = (TextView)findViewById(R.id.itens_os);
        osData.setText("O.S. " + numVenda + " - " + dataOs);

//        View barraOficina = findViewById(R.id.barra_labels);
//        barraOficina.setBackgroundColor(Color.parseColor(cor));

        ImageView imageOficina = (ImageView)findViewById(R.id.cover_image);
        imageOficina.setBackgroundColor(Color.parseColor(cor));



   /*     View grupoOficina = findViewById(R.id.grupo_itens_oficina);
        grupoOficina.setBackgroundColor(Color.parseColor(cor));

        View barraOficina = findViewById(R.id.barra_titulo);
        barraOficina.setBackgroundColor(Color.parseColor(cor));

        View listItem = findViewById(R.id.list_item_view);
        listItem.setBackgroundColor(Color.parseColor(cor));*/

        pegaHistoricoCompleto(cnpj, numVenda,token);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_os_itens, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem){
        switch (menuItem.getItemId()){
            case android.R.id.home:
                super.onBackPressed();
                return true;
            case R.id.id_menu_ligar:
//                Toast.makeText(this, "Ligar", Toast.LENGTH_LONG).show();
                fazerLigacao(telefoneOficina);
                break;
            case R.id.id_menu_mapa:
                mostraMapa(enderecoOficina);
//                Toast.makeText(this, "Mapa", Toast.LENGTH_LONG).show();
                break;
        }
        return true;
    }

    public void fazerLigacao(String telefone){
        if(ActivityCompat.checkSelfPermission(this,permissaoligar) == PackageManager.PERMISSION_GRANTED) {
            Intent intentLigar = new Intent(Intent.ACTION_CALL);
            intentLigar.setData(Uri.parse("tel:" + telefone));
            startActivity(intentLigar);
        }else {
            ActivityCompat.requestPermissions(this, new String[]{permissaoligar}, REQUEST_LIGACAO);
            if(ActivityCompat.checkSelfPermission(this,permissaoligar) == PackageManager.PERMISSION_GRANTED) {
                Intent intentLigar = new Intent(Intent.ACTION_CALL);
                intentLigar.setData(Uri.parse("tel:" + telefone));
                startActivity(intentLigar);
            }
        }
    }

    public void mostraMapa(String endereco){
        Intent intentMapa = new Intent(Intent.ACTION_VIEW);
        intentMapa.setData(Uri.parse("geo:0,0?z=14&q=" + Uri.encode(endereco)));
        startActivity(intentMapa);
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
                //Toast.makeText(activity, getString(R.string.erro_retorno, t.getMessage()), Toast.LENGTH_LONG).show();
                Toast.makeText(activity, R.string.erro_conexao, Toast.LENGTH_LONG).show();

            }
        });


    }
}
