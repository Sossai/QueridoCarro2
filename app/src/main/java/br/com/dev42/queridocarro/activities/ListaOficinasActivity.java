package br.com.dev42.queridocarro.activities;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import br.com.dev42.queridocarro.R;
import br.com.dev42.queridocarro.adapters.OficinaAdapter;
import br.com.dev42.queridocarro.interfaces.QueridoCarroInterface;
import br.com.dev42.queridocarro.model.Oficina;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ListaOficinasActivity extends AppCompatActivity {

    private static final String TAG = "DEV42";
    private String menuStatus;
    private QueridoCarroInterface service;
    private static final Integer quantidade = 10;   // ** Quantidade de oficinas **
    private static final Integer distancia = 20;    // ** Distancia 20km **
    private OficinaAdapter adapter;
    private ListView lvOficinas;
    private Activity activity;
    private View frameLoad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_oficinas);

        activity = this;
        lvOficinas = (ListView)findViewById(R.id.lista_oficinas);
        frameLoad = findViewById(R.id.frameload);

        //  **  Action Bar return   **
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        try{
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }catch (Exception e ){
            //Log.e("DEV42", e.getMessage());
        }

        Double latitude = getIntent().getDoubleExtra("LATITUDE",0);
        Double longitude = getIntent().getDoubleExtra("LONGITUDE",0);
        String cep = getIntent().getStringExtra("CEP");
        String endereco = getIntent().getStringExtra("ENDERECO");

        service = new Retrofit.Builder()
                .baseUrl(QueridoCarroInterface.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(QueridoCarroInterface.class);

        getOficinas(latitude, longitude, cep, endereco, distancia, quantidade);
    }

    private void getOficinas(Double latitude, Double longitude, String cep, String endereco, Integer distancia, Integer quantidade)
    {
        /*
        {"Latitude":-23.6048,"Longitude":-46.9179,"Endereco":"","Cep":13044000,"Distancia":20,"QuantidadeOficinas":3}
        */
        frameLoad.setVisibility(View.VISIBLE);

        Oficina.Envio oficinaEnvio = new Oficina.Envio(latitude, longitude, cep,endereco, distancia, quantidade);
        Log.e(TAG, oficinaEnvio.toString());

        Call<List<Oficina.Retorno>> retOficinas = service.getOficinasProximas(oficinaEnvio);

        retOficinas.enqueue(new Callback<List<Oficina.Retorno>>() {
            @Override
            public void onResponse(Call<List<Oficina.Retorno>> call, Response<List<Oficina.Retorno>> response) {
                frameLoad.setVisibility(View.GONE);
                if(!response.isSuccessful()){
                    Toast.makeText(activity, R.string.erro_conexao, Toast.LENGTH_LONG).show();
                }else{

                    if(!response.body().isEmpty()) {
                        adapter = new OficinaAdapter(response.body(), activity);
                        lvOficinas.setAdapter(adapter);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Oficina.Retorno>> call, Throwable t) {
                frameLoad.setVisibility(View.GONE);
                Toast.makeText(activity, getString(R.string.erro_retorno, t.getMessage()), Toast.LENGTH_LONG).show();
            }
        });
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
}
