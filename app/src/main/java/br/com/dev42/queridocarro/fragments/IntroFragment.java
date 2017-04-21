package br.com.dev42.queridocarro.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.com.dev42.queridocarro.R;


public class IntroFragment extends Fragment {
//    // TODO: Rename parameter arguments, choose names that match

    public IntroFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Bundle bundle = getArguments();
        if(bundle != null)
        {
            Integer pagina = bundle.getInt("intro");
            switch (pagina){
                case 0:
                    return inflater.inflate(R.layout.fragment_intro, container, false);
                case 1:
                    return inflater.inflate(R.layout.fragment_intro_page2, container, false);
            }
        }

        return inflater.inflate(R.layout.fragment_intro, container, false);
    }

}
