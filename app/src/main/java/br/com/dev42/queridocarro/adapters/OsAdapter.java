package br.com.dev42.queridocarro.adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import br.com.dev42.queridocarro.extra.CorSigla;
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
        holder.dataOs.setText(os.getData());
        holder.siglaOficina.setText(os.getOficina().substring(0,1).toUpperCase());
//        holder.siglaOficina.setBackgroundColor(ContextCompat.getColor(activity, R.color.colorAmarelo));

        GradientDrawable shape = (GradientDrawable)holder.siglaOficina.getBackground();
//        shape.setColor(Color.parseColor(CorSigla.escolheCor(os.getOficina().substring(0,1).toUpperCase())));
        shape.setColor(Color.parseColor(CorSigla.escolheCor(os.getOficina())));
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
}
