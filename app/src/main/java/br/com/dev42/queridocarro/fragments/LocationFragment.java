package br.com.dev42.queridocarro.fragments;


import android.content.Intent;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class LocationFragment extends Fragment {

    private View v;
    private MenuOficinasInterface menuOficinasInterface;
    private EditText estado, cidade;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_location, container, false);
        menuOficinasInterface = (MenuOficinasInterface)getActivity();
        setHasOptionsMenu(true);
        estado = (EditText)v.findViewById(R.id.id_estado);
        cidade = (EditText)v.findViewById(R.id.id_cidade);

        return v;
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_oficinas_location, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.id_menu_buscar:
                if(estado.getText() != null || cidade.getText() !=null) {
                    Intent intent = new Intent(getActivity(), ListaOficinasActivity.class);
                    intent.putExtra("LATITUDE", 0);
                    intent.putExtra("LONGITUDE", 0);
                    intent.putExtra("CEP", "");
                    intent.putExtra("ENDERECO", cidade.getText().toString() + "," + estado.getText().toString());
                    startActivity(intent);
                }else
                    Toast.makeText(getActivity(), R.string.erro_cidadeestado, Toast.LENGTH_SHORT).show();
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
