package br.com.dev42.queridocarro.fragments;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.Toast;

import br.com.dev42.queridocarro.R;
import br.com.dev42.queridocarro.extra.HideKeyboard;
import br.com.dev42.queridocarro.extra.MaskPlaca;
import br.com.dev42.queridocarro.interfaces.MenuOficinasInterface;

/**
 * A simple {@link Fragment} subclass.
 */
public class LostPassFragment extends Fragment {

    private View v;
    private Animation scapeLeft;
    private Animation scapeRight;
    private Animation contrair;
    private EditText lostEmail;

    private MenuOficinasInterface menuOficinasInterface;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_lost_pass, container, false);

        scapeLeft = AnimationUtils.loadAnimation(getActivity(),R.anim.scape_left);
        scapeRight = AnimationUtils.loadAnimation(getActivity(),R.anim.scape_right);
        contrair = AnimationUtils.loadAnimation(getActivity(),R.anim.anim_contrair);

        EditText lostPlaca = (EditText)v.findViewById(R.id.lvlost_placa);
        EditText lostCnpj = (EditText)v.findViewById(R.id.lvlost_cnpj);
        lostEmail = (EditText)v.findViewById(R.id.lv_lostemail);
        final FloatingActionButton lostBtnClosePlaca = (FloatingActionButton)v.findViewById(R.id.btn_lost_close_add_placa);

        menuOficinasInterface = (MenuOficinasInterface)getActivity();

        lostPlaca.addTextChangedListener(MaskPlaca.insert(lostPlaca));

        lostBtnClosePlaca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lostBtnClosePlaca.startAnimation(contrair);
            }
        });

        contrair.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
                menuOficinasInterface.mudaMenu("PLACA");
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });

        return v;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        HideKeyboard hideKeyboard = new HideKeyboard(getActivity());
        switch (item.getItemId()){
            case R.id.action_ok:
                if(isValidEmail(lostEmail.getText().toString())){
                    Toast.makeText(getActivity(), "Email ok", Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(getActivity(), "Email inválido.", Toast.LENGTH_LONG).show();
                }
                //if(placasenhaValidos())
                //    menuOficinasInterface.getToken(placa.getText().toString(),senha.getText().toString(), salvar_placa.isChecked());

                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }

}
