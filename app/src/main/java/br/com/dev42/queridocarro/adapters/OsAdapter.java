package br.com.dev42.queridocarro.adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.nfc.Tag;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import br.com.dev42.queridocarro.R;
import br.com.dev42.queridocarro.activities.ListaOsActivity;
import br.com.dev42.queridocarro.model.Historico;

/**
 * Created by sossai on 07/04/17.
 */

public class OsAdapter extends BaseAdapter {

    private List<Historico.Retorno> listaOs;
    private Activity activity;
    private View layout;

    public OsAdapter(List<Historico.Retorno> listaOs, Activity activity) {
        this.listaOs = listaOs;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return listaOs.size();
    }

    @Override
    public Object getItem(int position) {
        return listaOs.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Historico.Retorno os = listaOs.get(position);
        LayoutInflater inflater = activity.getLayoutInflater();
        ViewHolder holder;

        if(convertView == null){
            layout = inflater.inflate(R.layout.list_os,parent, false);
            holder = new ViewHolder(layout);
            layout.setTag(holder);
        }else {
            layout = convertView;
            holder = (ViewHolder)layout.getTag();
        }

        holder.nomeOficina.setText(os.getOficina());
        holder.numeroOs.setText("Os/Venda : " + os.getNumVenda().toString());
        //holder.dataOs.setText(dataToTextData(os.getData()));
        holder.dataOs.setText("26/01/1978");
        holder.siglaOficina.setText(os.getOficina().substring(0,1).toUpperCase());
//        holder.siglaOficina.setBackgroundColor(ContextCompat.getColor(activity, R.color.colorAmarelo));

        GradientDrawable shape = (GradientDrawable)holder.siglaOficina.getBackground();
//        shape.setColor(ContextCompat.getColor(activity, R.color.colorAmarelo));

        shape.setColor(Color.parseColor(escolheCor(os.getOficina().substring(0,1).toUpperCase())));
        holder.siglaOficina.setBackground(shape);

        return layout;
    }

    private class ViewHolder{
        TextView nomeOficina;
        TextView numeroOs;
        TextView dataOs;
        Button siglaOficina;

        ViewHolder(View view){
            this.nomeOficina = (TextView)view.findViewById(R.id.nomeOficina);
            this.numeroOs = (TextView)view.findViewById(R.id.numeroOs);
            this.dataOs = (TextView)view.findViewById(R.id.data_list_oficina);
            this.siglaOficina = (Button)view.findViewById(R.id.sigla_oficina);
        }
    }

    private String escolheCor(String letra){
        String colorSring = "";

        switch(letra){

            case "A":
                colorSring = "#213782";
                break;
            case "B":
                colorSring = "#346fbd";
                break;
            case "C":
                colorSring = "#5ea0f4";
                break;
            case "D":
                colorSring = "#f1d142";
                break;
            case "E":
                colorSring = "#f4dd79";
                break;
            case "F":
                colorSring = "#e7bd03";
                break;
            case "G":
                colorSring = "#bdc0c5";
                break;
            case "H":
                colorSring = "#a3a6ab";
                break;
            case "I":
                colorSring = "#f13030";
                break;
            case "J":
                colorSring = "#f45c5c";
                break;
            case "K":
                colorSring = "#a21111";
                break;
            case "L":
                colorSring = "#44a211";
                break;
            case "M":
                colorSring = "#5bcd1c";
                break;
            case "N":
                colorSring = "#2b6e07";
                break;
            case "O":
                colorSring = "#a013f1";
                break;
            case "P":
                colorSring = "#c071ed";
                break;
            case "Q":
                colorSring = "#eba346";
                break;
            case "R":
                colorSring = "#e28e23";
                break;
            case "S":
                colorSring = "#7698b0";
                break;
            case "T":
                colorSring = "#98ce7b";
                break;
            case "U":
                colorSring = "#21edf4";
                break;
            case "V":
                colorSring = "#947144";
                break;
            case "X":
                colorSring = "#938470";
                break;
            case "Y":
                colorSring = "#83ac6d";
                break;
            case "Z":
                colorSring = "#676099";
                break;
            case "W":
                colorSring = "#7fa8d6";
                break;
            default:
                colorSring = "#213782";
                break;
        }
        return colorSring;
    }

    /*
    // ** REVER **
    public String dataToTextData(String dataOsString){
        SimpleDateFormat  format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        try {
            Date dataOs = format.parse(dataOsString);
            Locale BRAZIL = new Locale("pt","BR");
            SimpleDateFormat fmt = new SimpleDateFormat("dd 'de' MMMM 'de' yyyy", BRAZIL);
            return fmt.format(dataOs);
        }catch (ParseException e){
            e.printStackTrace();
            return "";
        }
    }
    */
}
