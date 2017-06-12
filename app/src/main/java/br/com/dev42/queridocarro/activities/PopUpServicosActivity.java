package br.com.dev42.queridocarro.activities;

import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import br.com.dev42.queridocarro.R;
import br.com.dev42.queridocarro.adapters.ServicoAdapter;
import br.com.dev42.queridocarro.model.Oficina;
import br.com.dev42.queridocarro.model.Servico;

public class PopUpServicosActivity extends DialogFragment {

    private View v;
    public static String TAG = PopUpLoastPassActivity.class.getSimpleName();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.activity_pop_up_servicos, container, false);

        //btn_lservicos_prestados_close

        FloatingActionButton btnClose = (FloatingActionButton)v.findViewById(R.id.btn_lservicos_prestados_close);

        Gson gson = new Gson();
//        List<Servico> mListServicos = gson.fromJson(getIntent().getStringExtra("OFICINA"), Oficina.Retorno.class);
        Bundle mArgs = getArguments();
        String myValue = mArgs.getString("SERVICOS");

        Type listaTipo = new TypeToken<ArrayList<Servico>>(){}.getType();
        List<Servico> mListServicos = gson.fromJson(myValue, listaTipo);

        RecyclerView.LayoutManager layoutManager;
        RecyclerView rvServico = (RecyclerView)v.findViewById(R.id.rv_pop_servico);
        rvServico.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getActivity());
        rvServico.setLayoutManager(layoutManager);

//        List<Servico> mListServicos = oficina.getServicos();
        ServicoAdapter adapter = new ServicoAdapter(getActivity(), mListServicos);
        rvServico.setAdapter(adapter);


        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        return v;
    }
}
