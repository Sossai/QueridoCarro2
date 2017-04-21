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
    private MenuInflater inflater;
    private android.support.v7.app.ActionBar actionBar;

    private CepFragment cepFragment = new CepFragment();
    private GeolocationFragment geolocationFragment = new GeolocationFragment();
    private LocationFragment locationFragment = new LocationFragment();
    private String menuStatus;

    private EditText cep;
    private EditText cidade;
    private EditText estado;

    private Location location;
    private LocationManager locationManager;
    private Intent intent;

    private Double latitude, longitude;
    boolean isGPSEnabled = false;
    boolean isNetworkEnabled = false;
    // The minimum distance to change Updates in meters
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 meters
    // The minimum time between updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1; // 1 minute


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_oficina, container, false);

//        menuStatus = "GEOLOCATION";
//        escolheFiltroOficina("GEOLOCATION");

        MenuOficinasInterface menuOficinasInterface = (MenuOficinasInterface)getActivity();
        menuOficinasInterface.mudaMenu("GEOLOCATION");

        return v;
    }

/*    private void escolheFiltroOficina(String menuAtivo){
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        switch(menuAtivo){
            case "GEOLOCATION":
                //geolocationFragment.setArguments("opa","1");
                fragmentTransaction.replace(R.id.content_oficina, geolocationFragment);
                break;
            case "CEP":
                fragmentTransaction.replace(R.id.content_oficina, cepFragment);

                break;
            case "LOCATION":
                fragmentTransaction.replace(R.id.content_oficina, locationFragment);
            break;

            default:
                fragmentTransaction.replace(R.id.content_oficina, geolocationFragment);
        }
//        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }*/

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

/*    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        this.inflater = inflater;
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu){
        inflater.inflate(R.menu.menu_oficinas, menu);
        super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent(getActivity(), ListaOficinasActivity.class);;
            switch (item.getItemId()){
                case R.id.id_menu_buscar:

*//*                    LayoutInflater inflater = getActivity().getLayoutInflater();
                    View dialogView = inflater.inflate(R.layout.fragment_cep, null);

                    cep = (EditText) dialogView.findViewById(R.id.cep);

                    Toast.makeText(getActivity(), "opa22" + cep.getText(),Toast.LENGTH_SHORT).show();
                //getOficinasProximas();
*//*

                break;

            case R.id.id_menu_geolocation:
                menuStatus = "GEOLOCATION";
                escolheFiltroOficina("GEOLOCATION");
                break;

            case R.id.id_menu_cep:
                menuStatus = "CEP";
                escolheFiltroOficina("CEP");
                break;

            case R.id.id_menu_location:
                menuStatus = "LOCATION";
                escolheFiltroOficina("LOCATION");
                break;
        }

        return super.onOptionsItemSelected(item);
    }*/

    private void getOficinasProximas(){
        switch (menuStatus){
            case "GEOLOCATION":
                getLocation();
                if(latitude != null || longitude !=null) {
                    intent.putExtra("LATITUDE", latitude);
                    intent.putExtra("LONGITUDE", longitude);
                    intent.putExtra("CEP", "");
                    intent.putExtra("ENDERECO", "");
                    startActivity(intent);
                }
                break;
            case "CEP":
                if(cep.getText().length() > 0){
                    intent.putExtra("LATITUDE", 0);
                    intent.putExtra("LONGITUDE", 0);
                    intent.putExtra("CEP", cep.getText());
                    intent.putExtra("ENDERECO", "");
                    startActivity(intent);
                }
                break;
            case "LOCATION":
                break;
        }
    }

    private void getLocation() {

        try{

            locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

            // getting GPS status
            isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            // getting network status
            isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (!isGPSEnabled && !isNetworkEnabled) {
                Toast.makeText(getActivity(), "Sem sinal de GPS.", Toast.LENGTH_SHORT).show();
            }else {
                // ** verifica permição de uso do GPS **

                if (ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) ==
                        PackageManager.PERMISSION_GRANTED &&
                        ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION) ==
                                PackageManager.PERMISSION_GRANTED) {

                    if (isNetworkEnabled) {
                        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                                MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);

                        if (locationManager != null) {
                            location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                            if (location != null) {
//                            Log.e(TAG, "LOC by Network");

                                latitude = location.getLatitude();
                                longitude = location.getLongitude();
//                                Toast.makeText(getActivity(), "NET:" + latitude.toString() + " " + longitude.toString(), Toast.LENGTH_LONG).show();
                            }
                        }
                    }

                    if (isGPSEnabled) {
                        if (location == null) {
                            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                                    MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
//                            Log.d("activity", "RLOC: GPS Enabled");
                            if (locationManager != null) {
                                location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                                if (location != null) {
//                                Log.d("activity", "RLOC: loc by GPS");

                                    latitude = location.getLatitude();
                                    longitude = location.getLongitude();
//                                    Toast.makeText(getActivity(), "GPS:" + latitude.toString() + " " + longitude.toString(), Toast.LENGTH_LONG).show();
                                }
                            }
                        }
                    }

                }else
                {
                    ActivityCompat.requestPermissions(getActivity(), new String[] {
                                    Manifest.permission.ACCESS_FINE_LOCATION,
                                    Manifest.permission.ACCESS_COARSE_LOCATION },
                            8);
                }

            }


        }catch (Exception ex){
            //Log.e("Erro Location", ex.getMessage());
            Toast.makeText(getActivity(), "Falha ao capturar dados de Geolocalização.", Toast.LENGTH_LONG).show();
        }
    }

/*    @Override
    public void mudaMenu(String statusMenu) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        switch(statusMenu){
            case "GEOLOCATION":
                fragmentTransaction.replace(R.id.content_oficina, geolocationFragment);
                break;
            case "CEP":
                fragmentTransaction.replace(R.id.content_oficina, cepFragment);

                break;
            case "LOCATION":
                fragmentTransaction.replace(R.id.content_oficina, locationFragment);
                break;

            default:
                fragmentTransaction.replace(R.id.content_oficina, geolocationFragment);
        }
        fragmentTransaction.commit();
    }*/

    @Override
    public void onLocationChanged(Location location) {}

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {}

    @Override
    public void onProviderEnabled(String provider) {}

    @Override
    public void onProviderDisabled(String provider) {}
}
