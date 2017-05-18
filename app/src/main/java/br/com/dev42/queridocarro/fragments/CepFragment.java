package br.com.dev42.queridocarro.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.transition.ChangeBounds;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import br.com.dev42.queridocarro.R;
import br.com.dev42.queridocarro.activities.ListaOficinasActivity;
import br.com.dev42.queridocarro.extra.MaskCep;
import br.com.dev42.queridocarro.interfaces.MenuOficinasInterface;


public class CepFragment extends Fragment {

    private View v;
    private MenuOficinasInterface menuOficinasInterface;
    private EditText cep;
    private String savedCep;

    private Animation contrair, expandir;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_cep, container, false);
        menuOficinasInterface = (MenuOficinasInterface)getActivity();

        cep = (EditText)v.findViewById(R.id.cep);
        cep.addTextChangedListener(MaskCep.insert(cep));

        contrair = AnimationUtils.loadAnimation(getActivity(),R.anim.anim_contrair);
        expandir = AnimationUtils.loadAnimation(getActivity(),R.anim.anim_expandir);

        ImageView imgCep = (ImageView)v.findViewById(R.id.img_cep_fr);
        imgCep.startAnimation(expandir);

//        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            getActivity().getWindow().setSharedElementEnterTransition(new ChangeBounds());
//        }

        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        menu.removeItem(R.id.id_menu_cep);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.id_menu_buscar:
                if(cep.getText().toString().length() >= 9) {
                    Intent intent = new Intent(getActivity(), ListaOficinasActivity.class);
                    intent.putExtra("LATITUDE", 0.0);
                    intent.putExtra("LONGITUDE", 0.0);
                    intent.putExtra("CEP", cep.getText().toString());
                    intent.putExtra("ENDERECO", "");
                    intent.putExtra("TIPO", 4);
                    /*tipos
                    CidadeEstado = 1
                    LatLon = 2
                    Endereco = 3
                    Cep = 4*/
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
