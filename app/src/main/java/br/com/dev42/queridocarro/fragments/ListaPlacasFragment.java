package br.com.dev42.queridocarro.fragments;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import br.com.dev42.queridocarro.R;
import br.com.dev42.queridocarro.adapters.PlacaAdapter;
import br.com.dev42.queridocarro.dao.PlacaDao;
import br.com.dev42.queridocarro.extra.HideKeyboard;
import br.com.dev42.queridocarro.interfaces.MenuOficinasInterface;
import br.com.dev42.queridocarro.model.Placa;
import retrofit2.http.DELETE;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListaPlacasFragment extends Fragment {

    private View v;
    private ListView lvPlacas;
    private Placa placaSelecionada;
    private MenuOficinasInterface menuOficinasInterface;
    private static final int EXCLUIR = 1;
    private FloatingActionButton btnAdPlaca;
    private PlacaAdapter adapter;
    private List<Placa> placas;

    private Animation contrair, expandir;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_lista_placas, container, false);
        setHasOptionsMenu(true);

        contrair = AnimationUtils.loadAnimation(getActivity(),R.anim.anim_contrair);
        expandir = AnimationUtils.loadAnimation(getActivity(),R.anim.anim_expandir);

        menuOficinasInterface = (MenuOficinasInterface)getActivity();

        lvPlacas = (ListView) v.findViewById(R.id.lista_placas_lv);
        btnAdPlaca = (FloatingActionButton)v.findViewById(R.id.btn_add_placa);

//        PlacaDao placaDao = new PlacaDao(getActivity());
//        final List<Placa> placas = placaDao.listar();
//
//        adapter = new PlacaAdapter(placas, getActivity());
//        lvPlacas.setAdapter(adapter);
        carregaLista();

        registerForContextMenu(lvPlacas);
        lvPlacas.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                //  ** ContextMenu  **
                return false;
            }
        });
        lvPlacas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Placa placaAux = (Placa)lvPlacas.getItemAtPosition(position);
                menuOficinasInterface.getToken(placaAux.getPlaca(),placaAux.getCodigoAcesso(),false);
            }
        });

        btnAdPlaca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnAdPlaca.startAnimation(contrair);
                //menuOficinasInterface.mudaMenu("PLACA");
            }
        });

        contrair.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}
            @Override
            public void onAnimationEnd(Animation animation) {
                menuOficinasInterface.mudaMenu("PLACA");
            }
            @Override
            public void onAnimationRepeat(Animation animation) {}
        });

        btnAdPlaca.startAnimation(expandir);

        return v;
    }

    private void carregaLista(){
        PlacaDao placaDao = new PlacaDao(getActivity());
//        placas = placaDao.listar();
        List<Placa> placasAux = placaDao.listar();

        if(lvPlacas.getAdapter() == null) {
            placas = placasAux;
            adapter = new PlacaAdapter(placas, getActivity());
            lvPlacas.setAdapter(adapter);
        }else {
            placas.clear();
            placas.addAll(placasAux);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        menu.removeItem(R.id.action_ok);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;
        placaSelecionada = (Placa)lvPlacas.getItemAtPosition(info.position);

        //MenuItem excluir = menu.add("Excluir");
//        menu.setHeaderIcon(R.drawable.ic_cep_128_black);
//        menu.setHeaderTitle("Confirma ?");
        menu.add(Menu.NONE, EXCLUIR, menu.NONE, "Excluir" );
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case 1:
                PlacaDao placaDao = new PlacaDao(getActivity());
                placaDao.apagar(placaSelecionada);

                // ** Se não tem ao menos uma placa carrega a tela de busca placa simples **
                if(placaDao.quantidadePlacasCadastradas() == 0)
                    menuOficinasInterface.mudaMenu("PLACA");
                else
                    carregaLista();
                break;
        }
        return super.onContextItemSelected(item);
    }
}
