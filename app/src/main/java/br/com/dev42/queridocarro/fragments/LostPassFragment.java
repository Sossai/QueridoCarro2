package br.com.dev42.queridocarro.fragments;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
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
import br.com.dev42.queridocarro.extra.MaskCpfCnpj;
import br.com.dev42.queridocarro.extra.MaskPlaca;
import br.com.dev42.queridocarro.extra.Md5;
import br.com.dev42.queridocarro.interfaces.MenuOficinasInterface;
import br.com.dev42.queridocarro.interfaces.QueridoCarroInterface;
import br.com.dev42.queridocarro.model.RecuperarSenha;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class LostPassFragment extends Fragment {

    private View v;
    private QueridoCarroInterface service;
    private Animation scapeLeft;
    private Animation scapeRight;
    private Animation contrair;
    private EditText lostEmail, lostPlaca, lostCnpj;
    private MenuOficinasInterface menuOficinasInterface;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_lost_pass, container, false);

        scapeLeft = AnimationUtils.loadAnimation(getActivity(),R.anim.scape_left);
        scapeRight = AnimationUtils.loadAnimation(getActivity(),R.anim.scape_right);
        contrair = AnimationUtils.loadAnimation(getActivity(),R.anim.anim_contrair);

        lostPlaca = (EditText)v.findViewById(R.id.lvlost_placa);
        lostCnpj = (EditText)v.findViewById(R.id.lvlost_cnpj);
        lostEmail = (EditText)v.findViewById(R.id.lv_lostemail);
        final FloatingActionButton lostBtnClosePlaca = (FloatingActionButton)v.findViewById(R.id.btn_lost_close_add_placa);

        menuOficinasInterface = (MenuOficinasInterface)getActivity();

        lostPlaca.addTextChangedListener(MaskPlaca.insert(lostPlaca));
        //  ** Mask da Placa **
        lostCnpj.addTextChangedListener(MaskCpfCnpj.insert(lostCnpj));

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

        service = new Retrofit.Builder()
                .baseUrl(QueridoCarroInterface.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(QueridoCarroInterface.class);


        //  ** Tecla Enter do teclado **
        lostCnpj.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN)
                {
                    switch (keyCode)
                    {
                        case KeyEvent.KEYCODE_DPAD_CENTER:
                        case KeyEvent.KEYCODE_ENTER:

                            capturaSenha();
                            return true;
                        default:
                            break;
                    }
                }
                return false;
            }
        });

//        lostPlaca.setOnKeyListener(new View.OnKeyListener() {
//            @Override
//            public boolean onKey(View v, int keyCode, KeyEvent event) {
//                if (event.getAction() == KeyEvent.ACTION_DOWN)
//                {
//                    switch (keyCode)
//                    {
//                        case KeyEvent.KEYCODE_DPAD_CENTER:
//                        case KeyEvent.KEYCODE_ENTER:
//
//                            lostBtnClosePlaca.setFocusable(true);
//                            return true;
//                        default:
//                            break;
//                    }
//                }
//                return false;
//            }
//        });


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

                capturaSenha();

                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void capturaSenha(){
        if(validaDados()){
            Md5 md5 = new Md5();

            String cnpjFinal = lostCnpj.getText().toString().replace("/", "").replace("-", "").replace(".", "").trim();
            String placaFinal = lostPlaca.getText().toString().replace("-", "").trim();

            //Log.e("DEV42", cnpjFinal + " " + placaFinal);

            String hash = md5.md5("wg"+ cnpjFinal + placaFinal + "mobile").trim();
            //Log.e("DEV42", hash);

            RecuperarSenha.Envio recuperarSenha = new RecuperarSenha.Envio(hash, "", placaFinal, cnpjFinal);
            //Log.e("DEV42", recuperarSenha.toString());

            final Call<RecuperarSenha.Retorno> retornoSenha = service.getSenha(recuperarSenha);

            retornoSenha.enqueue(new Callback<RecuperarSenha.Retorno>() {
                @Override
                public void onResponse(Call<RecuperarSenha.Retorno> call, Response<RecuperarSenha.Retorno> response) {
                    if(!response.isSuccessful()) {
                        Toast.makeText(getActivity(), R.string.erro_padrao_dados, Toast.LENGTH_LONG).show();
                    }else {
                        //Log.e("DEV42", response.body().toString());
                        if(response.body().getSenha().isEmpty())
                            Toast.makeText(getActivity(), response.body().getMensagem(), Toast.LENGTH_LONG).show();
                        else
                            menuOficinasInterface.getToken(lostPlaca.getText().toString(),response.body().getSenha(), true);
                    }
                }

                @Override
                public void onFailure(Call<RecuperarSenha.Retorno> call, Throwable t) {
                    Toast.makeText(getActivity(), R.string.erro_padrao_dados, Toast.LENGTH_LONG).show();
                }
            });

        }
    }

    private boolean validaDados(){
        if(lostPlaca.length() < 8){
            lostPlaca.setError("Placa inválida.");
            return false;
        }else{
            if(lostCnpj.length() < 14) {
                lostCnpj.setError("CPF inválido");
                return false;
            }
            else{
                if(lostCnpj.length() < 18 && lostCnpj.length() > 14){
                    lostCnpj.setError("CNPJ inválido");
                    return false;
                }
                else
                    return true;
            }
        }
    }

//    private boolean isValidEmail(CharSequence target) {
//        if (target == null) {
//            return false;
//        } else {
//            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
//        }
//    }

}
