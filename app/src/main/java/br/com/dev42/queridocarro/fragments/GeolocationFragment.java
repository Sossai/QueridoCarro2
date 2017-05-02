package br.com.dev42.queridocarro.fragments;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.nfc.Tag;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.util.Pair;
import android.support.v4.view.ViewCompat;
import android.transition.ChangeBounds;
import android.transition.ChangeClipBounds;
import android.transition.ChangeImageTransform;
import android.transition.Fade;
import android.transition.TransitionInflater;
import android.transition.TransitionSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import br.com.dev42.queridocarro.R;
import br.com.dev42.queridocarro.activities.ListaOficinasActivity;
import br.com.dev42.queridocarro.extra.HideKeyboard;
import br.com.dev42.queridocarro.interfaces.MenuOficinasInterface;

/**
 * A simple {@link Fragment} subclass.
 */
public class GeolocationFragment extends Fragment implements LocationListener{

    private View v;

    private Location location;
    private LocationManager locationManager;
    private Menu menu;

//    private Intent intent;
//    private String bestProvider;

    private Double latitude, longitude;
    boolean isGPSEnabled = false;
    boolean isNetworkEnabled = false;
    // The minimum distance to change Updates in meters
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 meters
    // The minimum time between updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1; // 1 minute

    private MenuOficinasInterface menuOficinasInterface;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_geolocation, container, false);
        setHasOptionsMenu(true);

        menuOficinasInterface = (MenuOficinasInterface)getActivity();

//        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            getActivity().getWindow().setSharedElementExitTransition(new ChangeBounds());
//        }

        return v;
    }

/*    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }*/

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        this.menu = menu;
        menu.clear();
        inflater.inflate(R.menu.menu_oficinas_geolocation, menu);
    }

    @Override
    public void onResume() {
//        Log.e("DEV42", "resume Geolocation");
        super.onPrepareOptionsMenu(menu);
        super.onResume();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        //FragmentManager fragmentManager = getFragmentManager();


        switch (item.getItemId()){
            case R.id.id_menu_buscar:
                getLocation();
                if(latitude != null || longitude !=null) {
                    Intent intent = new Intent(getActivity(), ListaOficinasActivity.class);
                    intent.putExtra("LATITUDE", latitude);
                    intent.putExtra("LONGITUDE", longitude);
                    intent.putExtra("CEP", "");
                    intent.putExtra("ENDERECO", "");
                    intent.putExtra("TIPO", 2);
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

                // Transitions
//                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//
//                    View imgGeolocation = v.findViewById(R.id.img_geolocation_fr);

//                    ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(),
//                            Pair.create(imgGeolocation, "element_cep"));


//                    TransitionSet transitionSet = new TransitionSet();
//                    //transitionSet.addTransition(new ChangeImageTransform());
//                    transitionSet.addTransition(new ChangeBounds());
//                    transitionSet.addTransition(new ChangeClipBounds());
//                    transitionSet.setDuration(600);
//
////                    Fade fade = new Fade();
////                    fade.setStartDelay(300);
//
//                    CepFragment cepFragment = new CepFragment();
//                    cepFragment.setSharedElementEnterTransition(transitionSet);
                    //cepFragment.setSharedElementReturnTransition(transitionSet);


                    //cepFragment.setEnterTransition(new ChangeImageTransform());

//                    cepFragment.setSharedElementEnterTransition(new Fade());
//                    cepFragment.setEnterTransition(new ChangeImageTransform());
//                    imgGeolocation.setTransitionName("element_cep");

//                    ViewCompat.setTransitionName(v.findViewById(R.id.img_geolocation_fr), "element_cep");
//
//                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction()
//                            .addSharedElement(imgGeolocation, "element_cep")
//                            .replace(R.id.content_oficina, cepFragment);

                    //fragmentTransaction.replace(R.id.content_oficina, cepFragment);

                    //fragmentTransaction.commit();

//                    activity.startActivity(intent, options.toBundle());
//                }else {
//                    //startActivity(intent);
//                }

                break;

            case R.id.id_menu_location:
                menuOficinasInterface.mudaMenu("LOCATION");
                break;
        }

        return super.onOptionsItemSelected(item);
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

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
