package br.com.dev42.queridocarro.adapters;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

import br.com.dev42.queridocarro.R;
import br.com.dev42.queridocarro.extra.CorSigla;
import br.com.dev42.queridocarro.model.Placa;

/**
 * Created by sossai on 28/04/17.
 */

public class PlacaAdapter extends BaseAdapter {
    private List<Placa> listaPlacas;
    private Activity activity;
    private View layout;

    public PlacaAdapter(List<Placa> listaPlacas, Activity activity) {
        this.listaPlacas = listaPlacas;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return listaPlacas.size();
    }

    @Override
    public Object getItem(int position) {
        return listaPlacas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Placa placa = listaPlacas.get(position);
        LayoutInflater inflater = activity.getLayoutInflater();
        ViewHolder holder;

        if(convertView == null) {
            layout = inflater.inflate(R.layout.list_placas, null);
            holder = new ViewHolder(layout);
            layout.setTag(holder);
        }else {
            layout = convertView;
            holder = (ViewHolder)layout.getTag();
        }

        holder.placa.setText(placa.getPlaca());
        holder.siglaBtn.setText(placa.getPlaca().substring(0,1).toUpperCase());

        GradientDrawable shape = (GradientDrawable)holder.siglaBtn.getBackground();
        shape.setColor(Color.parseColor(CorSigla.escolheCor(placa.getPlaca())));
        holder.siglaBtn.setBackground(shape);

        return layout;
    }

    private class ViewHolder{
        private TextView placa;
        private Button siglaBtn;

        ViewHolder(View view){
            this.placa = (TextView)view.findViewById(R.id.placa);
            this.siglaBtn = (Button) view.findViewById(R.id.sigla_placa);
        }
    }
}
