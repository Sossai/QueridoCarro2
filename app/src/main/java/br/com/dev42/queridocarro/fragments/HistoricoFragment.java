package br.com.dev42.queridocarro.fragments;


import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.transition.Explode;
import android.transition.Slide;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.List;

import br.com.dev42.queridocarro.R;
import br.com.dev42.queridocarro.activities.ListaOsActivity;
import br.com.dev42.queridocarro.dao.PlacaDao;
import br.com.dev42.queridocarro.extra.HideKeyboard;
import br.com.dev42.queridocarro.extra.MaskPlaca;
import br.com.dev42.queridocarro.interfaces.MenuOficinasInterface;
import br.com.dev42.queridocarro.interfaces.QueridoCarroInterface;
import br.com.dev42.queridocarro.model.Historico;
import br.com.dev42.queridocarro.model.Token;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HistoricoFragment extends Fragment {

    private View v;
//    private MenuInflater inflater;
//    private EditText placa, senha;
    private static final String TAG = "DEV42";
//    private FrameLayout frameload;

//    private QueridoCarroInterface service;
    private FloatingActionButton btnAddPlaca, btnClosePlaca;

//

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_historico, container, false);

//        btnAddPlaca = (FloatingActionButton)v.findViewById(R.id.btn_add_placa);
//        btnClosePlaca = (FloatingActionButton)v.findViewById(R.id.btn_close_add_placa);

/*        placa = (EditText) v.findViewById(R.id.placa);
        senha = (EditText)v.findViewById(R.id.senha);

        //  ** Mask da Placa **
        placa.addTextChangedListener(MaskPlaca.insert(placa));

        frameload = (FrameLayout)v.findViewById(R.id.frameload);

        service = new Retrofit.Builder()
                .baseUrl(QueridoCarroInterface.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(QueridoCarroInterface.class);

        // *****

*/
        loadFragmentPlaca();

        return v;
    }

    @Override
    public void onResume() {
        loadFragmentPlaca();
        super.onResume();
    }

    public void loadFragmentPlaca(){
        MenuOficinasInterface menuOficinasInterface;
        menuOficinasInterface = (MenuOficinasInterface)getActivity();

        PlacaDao placaDao = new PlacaDao(getActivity());
        if(placaDao.quantidadePlacasCadastradas() > 0) {
//            btnClosePlaca.setVisibility(View.GONE);
//            btnAddPlaca.setVisibility(View.VISIBLE);
            menuOficinasInterface.mudaMenu("LISTAPLACA");
            //Toast.makeText(getActivity(), "Lista placas", Toast.LENGTH_LONG).show();
        }
        else {
            menuOficinasInterface.mudaMenu("PLACA");
//            btnClosePlaca.setVisibility(View.VISIBLE);
//            btnAddPlaca.setVisibility(View.GONE);
            //Toast.makeText(getActivity(), "placa Solo", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        this.inflater = inflater;
//    }

    @Override
    public void onPrepareOptionsMenu(Menu menu){
        //  **  Limpo o menu e dou inflate aqui pois preciso remover outros menus dos fragments
        menu.clear();
//        inflater.inflate(R.menu.menu_main, menu);
        super.onPrepareOptionsMenu(menu);
    }





/*
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        this.inflater = inflater;
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu){
        //  **  Limpo o menu e dou inflate aqui pois preciso remover outros menus dos fragments
        menu.clear();
        inflater.inflate(R.menu.menu_placa, menu);
        super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        HideKeyboard hideKeyboard = new HideKeyboard(getActivity());
        switch (item.getItemId()){
            case R.id.action_ok:

                if(placasenhaValidos())
                    getToken();

                break;
        }

        return super.onOptionsItemSelected(item);
    }

    //  ** Pego o código de acesso **
    protected void getToken(){
        Token.Envio tokenEnvio = new Token.Envio(placa.getText().toString().replace("-",""),senha.getText().toString());
        Call<Token.Retorno> retToken = service.getToken(tokenEnvio);
        frameload.setVisibility(View.VISIBLE);

        retToken.enqueue(new Callback<Token.Retorno>() {
            @Override
            public void onResponse(Call<Token.Retorno> call, Response<Token.Retorno> response) {
                frameload.setVisibility(View.GONE);
                if(!response.isSuccessful()){
                    Toast.makeText(getActivity(), R.string.erro_conexao,Toast.LENGTH_LONG ).show();
                }else {
                    Token.Retorno retornoToken = response.body();

                    if(retornoToken.getTokenHash().length() > 5){
                        //getHistorico(retornoToken.getTokenHash());
                        Intent intent = new Intent(getActivity(), ListaOsActivity.class);
                        intent.putExtra("placa", placa.getText().toString().replace("-",""));
                        intent.putExtra("token", retornoToken.getTokenHash());

                        // TRANSITIONS
//                        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
//                            ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(),null);
//                            startActivity(intent, options.toBundle());
//                        }else
//                        {
                            startActivity(intent);
//                        }

                    }else {
                        Toast.makeText(getActivity(), getString(R.string.erro_retorno,retornoToken.getErro()),Toast.LENGTH_LONG ).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<Token.Retorno> call, Throwable t) {
                Log.e(TAG, "Erro.:" + t.getMessage());
                Toast.makeText(getActivity(), "Erro:" + t.getMessage(),Toast.LENGTH_LONG ).show();
                frameload.setVisibility(View.GONE);
            }
        });
    }

    private boolean placasenhaValidos(){
        if(placa.length() < 8){
            placa.setError("Placa inválida.");
            return false;
        }else{
            if(senha.length() == 0){
                senha.setError("Código de acesso inválido.");
                return false;
            }else
                return true;
        }
    }*/
}
