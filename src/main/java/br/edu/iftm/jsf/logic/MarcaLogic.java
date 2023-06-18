package br.edu.iftm.jsf.logic;

import br.edu.iftm.jsf.dao.MarcaDAO;
import br.edu.iftm.jsf.entity.Marca;
import br.edu.iftm.jsf.util.Transacional;
import br.edu.iftm.jsf.util.exception.ErroNegocioException;
import br.edu.iftm.jsf.util.exception.ErroSistemaException;
import java.util.List;
import javax.inject.Inject;

public class MarcaLogic implements GenericLogic<Marca>{

    @Inject
    private MarcaDAO dao;
    
    
    
    public List<Marca> listarTodas(){
        return dao.listar();
    }

    @Override
    @Transacional
    public Marca salvar(Marca entity) throws ErroNegocioException, ErroSistemaException {
       throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void remover(Marca entity) throws ErroNegocioException, ErroSistemaException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<Marca> listar() throws ErroNegocioException, ErroSistemaException {
        return dao.listar();
    }
    
}
