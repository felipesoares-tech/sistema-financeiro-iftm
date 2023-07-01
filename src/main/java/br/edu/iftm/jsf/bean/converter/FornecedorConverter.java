package br.edu.iftm.jsf.bean.converter;

import br.edu.iftm.jsf.entity.Fornecedor;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter(value = "fornecedorConverter")
public class FornecedorConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value != null && !value.isEmpty()) {
            return component.getAttributes().get(value);
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value instanceof Fornecedor) {
            Fornecedor fornecedor = (Fornecedor) value;
            String chave = "fornecedor_" + fornecedor.getId();
            component.getAttributes().put(chave, fornecedor);
            return chave;
        }
        return null;
    }
}