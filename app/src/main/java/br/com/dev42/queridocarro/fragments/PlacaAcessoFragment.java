package br.com.dev42.queridocarro.fragments;


import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import br.com.dev42.queridocarro.R;
import br.com.dev42.queridocarro.activities.OficinaDetalheActivity;
import br.com.dev42.queridocarro.activities.PopUpLoastPassActivity;
import br.com.dev42.queridocarro.dao.PlacaDao;
import br.com.dev42.queridocarro.extra.HideKeyboard;
import br.com.dev42.queridocarro.extra.MaskPlaca;
import br.com.dev42.queridocarro.interfaces.MenuOficinasInterface;

/**
 * A simple {@link Fragment} subclass.
 */
public class PlacaAcessoFragment extends Fragment {

    private View v;
    private MenuInflater inflater;
    private EditText placa, senha;
    private CheckBox salvar_placa;
    private MenuOficinasInterface menuOficinasInterface;
    private FloatingActionButton btnClosePlaca;

    private Animation contrair, expandir;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_placa_acesso, container, false);
//        setHasOptionsMenu(true);

        placa = (EditText)v.findViewById(R.id.placa);
        senha = (EditText)v.findViewById(R.id.senha);
        salvar_placa = (CheckBox)v.findViewById(R.id.salvar_placa);
        btnClosePlaca = (FloatingActionButton)v.findViewById(R.id.btn_close_add_placa);
        TextView esqueciCodigoAcesso = (TextView)v.findViewById(R.id.esqueci_codigo_acesso);
        esqueciCodigoAcesso.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);

        contrair = AnimationUtils.loadAnimation(getActivity(),R.anim.anim_contrair);
        expandir = AnimationUtils.loadAnimation(getActivity(),R.anim.anim_expandir);

        menuOficinasInterface = (MenuOficinasInterface)getActivity();

        // ** Se existir placa cadastrada da opção de voltar a lista **
        PlacaDao placaDao = new PlacaDao(getActivity());
        if(placaDao.quantidadePlacasCadastradas() > 0)
            btnClosePlaca.setVisibility(View.VISIBLE);
        else
            btnClosePlaca.setVisibility(View.GONE);

        btnClosePlaca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnClosePlaca.startAnimation(contrair);
            }
        });

        esqueciCodigoAcesso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(getActivity(), PopUpLoastPassActivity.class);
//                startActivity(intent);

    /*            LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                PopupWindow pw = new PopupWindow(inflater.inflate(R.layout.activity_pop_up_loast_pass, null, false),200,200, true);
                pw.showAtLocation(v.findViewById(R.id.esqueci_codigo_acesso), Gravity.CENTER, 0, 0);
                */



//                PopUpLoastPassActivity popUpLoastPassActivity = new PopUpLoastPassActivity();
//                popUpLoastPassActivity.show(getFragmentManager(), PopUpLoastPassActivity.TAG);

//                OficinaDetalheFragment oficinaDetalheFragment= new OficinaDetalheFragment();
//                oficinaDetalheFragment.show(getFragmentManager(), OficinaDetalheFragment.TAG);

                flipCard();
            }
        });

        contrair.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}
            @Override
            public void onAnimationEnd(Animation animation) {
                menuOficinasInterface.mudaMenu("LISTAPLACA");
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });

        btnClosePlaca.startAnimation(expandir);

        //  ** Mask da Placa **
        placa.addTextChangedListener(MaskPlaca.insert(placa));

        //  ** Tecla Enter do teclado **
        senha.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN)
                {
                    switch (keyCode)
                    {
                        case KeyEvent.KEYCODE_DPAD_CENTER:
                        case KeyEvent.KEYCODE_ENTER:

                            if(placasenhaValidos())
                                menuOficinasInterface.getToken(placa.getText().toString(),senha.getText().toString(), salvar_placa.isChecked());

                            return true;
                        default:
                            break;
                    }
                }
                return false;
            }
        });

        return v;
    }

    private void flipCard() {
//        if (mShowingBack) {
//            getFragmentManager().popBackStack();
//            return;
//        }
//
//        // Flip to the back.
//
//        mShowingBack = true;

        // Create and commit a new fragment transaction that adds the fragment for
        // the back of the card, uses custom animations, and is part of the fragment
        // manager's back stack.

        FragmentManager fragmentManager = getFragmentManager();

//        fragmentManager.popBackStack();
        fragmentManager
                .beginTransaction()

                // Replace the default fragment animations with animator resources
                // representing rotations when switching to the back of the card, as
                // well as animator resources representing rotations when flipping
                // back to the front (e.g. when the system Back button is pressed).
//                .setCustomAnimations(
//                        R.anim.card_flip_right_in,
//                        R.anim.card_flip_right_out,
//                        R.anim.card_flip_left_in,
//                        R.anim.card_flip_left_out)

//                .setCustomAnimations(
//                        R.anim.anim_expandir,
//                        R.anim.anim_contrair)

                .setCustomAnimations(
                        R.anim.scape_left,
                        R.anim.scape_right)

                // Replace any fragments currently in the container view with a
                // fragment representing the next page (indicated by the
                // just-incremented currentPage variable).
                .replace(R.id.content_historico, new LostPassFragment())

                // Add this transaction to the back stack, allowing users to press
                // Back to get to the front of the card.
//                .addToBackStack(null)

                // Commit the transaction.
                .commit();
    }

//    @Override
//    public void onAttach(Context context) {
//        Log.e("DEV42", "onAttach PlacaAcesso");
//        super.onAttach(context);
//        getActivity().invalidateOptionsMenu();
//        setHasOptionsMenu(true);
//    }
//
    @Override
    public void onCreate(Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }
//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
////        Log.e("DEV42", "onCreateOptionsMenu PlacaAcesso");
//
//        super.onCreateOptionsMenu(menu, inflater);
////        this.inflater = inflater;
//        //  **  Limpo o menu e dou inflate aqui pois preciso remover outros menus dos fragments
//        menu.clear();
//        inflater.inflate(R.menu.menu_placa, menu);
//    }

//    @Override
//    public void onPrepareOptionsMenu(Menu menu){
//        //  **  Limpo o menu e dou inflate aqui pois preciso remover outros menus dos fragments
//        menu.clear();
//        MenuInflater inflater = getActivity().getMenuInflater();
//        inflater.inflate(R.menu.menu_main, menu);
//        super.onPrepareOptionsMenu(menu);
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        HideKeyboard hideKeyboard = new HideKeyboard(getActivity());
        switch (item.getItemId()){
            case R.id.action_ok:

                if(placasenhaValidos())
                    menuOficinasInterface.getToken(placa.getText().toString(),senha.getText().toString(), salvar_placa.isChecked());

                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private boolean placasenhaValidos(){
        if(placa.length() < 8){
            placa.setError("Placa inválida.");
            return false;
        }else{
            if(senha.length() == 0){
                senha.setError("Código de acesso inválido.");
                return false;
            }else
                return true;
        }
    }
}
