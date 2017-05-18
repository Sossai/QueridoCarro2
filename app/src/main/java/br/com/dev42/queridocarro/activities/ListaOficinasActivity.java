package br.com.dev42.queridocarro.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.nfc.Tag;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import br.com.dev42.queridocarro.R;
import br.com.dev42.queridocarro.adapters.OficinaAdapter;
import br.com.dev42.queridocarro.extra.ActivityHelper;
import br.com.dev42.queridocarro.extra.CorSigla;
import br.com.dev42.queridocarro.extra.HideKeyboard;
import br.com.dev42.queridocarro.interfaces.QueridoCarroInterface;
import br.com.dev42.queridocarro.model.Oficina;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ListaOficinasActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = "DEV42";
    private String menuStatus;
    private QueridoCarroInterface service;
    private Integer quantidade = 10;   // ** Quantidade de oficinas **
    private Integer distancia = 20;    // ** Distancia 20km **
    private OficinaAdapter adapter;
    private ListView lvOficinas;
    private Activity activity;
    private Boolean carregarMaisItens = true;
    private View frameLoad;
    private SwipeRefreshLayout swipe;
    private String ultimaCnpj;
    private List<Oficina.Retorno> retornoOficinas;

    private Double latitude;
    private Double longitude;
    private String cep;
    private String endereco;
    private Integer tipo;
    private String nomeOficina;


    private static final String PERMISSAO_LIGAR = android.Manifest.permission.CALL_PHONE;
    private static final int REQUEST_LIGACAO = 11;

    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_oficinas);

        ActivityHelper activityHelper = new ActivityHelper(this);
        activityHelper.mudaStatusCorTransparent();

        activity = this;
        lvOficinas = (ListView)findViewById(R.id.lista_oficinas);
        frameLoad = findViewById(R.id.frameload);

        latitude = getIntent().getDoubleExtra("LATITUDE",0.0);
        longitude = getIntent().getDoubleExtra("LONGITUDE",0.0);
        cep = getIntent().getStringExtra("CEP");
        endereco = getIntent().getStringExtra("ENDERECO");
        tipo = getIntent().getIntExtra("TIPO",0);

        ultimaCnpj = "";
        nomeOficina = "";

        //  **  Action Bar return   **
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        try{
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setElevation(0);

            //  ** Busca por Cidade/Estado não é pro proximidade
            if(tipo != 1)
                actionBar.setTitle("Oficinas Próximas");

            /*
            CidadeEstado = 1
            LatLon = 2
            Endereco = 3
            Cep = 4
            */

        }catch (Exception e ){
            //Log.e("DEV42", e.getMessage());
        }

        //  ** Cidade/Estado sem distancia  **
        if(tipo == 1)
            distancia = 0;

        service = new Retrofit.Builder()
                .baseUrl(QueridoCarroInterface.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(QueridoCarroInterface.class);

        swipe = (SwipeRefreshLayout)findViewById(R.id.swipe);
        swipe.setOnRefreshListener(this);

        lvOficinas.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {}

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if(firstVisibleItem + visibleItemCount == totalItemCount && totalItemCount !=0 ){
                    if(carregarMaisItens){
                        //Toast.makeText(activity, "Load", Toast.LENGTH_SHORT).show();
                        getOficinas();
//                        getOficinas(latitude, longitude, cep, endereco, distancia, quantidade, ultimaCnpj, tipo);
//                        Log.e(TAG, "Mais : " + QUANTIDADE_OS.toString());
                        carregarMaisItens = false;
                    }

                }
            }
        });

        registerForContextMenu(lvOficinas);

        lvOficinas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                view.showContextMenu();
                Intent intent = new Intent(ListaOficinasActivity.this, OficinaDetalheActivity.class);

                Oficina.Retorno oficinaSelecionada = retornoOficinas.get(position);

                intent.putExtra("COR", CorSigla.escolheCor(oficinaSelecionada.getOfNomeFan()));
                intent.putExtra("NOMEOFICINA", oficinaSelecionada.getOfNomeFan().trim());
                startActivity(intent);
            }
        });

        getOficinas();
//        getOficinas(latitude, longitude, cep, endereco, distancia, quantidade, "", tipo);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_list_oficinas, menu);
        super.onCreateOptionsMenu(menu);

        //Pega o Componente
        searchView = (SearchView)menu.findItem(R.id.search_v).getActionView();
        searchView.setQueryHint("Pesquisar");
        searchView.setIconifiedByDefault(true);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                nomeOficina = searchView.getQuery().toString();

                if(nomeOficina.length() > 1 || nomeOficina.length() == 0 ){
                    retornoOficinas.clear();
                    lvOficinas.setAdapter(null);
                    ultimaCnpj = "";
                    getOficinas();
                }

                return false;
            }
        });

        return true;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;
        final Oficina.Retorno oficinaSelecionada = (Oficina.Retorno)lvOficinas.getItemAtPosition(info.position);

        MenuItem ligar = menu.add("Ligar");
        MenuItem mapa = menu.add("Mapa");

        ligar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                String telCompleto = oficinaSelecionada.getOfDdd() + oficinaSelecionada.getOfTel();
                fazerLigacao(telCompleto);

                return false;
            }
        });

        mapa.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                mostraMapa(oficinaSelecionada.getOfEnderecoEncontrado());
                return false;
            }
        });
    }

    public void fazerLigacao(String telefone){
        if(ActivityCompat.checkSelfPermission(this,PERMISSAO_LIGAR) == PackageManager.PERMISSION_GRANTED) {
            Intent intentLigar = new Intent(Intent.ACTION_CALL);
            intentLigar.setData(Uri.parse("tel:" + telefone));
            startActivity(intentLigar);
        }else {
            ActivityCompat.requestPermissions(this, new String[]{PERMISSAO_LIGAR}, REQUEST_LIGACAO);
            if(ActivityCompat.checkSelfPermission(this,PERMISSAO_LIGAR) == PackageManager.PERMISSION_GRANTED) {
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

    @Override
    public void onRefresh() {
        retornoOficinas.clear();
        lvOficinas.setAdapter(null);
        ultimaCnpj = "";
        getOficinas();
//        getOficinas(latitude, longitude, cep, endereco, distancia, quantidade, "", tipo);
    }

    //private void getOficinas(Double latitude, Double longitude, String cep, String endereco, Integer distancia, Integer quantidade, String cnpj, Integer tipo)
    private void getOficinas()
    {
        frameLoad.setVisibility(View.VISIBLE);

        Oficina.Envio oficinaEnvio = new Oficina.Envio(latitude, longitude, endereco, cep, distancia, quantidade, ultimaCnpj, tipo, nomeOficina);

        Call<List<Oficina.Retorno>> retOficinas = service.getOficinasProximas(oficinaEnvio);

        retOficinas.enqueue(new Callback<List<Oficina.Retorno>>() {
            @Override
            public void onResponse(Call<List<Oficina.Retorno>> call, Response<List<Oficina.Retorno>> response) {
                frameLoad.setVisibility(View.GONE);
                swipe.setRefreshing(false);
                swipe.clearAnimation();

                if(!response.isSuccessful()){
                    Toast.makeText(activity, R.string.erro_conexao, Toast.LENGTH_LONG).show();
                    finish();
                }else{
                    if(!response.body().isEmpty()) {

                        List<Oficina.Retorno> retOficinasAux = response.body();

                        // ** Ultima cnpj download **
                        ultimaCnpj = retOficinasAux.get(retOficinasAux.size()-1).getOfCnpjCpf();

                        if(lvOficinas.getAdapter() == null){
                            retornoOficinas = retOficinasAux;
                            adapter = new OficinaAdapter(retornoOficinas, activity);
                            lvOficinas.setAdapter(adapter);
                        }else {
                            retornoOficinas.addAll(retOficinasAux);
                            adapter.notifyDataSetChanged();
                        }
                        carregarMaisItens = true;

                    }else {
                        //  ** Não devo verificar caso ja tenha algum item na lista pois carrega por requisição **
                        if(retornoOficinas == null || retornoOficinas.size() == 0){
                            Toast.makeText(activity, R.string.erro_busca_oficinas, Toast.LENGTH_LONG).show();
                            finish();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Oficina.Retorno>> call, Throwable t) {
                frameLoad.setVisibility(View.GONE);
                //Toast.makeText(activity, getString(R.string.erro_retorno, t.getMessage()), Toast.LENGTH_LONG).show();
                Toast.makeText(activity, R.string.erro_conexao, Toast.LENGTH_LONG).show();
                //Log.e(TAG, t.getMessage());
                finish();
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem){
        switch (menuItem.getItemId()){
            case android.R.id.home:
                //  ** Esconde o teclado caso clique em voltar  **
                HideKeyboard hideKeyboard = new HideKeyboard(this);
                super.onBackPressed();
                return true;
        }
        return true;
    }
}
