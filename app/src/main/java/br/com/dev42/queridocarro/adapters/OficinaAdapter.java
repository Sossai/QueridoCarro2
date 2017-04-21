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
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

import br.com.dev42.queridocarro.R;
import br.com.dev42.queridocarro.extra.CorSigla;
import br.com.dev42.queridocarro.model.Oficina;

/**
 * Created by sossai on 19/04/17.
 */

public class OficinaAdapter extends BaseAdapter {

    private List<Oficina.Retorno> listaOficinas;
    private Activity activity;
    private View layout;

    public OficinaAdapter(List<Oficina.Retorno> listaOficinas, Activity activity) {
        this.listaOficinas = listaOficinas;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return listaOficinas.size();
    }

    @Override
    public Object getItem(int position) {
        return listaOficinas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Oficina.Retorno oficina = listaOficinas.get(position);
        LayoutInflater inflater = activity.getLayoutInflater();
        ViewHolder holder;

        if(convertView == null){
            layout = inflater.inflate(R.layout.list_oficinas,null);
            holder = new ViewHolder(layout);
            layout.setTag(holder);
        }else
        {
            layout = convertView;
            holder = (ViewHolder)layout.getTag();
        }

        holder.nomeOficina.setText(oficina.getOfNomeFan().trim());
        String enderecoCompleto = oficina.getOfEndere().trim() +
                "," + oficina.getOfNumero().toString() +
                " - " + oficina.getOfBairro().trim() +
                ", " + oficina.getOfCidade().trim() +
                " - " + oficina.getOfEstado().trim();
        holder.enderecoOficina.setText(enderecoCompleto);

        holder.siglaOficina.setText(oficina.getOfNomeFan().substring(0,1).toUpperCase());

        GradientDrawable shape = (GradientDrawable)holder.siglaOficina.getBackground();
        shape.setColor(Color.parseColor(CorSigla.escolheCor(oficina.getOfNomeFan())));
        holder.siglaOficina.setBackground(shape);

        String telefoneCompleto = "(" + oficina.getOfDdd().toString() + ") " +
                oficina.getOfTel().trim();
        holder.telefoneOficina.setText(telefoneCompleto);

        return layout;
    }

    private class ViewHolder{
        Button siglaOficina;
        TextView nomeOficina;
        TextView enderecoOficina;
        TextView telefoneOficina;

        ViewHolder(View view){
            this.nomeOficina = (TextView)view.findViewById(R.id.nomeOficina);
            this.enderecoOficina = (TextView)view.findViewById(R.id.endereco_oficina);
            this.telefoneOficina = (TextView)view.findViewById(R.id.telefone_oficina);
            this.siglaOficina = (Button)view.findViewById(R.id.sigla_oficina);
        }
    }
}
