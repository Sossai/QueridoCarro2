package br.com.dev42.queridocarro.extra;

/**
 * Created by Fernando on 08/06/2016.
 */
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.widget.EditText;


public abstract class MaskCpfCnpj {

    private static final String maskCNPJ = "##.###.###/####-##";
    private static final String maskCPF = "###.###.###-##";

    public static String unmask(String s) {
        return s.replaceAll("[^0-9]*", "");
    }

    public static TextWatcher insert(final EditText editText) {
        return new TextWatcher() {
            boolean isUpdating;
            String old = "";

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String str = MaskCpfCnpj.unmask(s.toString());
                String mask;
                String defaultMask = getDefaultMask(str);
                editText.setInputType(InputType.TYPE_CLASS_NUMBER);

                switch (str.length()) {
                    case 11:
                        mask = maskCPF;
                        break;
                    case 14:
                        mask = maskCNPJ;
                        break;

                    default:
                        mask = defaultMask;
                        break;
                }

                String mascara = "";
                if (isUpdating) {
                    old = str;
                    isUpdating = false;
                    return;
                }
                int i = 0;
                for (char m : mask.toCharArray()) {
                    if ((m != '#' && str.length() > old.length()) || (m != '#' && str.length() < old.length() && str.length() != i)) {
                        mascara += m;
                        continue;
                    }

                    try {
                        mascara += str.charAt(i);
                    } catch (Exception e) {
                        break;
                    }
                    i++;
                }
                isUpdating = true;
                editText.setText(mascara);
                editText.setSelection(mascara.length());
            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            public void afterTextChanged(Editable s) {
            }
        };
    }

    private static String getDefaultMask(String str) {
        String defaultMask = maskCPF;
        if (str.length() > 11){
            defaultMask = maskCNPJ;
        }
        return defaultMask;
    }


/*
    public static String unmask(String s) {
        
        return s.replaceAll("[.]", "").replaceAll("[-]", "");
    }

    public static TextWatcher insert(final EditText ediTxt) {
        return new TextWatcher() {
            boolean isUpdating;
            String old = "";
            String mascarado = new String();

            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (isUpdating) {
                    isUpdating = false;
                    return;
                }

                //  **  Em alguns aparelhos as vezes não captava o retorno e dava erro no setSelection  **
                //if(s.toString().length() == 0 )
                //    return;

                ediTxt.setInputType(InputType.TYPE_CLASS_NUMBER);

                String placaDig = unmask(s.toString());
                Integer cont = 0;
                mascarado = "";
                for(char ch: placaDig.toCharArray()){

                    if(start == 5 && before == 1){//  ** Se for apagar o "-" não deve interferir  **
                        mascarado += ch;
                    }else
                    {
                        if(cont == 4)
                            mascarado += ch + "-";
                        else
                            mascarado += ch;
                    }
                    cont ++;
                    //  *** Tamanho maximo da placa **
                    if(cont >= 9)
                        break;
                }
                isUpdating = true;
                ediTxt.setText(mascarado);
                ediTxt.setSelection(mascarado.length());
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void afterTextChanged(Editable s) {}
        };
    }*/

}
