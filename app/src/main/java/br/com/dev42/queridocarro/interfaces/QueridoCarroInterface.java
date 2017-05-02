package br.com.dev42.queridocarro.interfaces;

import java.util.List;

import br.com.dev42.queridocarro.model.Estado;
import br.com.dev42.queridocarro.model.Historico;
import br.com.dev42.queridocarro.model.HistoricoCompleto;
import br.com.dev42.queridocarro.model.Oficina;
import br.com.dev42.queridocarro.model.Token;
import br.com.dev42.queridocarro.model.Totalizadores;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by sossai on 06/04/17.
 */

public interface QueridoCarroInterface {
    public static final String BASE_URL = "http://201.77.177.42/";

    @POST("/qcwebapi/acesso")
    Call<Token.Retorno> getToken(@Body Token.Envio tokenEnvio);

    @POST("/qcwebapi/HistoricoMobile")
    Call<List<Historico.Retorno>> getHistorico(@Body Historico.Envio historicoEnvio);

    @POST("/qcwebapi/venda")
    Call<HistoricoCompleto.Retorno> getHistoricoCompleto(@Body HistoricoCompleto.Envio historicoEnvio);

    @GET("/qcwebapi/Totalizadores")
    Call<Totalizadores> getTotalizadores();

    @POST("/qcwebapi/OficinaProxima")
    Call<List<Oficina.Retorno>> getOficinasProximas(@Body Oficina.Envio oficinaEnvio);

    @GET("/qcwebapi/estado")
    Call<List<Estado>> getEstados();

    @GET("/qcwebapi/estado/{siglaEstado}")
    Call<List<String>> getCidades(@Path("siglaEstado") String siglaEstado);

    /*
    @GET("users/{username}")
    Call<User> getUser(@Path("username") String username);
    */
}
