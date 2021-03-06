package br.com.dev42.queridocarro.model;

import java.util.Date;

/**
 * Created by sossai on 06/04/17.
 */

public class Historico {

    public static class Envio{
        private String Placa;
        private String Dt;
        private Integer Quantidade;
        private String Token;

        public Envio(String placa, String dt, Integer quantidade, String token) {
            Placa = placa;
            Dt = dt;
            Quantidade = quantidade;
            Token = token;
        }

        @Override
        public String toString() {
            return "Envio{" +
                    "Placa='" + Placa + '\'' +
                    ", Dt='" + Dt + '\'' +
                    ", Quantidade=" + Quantidade +
                    ", Token='" + Token + '\'' +
                    '}';
        }
    }
    public static class Retorno{
        /*
              "Placa": "FIR3997 ",
      "Carro": "FIT 1.4 AUTOM.                                     2014",
      "Oficina": "TACACHI COMERCIO DE PECAS E ACESSORIOS LTDA - ME                                ",
      "NumVenda": 2420,
      "cpfCnpj": "02873825000146     ",
      "Km": 59676,
      "KmRevisao": 0,
      "DataRevisao": null,
      "Data": "2016-11-28T00:00:00",
      "Hora": "16:04",
      "Comprador": "JENIFER MARQUES VICENTE                 ",
      "Total": 240,
         */
        private String Placa;
        private String Oficina;

        private Integer NumVenda;
        private String cpfCnpj;
        private String Km;
        private String KmRevisao;
        private String Data;
        private String Hora;
        private Integer Ddd;
        private String Telefone;
        private String EnderecoEncontrado;

        public String getPlaca() {
            return Placa;
        }

        public String getOficina() {
            return Oficina;
        }

        public Integer getNumVenda() {
            return NumVenda;
        }

        public String getCpfCnpj() {
            return cpfCnpj;
        }

        public String getKm() {
            return Km;
        }

        public String getKmRevisao() {
            return KmRevisao;
        }

        public String getData() {
            return Data;
        }

        public String getHora() {
            return Hora;
        }

        public Integer getDdd() {
            return Ddd;
        }

        public String getTelefone() {
            return Telefone;
        }

        public String getEnderecoEncontrado() {
            return EnderecoEncontrado;
        }
    }
}
