package br.com.dev42.queridocarro.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import br.com.dev42.queridocarro.R;
import br.com.dev42.queridocarro.model.Servico;

/**
 * Created by sossai on 12/05/17.
 */

public class ServicoAdapter extends RecyclerView.Adapter<ServicoAdapter.MyViewHolder> {

//    private String[] mDataset;
    private List<Servico>  mlist;
    private LayoutInflater mlayoutInflater;

    public ServicoAdapter(Context c, List<Servico> mlist) {
        this.mlist = mlist;
        mlayoutInflater = (LayoutInflater)c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = mlayoutInflater.inflate(R.layout.list_servicos, parent , false);
        //View v = mlayoutInflater.inflate(android.R.layout.simple_list_item_1, parent , false);

        MyViewHolder mvh = new MyViewHolder(v);
        return mvh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder myViewHolder, int position) {
        myViewHolder.tvServico.setText(mlist.get(position).getServDescri());

    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public ImageView ivImgServico;
        public TextView tvServico;

        public MyViewHolder(View itemView) {
            super(itemView);

            ivImgServico = (ImageView)itemView.findViewById(R.id.iv_check_servico);
            tvServico = (TextView) itemView.findViewById(R.id.tv_servico);
        }
    }

}
