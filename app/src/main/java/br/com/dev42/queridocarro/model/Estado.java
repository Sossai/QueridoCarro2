package br.com.dev42.queridocarro.model;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by sossai on 25/04/17.
 */

public class Estado {
    private String OfEstado;
    private String EstDescri;
//    private List<String> OfCidades;

    public String getOfEstado() {
        return OfEstado;
    }

    public String getEstDescri() {
        return EstDescri;
    }

    public static LinkedHashMap<String, String> listaEstados(){

        LinkedHashMap<String,String> estados = new LinkedHashMap<>();

        estados.put("Acre".toUpperCase(),"AC");
        estados.put("Alagoas".toUpperCase(),"AL");
        estados.put("Amapá".toUpperCase(),"AP");
        estados.put("Amazonas".toUpperCase(),"AM");
        estados.put("Bahia".toUpperCase(),"BA");
        estados.put("Ceará".toUpperCase(),"CE");
        estados.put("Distrito Federal".toUpperCase(),"DF");
        estados.put("Espírito Santo".toUpperCase(),"ES");
        estados.put("Goiás".toUpperCase(),"GO");
        estados.put("Maranhão".toUpperCase(),"MA");
        estados.put("Mato Grosso".toUpperCase(),"MT");
        estados.put("Mato Grosso do Sul".toUpperCase(),"MS");
        estados.put("Minas Gerais".toUpperCase(),"MG");
        estados.put("Pará".toUpperCase(),"PA");
        estados.put("Paraíba".toUpperCase(),"PB");
        estados.put("Paraná".toUpperCase(),"PR");
        estados.put("Pernambuco".toUpperCase(),"PE");
        estados.put("Piauí".toUpperCase(),"PI");
        estados.put("Rio de Janeiro".toUpperCase(),"RJ");
        estados.put("Rio Grande do Norte".toUpperCase(),"RN");
        estados.put("Rio Grande do Sul".toUpperCase(),"RS");
        estados.put("Rondônia".toUpperCase(),"RO");
        estados.put("Roraima".toUpperCase(),"RR");
        estados.put("Santa Catarina".toUpperCase(),"SC");
        estados.put("São Paulo".toUpperCase(),"SP");
        estados.put("Sergipe".toUpperCase(),"SE");
        estados.put("Tocantins".toUpperCase(),"TO");

        return estados;
    }

}
