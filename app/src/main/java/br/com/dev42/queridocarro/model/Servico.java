package br.com.dev42.queridocarro.model;

/**
 * Created by sossai on 25/05/17.
 */

public class Servico {
    private Integer ServCodigo;
    private String ServDescri;

    public Servico() {
    }

    public Servico(Integer servCodigo, String servDescri) {
        ServCodigo = servCodigo;
        ServDescri = servDescri;
    }

    public Integer getServCodigo() {
        return ServCodigo;
    }

    public String getServDescri() {
        return ServDescri;
    }

    public void setServCodigo(Integer servCodigo) {
        ServCodigo = servCodigo;
    }

    public void setServDescri(String servDescri) {
        ServDescri = servDescri;
    }
}
