package br.com.dev42.queridocarro.extra;

/**
 * Created by Fernando on 08/06/2016.
 */
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.widget.EditText;

public abstract class MaskPlaca {

    public static String unmask(String s) {
        
        return s.replaceAll("[.]", "").replaceAll("[-]", "")
                .replaceAll("[/]", "").replaceAll("[(]", "")
                .replaceAll("[)]", "");
    }

    public static TextWatcher insert(final EditText ediTxt) {
        return new TextWatcher() {
            boolean isUpdating;
            String old = "";
            String mascarado;

            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (isUpdating) {
                    isUpdating = false;
                    return;
                }

                //  **  Em alguns aparelhos as vezes não captava o retorno e dava erro no setSelection  **
                if(s.toString().length() == 0 )
                    return;

                if(s.length() >= 3)
                    ediTxt.setInputType(InputType.TYPE_CLASS_NUMBER);
                else
                    ediTxt.setInputType(InputType.TYPE_CLASS_TEXT);


                String placaDig = unmask(s.toString());
                int cont = 0;
                mascarado = "";
                for(char ch: placaDig.toCharArray()){

                    if(start == 3 && before == 1){//  ** Se for apagar o "-" não deve interferir  **
                        mascarado += ch;
                    }else
                    {
                        if(cont == 2){
                            mascarado += ch + "-";
                        }
                        else
                            mascarado += ch;
                    }
                    cont ++;
                    //  *** Tamanho maximo da placa **
                    if(cont >= 7)
                        break;
                }

                isUpdating = true;
                ediTxt.setText(mascarado.toUpperCase());
                ediTxt.setSelection(mascarado.length());
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void afterTextChanged(Editable s) {}
        };
    }

}
