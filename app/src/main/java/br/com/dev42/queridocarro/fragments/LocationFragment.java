package br.com.dev42.queridocarro.fragments;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import br.com.dev42.queridocarro.R;
import br.com.dev42.queridocarro.activities.ListaOficinasActivity;
import br.com.dev42.queridocarro.interfaces.MenuOficinasInterface;
import br.com.dev42.queridocarro.interfaces.QueridoCarroInterface;
import br.com.dev42.queridocarro.model.Estado;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class LocationFragment extends Fragment {

    private View v;
    private MenuOficinasInterface menuOficinasInterface;
    private EditText estado, cidade;
    private QueridoCarroInterface service;
    private String estadoSelecionado;
    private ListView modeList;
    private Dialog dialog;
    private View frameLoad;

    private Animation contrair, expandir;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_location, container, false);
        menuOficinasInterface = (MenuOficinasInterface)getActivity();
        setHasOptionsMenu(true);
        estado = (EditText)v.findViewById(R.id.id_estado);
        cidade = (EditText)v.findViewById(R.id.id_cidade);
        frameLoad = v.findViewById(R.id.frameload);

        contrair = AnimationUtils.loadAnimation(getActivity(),R.anim.anim_contrair);
        expandir = AnimationUtils.loadAnimation(getActivity(),R.anim.anim_expandir);

        ImageView imgLoc = (ImageView)v.findViewById(R.id.img_location_fr);
        imgLoc.startAnimation(expandir);

        service = new Retrofit.Builder()
                .baseUrl(QueridoCarroInterface.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(QueridoCarroInterface.class);

        estado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final LinkedHashMap<String,String> estadosMap;
                estadosMap = Estado.listaEstados();

                List<String> nomesEstados = new ArrayList<String>();
                nomesEstados.addAll(estadosMap.keySet());

                criaDialog(nomesEstados);

                modeList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        //  ** Pego a sigla do estado   no HashMap **
                        //estado.setText(estadosMap.get( modeList.getItemAtPosition(position).toString()));
                        cidade.setText("");
                        estado.setText(modeList.getItemAtPosition(position).toString().toUpperCase());
                        estadoSelecionado = estadosMap.get( modeList.getItemAtPosition(position).toString());
                        dialog.dismiss();
                    }
                });
            }
        });

        cidade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(estadoSelecionado == null || estadoSelecionado.length() < 2){
                    Toast.makeText(getActivity(), "Selecione um estado.", Toast.LENGTH_LONG).show();
                }else {
                    frameLoad.setVisibility(View.VISIBLE);

                    Call<List<String>> listaCidades = service.getCidades(estadoSelecionado);
                    listaCidades.enqueue(new Callback<List<String>>() {
                        @Override
                        public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                            frameLoad.setVisibility(View.GONE);

                            if(!response.isSuccessful()){
                                Toast.makeText(getActivity(), "Não foi possível retornar a lista de cidades.", Toast.LENGTH_LONG).show();
                            }else {
                                if(!response.body().isEmpty()){
                                    criaDialog(response.body());

                                    modeList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                            cidade.setText(modeList.getItemAtPosition(position).toString().trim());
                                            dialog.dismiss();
                                        }
                                    });

                                }else
                                    Toast.makeText(getActivity(), "Não foram encontradas cidades.", Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<List<String>> call, Throwable t) {
                            frameLoad.setVisibility(View.GONE);
                            Toast.makeText(getActivity(), "Não foi possível retornar a lista de cidades.", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });

        return v;
    }

    private void criaDialog(List<String> lista){

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Selecione:");
//        final ListView modeList = new ListView(getActivity());
        modeList = new ListView(getActivity());

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, lista);
        modeList.setAdapter(adapter);

        builder.setView(modeList);
        //final Dialog dialog = builder.create();
        dialog = builder.create();
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        menu.removeItem(R.id.id_menu_location);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.id_menu_buscar:
                if( (estadoSelecionado == null || estadoSelecionado.length() < 2) || cidade.getText().length() < 2) {
                    Toast.makeText(getActivity(), R.string.erro_cidadeestado, Toast.LENGTH_SHORT).show();
                }else {
                    Intent intent = new Intent(getActivity(), ListaOficinasActivity.class);
                    intent.putExtra("LATITUDE", 0.0);
                    intent.putExtra("LONGITUDE", 0.0);
                    intent.putExtra("CEP", "");
                    //intent.putExtra("ENDERECO", cidade.getText().toString() + "," + estado.getText().toString());
                    intent.putExtra("ENDERECO", cidade.getText().toString() + "," + estadoSelecionado);
                    intent.putExtra("TIPO", 1);
                    /*tipos
                    CidadeEstado = 1
                    LatLon = 2
                    Endereco = 3
                    Cep = 4*/
                    startActivity(intent);
                }

                break;

            case R.id.id_menu_geolocation:
                menuOficinasInterface.mudaMenu("GEOLOCATION");
                break;

            case R.id.id_menu_cep:
                menuOficinasInterface.mudaMenu("CEP");
                break;

            case R.id.id_menu_location:
                menuOficinasInterface.mudaMenu("LOCATION");
                break;
        }

        return super.onOptionsItemSelected(item);
    }

}
