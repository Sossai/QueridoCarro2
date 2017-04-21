package br.com.dev42.queridocarro.model;

/**
 * Created by sossai on 19/04/17.
 */

public class Oficina {
    public static class Envio{
        private Double Latitude;
        private Double Longitude;
        private String Endereco;
        private String Cep;
        private Integer Distancia;
        private Integer QuantidadeOficinas;

        public Envio(Double latitude, Double longitude, String endereco, String cep, Integer distancia, Integer quantidadeOficinas) {
            Latitude = latitude;
            Longitude = longitude;
            Endereco = endereco;
            Cep = cep;
            Distancia = distancia;
            QuantidadeOficinas = quantidadeOficinas;
        }

        @Override
        public String toString() {
            return "Envio{" +
                    "Latitude='" + Latitude + '\'' +
                    ", Longitude='" + Longitude + '\'' +
                    ", Endereco='" + Endereco + '\'' +
                    ", Cep=" + Cep +
                    ", Distancia=" + Distancia +
                    ", QuantidadeOficinas=" + QuantidadeOficinas +
                    '}';
        }
    }
    public static class Retorno{
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
    }
}
