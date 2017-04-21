package br.com.dev42.queridocarro.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import br.com.dev42.queridocarro.R;
import br.com.dev42.queridocarro.activities.ListaOficinasActivity;
import br.com.dev42.queridocarro.interfaces.MenuOficinasInterface;


public class CepFragment extends Fragment {

    private View v;
    private MenuOficinasInterface menuOficinasInterface;
    private EditText cep;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_cep, container, false);
        menuOficinasInterface = (MenuOficinasInterface)getActivity();
        setHasOptionsMenu(true);

        cep = (EditText)v.findViewById(R.id.cep);

        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_oficinas_cep, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.id_menu_buscar:
                if(cep.getText().toString().length() > 5) {
                    Intent intent = new Intent(getActivity(), ListaOficinasActivity.class);
                    intent.putExtra("LATITUDE", 0);
                    intent.putExtra("LONGITUDE", 0);
                    intent.putExtra("CEP", cep.getText().toString());
                    intent.putExtra("ENDERECO", "");
                    startActivity(intent);
                }else
                    Toast.makeText(getActivity(), R.string.erro_cep, Toast.LENGTH_SHORT).show();
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
