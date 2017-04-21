package br.com.dev42.queridocarro.model;

/**
 * Created by sossai on 13/04/17.
 */

public class HistoricoItens {
    private Integer Orcodigo;
    private Integer Ocitem;
    private String Procodigo;
    private String Marca;
    private String Descricao;
    private String ProximaRevisao;
    private String GarantiaAte;
    private Integer Quantidade;
    private String DataAprovacaoItem;
    private Integer TipoItem;

    public Integer getOrcodigo() {
        return Orcodigo;
    }

    public Integer getOcitem() {
        return Ocitem;
    }

    public String getProcodigo() {
        return Procodigo;
    }

    public String getMarca() {
        return Marca;
    }

    public String getDescricao() {
        return Descricao;
    }

    public String getProximaRevisao() {
        return ProximaRevisao;
    }

    public String getGarantiaAte() {
        return GarantiaAte;
    }

    public Integer getQuantidade() {
        return Quantidade;
    }

    public String getDataAprovacaoItem() {
        return DataAprovacaoItem;
    }

    public Integer getTipoItem() {
        return TipoItem;
    }
}
