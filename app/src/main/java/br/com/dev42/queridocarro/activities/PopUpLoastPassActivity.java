package br.com.dev42.queridocarro.activities;

import android.app.Activity;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import br.com.dev42.queridocarro.R;


public class PopUpLoastPassActivity extends DialogFragment {
    private View v;
    public static String TAG = PopUpLoastPassActivity.class.getSimpleName();

/*    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up_loast_pass);

//        DisplayMetrics dm = new DisplayMetrics();
//        getWindowManager().getDefaultDisplay().getMetrics(dm);
//
//        int width = dm.widthPixels * 8;
//        int height = dm.heightPixels * 6;
//
//        getWindow().setLayout((width),(height ));

    }*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.activity_pop_up_loast_pass, container, false);
//        return super.onCreateView(inflater, container, savedInstanceState);
        return v;
    }
}
