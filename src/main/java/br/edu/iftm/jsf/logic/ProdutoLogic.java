package br.edu.iftm.jsf.logic;

import br.edu.iftm.jsf.dao.ProdutoDAO;
import br.edu.iftm.jsf.entity.Produto;
import br.edu.iftm.jsf.util.Transacional;
import br.edu.iftm.jsf.util.exception.ErroNegocioException;
import br.edu.iftm.jsf.util.exception.ErroSistemaException;
import java.util.List;
import javax.inject.Inject;
import lombok.Getter;


@Getter
public class ProdutoLogic implements GenericLogic<Produto> {

    @Inject
    private ProdutoDAO dao;    
    

    @Override
    @Transacional
    public Produto salvar(Produto entity) throws ErroNegocioException, ErroSistemaException {
        if ("".equals(entity.getNome())) {
            throw new ErroNegocioException("Por favor informe o nome");
        }
        if ("".equals(entity.getDescricao())) {
            throw new ErroNegocioException("Por favor informe a descrição");
        }
        if (entity.getValor() == null || entity.getValor().floatValue() <= 0f) {
            throw new ErroNegocioException("Por favor informe um valor maior que zero.");
        }
        if (entity.getDataFabricacao() == null) {
            throw new ErroNegocioException("Por favor informe a data de fabricação");
        }
        if (entity.getFornecedor() == null || entity.getFornecedor().getId() == null) {
            throw new ErroNegocioException("Por favor selecione um fornecedor");
        }
        if (entity.getMarca() == null || entity.getMarca().getId() == null) {
            throw new ErroNegocioException("Por favor selecione uma marca");
        }
        entity = dao.salvar(entity);

        return entity;
    }

    @Override
    @Transacional
    public void remover(Produto entity) throws ErroNegocioException, ErroSistemaException {
        dao.deletar(entity.getId());
    }

    @Override
    public List<Produto> listar() throws ErroNegocioException, ErroSistemaException {
        return dao.listar();
    }
}
