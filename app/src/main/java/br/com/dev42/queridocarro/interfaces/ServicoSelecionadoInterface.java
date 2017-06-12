package br.com.dev42.queridocarro.interfaces;

import br.com.dev42.queridocarro.model.Servico;

/**
 * Created by sossai on 26/05/17.
 */

public interface ServicoSelecionadoInterface {
    public void setServicoSelecionado(Servico servico);
    public Servico getServicoSelecionado();
}
