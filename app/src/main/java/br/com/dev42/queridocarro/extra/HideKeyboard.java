package br.com.dev42.queridocarro.extra;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by Fernando on 06/08/2016.
 */
public class HideKeyboard {

    public HideKeyboard(Context ctx){
        InputMethodManager inputManager = (InputMethodManager) ctx
                .getSystemService(Context.INPUT_METHOD_SERVICE);

        // check if no view has focus:
        View v = ((Activity) ctx).getCurrentFocus();
        if (v == null)
                return;

        inputManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }
}
