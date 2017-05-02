package br.com.dev42.queridocarro.fragments;


import android.app.Activity;
import android.os.Bundle;
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
//    private MenuInflater inflater;
    private ListView lvPlacas;
    private Placa placaSelecionada;
    private MenuOficinasInterface menuOficinasInterface;
    private static final int EXCLUIR = 1;
    private FloatingActionButton btnAdPlaca;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_lista_placas, container, false);
        setHasOptionsMenu(true);

        menuOficinasInterface = (MenuOficinasInterface)getActivity();

        lvPlacas = (ListView) v.findViewById(R.id.lista_placas_lv);
        btnAdPlaca = (FloatingActionButton)v.findViewById(R.id.btn_add_placa);

        PlacaDao placaDao = new PlacaDao(getActivity());
        final List<Placa> placas = placaDao.listar();

        PlacaAdapter adapter = new PlacaAdapter(placas, getActivity());
        lvPlacas.setAdapter(adapter);

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
                menuOficinasInterface.mudaMenu("PLACA");
            }
        });
        return v;
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
                break;
        }
        return super.onContextItemSelected(item);
    }
}
