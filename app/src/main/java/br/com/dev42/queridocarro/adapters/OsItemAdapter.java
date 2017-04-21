package br.com.dev42.queridocarro.adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import br.com.dev42.queridocarro.R;
import br.com.dev42.queridocarro.model.HistoricoItens;

/**
 * Created by sossai on 13/04/17.
 */

public class OsItemAdapter extends BaseAdapter {
    private List<HistoricoItens> listaItens;
    private Activity activity;
    private View layout;

    public OsItemAdapter(List<HistoricoItens> listaItens, Activity activity) {
        this.listaItens = listaItens;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return listaItens.size();
    }

    @Override
    public Object getItem(int position) {
        return listaItens.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        HistoricoItens item = listaItens.get(position);
        LayoutInflater inflater =  activity.getLayoutInflater();
        ViewHolder holder;

        if(convertView == null){
            layout = inflater  .inflate(R.layout.list_os_itens,parent,false);
            holder = new ViewHolder(layout);
            layout.setTag(holder);
        }else {
            layout = convertView;
            holder = (ViewHolder)layout.getTag();
        }

        holder.descricao.setText(item.getDescricao());
        holder.quantidade.setText(item.getQuantidade().toString());

        if(item.getGarantiaAte().isEmpty())
            holder.dataGarantia.setText("  /  /  ");
        else
            holder.dataGarantia.setText(item.getGarantiaAte());

        if(item.getDataAprovacaoItem().isEmpty())
            holder.dataRevisão.setText("  /  /  ");
        else
            holder.dataRevisão.setText(item.getDataAprovacaoItem());

//        holder.dataGarantia.setText("01/01/01");
//        holder.dataRevisão.setText("01/01/01");


        return layout;
    }

    private class ViewHolder{
        TextView descricao;
        TextView quantidade;
        TextView dataGarantia;
        TextView dataRevisão;

        ViewHolder(View view) {
            this.descricao = (TextView)view.findViewById(R.id.itens_descricao);
            this.quantidade = (TextView)view.findViewById(R.id.itens_qtd);
            this.dataGarantia = (TextView)view.findViewById(R.id.item_garantia);
            this.dataRevisão = (TextView)view.findViewById(R.id.item_revisao);
        }
    }
}
