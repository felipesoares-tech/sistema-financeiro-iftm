package br.edu.iftm.jsf.bean;

import br.edu.iftm.jsf.entity.Marca;
import br.edu.iftm.jsf.entity.Permissao;
import br.edu.iftm.jsf.logic.MarcaLogic;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@SessionScoped
public class MarcaBean extends GenericBean<Marca, MarcaLogic>{

    @Inject
    private MarcaLogic logic;
    
    @Override
    public MarcaLogic getLogic() {
        return logic;
    }
    
    @Inject
    private MarcaLogic marcaLogic;
    
    private List<Marca> marcaList;

    @Override
    public Class<Marca> getClassEntity() {
        return Marca.class;
    }
    
    public List<Marca> getMarcaList() {
        if(marcaList == null) {
            marcaList = marcaLogic.listarTodas();
        }
        return marcaList;
    }
    
}
