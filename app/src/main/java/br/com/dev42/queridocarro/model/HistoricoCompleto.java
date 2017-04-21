package br.com.dev42.queridocarro.model;

import java.security.PrivateKey;
import java.util.List;

/**
 * Created by sossai on 12/04/17.
 */

public class HistoricoCompleto {

    public static class Envio{
        private Integer Id;
        private String Cnpj;
        private String Token;

        public Envio(Integer id, String cnpj, String token) {
            Id = id;
            Cnpj = cnpj;
            Token = token;
        }
    }
    public static class Retorno{
        private String Placa;
        private String Oficina;
        private Integer NumVenda;
        private String cpfCnpj;
        private String Data;
        private String Hora;

        private List<HistoricoItens> Itens;

        public String getPlaca() {
            return Placa;
        }

        public String getOficina() {
            return Oficina.trim();
        }

        public Integer getNumVenda() {
            return NumVenda;
        }

        public String getCpfCnpj() {
            return cpfCnpj;
        }

        public String getData() {
            return Data;
        }

        public String getHora() {
            return Hora;
        }

        public List<HistoricoItens> getItens() {
            return Itens;
        }
    }
}
