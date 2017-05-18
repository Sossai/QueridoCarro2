package br.com.dev42.queridocarro.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.nfc.Tag;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
//import android.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.com.dev42.queridocarro.R;
import br.com.dev42.queridocarro.adapters.ServicoAdapter;
import br.com.dev42.queridocarro.extra.ActivityHelper;
import br.com.dev42.queridocarro.interfaces.QueridoCarroInterface;
import br.com.dev42.queridocarro.model.Oficina;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class OficinaDetalheActivity extends AppCompatActivity {

    private static final String TAG = "DEV42";
    private QueridoCarroInterface service;
    private Activity activity;

    private RecyclerView rvServico;
    protected RecyclerView.LayoutManager layoutManager;

    private static final String PERMISSAO_LIGAR = android.Manifest.permission.CALL_PHONE;
    private static final int REQUEST_LIGACAO = 11;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oficina_detalhe);

        activity = this;

        ActivityHelper activityHelper = new ActivityHelper(this);
        activityHelper.mudaStatusCorTransparent();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        Toolbar toolbarName = (Toolbar) findViewById(R.id.toolbar_name);

        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        String corOficina = getIntent().getStringExtra("COR");
        String nomeOficina = getIntent().getStringExtra("NOMEOFICINA");

        TextView tvNomeOficina = (TextView)findViewById(R.id.tv_nome_oficina_detalhe);
        tvNomeOficina.setText(nomeOficina);

        ImageView imageOficina = (ImageView)findViewById(R.id.cover_image);
        imageOficina.setBackgroundColor(Color.parseColor(corOficina));

        final ImageView imageOptionTel = (ImageView)findViewById(R.id.iv_opt_tel);
        imageOptionTel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PopupMenu popup = new PopupMenu(activity, imageOptionTel, Gravity.RIGHT);

                //popup.getMenuInflater().inflate(R.menu.menu_popup_telefone, popup.getMenu());

//                popup.getMenu().add(groupId, itemId, order, title);
                popup.getMenu().add(1,1,1,"Ligar");
//                popup.getMenu().add(1,2,1,"Adicionar");

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case 1:
                                Toast.makeText(activity, "Ligar", Toast.LENGTH_LONG).show();
                                break;
//                            case 2:
//                                Toast.makeText(activity, "Adicionar aos contatos", Toast.LENGTH_LONG).show();
//                                break;
                        }

                        return false;
                    }
                });
                popup.show();
            }
        });

        final ImageView imageOptionGeo = (ImageView)findViewById(R.id.iv_opt_geo);
        imageOptionGeo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(activity, imageOptionGeo, Gravity.RIGHT);

                popup.getMenu().add(1,1,1,"Mostrar mapa");

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case 1:
                                Toast.makeText(activity, "Mapa", Toast.LENGTH_LONG).show();
                                break;
//                            case 2:
//                                Toast.makeText(activity, "Adicionar aos contatos", Toast.LENGTH_LONG).show();
//                                break;
                        }

                        return false;
                    }
                });
                popup.show();
            }
        });


        service = new Retrofit.Builder()
                .baseUrl(QueridoCarroInterface.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(QueridoCarroInterface.class);

        rvServico = (RecyclerView)findViewById(R.id.rv_servicos);
        rvServico.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        rvServico.setLayoutManager(layoutManager);

        List<String> mListServicos = new ArrayList<>();
        mListServicos.add("Alinhamento / Balanceamento  ");
        mListServicos.add("Funilaria");
        mListServicos.add("Mecânica Geral  ");
        mListServicos.add("Bombas Injetoras");
        mListServicos.add("Suspensão");
        mListServicos.add("Ar Condicionado");
        mListServicos.add("Pneus ");
        mListServicos.add("Adaptações - Veículos Especiais");
        mListServicos.add("Amortecedores");
        mListServicos.add("Lavagem");
        mListServicos.add("Lavagem");
        mListServicos.add("Lavagem");
        mListServicos.add("Lavagem");
        mListServicos.add("Lavagem");
        mListServicos.add("Lavagem");
        mListServicos.add("Lavagem");
        mListServicos.add("Lavagem");
        mListServicos.add("Adaptações - Veículos Especiais");

        ServicoAdapter adapter = new ServicoAdapter(this, mListServicos);
        rvServico.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
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

/*    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

        Log.e(TAG, "CONTEXT MENU");

//        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;
//        final Oficina.Retorno oficinaSelecionada = (Oficina.Retorno)lvOficinas.getItemAtPosition(info.position);

        menu.setHeaderIcon(R.drawable.ic_telefone_128_green);
        MenuItem ligar = menu.add("Ligar");
        MenuItem mapa = menu.add("Mapa");

        ligar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
//                String telCompleto = oficinaSelecionada.getOfDdd() + oficinaSelecionada.getOfTel();
//                fazerLigacao(telCompleto);

                Toast.makeText(activity, "Ligar", Toast.LENGTH_LONG).show();
                return false;
            }
        });

        mapa.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
//                mostraMapa(oficinaSelecionada.getOfEnderecoEncontrado());

                Toast.makeText(activity, "Mapa", Toast.LENGTH_LONG).show();
                return false;
            }
        });
    }*/

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
}
