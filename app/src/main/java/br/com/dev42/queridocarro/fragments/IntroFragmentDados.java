package br.com.dev42.queridocarro.fragments;

import android.nfc.Tag;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

import br.com.dev42.queridocarro.R;
import br.com.dev42.queridocarro.interfaces.QueridoCarroInterface;
import br.com.dev42.queridocarro.model.Totalizadores;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class IntroFragmentDados extends Fragment {
    private View v;
    private TextView totLojas, totVeiculos, totOs, totItens, totDtAtualiza;
    private View frameLoad;
    private static final String TAG = "DEV42";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_intro_dados, container, false);

        totLojas = (TextView)v.findViewById(R.id.tot_lojas);
        totVeiculos = (TextView)v.findViewById(R.id.tot_veiculos);
        totOs = (TextView)v.findViewById(R.id.tot_os);
        totItens = (TextView)v.findViewById(R.id.tot_itens);
        totDtAtualiza = (TextView)v.findViewById(R.id.tot_dt_atualiza);
        frameLoad = (View)v.findViewById(R.id.frameload);

        getTotalizadores();

        return v;
    }

    private void getTotalizadores(){

        frameLoad.setVisibility(View.VISIBLE);

        QueridoCarroInterface service = new Retrofit.Builder()
                .baseUrl(QueridoCarroInterface.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(QueridoCarroInterface.class);

        Call<Totalizadores> tot = service.getTotalizadores();
        tot.enqueue(new Callback<Totalizadores>() {
            @Override
            public void onResponse(Call<Totalizadores> call, Response<Totalizadores> response) {
                frameLoad.setVisibility(View.GONE);
                if(!response.isSuccessful()){
                    Log.e(TAG, "Erro:" + response.code());

                }else
                {
                    DecimalFormat myFormatter = new DecimalFormat("#,###");

                    Totalizadores totalizadores = response.body();
                    totLojas.setText(myFormatter.format(totalizadores.getNumLojas()).replace(",", "."));
                    totVeiculos.setText(myFormatter.format(totalizadores.getNumVeiculos()).replace(",", "."));
                    totOs.setText(myFormatter.format(totalizadores.getNumVendas()).replace(",", "."));
                    totItens.setText(myFormatter.format(totalizadores.getNumItens()).replace(",", "."));
                    totDtAtualiza.setText(getString(R.string.label_data_atualiza_totalizadores, totalizadores.getDataAtualizacao()));

                }
            }

            @Override
            public void onFailure(Call<Totalizadores> call, Throwable t) {
                frameLoad.setVisibility(View.GONE);
                Log.e(TAG, "Erro.:" + t.getMessage());
            }
        });
    }
}
