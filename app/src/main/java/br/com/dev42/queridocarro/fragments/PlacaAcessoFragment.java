package br.com.dev42.queridocarro.fragments;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import br.com.dev42.queridocarro.R;
import br.com.dev42.queridocarro.dao.PlacaDao;
import br.com.dev42.queridocarro.extra.HideKeyboard;
import br.com.dev42.queridocarro.extra.MaskPlaca;
import br.com.dev42.queridocarro.interfaces.MenuOficinasInterface;

/**
 * A simple {@link Fragment} subclass.
 */
public class PlacaAcessoFragment extends Fragment {

    private View v;
    private MenuInflater inflater;
    private EditText placa, senha;
    private CheckBox salvar_placa;
    private MenuOficinasInterface menuOficinasInterface;
    private FloatingActionButton btnClosePlaca;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_placa_acesso, container, false);

        placa = (EditText)v.findViewById(R.id.placa);
        senha = (EditText)v.findViewById(R.id.senha);
        salvar_placa = (CheckBox)v.findViewById(R.id.salvar_placa);
        btnClosePlaca = (FloatingActionButton)v.findViewById(R.id.btn_close_add_placa);

        menuOficinasInterface = (MenuOficinasInterface)getActivity();

        // ** Se existir placa cadastrada da opção de voltar a lista **
        PlacaDao placaDao = new PlacaDao(getActivity());
        if(placaDao.quantidadePlacasCadastradas() > 0)
            btnClosePlaca.setVisibility(View.VISIBLE);
        else
            btnClosePlaca.setVisibility(View.GONE);

        btnClosePlaca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menuOficinasInterface.mudaMenu("LISTAPLACA");
            }
        });

        //  ** Mask da Placa **
        placa.addTextChangedListener(MaskPlaca.insert(placa));

        return v;
    }
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
                    menuOficinasInterface.getToken(placa.getText().toString(),senha.getText().toString(), salvar_placa.isChecked());

                break;
        }

        return super.onOptionsItemSelected(item);
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
    }
}
