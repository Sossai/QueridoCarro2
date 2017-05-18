package br.com.dev42.queridocarro.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.com.dev42.queridocarro.R;
import br.com.dev42.queridocarro.activities.PopUpLoastPassActivity;

public class OficinaDetalheFragment extends DialogFragment {
    private View v;
    public static String TAG = PopUpLoastPassActivity.class.getSimpleName();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_oficina_detalhe, container, false);
        return v;
    }

}
