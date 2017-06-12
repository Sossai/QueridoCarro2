package br.com.dev42.queridocarro.adapters;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.List;

import br.com.dev42.queridocarro.R;
import br.com.dev42.queridocarro.extra.CorSigla;
import br.com.dev42.queridocarro.interfaces.OficinaManagerInterface;
import br.com.dev42.queridocarro.model.Oficina;

/**
 * Created by sossai on 19/04/17.
 */

public class OficinaAdapter extends BaseAdapter{

    private List<Oficina.Retorno> listaOficinas;
    private Activity activity;
    private View layout;
    private ViewHolder holder;
    private Oficina.Retorno oficina;
    private OficinaManagerInterface oficinaManagerInterface;

    public OficinaAdapter(List<Oficina.Retorno> listaOficinas, Activity activity) {
        this.listaOficinas = listaOficinas;
        this.activity = activity;
        oficinaManagerInterface = (OficinaManagerInterface)activity;
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
    public View getView(final int position, View convertView, ViewGroup parent) {

        oficina = listaOficinas.get(position);
        final LayoutInflater inflater = activity.getLayoutInflater();

        if(convertView == null){
            layout = inflater.inflate(R.layout.list_oficinas,null);
            holder = new ViewHolder(layout);
            layout.setTag(holder);
        }else
        {
            layout = convertView;
            holder = (ViewHolder)layout.getTag();
        }

        //  Salvo a posicao do item clicado
        holder.optionOficina.setTag(position);


        holder.nomeOficina.setText(oficina.getOfNomeFan().trim());
        String enderecoCompleto = oficina.getOfEndere().trim() +
                "," + oficina.getOfNumero().toString() +
                " - " + oficina.getOfBairro().trim() +
                ", " + oficina.getOfCidade().trim() +
                " - " + oficina.getOfEstado().trim();

        if(oficina.getOfEnderecoEncontrado().isEmpty())
            holder.enderecoOficina.setText(enderecoCompleto);
        else
            holder.enderecoOficina.setText(oficina.getOfEnderecoEncontrado().replace(", Brazil",""));

        holder.siglaOficina.setText(oficina.getOfNomeFan().substring(0,1).toUpperCase());

        GradientDrawable shape = (GradientDrawable)holder.siglaOficina.getBackground();
        shape.setColor(Color.parseColor(CorSigla.escolheCor(oficina.getOfNomeFan())));
        holder.siglaOficina.setBackground(shape);

        String telefoneCompleto = "(" + oficina.getOfDdd().toString() + ") " +
                oficina.getOfTel().trim();
        holder.telefoneOficina.setText(telefoneCompleto);

        holder.optionOficina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(activity, v);
                popupMenu.getMenu().add(1,1,1,"Ligar");
                popupMenu.getMenu().add(1,2,1,"Mapa");

                final Oficina.Retorno oficinaClick = listaOficinas.get(position);

                if(oficinaClick.getServicos().size() > 0)
                    popupMenu.getMenu().add(1,3,1,"Serviços");

                popupMenu.show();
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case 1:
                                oficinaManagerInterface.ligar(oficinaClick.getOfDdd().toString() + oficinaClick.getOfTel().trim());
//                                oficinaManagerInterface.ligar(position);
                                break;
                            case 2:
                                oficinaManagerInterface.mostrarMapa(oficinaClick.getOfEnderecoEncontrado());
//                                oficinaManagerInterface.mostrarMapa(position);
                                break;
                            case 3:
                                oficinaManagerInterface.listarServicos(oficinaClick.getServicos());
//                                oficinaManagerInterface.listarServicos(position);
                                break;
                        }
                        return false;
                    }
                });
            }
        });

        return layout;
    }

    private class ViewHolder{
        Button siglaOficina;
        TextView nomeOficina;
        TextView enderecoOficina;
        TextView telefoneOficina;
        ImageView optionOficina;

        ViewHolder(View view){
            this.nomeOficina = (TextView)view.findViewById(R.id.nomeOficina);
            this.enderecoOficina = (TextView)view.findViewById(R.id.endereco_oficina);
            this.telefoneOficina = (TextView)view.findViewById(R.id.telefone_oficina);
            this.siglaOficina = (Button)view.findViewById(R.id.sigla_oficina);
            this.optionOficina = (ImageView)view.findViewById(R.id.iv_oficina_option);
        }
    }

}
