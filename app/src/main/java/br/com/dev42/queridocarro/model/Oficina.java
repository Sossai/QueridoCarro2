package br.com.dev42.queridocarro.model;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;

/**
 * Created by sossai on 19/04/17.
 */

public class Oficina{

    public static class Envio{
        private Double Latitude;
        private Double Longitude;
        private String Endereco;
        private String Cep;
        private Integer Distancia;
        private Integer QuantidadeOficinas;
        private String Cnpj;
        private Integer Tipo;
        private String Oficina;
        private List<Integer> Servicos;

        public Envio(Double latitude, Double longitude, String endereco, String cep, Integer distancia, Integer quantidadeOficinas, String cnpj, Integer tipo, String oficina, List<Integer> servicos) {
            Latitude = latitude;
            Longitude = longitude;
            Endereco = endereco;
            Cep = cep;
            Distancia = distancia;
            QuantidadeOficinas = quantidadeOficinas;
            Cnpj = cnpj;
            Tipo = tipo;
            Oficina = oficina;
            Servicos = servicos;
        }

        @Override
        public String toString() {
            return "Envio{" +
                    "Latitude=" + Latitude +
                    ", Longitude=" + Longitude +
                    ", Endereco='" + Endereco + '\'' +
                    ", Cep='" + Cep + '\'' +
                    ", Distancia=" + Distancia +
                    ", QuantidadeOficinas=" + QuantidadeOficinas +
                    ", Cnpj='" + Cnpj + '\'' +
                    ", Tipo=" + Tipo +
                    ", Oficina='" + Oficina + '\'' +
                    ", Servicos=" + Servicos +
                    '}';
        }
    }
    public static class Retorno implements Serializable{
        private String OfCnpjCpf;
        private String OfNomeFan;
        private String OfEstado;
        private String OfCidade;
        private Integer OfDdd;
        private String OfTel;
        private String OfBairro;
        private String OfEndere;
        private Integer OfNumero;
        private String OfLatitude;
        private String OfLongitude;
        private String OfEnderecoEncontrado;
        private String OfEmail;
        //  * transient não entra no Serializable
        private List<Servico> Servicos;

        public String getOfEmail() {
            return OfEmail;
        }

        public String getOfCnpjCpf() {
            return OfCnpjCpf;
        }

        public String getOfNomeFan() {
            return OfNomeFan;
        }

        public String getOfEstado() {
            return OfEstado;
        }

        public String getOfCidade() {
            return OfCidade;
        }

        public Integer getOfDdd() {
            return OfDdd;
        }

        public String getOfTel() {
            return OfTel;
        }

        public String getOfBairro() {
            return OfBairro;
        }

        public String getOfEndere() {
            return OfEndere;
        }

        public Integer getOfNumero() {
            return OfNumero;
        }

        public String getOfLatitude() {
            return OfLatitude;
        }

        public String getOfLongitude() {
            return OfLongitude;
        }

        public String getOfEnderecoEncontrado() {
            return OfEnderecoEncontrado;
        }

        public List<Servico> getServicos() {
            return Servicos;
        }

        @Override
        public String toString() {
            return "Retorno{" +
                    "OfCnpjCpf='" + OfCnpjCpf + '\'' +
                    ", OfNomeFan='" + OfNomeFan + '\'' +
                    ", OfEstado='" + OfEstado + '\'' +
                    ", OfCidade='" + OfCidade + '\'' +
                    ", OfDdd=" + OfDdd +
                    ", OfTel='" + OfTel + '\'' +
                    ", OfBairro='" + OfBairro + '\'' +
                    ", OfEndere='" + OfEndere + '\'' +
                    ", OfNumero=" + OfNumero +
                    ", OfLatitude='" + OfLatitude + '\'' +
                    ", OfLongitude='" + OfLongitude + '\'' +
                    ", OfEnderecoEncontrado='" + OfEnderecoEncontrado + '\'' +
                    ", OfEmail='" + OfEmail + '\'' +
                    '}';
        }
    }
}
