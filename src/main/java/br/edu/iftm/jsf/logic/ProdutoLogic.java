package br.edu.iftm.jsf.logic;

import br.edu.iftm.jsf.dao.ProdutoDAO;
import br.edu.iftm.jsf.entity.Produto;
import br.edu.iftm.jsf.util.Transacional;
import br.edu.iftm.jsf.util.exception.ErroNegocioException;
import br.edu.iftm.jsf.util.exception.ErroSistemaException;
import java.io.ByteArrayInputStream;
import org.hibernate.Session;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.servlet.ServletContext;
import lombok.Getter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import org.hibernate.jdbc.Work;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.DefaultStreamedContent;

@Getter
public class ProdutoLogic implements GenericLogic<Produto> {

    @Inject
    private ProdutoDAO dao;

    @Inject
    private EntityManager manager;

    private Connection connection;

    private void setConnection(Connection connection) {
        this.connection = connection;
    }

    private Connection getConnection() {
        Session session = manager
                .getEntityManagerFactory()
                .createEntityManager()
                .unwrap(Session.class);
        session.doWork(new Work() {
            public void execute(Connection connection) throws SQLException {
                setConnection(connection);
            }
        });
        return this.connection;
    }

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

    private static JasperReport compileReport(String jrxmlFilePath) {
        JasperReport jasperReport = null;

        try {
            jasperReport = JasperCompileManager.compileReport(jrxmlFilePath);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return jasperReport;
    }

    public StreamedContent relatorioProduto() {
        Map<String, Object> parameters = new HashMap<>();
        JasperReport jasperReport = compileReport("resources/reports/produtos.jrxml");

        try {
            Connection connection = getConnection();
            // Preencher o relatório com os dados do banco de dados
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, connection);

            // Exportar o relatório para o formato PDF
            JasperExportManager.exportReportToPdfFile(jasperPrint, System.getProperty(""));

            System.out.println("Relatório gerado com sucesso: " + System.getProperty(""));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Produto buscarPorId(Long id) {
        return dao.buscarPorID(id);
    }
}
