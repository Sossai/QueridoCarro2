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

import com.google.gson.Gson;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import br.com.dev42.queridocarro.R;
import br.com.dev42.queridocarro.adapters.ServicoAdapter;
import br.com.dev42.queridocarro.extra.ActivityHelper;
import br.com.dev42.queridocarro.interfaces.QueridoCarroInterface;
import br.com.dev42.queridocarro.model.Oficina;
import br.com.dev42.queridocarro.model.Servico;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class OficinaDetalheActivity extends AppCompatActivity {

    private static final String TAG = "DEV42";
//    private QueridoCarroInterface service;
    private Activity activity;

    private RecyclerView rvServico;
    protected RecyclerView.LayoutManager layoutManager;

    private static final String PERMISSAO_LIGAR = android.Manifest.permission.CALL_PHONE;
    private static final int REQUEST_LIGACAO = 11;
//    private View frameLoad;
//    private String cnpjOficina;
    private Oficina.Retorno oficinaParm;

    private TextView telOficina, endOficina, emailOficina, tvNomeOficina, labelServicos;

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
//        final String nomeOficina = getIntent().getStringExtra("NOMEOFICINA");
//        cnpjOficina = getIntent().getStringExtra("CNPJOFICINA");

//        Bundle bundle = getIntent().getExtras();
//        oficinaParm = (Oficina.Retorno)bundle.getSerializable("OFICINA");

        Gson gson = new Gson();
        oficinaParm = gson.fromJson(getIntent().getStringExtra("OFICINA"), Oficina.Retorno.class);

//        String nomeOficina = oficinaParm.getOfNomeFan().trim();
//        cnpjOficina = oficinaParm.getOfCnpjCpf().trim();

        tvNomeOficina = (TextView)findViewById(R.id.tv_nome_oficina_detalhe);

        ImageView imageOficina = (ImageView)findViewById(R.id.cover_image);
        imageOficina.setBackgroundColor(Color.parseColor(corOficina));

        telOficina = (TextView)findViewById(R.id.tv_tel_number_det);
        endOficina = (TextView)findViewById(R.id.tv_end_det);
        emailOficina = (TextView)findViewById(R.id.tv_email_det);
        labelServicos = (TextView)findViewById(R.id.tv_servicos_prestados);


        final ImageView imageOptionTel = (ImageView)findViewById(R.id.iv_opt_tel);
        imageOptionTel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PopupMenu popup = new PopupMenu(activity, imageOptionTel, Gravity.RIGHT);
                popup.getMenu().add(1,1,1,"Ligar");

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case 1:
                                fazerLigacao(oficinaParm.getOfDdd().toString().trim() + oficinaParm.getOfTel().trim());
                                break;
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
                                mostraMapa(oficinaParm.getOfEnderecoEncontrado());
                                break;
                        }

                        return false;
                    }
                });
                popup.show();
            }
        });

//        frameLoad = findViewById(R.id.frameload);

//        service = new Retrofit.Builder()
//                .baseUrl(QueridoCarroInterface.BASE_URL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build().create(QueridoCarroInterface.class);

        rvServico = (RecyclerView)findViewById(R.id.rv_servicos);
        rvServico.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        rvServico.setLayoutManager(layoutManager);

        holder(oficinaParm);
    }

    private void holder(Oficina.Retorno oficina){

        String enderecoCompleto = oficina.getOfEndere().trim() +
                "," + oficina.getOfNumero().toString() +
                " - " + oficina.getOfBairro().trim() +
                ", " + oficina.getOfCidade().trim() +
                " - " + oficina.getOfEstado().trim();

        String telefoneCompleto = "(" + oficina.getOfDdd() + ") " +
                oficina.getOfTel().trim();

        telOficina.setText(telefoneCompleto.trim());
        endOficina.setText(enderecoCompleto.trim());
        emailOficina.setText(oficina.getOfEmail().trim());

        tvNomeOficina.setText(oficina.getOfNomeFan().trim());

        if(oficina.getServicos().size() > 0){
            List<Servico> mListServicos = oficina.getServicos();
            ServicoAdapter adapter = new ServicoAdapter(activity, mListServicos);
            rvServico.setAdapter(adapter);
        }else {
            labelServicos.setVisibility(View.GONE);
        }
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
