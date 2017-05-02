package br.com.dev42.queridocarro.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

import br.com.dev42.queridocarro.model.Estado;

/**
 * Created by sossai on 25/04/17.
 */

public class EstadoAdapter extends BaseAdapter {
    private List<Estado> estados;
    private Activity activity;

    public EstadoAdapter(Activity activity, List<Estado> estados) {
        this.estados = estados;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return estados.size();
    }

    @Override
    public Object getItem(int position) {
        return estados.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = activity.getLayoutInflater();
        Estado estado = estados.get(position);

        return null;
    }
}
