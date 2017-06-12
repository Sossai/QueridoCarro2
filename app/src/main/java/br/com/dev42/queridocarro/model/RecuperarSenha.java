package br.com.dev42.queridocarro.model;

/**
 * Created by sossai on 30/05/17.
 */

public class RecuperarSenha {

    public static class Envio{
        private String Hash;
        private String Email;
        private String Placa;
        private String cpf;

        public Envio(String hash, String email, String placa, String cpf) {
            Hash = hash;
            Email = email;
            Placa = placa;
            this.cpf = cpf;
        }

        @Override
        public String toString() {
            return "Envio{" +
                    "Hash='" + Hash + '\'' +
                    ", Email='" + Email + '\'' +
                    ", Placa='" + Placa + '\'' +
                    ", cpf='" + cpf + '\'' +
                    '}';
        }
    }
    public static class Retorno{
        private String Senha;
        private String Mensagem;

        public String getSenha() {
            return Senha;
        }

        public String getMensagem() {
            return Mensagem;
        }
    }
}
