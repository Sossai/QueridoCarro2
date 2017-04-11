package br.com.dev42.queridocarro.activities;

import android.app.Activity;
import android.nfc.Tag;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Slide;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Adapter;
import android.widget.FrameLayout;
import android.widget.ListView;

import java.util.List;

import br.com.dev42.queridocarro.R;
import br.com.dev42.queridocarro.adapters.OsAdapter;
import br.com.dev42.queridocarro.interfaces.QueridoCarroInterface;
import br.com.dev42.queridocarro.model.Historico;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ListaOsActivity extends AppCompatActivity {
    private static final String TAG = "DEV42";

    private QueridoCarroInterface service;
    private Activity activity;
    private FrameLayout frameload;

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

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_os);

//        setHasOptionsMenu(true);

        activity = this;
        frameload = (FrameLayout)findViewById(R.id.frameload);

        service = new Retrofit.Builder()
                .baseUrl(QueridoCarroInterface.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(QueridoCarroInterface.class);

        String placa = (String)getIntent().getStringExtra("placa");
        String token = (String)getIntent().getStringExtra("token");

        getHistorico(placa, token);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    //  ** Pego o Hiatórico **
    protected void getHistorico(String placa, String token){

        //Historico.Envio histEnvio = new Historico.Envio(placa.getText().toString().replace("-",""),"2016-11-28","2016-11-28", token);
        Historico.Envio histEnvio = new Historico.Envio(placa,"","", token);
        Call<List<Historico.Retorno>> listHistorico = service.getHistorico(histEnvio);
        frameload.setVisibility(View.VISIBLE);

        listHistorico.enqueue(new Callback<List<Historico.Retorno>>() {
            @Override
            public void onResponse(Call<List<Historico.Retorno>> call, Response<List<Historico.Retorno>> response) {
                if(!response.isSuccessful()){
//                    Log.e(TAG, "Erro Historico.:" + response.code());
                    frameload.setVisibility(View.GONE);
                }else {
//                    Log.e(TAG, "Sucesso Historico");
                    List<Historico.Retorno> retornoHistorico = response.body();
//                    Toast.makeText(getActivity(), "Token:" + retornoToken.getTokenHash(),Toast.LENGTH_LONG ).show();
                    //for(Historico.Retorno ret : retornoHistorico){
                     //   Log.e(TAG, ret.getOficina());
                    //}

                    frameload.setVisibility(View.GONE);

                    ListView listViewOs = (ListView)findViewById(R.id.listaOsV);
                    OsAdapter adapter = new OsAdapter(retornoHistorico, activity);
                    listViewOs.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<Historico.Retorno>> call, Throwable t) {
                //Log.e(TAG, "Erro. Historico:" + t.getMessage());
                frameload.setVisibility(View.GONE);
            }
        });

    }
}
