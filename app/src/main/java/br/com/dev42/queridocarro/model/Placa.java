package br.com.dev42.queridocarro.model;

/**
 * Created by sossai on 27/04/17.
 */

public class Placa {
    private Integer id;
    private String placa;
    private String codigoAcesso;
    private Integer quantidadeOs;

    public Placa() {
    }

    public Placa(String placa, String codigoAcesso) {
        this.placa = placa;
        this.codigoAcesso = codigoAcesso;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getCodigoAcesso() {
        return codigoAcesso;
    }

    public void setCodigoAcesso(String codigoAcesso) {
        this.codigoAcesso = codigoAcesso;
    }

    public Integer getQuantidadeOs() {
        return quantidadeOs;
    }

    public void setQuantidadeOs(Integer quantidadeOs) {
        this.quantidadeOs = quantidadeOs;
    }
}
