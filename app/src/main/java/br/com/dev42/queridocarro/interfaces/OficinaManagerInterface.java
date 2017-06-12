package br.com.dev42.queridocarro.interfaces;

import java.util.List;

import br.com.dev42.queridocarro.model.Servico;

/**
 * Created by sossai on 09/06/17.
 */

public interface OficinaManagerInterface {
    void ligar(String telefone);
//    void ligar(Integer position);
    void mostrarMapa(String endereco);
//    void mostrarMapa(Integer position);
    void listarServicos(List<Servico> servicos);
//    void listarServicos(Integer position);
}