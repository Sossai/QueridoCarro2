package br.com.dev42.queridocarro.interfaces;

import java.util.List;

import br.com.dev42.queridocarro.model.Historico;
import br.com.dev42.queridocarro.model.Token;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by sossai on 06/04/17.
 */

public interface QueridoCarroInterface {
    public static final String BASE_URL = "http://201.77.177.42/";

    @POST("/qcwebapi/acesso")
    Call<Token.Retorno> getToken(@Body Token.Envio tokenEnvio);

    @POST("/qcwebapi/Historico")
    Call<List<Historico.Retorno>> getHistorico(@Body Historico.Envio historicoEnvio);
}
