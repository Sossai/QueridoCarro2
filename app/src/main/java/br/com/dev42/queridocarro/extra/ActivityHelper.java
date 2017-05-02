package br.com.dev42.queridocarro.extra;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by sossai on 24/04/17.
 */

public class ActivityHelper {
    private Activity activity;

    public ActivityHelper(Activity activity) {
        this.activity = activity;
    }

    public void mudaStatusCorTransparent() {
        //make full transparent statusBar
        if (Build.VERSION.SDK_INT >= 19 && Build.VERSION.SDK_INT < 21) {
            setWindowFlag(activity, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, true);
        }
        if (Build.VERSION.SDK_INT >= 19) {
            activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
        if (Build.VERSION.SDK_INT >= 21) {
            setWindowFlag(activity, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, true);
            setWindowFlag(activity, WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, false);
            //setWindowFlag(this, WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS, false);
            activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
    }

    private static void setWindowFlag(Activity activity, final int bits, boolean on) {
        Window win = activity.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }
    
}
