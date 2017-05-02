package br.com.dev42.queridocarro.fragments;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
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

public class OficinaFragment extends Fragment implements LocationListener {

    private static final String TAG = "DEV42";
    private View v;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_oficina, container, false);

//        menuStatus = "GEOLOCATION";
//        escolheFiltroOficina("GEOLOCATION");

        MenuOficinasInterface menuOficinasInterface = (MenuOficinasInterface)getActivity();
        menuOficinasInterface.mudaMenu("GEOLOCATION");

        return v;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onLocationChanged(Location location) {}

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {}

    @Override
    public void onProviderEnabled(String provider) {}

    @Override
    public void onProviderDisabled(String provider) {}
}
