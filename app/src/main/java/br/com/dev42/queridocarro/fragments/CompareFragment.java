package br.com.dev42.queridocarro.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import br.com.dev42.queridocarro.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CompareFragment extends Fragment {

//    private MenuInflater inflater;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_compare, container, false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        this.inflater = inflater;
//    }

    @Override
    public void onPrepareOptionsMenu(Menu menu){
        //  **  Limpo o menu e dou inflate aqui pois preciso remover outros menus dos fragments
        menu.clear();
        MenuInflater inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.menu_compare, menu);
        super.onPrepareOptionsMenu(menu);
    }
}
