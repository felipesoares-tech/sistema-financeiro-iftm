package br.edu.iftm.jsf.logic;

import br.edu.iftm.jsf.dao.FornecedorDAO;
import br.edu.iftm.jsf.entity.Fornecedor;
import br.edu.iftm.jsf.util.Transacional;
import br.edu.iftm.jsf.util.exception.ErroNegocioException;
import br.edu.iftm.jsf.util.exception.ErroSistemaException;
import java.util.List;
import javax.inject.Inject;

public class FornecedorLogic implements GenericLogic<Fornecedor>{

    @Inject
    private FornecedorDAO dao;
       
    @Override
    @Transacional
    public Fornecedor salvar(Fornecedor entity) throws ErroNegocioException, ErroSistemaException {
       return dao.salvar(entity);
    }

    @Override
    public void remover(Fornecedor entity) throws ErroNegocioException, ErroSistemaException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<Fornecedor> listar() throws ErroNegocioException, ErroSistemaException {
        return dao.listar();
    }
    
}
