package br.com.dev42.queridocarro.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import br.com.dev42.queridocarro.R;
import br.com.dev42.queridocarro.model.Servico;

/**
 * Created by sossai on 25/05/17.
 */

public class ServicoDialogAdapter extends BaseAdapter {
    private List<Servico> listaServico;
    private Activity activity;
    private View layout;

    public ServicoDialogAdapter(List<Servico> listaServico, Activity activity) {
        this.listaServico = listaServico;
        this.activity = activity;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Servico servico = listaServico.get(position);
        LayoutInflater inflater = activity.getLayoutInflater();

        ViewHolder holder;
        if(convertView == null){
            layout = inflater.inflate(R.layout.list_simple,parent, false);
            holder = new ServicoDialogAdapter.ViewHolder(layout);
            layout.setTag(holder);
        }else {
            layout = convertView;
            holder = (ServicoDialogAdapter.ViewHolder)layout.getTag();
        }

        holder.text1.setText(servico.getServDescri());



        return layout;
    }

    @Override
    public int getCount() {
        return listaServico.size();
    }

    @Override
    public Object getItem(int position) {
        return listaServico.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private class ViewHolder{
        TextView text1;
        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, lista);
        ViewHolder(View view){
            this.text1 = (TextView)view.findViewById(R.id.text_simple);
        }
    }

}
