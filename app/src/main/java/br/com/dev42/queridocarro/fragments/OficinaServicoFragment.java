package br.com.dev42.queridocarro.fragments;


import android.app.Dialog;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.annotations.Until;

import java.util.List;

import br.com.dev42.queridocarro.R;
import br.com.dev42.queridocarro.adapters.ServicoAdapter;
import br.com.dev42.queridocarro.adapters.ServicoDialogAdapter;
import br.com.dev42.queridocarro.interfaces.QueridoCarroInterface;
import br.com.dev42.queridocarro.interfaces.ServicoSelecionadoInterface;
import br.com.dev42.queridocarro.model.Servico;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.support.v7.recyclerview.R.attr.layoutManager;

/**
 * A simple {@link Fragment} subclass.
 */
public class OficinaServicoFragment extends Fragment {

    private View v;
    private QueridoCarroInterface service;
    private ListView modeList;
    private Dialog dialog;
    private Servico servicoSelecionado;
    private ServicoSelecionadoInterface servicoSelecionadoInterface;
    private View frameLoad;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_oficina_servico, container, false);

        final EditText mServico = (EditText)v.findViewById(R.id.id_servico);
        final TextView mLimpaServico = (TextView)v.findViewById(R.id.clear_busca_servico);
        mLimpaServico.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);

        frameLoad = v.findViewById(R.id.frameload);

        service = new Retrofit.Builder()
                .baseUrl(QueridoCarroInterface.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(QueridoCarroInterface.class);

        //  **  Interface passa dados fragment serviço   **
        servicoSelecionadoInterface = (ServicoSelecionadoInterface) getActivity();
        servicoSelecionado = servicoSelecionadoInterface.getServicoSelecionado();
        if(servicoSelecionado != null) {
            mServico.setText(servicoSelecionado.getServDescri().trim());
            mLimpaServico.setVisibility(View.VISIBLE);
        }

        mServico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                frameLoad.setVisibility(View.VISIBLE);
                Call<List<Servico>> listaServico = service.getServicos();
                listaServico.enqueue(new Callback<List<Servico>>() {
                    @Override
                    public void onResponse(Call<List<Servico>> call, Response<List<Servico>> response) {
                        frameLoad.setVisibility(View.GONE);
                        if(!response.isSuccessful()){
                            Toast.makeText(getActivity(), "Não foi possível retornar a lista de serviços.", Toast.LENGTH_LONG).show();
                        }else {
                            if(!response.body().isEmpty()){

                                criaDialog(response.body());

                                modeList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                        servicoSelecionado = (Servico) modeList.getItemAtPosition(position);
                                        mServico.setText(servicoSelecionado.getServDescri().trim());

//                                        ServicoSelecionadoInterface servicoSelecionadoInterface = (ServicoSelecionadoInterface) getActivity();
                                        servicoSelecionadoInterface.setServicoSelecionado(servicoSelecionado);
                                        //GeolocationFragment geolocationFragment = (GeolocationFragment)getActivity();

                                        mLimpaServico.setVisibility(View.VISIBLE);
                                        dialog.dismiss();
                                    }
                                });

                            }else
                                Toast.makeText(getActivity(), "Não foram encontradas serviços.", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Servico>> call, Throwable t) {
                        frameLoad.setVisibility(View.GONE);
                        Toast.makeText(getActivity(), "Não foi possível retornar a lista de serviços.", Toast.LENGTH_LONG).show();
                    }
                });
                //criaDialog(listaServico);
            }
        });

        mLimpaServico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mServico.setText("");
                servicoSelecionado = null;
                servicoSelecionadoInterface.setServicoSelecionado(servicoSelecionado);
                mLimpaServico.setVisibility(View.GONE);
            }
        });
        return v;
    }

    private void criaDialog(List<Servico> lista){

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Selecione:");
        modeList = new ListView(getActivity());

        ServicoDialogAdapter adapter = new ServicoDialogAdapter(lista, getActivity());
        modeList.setAdapter(adapter);

        builder.setView(modeList);
        dialog = builder.create();
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
    }

}
