package br.com.dev42.queridocarro.model;

/**
 * Created by sossai on 06/04/17.
 */

public class Token {

    public static class Envio {
        private String Placa;
        private String Senha;

        public Envio(String placa, String senha) {
            Placa = placa;
            Senha = senha;
        }
    }
    public static class Retorno {
        private String TokenHash;
        private String Erro;

        public String getTokenHash() {
            return TokenHash;
        }

        public String getErro() {
            return Erro;
        }
    }
}


